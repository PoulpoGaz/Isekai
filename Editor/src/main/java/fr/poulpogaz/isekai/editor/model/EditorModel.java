package fr.poulpogaz.isekai.editor.model;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.PlayerTool;
import fr.poulpogaz.isekai.editor.tools.Tool;

public class EditorModel extends Model {

    public static final String SELECTED_LEVEL_PROPERTY = "SelectedLevelProperty";
    public static final String TOOL_PROPERTY = "ToolProperty";
    public static final String SELECTED_TILE_PROPERTY = "SelectedTileProperty";
    public static final String SHOW_GRID_PROPERTY = "ShowGridProperty";

    private Tool tool = PaintTool.getInstance();
    private Tile selectedTile = Tile.FLOOR;

    private Level selectedLevel = null;

    private boolean showGrid = true;

    public EditorModel() {

    }

    public void applyTool(int x, int y) {
        tool.apply(selectedLevel, selectedTile, x, y);
    }

    public AbstractSprite getToolSprite(Pack pack) {
        return tool.getToolSprite(pack, selectedTile);
    }

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

    public void setSelectedLevel(Level selectedLevel) {
        if (this.selectedLevel != selectedLevel) {
            Level old = this.selectedLevel;

            this.selectedLevel = selectedLevel;

            firePropertyChange(SELECTED_LEVEL_PROPERTY, old, selectedLevel);
        }
    }

    public void setSelectedLevel(Pack pack, int index) {
        Level level = pack.getLevel(index);

        setSelectedLevel(level);
    }

    public int getSelectedLevelIndex() {
        return selectedLevel.getIndex();
    }

    public Level getSelectedLevel() {
        return selectedLevel;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        if (this.tool != tool) {
            Tool old = this.tool;

            this.tool = tool;

            firePropertyChange(TOOL_PROPERTY, old, tool);
        }
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        if (selectedTile != null) {
            if (this.selectedTile != selectedTile) {
                Tile old = this.selectedTile;

                this.selectedTile = selectedTile;

                firePropertyChange(SELECTED_TILE_PROPERTY, old, selectedTile);
            }

            if (tool instanceof PlayerTool) {
                setTool(PaintTool.getInstance());
            }
        }
    }
}