package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import fr.poulpogaz.isekai.editor.utils.Cache;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            Compressor.compress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cache.setRoot(System.getProperty("user.home"), ".PoulpoGaz/Isekai/editor");

        EventQueue.invokeLater(() -> {
            FlatLaf.registerCustomDefaultsSource("theme");

            FlatDarculaLaf.install();

            IsekaiEditor.getInstance().setVisible(true);
        });
    }
}