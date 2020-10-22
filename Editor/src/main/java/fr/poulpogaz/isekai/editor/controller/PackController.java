package fr.poulpogaz.isekai.editor.controller;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class PackController {

    public static final String SELECTED_LEVEL_PROPERTY = "SelectedLevelProperty";
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private Pack pack;

    private ArrayList<LevelController> levels;
    private int selectedLevel;

    public PackController(Pack pack) {
        super();

        this.pack = pack;

        levels = new ArrayList<>();
        for (Level level : pack.getLevels()) {
            levels.add(new LevelController(level));
        }

        selectedLevel = 0;
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


    public void addSelectedLevelListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(SELECTED_LEVEL_PROPERTY, listener);
    }

    public void removeSelectedLevelListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(SELECTED_LEVEL_PROPERTY, listener);
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
            setSelectedLevel(Math.max(index - 1, 0));
        }
    }

    public void removeLevel(LevelController controller) {
        int index = levels.indexOf(controller);

        if (index != -1) {
            removeLevel(index);
        }
    }

    public void swapLevels(int index2) {
        pack.swapLevels(selectedLevel, index2);

        setSelectedLevel(index2);
    }

    public LevelController getLevel(int index) {
        return levels.get(index);
    }

    public int getNumberOfLevels() {
        return pack.getNumberOfLevels();
    }

    public void setSelectedLevel(int index) {
        if (selectedLevel != index) {
            int old = selectedLevel;

            this.selectedLevel = index;

            changeSupport.firePropertyChange(new PropertyChangeEvent(this, SELECTED_LEVEL_PROPERTY, old, selectedLevel));
        }
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }
}