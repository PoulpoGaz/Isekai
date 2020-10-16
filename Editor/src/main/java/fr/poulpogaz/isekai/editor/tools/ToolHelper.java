package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.ui.editor.Default;

public class ToolHelper {

    private Tool tool = Default.TOOL;
    private Tile selectedTile = Default.SELECTED_TILE;

    public ToolHelper() {

    }

    public void apply(Level level, int x, int y) {
        tool.apply(level, selectedTile, x, y);
    }

    public AbstractSprite getToolSprite(Pack pack) {
        return tool.getToolSprite(pack, selectedTile);
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }
}