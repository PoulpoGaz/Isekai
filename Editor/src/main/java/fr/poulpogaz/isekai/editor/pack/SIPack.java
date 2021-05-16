package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.utils.concurrent.ExecutorWithException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public record SIPack(String name, String author, int nLevels, int id) {

    private static final Logger LOGGER = LogManager.getLogger(SIPack.class);

    private static int ID = 1;

    public SIPack(String name, String author, int nLevels) {
        this(name, author, nLevels, ID++);
    }

    /**
     * @param index a value between 1 and nLevels inclusive
     */
    public Level importLevel(int index) {
        if (index <= 0 || index > nLevels) {
            throw new IndexOutOfBoundsException("Index out of range (0; %d): %d".formatted(nLevels, index));
        }

        try {
            String link = "https://sokoban.info/?%d_%d".formatted(id, index);
            LOGGER.info("Importing level from {}", link);

            Document document = Jsoup.connect(link).get();
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
        } catch (Exception e) {
            LOGGER.warn("Failed to import level {}", index, e);

            return null;
        }
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
        int x = -1;
        int y = 0;
        for (char c : data.toCharArray()) {
            x++;

            switch (c) {
                case ' ' -> level.setTile(Tile.FLOOR, x, y);
                case '#', 'x' -> level.setTile(Tile.WALL, x, y);
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
                case '!' -> {
                    x = -1;
                    y++;
                }
                default -> {
                    return false;
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

    // download all packs
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SIPack.loadPacks();

        ExecutorService scanner = ExecutorWithException.newFixedThreadPool(1);
        ExecutorService executor = ExecutorWithException.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (final SIPack pack : packs) {
            final List<Level> levels = new ArrayList<>();

            for (int i = 0; i < pack.nLevels; i++) {
                final int i2 = i;

                executor.submit(() -> {
                    Level level = pack.importLevel(i2);

                    if (level != null) {
                        levels.add(level);
                    }
                });
            }

            executor.submit(() -> {
                Pack p = new Pack() ;
                p.addAll(levels);

                String fileName;
                if (pack.name().length() > Pack.MAX_FILE_NAME_SIZE) {
                    try {
                        fileName = scanner.submit(() -> askName(sc, pack)).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    fileName = pack.name();
                }

                p.setFileName(fileName);
                p.setPackName(pack.name());
                p.setAuthor(pack.author());

                try {
                    PackIO.serialize(p, Path.of("levels/"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static String askName(Scanner scanner, SIPack pack) {
        System.out.println(pack.name + "?");

        String name;
        while (true) {
            if (scanner.hasNextLine()) {
                name = scanner.nextLine();

                if (name.length() <= Pack.MAX_FILE_NAME_SIZE) {
                    break;
                }

                System.out.println("Too long");
            }
        }

        return name;
    }
}