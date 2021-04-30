package fr.poulpogaz.isekai.editor.ui.importer;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public record SIPack(String name, String author, int nLevels, int id) {

    private static final Logger LOGGER = LogManager.getLogger(SIPack.class);

    private static int ID = 0;

    public SIPack(String name, String author, int nLevels) {
        this(name, author, nLevels, ID++);
    }

    public Level importLevel(int index) {
        if (index < 0 || index >= nLevels) {
            throw new IndexOutOfBoundsException();
        }

        try {
            String link = "https://sokoban.info/?%d_%d".formatted(id + 1, index + 1);
            LOGGER.info("Importing level from {}", link);

            Document document = Jsoup.connect(link).get();
            Elements scripts = document.head().select("script");

            for (Element element : scripts) {
                String js = element.html();

                if (js.contains("Board")) {
                    String board = getValue(js, "Board");
                    String xMax = getValue(js, "BoardXMax");
                    String yMax = getValue(js, "BoardYMax");

                    LOGGER.info("{}end\n{}end\n{}end", board, xMax, yMax);

                    if (board == null || xMax == null || yMax == null) {
                        return null;
                    }

                    int width = Integer.parseInt(xMax);
                    int height = Integer.parseInt(yMax);

                    Level level = new Level(width, height);

                    if (decode(level, board.substring(1, board.length() - 1))) {
                        return level;
                    }

                }
            }

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
        int x = 0;
        int y = 0;
        for (char c : data.toCharArray()) {
            x++;

            if (x >= level.getWidth()) {
                x = 0;
                y++;
            }

            switch (c) {
                case 'x', ' ' -> level.set(Tile.FLOOR, x, y);
                case '#' -> level.set(Tile.WALL, x, y);
                case '$' -> level.set(Tile.CRATE, x, y);
                case '.' -> level.set(Tile.TARGET, x, y);
                case '*' -> level.set(Tile.CRATE_ON_TARGET, x, y);
                case '@' -> {
                    level.set(Tile.FLOOR, x, y);
                    level.setPlayerPos(new Vector2i(x, y));
                }
                case '+' -> {
                    level.set(Tile.TARGET, x, y);
                    level.setPlayerPos(new Vector2i(x, y));
                }
                case '!' -> {}
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

    private static List<SIPack> packs;
    private static boolean packLoaded = false;

    public static void loadPacks() {
        if (packLoaded) {
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

                    SIPack pack = new SIPack(author, name, nLevels);
                    LOGGER.info("New pack loaded: {}", pack);
                    packs.add(pack);
                }
            }

        } catch (IOException | NumberFormatException e) {
            LOGGER.warn("Failed to load packs", e);

            packs = null;
        }

        packLoaded = true;
    }

    public static boolean arePacksLoaded() {
        return packLoaded;
    }

    public static List<SIPack> getPacks() {
        if (!packLoaded) {
            throw new IllegalStateException("Packs aren't loaded");
        }

        return packs;
    }

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://sokoban.info/?1_1");

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("text.html"));

        url.openStream().transferTo(bos);
        bos.close();


        SIPack.loadPacks();
        SIPack pack = SIPack.getPacks().get(0);

        Level level = pack.importLevel(0);

        if (level == null) {
            throw new IllegalStateException();
        }

        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                System.out.print(level.get(x, y) + " ");
            }

            System.out.println();
        }
    }
}