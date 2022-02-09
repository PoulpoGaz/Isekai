package fr.poulpogaz.isekai.commons;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.nio.file.Path;

public class Log4j2Init {

    public static void init(String module) {
        Path log = Cache.of("isekai-%s.log".formatted(module));

        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        builder.setStatusLevel(Level.INFO);

        LayoutComponentBuilder layout = builder.newLayout("PatternLayout");
        layout.addAttribute("pattern", "%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n");

        AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        console.add(layout);

        AppenderComponentBuilder file = builder.newAppender("file", "File");
        file.addAttribute("fileName", log.toString());
        file.addAttribute("append", true);
        file.add(layout);

        builder.add(console);
        builder.add(file);

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.TRACE);
        rootLogger.add(builder.newAppenderRef("stdout"));
        rootLogger.add(builder.newAppenderRef("file"));
        builder.add(rootLogger);

        ((LoggerContext) LogManager.getContext(false)).reconfigure(builder.build());
        LogManager.getLogger("ROOT").info("=== STARTING {} ===", module);
    }

    public static void initPath(String module, Path root) {
        System.setProperty("log-path", root.resolve("isekai-%s.log".formatted(module)).toString());

        ((LoggerContext) LogManager.getContext(false)).reconfigure();
        LogManager.getLogger("ROOT").info("=== STARTING {} ===", module);
    }
}
