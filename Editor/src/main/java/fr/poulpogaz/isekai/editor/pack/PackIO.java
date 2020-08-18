package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.AnimatedSprite;
import fr.poulpogaz.isekai.editor.pack.image.Sprite;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.json.IJsonReader;
import fr.poulpogaz.json.JsonException;
import fr.poulpogaz.json.JsonReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PackIO {

    public static Pack deserialize(Path path) throws IOException, JsonException {
        if (!Utils.checkExtension(path, "skb")) {
            throw new IOException();
        }

        FileSystem system = FileSystems.newFileSystem(path);

        Pack pack = new Pack();
        readSettingsFile(pack, system.getPath("settings.json"));
        readMapFile(pack, system);
        readPlayerFile(pack, system);
        pack.setLevels(readLevels(system.getPath("levels/")));

        system.close();

        return pack;
    }

    private static void readSettingsFile(Pack pack, Path settingsPath) throws IOException, JsonException {
        IJsonReader reader = new JsonReader(Files.newBufferedReader(settingsPath));

        reader.beginObject();
        while (!reader.isObjectEnd()) {
            if (reader.hasNextKey()) {
                String key = reader.nextKey();

                switch (key) {
                    case "name" -> pack.setPackName(reader.nextString());
                    case "author" -> pack.setAuthor(reader.nextString());
                    case "version" -> pack.setVersion(reader.nextString());
                    case "game_width" -> pack.setGameWidth(reader.nextInt());
                    case "game_height" -> pack.setGameHeight(reader.nextInt());
                    case "tile_width" -> pack.setTileWidth(reader.nextInt());
                    case "tile_height" -> pack.setTileHeight(reader.nextInt());
                    case "main_menu", "timeline" -> reader.skipValue();
                    default -> throw new IllegalStateException(key);
                }
            }
        }
        reader.endObject();
        reader.close();
    }

    private static void readMapFile(Pack pack, FileSystem system) throws IOException, JsonException {
        IJsonReader reader = new JsonReader(Files.newBufferedReader(system.getPath("map.json")));

        reader.beginObject();

        while (!reader.isObjectEnd()) {
            if (reader.hasNextKey()) {
                String key = reader.nextKey();

                if (!"wall".equals(key) && !"crate".equals(key) && !"target".equals(key) && !"crate_on_target".equals(key) && !"floor".equals(key)) {
                    throw new IllegalStateException();
                }

                reader.beginObject();
                AbstractSprite sprite = parseSprite(pack, reader, key, system, true);
                reader.endObject();

                pack.putSprite(sprite);
            }
        }

        reader.endObject();
        reader.close();
    }

    private static void readPlayerFile(Pack pack, FileSystem system) throws IOException, JsonException {
        IJsonReader reader = new JsonReader(Files.newBufferedReader(system.getPath("player.json")));

        reader.beginObject();

        while (!reader.isObjectEnd()) {
            if (reader.hasNextKey()) {
                String key = reader.nextKey();

                if (!"left".equals(key) && !"right".equals(key) && !"up".equals(key) && !"down".equals(key)) {
                    throw new IllegalStateException(key);
                }

                reader.beginObject();

                for (int i = 0; i < 2; i++) {
                    String key2 = reader.nextKey();

                    reader.beginObject();

                    AbstractSprite sprite = parseSprite(pack, reader, key + "_" + key2, system, true);

                    pack.putSprite(sprite);

                    reader.endObject();
                }

                reader.endObject();
            }
        }

        reader.endObject();
        reader.close();
    }

    private static AbstractSprite parseSprite(Pack pack, IJsonReader reader, String name, FileSystem system, boolean acceptAnimatedSprite) throws IOException, JsonException {
        String type = reader.skipKey().nextString();

        switch (type) {
            case "sprite" -> {
                String image = reader.skipKey().nextString();

                return new Sprite(name, loadIfNeeded(image, pack, system));
            }
            case "sub_sprite" -> {
                String image = reader.skipKey().nextString();

                int x = reader.skipKey().nextInt() * pack.getTileWidth();
                int y = reader.skipKey().nextInt() * pack.getTileHeight();

                return new SubSprite(name, loadIfNeeded(image, pack, system), x, y, pack.getTileWidth(), pack.getTileWidth());
            }
            case "animated_sprite" -> {
                if (acceptAnimatedSprite) {
                    int delay = reader.skipKey().nextInt();

                    AnimatedSprite sprite = new AnimatedSprite(name, delay);

                    reader.skipKey().beginArray(); // frames

                    int i = 0;
                    while (!reader.isArrayEnd()) {
                        reader.beginObject();

                        String frameName = String.format("%s_%d", name, i);

                        sprite.addFrame(parseSprite(pack, reader, frameName, system, false));

                        reader.endObject();
                        i++;
                    }

                    reader.endArray();

                    return sprite;
                } else {
                    throw new IllegalStateException();
                }
            }
            default -> throw new IllegalStateException(type);
        }
    }

    private static BufferedImage loadIfNeeded(String image, Pack pack, FileSystem system) throws IOException {
        BufferedImage img = pack.getImage(image);

        if (img == null) {
            Path path = system.getPath(String.format("sprites/%s.png", image));

            img = ImageIO.read(Files.newInputStream(path));
            pack.putImage(image, img);
        }

        return img;
    }

    private static ArrayList<Level> readLevels(Path levelDirectory) throws IOException {
        List<Path> levelsPaths;

        try (Stream<Path> levelPath = Files.walk(levelDirectory)) {
            levelsPaths = levelPath.collect(Collectors.toList());
        }

        ArrayList<Level> levels = new ArrayList<>();
        for (Path levelPath : levelsPaths) {
            if (Files.isDirectory(levelPath)) {
                continue;
            }

            BufferedReader br = Files.newBufferedReader(levelPath);

            int width = Integer.parseInt(br.readLine());
            int height = Integer.parseInt(br.readLine());

            Level level = new Level(width, height);

            for (int y = 0; y < height; y++) {
                String line = br.readLine();

                if (line.length() > width) {
                    throw new IOException();
                }

                for (int x = 0; x < width; x++) {
                    Tile tile;

                    if (line.length() <= x) {
                        tile = Tile.WALL;
                    } else {
                        char c = line.charAt(x);

                        tile = Tile.of(c);

                        if (c == '@') {
                            level.setPlayer(x, y);
                        }
                    }

                    level.setTile(x, y, tile);
                }
            }

            br.close();
        }

        return levels;
    }
}