package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.AnimatedSprite;
import fr.poulpogaz.isekai.editor.pack.image.Sprite;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.json.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger LOGGER = LogManager.getLogger(PackIO.class);

    private static final String[] PLAYER_DIRECTIONS = new String[] {"left", "right", "up", "down"};
    private static final String[] PLAYER_SPRITE_TYPES = new String[] {"static", "walk"};

    public static Pack deserialize(Path path) {
        try {
            return deserializeWithoutCatchingException(path);
        } catch (IOException | JsonException e) {
            e.printStackTrace();

            LOGGER.error("Failed to load pack.");

            return null;
        }
    }

    public static Pack deserializeWithoutCatchingException(Path path) throws IOException, JsonException {
        if (!Utils.checkExtension(path, "skb")) {
            return null;
        }

        LOGGER.info("Reading pack at {}", path);

        FileSystem system = FileSystems.newFileSystem(path);

        Pack pack = new Pack();
        readSettingsFile(pack, system.getPath("settings.json"));
        readMapFile(pack, system);
        readPlayerFile(pack, system);
        pack.setLevels(readLevels(system.getPath("levels/")));

        system.close();

        LOGGER.info("Finished reading");

        return pack;
    }

    private static void readSettingsFile(Pack pack, Path settingsPath) throws IOException, JsonException {
        IJsonReader reader = new JsonReader(Files.newBufferedReader(settingsPath));

        reader.beginObject();

        pack.setPackName(Utils.assertKeyEquals(reader, "name").nextString());
        pack.setAuthor(Utils.assertKeyEquals(reader, "author").nextString());
        pack.setVersion(Utils.assertKeyEquals(reader, "version").nextString());
        pack.setGameWidth(Utils.assertKeyEquals(reader, "game_width").nextInt());
        pack.setGameHeight(Utils.assertKeyEquals(reader, "game_height").nextInt());
        pack.setTileWidth(Utils.assertKeyEquals(reader, "tile_width").nextInt());
        pack.setTileHeight(Utils.assertKeyEquals(reader, "tile_height").nextInt());

        reader.endObject();
        reader.close();
    }

    private static void readMapFile(Pack pack, FileSystem system) throws IOException, JsonException {
        IJsonReader reader = new JsonReader(Files.newBufferedReader(system.getPath("map.json")));

        reader.beginObject();

        for (Tile tile : Tile.values()) {
            String spriteName = tile.getSprite();

            Utils.assertKeyEquals(reader, spriteName);

            reader.beginObject();
            AbstractSprite sprite = parseSprite(pack, reader, system, true);
            reader.endObject();

            pack.putSprite(spriteName, sprite);
        }

        reader.endObject();
        reader.close();
    }

    private static void readPlayerFile(Pack pack, FileSystem system) throws IOException, JsonException {
        IJsonReader reader = new JsonReader(Files.newBufferedReader(system.getPath("player.json")));

        reader.beginObject();

        for (String direction : PLAYER_DIRECTIONS) {
            Utils.assertKeyEquals(reader, direction);

            reader.beginObject();

            for (String type : PLAYER_SPRITE_TYPES) {
                Utils.assertKeyEquals(reader, type);

                reader.beginObject();
                AbstractSprite sprite = parseSprite(pack, reader, system, true);
                pack.putSprite(direction + "_" + type, sprite);

                reader.endObject();
            }

            reader.endObject();
        }

        reader.endObject();
        reader.close();
    }

    private static AbstractSprite parseSprite(Pack pack, IJsonReader reader, FileSystem system, boolean acceptAnimatedSprite) throws IOException, JsonException {
        String type = Utils.assertKeyEquals(reader, "type").nextString();

        switch (type) {
            case "sprite" -> {
                String image = Utils.assertKeyEquals(reader, "image").nextString();
                loadIfNeeded(image, pack, system);

                return new Sprite(pack, image);
            }
            case "sub_sprite" -> {
                String image = Utils.assertKeyEquals(reader, "image").nextString();
                loadIfNeeded(image, pack, system);

                int x = Utils.assertKeyEquals(reader, "x").nextInt() * pack.getTileWidth();
                int y = Utils.assertKeyEquals(reader, "y").nextInt() * pack.getTileHeight();

                return new SubSprite(pack, image, x, y, pack.getTileWidth(), pack.getTileWidth());
            }
            case "animated_sprite" -> {
                if (acceptAnimatedSprite) {
                    int delay =  Utils.assertKeyEquals(reader, "delay").nextInt();

                    AnimatedSprite sprite = new AnimatedSprite(pack, delay);

                    Utils.assertKeyEquals(reader, "frames").beginArray(); // frames

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

    private static ArrayList<Level> readLevels(Path levelDirectory) throws IOException, JsonException {
        List<Path> levelsPaths;

        try (Stream<Path> levelPath = Files.walk(levelDirectory)) {
            levelsPaths = levelPath.collect(Collectors.toList());
        }

        ArrayList<Level> levels = new ArrayList<>();
        for (Path levelPath : levelsPaths) {
            if (Files.isDirectory(levelPath)) {
                continue;
            }

            Level level = LevelIO.deserialize(levelPath);

            /*BufferedReader br = Files.newBufferedReader(levelPath);

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

            br.close();*/

            levels.add(level);
        }

        return levels;
    }

    /*
     *****************
     * SERIALIZATION *
     *****************
     */
    public static boolean serialize(Pack pack, Path out) {
        if (!Utils.checkExtension(out, "skb")) {
            return false;
        }

        LOGGER.info("Writing pack at {}", out);

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        try {
            FileSystem system = FileSystems.newFileSystem(out, env);

            writeSettingsFile(pack, system.getPath("settings.json"));
            writeMapFile(pack, system);
            writePlayerFile(pack, system);
            writeImages(pack, system);
            writeLevels(pack, system);

            system.close();
        } catch (IOException | JsonException e) {
            e.printStackTrace();
            LOGGER.error("Failed to write pack.");

            return false;
        }

        LOGGER.info("Writing finished");

        return true;
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

    private static void writeLevels(Pack pack, FileSystem system) throws IOException, JsonException {
        ArrayList<Level> levels = pack.getLevels();

        createDirectory("levels", system);
        for (int i = 0, levelsSize = levels.size(); i < levelsSize; i++) {
            Level level = levels.get(i);

            LevelIO.serialize(level, system.getPath("levels/" + i));
        }
    }

    private static void createDirectory(String name, FileSystem system) throws IOException {
        Path path = system.getPath(name);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }
}