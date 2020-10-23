package fr.poulpogaz.isekai.editor.controller;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.tools.Tool;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class PackController {

    public static final String SELECTED_LEVEL_PROPERTY = "SelectedLevelProperty";
    public static final String TOOL_PROPERTY = "ToolProperty";
    public static final String SELECTED_TILE_PROPERTY = "SelectedTileProperty";

    private final Pack pack;
    private final EditorModel editorModel;
    private final ArrayList<LevelController> levels;

    public PackController(Pack pack) {
        this.pack = pack;
        editorModel = new EditorModel();

        levels = new ArrayList<>();
        for (Level level : pack.getLevels()) {
            levels.add(new LevelController(level));
        }
    }

    public void addLevelsOrganisationListener(LevelsOrganisationListener listener) {
        pack.addLevelsOrganisationListener(listener);
    }

    public void removeLevelsOrganisationListener(LevelsOrganisationListener listener) {
        pack.removeLevelsOrganisationListener(listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pack.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pack.addPropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pack.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        pack.removePropertyChangeListener(property, listener);
    }


    public void addEditorPropertyChangeListener(PropertyChangeListener listener) {
        pack.addPropertyChangeListener(listener);
    }

    public void addEditorPropertyChangeListener(String property, PropertyChangeListener listener) {
        pack.addPropertyChangeListener(property, listener);
    }

    public void removeEditorPropertyChangeListener(PropertyChangeListener listener) {
        pack.removePropertyChangeListener(listener);
    }

    public void removeEditorPropertyChangeListener(String property, PropertyChangeListener listener) {
        pack.removePropertyChangeListener(property, listener);
    }



    // simple, just redirect
    public String getName() {
        return pack.getName();
    }

    public void setName(String name) {
        pack.setName(name);
    }

    public String getAuthor() {
        return pack.getAuthor();
    }

    public void setAuthor(String author) {
        pack.setAuthor(author);
    }

    public String getVersion() {
        return pack.getVersion();
    }

    public void setVersion(String version) {
        pack.setVersion(version);
    }

    public AbstractSprite getSprite(String name) {
        return pack.getSprite(name);
    }

    public LevelController addLevel(int index) {
        Level level = new Level();
        pack.addLevel(level, index);

        LevelController controller = new LevelController(level);
        levels.add(controller);

        return controller;
    }

    public void removeLevel(int index) {
        if (pack.getNumberOfLevels() > 1) {
            levels.remove(index);
            pack.removeLevel(index);
            editorModel.setSelectedLevel(Math.max(index - 1, 0));
        }
    }

    public void removeLevel(LevelController controller) {
        int index = levels.indexOf(controller);

        if (index != -1) {
            removeLevel(index);
        }
    }

    public void swapLevels(int index2) {
        pack.swapLevels(editorModel.getSelectedLevel(), index2);

        editorModel.setSelectedLevel(index2);
    }

    public LevelController getLevel(int index) {
        return levels.get(index);
    }

    public int getNumberOfLevels() {
        return pack.getNumberOfLevels();
    }

    public void setSelectedLevel(int level) {
        editorModel.setSelectedLevel(level);
    }

    public int getSelectedLevel() {
        return editorModel.getSelectedLevel();
    }

    public void setTool(Tool tool) {
        editorModel.setCurrentTool(tool);
    }

    public Tool getTool() {
        return editorModel.getCurrentTool();
    }

    public void setSelectedTile(Tile tile) {
        editorModel.setSelectedTile(tile);
    }

    public Tile getSelectedTile() {
        return editorModel.getSelectedTile();
    }

    public void setShowGrid(boolean showGrid) {
        editorModel.setShowGrid(showGrid);
    }

    public boolean isShowGrid() {
        return editorModel.isShowGrid();
    }
}