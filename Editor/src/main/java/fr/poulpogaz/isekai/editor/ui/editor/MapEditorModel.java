package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.PlayerTool;
import fr.poulpogaz.isekai.editor.ui.EditorModel;

public class MapEditorModel extends EditorModel<Level, Tile> {

    public MapEditorModel(Level level) {
        super(level, Tile.FLOOR);
    }

    public AbstractSprite getToolSprite(Pack pack) {
        if (tool instanceof PlayerTool) {
            return pack.getSprite(PackSprites.PLAYER_DOWN_STATIC);
        } else {
            return selectedElement.getSprite(pack);
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