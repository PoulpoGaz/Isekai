package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.FlatDarculaLaf;
import fr.poulpogaz.isekai.editor.utils.Cache;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Cache.setRoot(System.getenv("APPDATA") + "/Isekai/editor");

        EventQueue.invokeLater(() -> {
            FlatDarculaLaf.install();

            UIManager.put("MenuItem.selectionType", "underline");

            new IsekaiEditor();
        });
    }
}