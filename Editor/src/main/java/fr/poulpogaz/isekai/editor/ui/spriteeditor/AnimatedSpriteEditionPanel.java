package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import javax.swing.*;

public class AnimatedSpriteEditionPanel extends JPanel {

    private final SpriteEditorModel model;

    public AnimatedSpriteEditionPanel(SpriteEditorModel model) {
        this.model = model;

        initComponents();
    }

    private void initComponents() {
        setVisible(false);
    }
}