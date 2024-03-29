package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.commons.pack.Level;
import fr.poulpogaz.isekai.commons.pack.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public record SIPack(String name, String author, int nLevels, int id) {

    private static final Logger LOGGER = LogManager.getLogger(SIPack.class);

    private static int ID = 1;

    public SIPack(String name, String author, int nLevels) {
        this(name, author, nLevels, ID++);
    }

    /**
     * @param index a value between 1 and nLevels inclusive
     */
    public String getLinkFor(int index) {
        if (index <= 0 || index > nLevels) {
            throw new IndexOutOfBoundsException("Index out of range (0; %d): %d".formatted(nLevels, index));
        }

        return "https://sokoban.info/?%d_%d".formatted(id, index);
    }

    /**
     * @param index a value between 1 and nLevels inclusive
     */
    public Level importLevel(int index) {
        try {
            String link = getLinkFor(index);
            LOGGER.info("Importing level from {}", link);

            URL url = new URL(link);

            InputStream is = new BufferedInputStream(url.openStream());
            Document document = Jsoup.parse(is, null, url.toString());
            is.close();

            return importLevel(document, index);
        } catch (IOException e) {
            LOGGER.warn("Failed to import level {}", index, e);

            return null;
        }
    }

    Level importLevel(Document document, int index) {
        Elements scripts = document.head().select("script");

        for (Element element : scripts) {
            String js = element.html();

            if (js.contains("Board")) {
                String board = getValue(js, "Board");
                String xMax = getValue(js, "BoardXMax");
                String yMax = getValue(js, "BoardYMax");

                if (board == null || xMax == null || yMax == null) {
                    LOGGER.warn("Can't find variables");

                    return null;
                }

                int width = Integer.parseInt(xMax);
                int height = Integer.parseInt(yMax);

                if (width < Level.MINIMUM_MAP_WIDTH || height < Level.MINIMUM_MAP_HEIGHT) {
                    LOGGER.warn("Level is too small");

                    return null;
                }

                Level level = new Level(width, height);

                if (decode(level, board.substring(1, board.length() - 1))) {
                    return level;
                }
            }
        }

        LOGGER.warn("Failed to import level {}", index);

        return null;
    }

    private String getValue(String js, String variable) {
        while (true) {
            int index = js.indexOf(variable);

            if (index == -1) {
                return null;
            }

            int afterIndex = index + variable.length();
            char after = js.charAt(index + variable.length());

            js = js.substring(afterIndex);
            if (after == '=' || Character.isWhitespace(after)) {
                break;
            }
        }

        int first = js.indexOf('=') + 1;
        int last = js.indexOf(';') - 1;

        if (first == 0 || last == -1) { // first == -1 + 1 = 0
            return null;
        }

        while (Character.isWhitespace(js.charAt(first))) {
            first++;

            if (first >= js.length()) {
                return null;
            }
        }

        while (Character.isWhitespace(js.charAt(last))) {
            last--;

            if (last < 0) {
                return null;
            }
        }

        return js.substring(first, last + 1);
    }

    private boolean decode(Level level, String data) {
        // put the player at the last tile
        // This is important because Level#set check if the player
        // is at the targeted tile. To prevent this, we just have
        // to put the player at the last tile. Then it will
        // automatically add at his correct position without disturb
        level.setPlayerPos(level.getWidth() - 1, level.getHeight() - 1);

        int x = -1;
        int y = 0;
        for (char c : data.toCharArray()) {
            x++;

            switch (c) {
                case ' ' -> level.set(Tile.FLOOR, x, y);
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
                case '!' -> {
                    x = -1;
                    y++;
                }
                default -> {
                    level.set(Tile.WALL, x, y);
                }
            }
        }

        return true;
    }

    /**
     * STATIC PART
     */
    public static final int NOT_LOADED = 0;
    public static final int OK = 1;
    public static final int ERROR = 2;

    private static List<SIPack> packs;
    private static int packLoaded = NOT_LOADED;
    private static Exception exception;

    public static void loadPacks() {
        if (packLoaded != NOT_LOADED) {
            return;
        }

        LOGGER.info("Loading sokoban.info packs");

        packs = new ArrayList<>();
        try {
            Document document = Jsoup.connect("https://sokoban.info").get();

            Element element = document.select("#SelectCollection").first();

            for (Element child : element.children()) {
                String author = child.attr("label");

                for (Element child2 : child.children()) {
                    String text = child2.text();

                    int parenthesis = text.lastIndexOf('(');
                    int close = text.lastIndexOf(')');
                    int nLevels = Integer.parseInt(text.substring(parenthesis + 1, close));

                    String name = text.substring(0, parenthesis - 1);

                    SIPack pack = new SIPack(name, author, nLevels);
                    LOGGER.info("New pack loaded: {}", pack);
                    packs.add(pack);
                }
            }

            packLoaded = OK;
        } catch (IOException | NumberFormatException e) {
            exception = e;
            LOGGER.warn("Failed to load packs", e);

            packs = null;
            packLoaded = ERROR;
        }
    }

    public static boolean arePacksLoaded() {
        return packLoaded == OK;
    }

    public static List<SIPack> getPacks() {
        if (!arePacksLoaded()) {
            throw new IllegalStateException("Packs aren't loaded");
        }

        return packs;
    }

    public static boolean isError() {
        return packLoaded == ERROR;
    }

    public static Exception getException() {
        return exception;
    }
}