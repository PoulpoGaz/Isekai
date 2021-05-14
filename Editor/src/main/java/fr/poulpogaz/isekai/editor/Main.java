package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.FlatLaf;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.ui.theme.ThemeManager;
import fr.poulpogaz.isekai.editor.utils.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main {

    public static final String VERSION = "1.0-SNAPSHOT";

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            PackSprites.initialize();
        } catch (IOException e) {
            LOGGER.fatal("Failed to load tileset", e);

            JOptionPane.showMessageDialog(null, "Failed to load tileset\n\nError:" + e, "FATAL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cache.setRoot(System.getProperty("user.home"), ".PoulpoGaz/Isekai/editor");

        Prefs.init();

        EventQueue.invokeLater(() -> {
            FlatLaf.registerCustomDefaultsSource("themes");
            ThemeManager.loadThemes();
            ThemeManager.setTheme(false);

            IsekaiEditor editor = IsekaiEditor.getInstance();
            editor.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Prefs.setMaximized(editor.getExtendedState() == Frame.MAXIMIZED_BOTH);
                    Prefs.setWidth(editor.getWidth());
                    Prefs.setHeight(editor.getHeight());

                    Point location = editor.getLocationOnScreen();

                    Prefs.setWindowX(location.x);
                    Prefs.setWindowY(location.y);
                }
            });

            editor.setVisible(true);
        });
    }
}