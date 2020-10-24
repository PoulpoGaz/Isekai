package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import fr.poulpogaz.isekai.editor.settings.Settings;
import fr.poulpogaz.isekai.editor.utils.Cache;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Compressor.compress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cache.setRoot(System.getProperty("user.home"), ".PoulpoGaz/Isekai/editor");

        FlatLaf.registerCustomDefaultsSource("theme");
        FlatDarculaLaf.install();

        Settings.initSettings();
        Settings.read();

        EventQueue.invokeLater(() -> {
            IsekaiEditor editor = new IsekaiEditor();
            editor.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Settings.write();
                }
            });
            editor.setVisible(true);
        });
    }
}