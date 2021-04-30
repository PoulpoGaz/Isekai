package fr.poulpogaz.isekai.editor.ui.importer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record SIPack(String name, String author, int nLevels, int id) {

    private static final Logger LOGGER = LogManager.getLogger(SIPack.class);

    private static int ID = 0;

    public SIPack(String name, String author, int nLevels) {
        this(name, author, nLevels, ID++);
    }

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

    public static void main(String[] args) {
        SIPack.loadPacks();
    }
}