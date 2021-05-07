package fr.poulpogaz.isekai.editor.pack;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SOKReader {

    private static final String SYMBOLS = "#@+$*. -_";

    public static List<Level> read(Path file) {
        try {
            BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8);

            List<Level> levels = null;

            String line;
            while ((line = br.readLine()) != null) {
                if (isSokobanLine(line)) {
                    Level level = parseLevel(line, br);

                    if (level != null) {
                        if (levels == null) {
                            levels = new ArrayList<>();
                        }

                        levels.add(level);
                    }
                }
            }

            br.close();

            return levels;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    private static boolean isSokobanLine(String line) {
        if (line == null || line.length() == 0) {
            return false;
        }

        for (char c : line.toCharArray()) {
            if (SYMBOLS.indexOf(c) < 0) {
                return false;
            }
        }

        return true;
    }

    private static Level parseLevel(String firstLine, BufferedReader br) throws IOException {
        int width = firstLine.length();
        int height;

        List<String> lines = new ArrayList<>();
        lines.add(firstLine);

        String line;
        while ((line = br.readLine()) != null) {
            if (isSokobanLine(line)) {
                width = Math.max(width, line.length());

                lines.add(line);
            } else {
                break;
            }
        }

        height = lines.size();

        Level level = new Level(width, height);

        int y = 0;
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);

            int x = 0;
            for (char c : line.toCharArray()) {
                switch (c) {
                    case ' ', '-' -> level.setTile(Tile.FLOOR, x, y);
                    case '#', '_' -> level.setTile(Tile.WALL, x, y);
                    case '$' -> level.setTile(Tile.CRATE, x, y);
                    case '.' -> level.setTile(Tile.TARGET, x, y);
                    case '*' -> level.setTile(Tile.CRATE_ON_TARGET, x, y);
                    case '@' -> {
                        level.setTile(Tile.FLOOR, x, y);
                        level.setPlayerPos(x, y);
                    }
                    case '+' -> {
                        level.setTile(Tile.TARGET, x, y);
                        level.setPlayerPos(x, y);
                    }
                    default -> {
                        return null;
                    }
                }

                x++;
            }

            if (x != width) {
                for (; x < width; x++) {
                    level.setTile(Tile.WALL, x, y);
                }
            }

            y++;
        }

        return level;
    }
}