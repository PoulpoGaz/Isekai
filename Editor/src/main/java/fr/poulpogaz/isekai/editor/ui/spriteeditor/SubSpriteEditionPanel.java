package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.PackImage;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class SubSpriteEditionPanel extends JPanel {

    private final SpriteEditorModel model;

    private SubSprite sprite;

    private JSpinner x;
    private JSpinner y;
    private JSpinner width;
    private JSpinner height;

    public SubSpriteEditionPanel(SpriteEditorModel model) {
        this.model = model;
        model.addPropertyChangeListener(SpriteEditorModel.SELECTED_SPRITE_PROPERTY, this::selectedSpriteChanged);

        setLayout(new MigLayout("", "[grow]5[grow]", "[grow]5[grow]"));
        initComponents();
    }

    private void initComponents() {
        x = new JSpinner();
        y = new JSpinner();
        width = new JSpinner();
        height = new JSpinner();

        add(x);
        add(y);
        add(width);
        add(height);
    }

    private void selectedSpriteChanged(PropertyChangeEvent evt) {
        AbstractSprite s = model.getSelectedSprite();

        if (s instanceof SubSprite) {
            this.sprite = (SubSprite) s;

            PackImage image = model.getImage(sprite);

            x.setModel(new SpinnerNumberModel(sprite.getX(), 0, image.getWidth(), 1));
            y.setModel(new SpinnerNumberModel(sprite.getY(), 0, image.getHeight(), 1));
            width.setModel(new SpinnerNumberModel(sprite.getWidth(), 0, image.getWidth(), 1));
            height.setModel(new SpinnerNumberModel(sprite.getHeight(), 0, image.getWidth(), 1));
        } else {
            sprite = null;
        }
    }
}