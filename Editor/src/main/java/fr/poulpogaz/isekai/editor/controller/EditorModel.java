package fr.poulpogaz.isekai.editor.controller;

import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.Tool;

public class EditorModel extends Model {

    public static final String SELECTED_LEVEL_PROPERTY = "SelectedLevelProperty";
    public static final String TOOL_PROPERTY = "ToolProperty";
    public static final String SELECTED_TILE_PROPERTY = "SelectedTileProperty";
    public static final String SHOW_GRID_PROPERTY = "ShowGridProperty";

    private Tool currentTool = PaintTool.getInstance();
    private Tile selectedTile = Tile.FLOOR;
    private int selectedLevel = 0;
    private boolean showGrid = true;

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        if (this.showGrid != showGrid) {
            boolean old = this.showGrid;

            this.showGrid = showGrid;

            firePropertyChange(SHOW_GRID_PROPERTY, old, showGrid);
        }
    }

    public void setSelectedLevel(int selectedLevel) {
        if (this.selectedLevel != selectedLevel) {
            int old = this.selectedLevel;

            this.selectedLevel = selectedLevel;

            firePropertyChange(SELECTED_LEVEL_PROPERTY, old, selectedLevel);
        }
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        if (this.currentTool != currentTool) {
            Tool old = this.currentTool;

            this.currentTool = currentTool;

            firePropertyChange(TOOL_PROPERTY, old, currentTool);
        }
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        if (this.selectedTile != selectedTile) {
            Tile old = this.selectedTile;

            this.selectedTile = selectedTile;

            firePropertyChange(SELECTED_TILE_PROPERTY, old, selectedTile);
        }
    }
}