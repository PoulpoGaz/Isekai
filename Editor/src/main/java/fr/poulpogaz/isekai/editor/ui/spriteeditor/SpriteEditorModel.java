package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.PackImage;
import fr.poulpogaz.isekai.editor.ui.editorbase.EditorModelBase;

import java.awt.*;
import java.util.HashMap;

public class SpriteEditorModel extends EditorModelBase<PackImage, Color> {

    public static final String SELECTED_SPRITE_PROPERTY = "SelectedSpriteProperty";

    private final Pack pack;

    private AbstractSprite selectedSprite;
    private HashMap<String, PackImage> images;

    protected SpriteEditorModel(Pack pack) {
        this.pack = pack;

        images = pack.getImages();

        selectedSprite = pack.getSprite(PackSprites.FLOOR);
        selectedMap = getImage(selectedSprite);
        selectedElement = Color.WHITE;

        showGrid = false;
    }

    public PackImage getImage(AbstractSprite sprite) {
        return images.get(sprite.getTexture());
    }

    public AbstractSprite getSelectedSprite() {
        return selectedSprite;
    }

    public void setSelectedSprite(AbstractSprite selectedSprite) {
        if (selectedSprite != null && this.selectedSprite != selectedSprite) {
            AbstractSprite old = this.selectedSprite;

            this.selectedSprite = selectedSprite;

            firePropertyChange(SELECTED_SPRITE_PROPERTY, old, null);
            setSelectedMap(images.get(selectedSprite.getTexture()));
        }
    }
}