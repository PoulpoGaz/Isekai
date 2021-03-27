package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.PlayerTool;
import fr.poulpogaz.isekai.editor.ui.editorbase.EditorModel;

import java.awt.image.BufferedImage;

public class LevelEditorModel extends EditorModel<Level, Tile> {

    public LevelEditorModel(Level level) {
        super(level, Tile.FLOOR);
    }

    public BufferedImage getToolSprite() {
        if (tool instanceof PlayerTool) {
            return PackSprites.getPlayer();
        } else {
            return selectedElement.getSprite();
        }
    }

    public void setSelectedLevel(Pack pack, int index) {
        Level level = pack.getLevel(index);

        setSelectedMap(level);
    }

    public int getSelectedLevelIndex() {
        return selectedMap.getIndex();
    }

    public void setSelectedElement(Tile selectedElement) {
        if (selectedElement != null) {
            if (this.selectedElement != selectedElement) {
                Tile old = this.selectedElement;

                this.selectedElement = selectedElement;

                firePropertyChange(SELECTED_ELEMENT_PROPERTY, old, selectedElement);
            }

            if (tool instanceof PlayerTool) {
                setTool(PaintTool.getInstance());
            }
        }
    }
}