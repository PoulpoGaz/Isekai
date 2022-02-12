package fr.poulpogaz.isekai.game;

import fr.poulpogaz.isekai.commons.Cache;
import fr.poulpogaz.isekai.commons.Log4j2Init;
import fr.poulpogaz.isekai.game.renderer.io.GameEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args)  {
        Cache.setRoot();
        Log4j2Init.init("game");

        System.setProperty("joml.format", "false");

        GameEngine engine = new GameEngine(Isekai.getInstance(), "Isekai", Isekai.DEFAULT_WIDTH, Isekai.DEFAULT_HEIGHT);

        try {
            engine.init();
        } catch (Exception e) {
            LOGGER.fatal("Can't initialize", e);

            return;
        }

        engine.run();
    }
}