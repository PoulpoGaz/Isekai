package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.commons.pack.Level;
import fr.poulpogaz.isekai.commons.pack.Tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SOKReader {

    private static final String SYMBOLS = "#@+$*. -_";

    public static List<Level> read(Path file) {
        try {
            BufferedReader br = Files.newBufferedReader(file);

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

    /**
     * {@link SIPack#decode(Level, String)}
     */
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

        if (width < Level.MINIMUM_MAP_WIDTH || height < Level.MINIMUM_MAP_HEIGHT) {
            return null;
        }

        Level level = new Level(width, height);
        level.setPlayerPos(width - 1, height - 1);

        int y = 0;
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);

            int x = 0;
            for (char c : line.toCharArray()) {
                switch (c) {
                    case ' ', '-' -> level.set(Tile.FLOOR, x, y);
                    case '#', '_' -> level.set(Tile.WALL, x, y);
                    case '$' -> level.set(Tile.CRATE, x, y);
                    case '.' -> level.set(Tile.TARGET, x, y);
                    case '*' -> level.set(Tile.CRATE_ON_TARGET, x, y);
                    case '@' -> {
                        level.set(Tile.FLOOR, x, y);
                        level.setPlayerPos(x, y);
                    }
                    case '+' -> {
                        level.set(Tile.TARGET, x, y);
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
                    level.set(Tile.WALL, x, y);
                }
            }

            y++;
        }

        return level;
    }
}