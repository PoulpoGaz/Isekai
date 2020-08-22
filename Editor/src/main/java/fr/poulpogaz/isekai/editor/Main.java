package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.FlatDarculaLaf;
import fr.poulpogaz.isekai.editor.utils.Cache;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Compressor.compress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cache.setRoot(System.getenv("APPDATA") + "/Isekai/editor");

        EventQueue.invokeLater(() -> {
            FlatDarculaLaf.install();

            UIManager.put("MenuItem.selectionType", "underline");

            UIManager.put("Icon.color", UIManager.getColor("Table.sortIconColor"));

            IsekaiEditor.getInstance().setVisible(true);
        });
    }
}