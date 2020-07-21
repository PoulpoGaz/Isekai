package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.ui.ButtonPanel;
import fr.poulpogaz.isekai.editor.ui.EditorMenuBar;
import fr.poulpogaz.isekai.editor.ui.ProgressBarPanel;
import fr.poulpogaz.isekai.editor.ui.TextFieldPanel;
import fr.poulpogaz.isekai.editor.ui.area.BArea;

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
        BArea container = new BArea();

        container.addView(new ButtonPanel());
        container.addView(new TextFieldPanel());
        container.addView(new ProgressBarPanel());

        setContentPane(container);
    }
}