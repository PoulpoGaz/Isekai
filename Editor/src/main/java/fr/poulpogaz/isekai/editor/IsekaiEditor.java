package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.ui.EditorMenuBar;
import fr.poulpogaz.isekai.editor.ui.editor.EditorPanel;

import javax.swing.*;

public class IsekaiEditor extends JFrame {

    public IsekaiEditor() {
        super("Editor");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();

        setJMenuBar(new EditorMenuBar());

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setContentPane(new EditorPanel());
    }
}