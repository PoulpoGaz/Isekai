package fr.poulpogaz.isekai.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.nio.file.Path;

public class Log4j2Init {

    public static void init(String module) {
        Path log = Cache.of("isekai-%s.log".formatted(module));
        System.out.println(log);
        System.setProperty("log-path", log.toString());

        ((LoggerContext) LogManager.getContext(false)).reconfigure();
        LogManager.getLogger("ROOT").info("=== STARTING {} ===", module);
    }
}
