package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.AnimatedSprite;
import fr.poulpogaz.isekai.editor.pack.image.Sprite;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.json.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                AbstractSprite sprite = parseSprite(pack, reader, system, true);
                reader.endObject();

                pack.putSprite(key, sprite);
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

                    AbstractSprite sprite = parseSprite(pack, reader, system, true);

                    pack.putSprite(key + "_" + key2, sprite);

                    reader.endObject();
                }

                reader.endObject();
            }
        }

        reader.endObject();
        reader.close();
    }

    private static AbstractSprite parseSprite(Pack pack, IJsonReader reader, FileSystem system, boolean acceptAnimatedSprite) throws IOException, JsonException {
        String type = reader.skipKey().nextString();

        switch (type) {
            case "sprite" -> {
                String image = reader.skipKey().nextString();
                loadIfNeeded(image, pack, system);

                return new Sprite(pack, image);
            }
            case "sub_sprite" -> {
                String image = reader.skipKey().nextString();
                loadIfNeeded(image, pack, system);

                int x = reader.skipKey().nextInt() * pack.getTileWidth();
                int y = reader.skipKey().nextInt() * pack.getTileHeight();

                return new SubSprite(pack, image, x, y, pack.getTileWidth(), pack.getTileWidth());
            }
            case "animated_sprite" -> {
                if (acceptAnimatedSprite) {
                    int delay = reader.skipKey().nextInt();

                    AnimatedSprite sprite = new AnimatedSprite(pack, delay);

                    reader.skipKey().beginArray(); // frames

                    while (!reader.isArrayEnd()) {
                        reader.beginObject();

                        sprite.addFrame(parseSprite(pack, reader, system, false));

                        reader.endObject();
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

    private static void loadIfNeeded(String image, Pack pack, FileSystem system) throws IOException {
        BufferedImage img = pack.getImage(image);

        if (img == null) {
            Path path = system.getPath(String.format("sprites/%s.png", image));

            img = ImageIO.read(Files.newInputStream(path));
            pack.putImage(image, img);
        }
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

            levels.add(level);
        }

        return levels;
    }

    /*
     *****************
     * SERIALIZATION *
     *****************
     */

    public static void serialize(Pack pack, Path out) throws IOException, JsonException {
        if (!Utils.checkExtension(out, "skb")) {
            throw new IOException();
        }

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        FileSystem system = FileSystems.newFileSystem(out, env);

        writeSettingsFile(pack, system.getPath("settings.json"));
        writeMapFile(pack, system);
        writePlayerFile(pack, system);
        writeImages(pack, system);
        writeLevels(pack, system);

        system.close();
    }

    private static void writeSettingsFile(Pack pack, Path out) throws IOException, JsonException {
        IJsonWriter writer = new JsonPrettyWriter(Files.newBufferedWriter(out));

        writer.beginObject();

        writer.field("name", pack.getPackName());
        writer.field("author", pack.getAuthor());
        writer.field("version", pack.getVersion());
        writer.field("game_width", pack.getGameWidth());
        writer.field("game_height", pack.getGameHeight());
        writer.field("tile_width", pack.getTileWidth());
        writer.field("tile_height", pack.getTileHeight());

        writer.endObject();
        writer.close();
    }

    private static void writeMapFile(Pack pack, FileSystem system) throws IOException, JsonException {
        IJsonWriter writer = new JsonPrettyWriter(Files.newBufferedWriter(system.getPath("map.json")));

        writer.beginObject();

        writer.key("wall").beginObject();
        writeSprite(pack, pack.getSprite("wall"), writer);
        writer.endObject();

        writer.key("crate").beginObject();
        writeSprite(pack, pack.getSprite("crate"), writer);
        writer.endObject();

        writer.key("crate_on_target").beginObject();
        writeSprite(pack, pack.getSprite("crate_on_target"), writer);
        writer.endObject();

        writer.key("floor").beginObject();
        writeSprite(pack, pack.getSprite("floor"), writer);
        writer.endObject();

        writer.key("target").beginObject();
        writeSprite(pack, pack.getSprite("target"), writer);
        writer.endObject();

        writer.endObject();
        writer.close();
    }

    private static void writePlayerFile(Pack pack, FileSystem system) throws IOException, JsonException {
        IJsonWriter writer = new JsonPrettyWriter(Files.newBufferedWriter(system.getPath("player.json")));

        writer.beginObject();

        final String[] direction = new String[]{"left", "right", "down", "up"};
        for (int i = 0; i < 4; i++) {
            writer.key(direction[i]).beginObject();

            writer.key("static").beginObject();
            writeSprite(pack, pack.getSprite(direction[i] + "_static"), writer);
            writer.endObject();

            writer.key("walk").beginObject();
            writeSprite(pack, pack.getSprite(direction[i] + "_walk"), writer);
            writer.endObject();

            writer.endObject();
        }

        writer.endObject();
        writer.close();
    }

    private static void writeSprite(Pack pack, AbstractSprite sprite, IJsonWriter writer) throws IOException, JsonException {
        if (sprite instanceof Sprite) {
            writer.field("type", "sprite");
            writer.field("image", sprite.getTexture());
        } else if (sprite instanceof SubSprite) {
            writer.field("type", "sub_sprite");
            writer.field("image", sprite.getTexture());

            SubSprite subSprite = (SubSprite) sprite;
            writer.field("x", subSprite.getX() / pack.getTileWidth());
            writer.field("y", subSprite.getY() / pack.getTileHeight());
        } else if (sprite instanceof AnimatedSprite) {
            AnimatedSprite animatedSprite = (AnimatedSprite) sprite;

            writer.field("type", "animated_sprite");
            writer.field("delay", animatedSprite.getDelay());

            writer.key("frames").beginArray();
            for (AbstractSprite frame : animatedSprite.getFrames()) {
                writer.beginObject();
                writeSprite(pack, frame, writer);
                writer.endObject();
            }

            writer.endArray();
        }
    }

    private static void writeImages(Pack pack, FileSystem system) throws IOException {
        createDirectory("sprites", system);

        for (Map.Entry<String, BufferedImage> images : pack.getImages().entrySet()) {
            Path out = system.getPath("sprites/" + images.getKey() + ".png");

            try (OutputStream stream = Files.newOutputStream(out)) {
                ImageIO.write(images.getValue(), "png", stream);
            }
        }
    }

    private static void writeLevels(Pack pack, FileSystem system) throws IOException {
        ArrayList<Level> levels = pack.getLevels();

        createDirectory("levels", system);
        for (int i = 0, levelsSize = levels.size(); i < levelsSize; i++) {
            Level level = levels.get(i);

            BufferedWriter writer = Files.newBufferedWriter(system.getPath("levels/" + i));

            writer.write(String.format("%d\n%d\n", level.getWidth(), level.getHeight()));

            for (int y = 0; y < level.getHeight(); y++) {
                for (int x = 0; x < level.getWidth(); x++) {
                    Tile tile = level.getTile(x, y);

                    if (level.getPlayerX() == x && level.getPlayerY() == y) {

                        if (tile == Tile.FLOOR) {
                            writer.write(Pack.PLAYER);
                        } else if (tile == Tile.TARGET) {
                            writer.write(Pack.PLAYER_ON_TARGET);
                        }
                    } else {
                        writer.write(tile.getSymbol());
                    }
                }

                writer.write("\n");
            }

            writer.close();
        }
    }

    private static void createDirectory(String name, FileSystem system) throws IOException {
        Path path = system.getPath(name);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }
}