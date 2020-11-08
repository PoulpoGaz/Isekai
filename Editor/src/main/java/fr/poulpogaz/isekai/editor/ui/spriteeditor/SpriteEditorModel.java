package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.*;
import fr.poulpogaz.isekai.editor.ui.editorbase.EditorModelBase;

import java.awt.*;

public class SpriteEditorModel extends EditorModelBase<PackImage, Color> {

    public static final String SELECTED_SPRITE_PROPERTY = "SelectedSpriteProperty";

    private final Pack pack;

    private AbstractSprite selectedSprite;

    protected SpriteEditorModel(Pack pack) {
        this.pack = pack;

        selectedElement = Color.WHITE;
        setSelectedSprite(pack.getSprite(PackSprites.FLOOR));

        showGrid = false;
    }

    public void changeSpriteType(AbstractSprite sprite, String newType) {
        AbstractSprite newSprite = switch (newType) {
            case AbstractSprite.SPRITE -> sprite.toSprite();
            case AbstractSprite.SUB_SPRITE -> sprite.toSubSprite();
            case AbstractSprite.ANIMATED_SPRITE -> sprite.toAnimatedSprite();
            default -> null;
        };

        if (newSprite != sprite) {
            pack.addSprite(newSprite);
            setSelectedSprite(newSprite);
        }
    }

    public AbstractSprite getSelectedSprite() {
        return selectedSprite;
    }

    public void setSelectedSprite(AbstractSprite selectedSprite) {
        if (selectedSprite != null && this.selectedSprite != selectedSprite) {
            AbstractSprite old = this.selectedSprite;

            this.selectedSprite = selectedSprite;

            firePropertyChange(SELECTED_SPRITE_PROPERTY, old, null);
            setSelectedMap(getImageFor(selectedSprite));
        }
    }

    protected PackImage getImageFor(AbstractSprite sprite) {
        if (sprite instanceof BasicSprite) {
            return ((BasicSprite) sprite).getImage();
        } else if (sprite instanceof SubSprite) {
           return ((SubSprite) sprite).getParent();
        } else if (sprite instanceof AnimatedSprite) {
            AnimatedSprite animatedSprite = (AnimatedSprite) sprite;

            AbstractSprite frame0 = animatedSprite.getFrame(0);

            return getImageFor(frame0); // call once
        } else {
            return null;
        }
    }
}