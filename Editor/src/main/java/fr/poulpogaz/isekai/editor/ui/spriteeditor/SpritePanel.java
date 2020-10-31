package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.Sprite;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class SpritePanel extends JPanel {

    private final Pack pack;
    private final SpriteEditorModel editor;

    private JComboBox<AbstractSprite> sprites;
    private JComboBox<String> spriteType;

    private JPanel subSpriteEditionPanel;

    public SpritePanel(Pack pack, SpriteEditorModel editor) {
        this.pack = pack;
        this.editor = editor;

        setLayout(new VerticalLayout());
        initComponents();
    }

    private void initComponents() {
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        sprites = new JComboBox<>();
        for (String sprite : PackSprites.SPRITES) {
            sprites.addItem(pack.getSprite(sprite));
        }
        sprites.setSelectedItem(editor.getSelectedSprite());
        sprites.addItemListener((i) -> {
            editor.setSelectedSprite((AbstractSprite) sprites.getSelectedItem());
        });

        spriteType = new JComboBox<>();
        spriteType.addItem("Sprite");
        spriteType.addItem("Sub image");
        setSpriteType();

        subSpriteEditionPanel = createSubSpriteEditionPanel();

        add(sprites, constraint);
        add(spriteType, constraint);
    }

    private void setSpriteType() {
        AbstractSprite sprite = editor.getSelectedSprite();

        if (sprite instanceof Sprite) {
            spriteType.setSelectedItem("Sprite");
        } else {
            spriteType.setSelectedItem("Sub image");
        }
    }

    private JPanel createSubSpriteEditionPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Edit sub sprite"));

        panel.setLayout(new MigLayout("", "[grow]5[grow]", "[grow]5[grow]"));

        JSpinner x = new JSpinner();


        return panel;
    }
}