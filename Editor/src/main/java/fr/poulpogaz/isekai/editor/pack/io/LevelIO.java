package fr.poulpogaz.isekai.editor.pack.io;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;
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

        Vector2i pos = level.getPlayerPos();

        writer.field("playerX", pos.getX());
        writer.field("playerY", pos.getY());

        writer.key("data").beginArray();

        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                writer.value(level.get(x, y).ordinal());
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
        level.setPlayerPos(new Vector2i(playerX, playerY));

        reader.skipKey().beginArray();

        Tile[] values = Tile.values();
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                int tile = reader.nextInt();

                if (tile < 0 || tile > values.length) {
                    throw new IOException("Incorrect tile value: " + tile + " at x: " + x + ", y: " + y);
                }

                level.set(values[tile], x, y);
            }
        }

        reader.endArray();
        reader.endObject();
        reader.close();

        return level;
    }
}