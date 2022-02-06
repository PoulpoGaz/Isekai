package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.PlayerTool;
import fr.poulpogaz.isekai.editor.tools.Tool;
import fr.poulpogaz.isekai.editor.ui.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.undo.UndoableEdit;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class LevelEditorModel extends Model {

    public static final String SELECTED_MAP_PROPERTY = "SelectedLevelProperty";
    public static final String SELECTED_ELEMENT_PROPERTY = "SelectedTileProperty";
    public static final String TOOL_PROPERTY = "ToolProperty";
    public static final String SHOW_GRID_PROPERTY = "ShowGridProperty";

    private static final Logger LOGGER = LogManager.getLogger(LevelEditorModel.class);

    protected Tool tool = PaintTool.getInstance();
    protected Pack pack;
    protected Level selectedLevel;
    protected Tile selectedTile;

    protected boolean showGrid = true;

    public LevelEditorModel(Pack pack, Tile selectedTile) {
        this.pack = Objects.requireNonNull(pack);
        this.selectedLevel = pack.getLevel(0);
        this.selectedTile = Objects.requireNonNull(selectedTile);
    }

    public void setSelectedLevel(Level selectedLevel) {
        if (this.selectedLevel != selectedLevel) {
            Level old = this.selectedLevel;

            this.selectedLevel = selectedLevel;

            firePropertyChange(SELECTED_MAP_PROPERTY, old, selectedLevel);
        }
    }

    public Level getSelectedLevel() {
        return selectedLevel;
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

    public void pressTool(int x, int y) {
        if (selectedLevel.isInside(x, y)) {
            tool.press(selectedLevel, selectedTile, x, y);
        }
    }

    public void releaseTool(int x, int y) {
        LOGGER.info("Release tool ({}) at {}, {}", tool.getClass().getSimpleName(), x, y);

        if (selectedLevel.isInside(x, y)) {
            UndoableEdit edit = tool.release(selectedLevel, selectedTile, x, y);

            if (edit != null) {
                IsekaiEditor.getInstance().addEdit(edit);
            }
        }
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

    public BufferedImage getToolSprite() {
        if (tool instanceof PlayerTool) {
            return PackSprites.getPlayer();
        } else {
            return selectedTile.getSprite();
        }
    }

    public void setSelectedLevel(Pack pack, int index) {
        Level level = pack.getLevel(index);

        setSelectedLevel(level);
    }

    public int getSelectedLevelIndex() {
        return selectedLevel.getIndex();
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        if (selectedTile != null) {
            if (this.selectedTile != selectedTile) {
                Tile old = this.selectedTile;

                this.selectedTile = selectedTile;

                firePropertyChange(SELECTED_ELEMENT_PROPERTY, old, selectedTile);
            }

            if (tool instanceof PlayerTool) {
                setTool(PaintTool.getInstance());
            }
        }
    }
}