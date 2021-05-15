package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.FlatLaf;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.ui.theme.ThemeManager;
import fr.poulpogaz.isekai.editor.utils.Cache;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static final String VERSION = "1.0-SNAPSHOT";

    private static final Logger LOGGER;

    static {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            Cache.setRoot(System.getenv("APPDATA"), "/Isekai-Editor");
        } else {
            Cache.setRoot(System.getProperty("user.home"), "/Isekai-Editor");
        }

        setupLog4j2();
        LOGGER = LogManager.getLogger(Main.class);
    }

    public static void main(String[] args) {
        LOGGER.info("Launching Isekai Editor");

        try {
            PackSprites.initialize();
        } catch (IOException e) {
            LOGGER.fatal("Failed to load tileset", e);

            JOptionPane.showMessageDialog(null, "Failed to load tileset\n\nError:" + e, "FATAL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Prefs.init();

        EventQueue.invokeLater(() -> {
            FlatLaf.registerCustomDefaultsSource("themes");
            ThemeManager.loadThemes();
            ThemeManager.setTheme(false);

            IsekaiEditor editor = IsekaiEditor.getInstance();
            editor.setVisible(true);
        });
    }

    private static void setupLog4j2() {
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