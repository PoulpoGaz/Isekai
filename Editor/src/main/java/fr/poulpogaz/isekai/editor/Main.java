package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.FlatLaf;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.ui.theme.ThemeManager;
import fr.poulpogaz.isekai.editor.utils.Cache;
import fr.poulpogaz.isekai.editor.utils.Log4j2Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static final String VERSION = "1.0";

    private static final Logger LOGGER;

    static {
        Cache.setRoot();
        Log4j2Utils.setup();
        LOGGER = LogManager.getLogger(Main.class);
    }

    public static void main(String[] args) {
        LOGGER.info("Launching Isekai Editor");

        try {
            PackSprites.initialize();
        } catch (Exception e) {
            LOGGER.fatal("Failed to load tileset", e);

            JOptionPane.showMessageDialog(null, "Failed to load tileset\n\nError:" + e, "FATAL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");

        Prefs.init();

        EventQueue.invokeLater(() -> {
            FlatLaf.registerCustomDefaultsSource("themes");
            ThemeManager.loadThemes();
            ThemeManager.setTheme(false);

            IsekaiEditor editor = IsekaiEditor.getInstance();
            editor.setVisible(true);
        });
    }
}