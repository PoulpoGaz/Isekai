package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LevelIO {

    static void serialize(Level level, Path out) throws IOException, JsonException {
        IJsonWriter writer = new JsonPrettyWriter(Files.newBufferedWriter(out));

        writer.beginObject();

        writer.field("width", level.getWidth());
        writer.field("height", level.getHeight());

        writer.field("playerX", level.getPlayerX());
        writer.field("playerY", level.getPlayerY());

        writer.key("data").beginArray();

        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                writer.value(level.getTile(x, y).ordinal());
            }
        }

        writer.endArray();
        writer.endObject();
        writer.close();
    }

    static Level deserialize(Path in) throws IOException, JsonException {
        IJsonReader reader = new JsonReader(Files.newBufferedReader(in));

        reader.beginObject();

        int width = reader.skipKey().nextInt();
        int height = reader.skipKey().nextInt();
        int playerX = reader.skipKey().nextInt();
        int playerY = reader.skipKey().nextInt();

        Level level = new Level(width, height);
        level.setPlayer(playerX, playerY);

        reader.skipKey().beginArray();

        Tile[] values = Tile.values();
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                int tile = reader.nextInt();

                if (tile < 0 || tile > values.length) {
                    throw new IOException("Incorrect tile value: " + tile + " at x: " + x + ", y: " + y);
                }

                level.setTile(x, y, values[tile]);
            }
        }

        reader.endArray();
        reader.endObject();
        reader.close();

        return level;
    }
}