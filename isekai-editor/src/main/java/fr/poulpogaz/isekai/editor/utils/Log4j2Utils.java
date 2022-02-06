package fr.poulpogaz.isekai.editor.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log4j2Utils {

    public static void setup() {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        builder.setStatusLevel(Level.INFO);

        LayoutComponentBuilder layout = builder.newLayout("PatternLayout");
        layout.addAttribute("pattern", "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n");

        AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        console.addAttribute("target", "SYSTEM_OUT");
        console.add(layout);

        AppenderComponentBuilder file = builder.newAppender("log", "File");
        file.addAttribute("fileName", getLogFile());
        file.addAttribute("immediateFlush", false);
        file.addAttribute("append", false);
        file.add(layout);

        builder.add(console);
        builder.add(file);

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.TRACE);
        rootLogger.add(builder.newAppenderRef("stdout"));
        rootLogger.add(builder.newAppenderRef("log"));
        builder.add(rootLogger);

        Configurator.initialize(builder.build());
    }

    private static Path getLogFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();

        return Cache.of("isekai_editor-" + formatter.format(now) + ".log");
    }
}