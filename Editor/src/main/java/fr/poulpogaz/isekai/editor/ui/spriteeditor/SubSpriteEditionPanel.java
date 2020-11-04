package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.PackImage;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SubSpriteEditionPanel extends JPanel {

    private final SpriteEditorModel model;

    private ChangeListener listener;

    private SubSprite sprite;

    private JSpinner x;
    private JSpinner y;
    private JSpinner width;
    private JSpinner height;

    private SpinnerNumberModel xModel;
    private SpinnerNumberModel yModel;
    private SpinnerNumberModel widthModel;
    private SpinnerNumberModel heightModel;

    private boolean fire = true;

    public SubSpriteEditionPanel(SpriteEditorModel model) {
        this.model = model;
        model.addPropertyChangeListener(SpriteEditorModel.SELECTED_SPRITE_PROPERTY, (e) -> revalidateComponents());

        listener = this::spriteChanged;

        setLayout(new MigLayout("fillx"));
        initComponents();
        revalidateComponents();
    }

    private void initComponents() {
        x = new JSpinner();
        x.addChangeListener(this::spinnerChanged);
        xModel = new SpinnerNumberModel();
        x.setModel(xModel);

        y = new JSpinner();
        y.addChangeListener(this::spinnerChanged);
        yModel = new SpinnerNumberModel(1, null, null, 1);
        y.setModel(yModel);

        width = new JSpinner();
        width.addChangeListener(this::spinnerChanged);
        widthModel = new SpinnerNumberModel(1, null, null, 1);
        width.setModel(widthModel);

        height = new JSpinner();
        height.addChangeListener(this::spinnerChanged);
        heightModel = new SpinnerNumberModel();
        height.setModel(heightModel);

        add(new JLabel("X:"), "grow");
        add(x);
        add(new JLabel("Y:"), "grow");
        add(y);
        add(new JLabel("Width:"), "grow");
        add(width);
        add(new JLabel("Height:"), "grow");
        add(height);
    }

    private void spinnerChanged(ChangeEvent e) {
        if (fire) {
            sprite.setX((Integer) x.getValue());
            sprite.setY((Integer) y.getValue());
            sprite.setWidth((Integer) width.getValue());
            sprite.setHeight((Integer) height.getValue());
        }
    }

    private void spriteChanged(ChangeEvent e) {
        updateModels(sprite, false);
    }

    private void revalidateComponents() {
        AbstractSprite s = model.getSelectedSprite();

        if (sprite != null) {
            sprite.removeChangeListener(listener);
        }

        if (s instanceof SubSprite) {
            this.sprite = (SubSprite) s;
            sprite.addChangeListener(listener);

            fire = false;
            updateModels(sprite, false);
            fire = true;
            setVisible(true);
        } else {
            sprite = null;
            setVisible(false);
        }
    }

    private void updateModels(SubSprite sprite, boolean fire) {
        boolean old = this.fire;

        this.fire = fire;

        PackImage image = sprite.getParent();

        xModel.setValue(sprite.getX());
        yModel.setValue(sprite.getY());
        widthModel.setValue(sprite.getWidth());
        heightModel.setValue(sprite.getHeight());

        xModel.setMaximum(image.getWidth() - sprite.getWidth());
        yModel.setMaximum(image.getHeight() - sprite.getHeight());
        widthModel.setMaximum(image.getWidth() - sprite.getX());
        heightModel.setMaximum(image.getHeight() - sprite.getY());

        this.fire = old;
    }
}