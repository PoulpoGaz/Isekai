package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.AnimatedSprite;
import fr.poulpogaz.isekai.editor.pack.image.BasicSprite;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.Utils;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class SpritePanel extends JPanel {

    private static final String SPRITE = "Sprite";
    private static final String SUB_SPRITE = "Sub sprite";
    private static final String ANIMATED_SPRITE = "Animated sprite";

    private final Pack pack;
    private final SpriteEditorModel editor;

    private JComboBox<String> sprites;
    private JComboBox<String> spriteType;

    public SpritePanel(Pack pack, SpriteEditorModel editor) {
        this.pack = pack;
        this.editor = editor;

        editor.addPropertyChangeListener(SpriteEditorModel.SELECTED_SPRITE_PROPERTY, this::switchSprite);

        setLayout(new VerticalLayout());
        initComponents();
    }

    private void initComponents() {
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        sprites = new JComboBox<>();
        for (String sprite : PackSprites.SPRITES) {
            sprites.addItem(sprite);
        }
        sprites.setSelectedItem(editor.getSelectedSprite());
        sprites.addItemListener((i) -> {
            editor.setSelectedSprite(pack.getSprite((String) sprites.getSelectedItem()));
        });

        spriteType = new JComboBox<>();
        spriteType.addItem(SPRITE);
        spriteType.addItem(SUB_SPRITE);
        spriteType.addItem(ANIMATED_SPRITE);
        setSpriteType();

        add(Utils.split(sprites, spriteType), constraint);
    }

    private void setSpriteType() {
        AbstractSprite sprite = editor.getSelectedSprite();

        if (sprite instanceof BasicSprite) {
            spriteType.setSelectedItem(SPRITE);
        } else if (sprite instanceof SubSprite){
            spriteType.setSelectedItem(SUB_SPRITE);
        } else if (sprite instanceof AnimatedSprite) {
            spriteType.setSelectedItem(ANIMATED_SPRITE);
        }
    }

    private void switchSprite(PropertyChangeEvent evt) {
        spriteType.setSelectedItem(editor.getSelectedSprite().getName());

        setSpriteType();
    }
}