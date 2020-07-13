package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.ui.ButtonPanel;
import fr.poulpogaz.isekai.editor.ui.EditorMenuBar;
import fr.poulpogaz.isekai.editor.ui.ProgressBarPanel;
import fr.poulpogaz.isekai.editor.ui.TextFieldPanel;
import fr.poulpogaz.isekai.editor.ui.blenderpane.BlenderArea;
import fr.poulpogaz.isekai.editor.ui.blenderpane.BlenderSplitArea;

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
        BlenderArea container = new BlenderArea();

        container.addPanel(new ButtonPanel());
        container.addPanel(new TextFieldPanel());
        container.addPanel(new ProgressBarPanel());

        setContentPane(new BlenderSplitArea(container));
    }
}