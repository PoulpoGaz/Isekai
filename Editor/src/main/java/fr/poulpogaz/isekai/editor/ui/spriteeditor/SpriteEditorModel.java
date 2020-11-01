package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.IAnimatedSprite;
import fr.poulpogaz.isekai.editor.pack.image.IImageSprite;
import fr.poulpogaz.isekai.editor.pack.image.PackImage;
import fr.poulpogaz.isekai.editor.ui.editorbase.EditorModelBase;

import java.awt.*;

public class SpriteEditorModel extends EditorModelBase<PackImage, Color> {

    public static final String SELECTED_SPRITE_PROPERTY = "SelectedSpriteProperty";

    private final Pack pack;

    private AbstractSprite selectedSprite;

    protected SpriteEditorModel(Pack pack) {
        this.pack = pack;

        selectedSprite = pack.getSprite(PackSprites.FLOOR);
        selectedMap = getImageFor(selectedSprite);
        selectedElement = Color.WHITE;

        showGrid = false;
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
        if (sprite instanceof IImageSprite) {
            return ((IImageSprite) sprite).getImage();
        } else if (sprite instanceof IAnimatedSprite) {
            IAnimatedSprite animatedSprite = (IAnimatedSprite) sprite;

            AbstractSprite frame0 = animatedSprite.getFrame(0);

            return getImageFor(frame0); // call once
        } else {
            return null;
        }
    }
}