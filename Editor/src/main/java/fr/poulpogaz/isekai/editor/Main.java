package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.FlatLaf;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.ui.theme.ThemeManager;
import fr.poulpogaz.isekai.editor.utils.Cache;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            PackSprites.initialize();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load tileset:\n" + e, "FATAL", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
            return;
        }

        Cache.setRoot(System.getProperty("user.home"), ".PoulpoGaz/Isekai/editor");

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