package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.ui.Model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Pack extends Model {

    public static final String NAME_PROPERTY = "NameProperty";
    public static final String AUTHOR_PROPERTY = "AuthorProperty";
    public static final String VERSION_PROPERTY = "VersionProperty";

    protected String name;
    protected String author;
    protected String version;

    protected ArrayList<Level> levels;

    protected Path saveLocation;

    protected boolean modified = true;

    public Pack() {
        this.levels = new ArrayList<>();

        addLevel(new Level());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            String old = this.name;

            this.name = name;

            modified = true;
            firePropertyChange(NAME_PROPERTY, old, name);
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (!Objects.equals(this.author, author)) {
            String old = this.author;

            this.author = author;

            modified = true;
            firePropertyChange(AUTHOR_PROPERTY, old, author);
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (!Objects.equals(this.version, version)) {
            String old = this.version;

            this.version = version;

            modified = true;
            firePropertyChange(VERSION_PROPERTY, old, version);
        }
    }

    public int getNumberOfLevels() {
        return levels.size();
    }

    public void addLevel(Level level) {
        level.index = levels.size();
        levels.add(level);

        modified = true;
        fireLevelInserted(level.index, level.index);
    }

    public void addLevel(Level level, int index) {
        level.index = index;
        levels.add(index, level);

        for (int i = index + 1; i < levels.size(); i++) {
            levels.get(i).index = i;
        }

        modified = true;
        fireLevelInserted(index, index);
    }

    public void addAll(Collection<Level> newLevels) {
        int oldSize = levels.size();

        int index = levels.size();
        for (Level level : newLevels) {
            level.index = index;
            index++;

            levels.add(level);
        }

        modified = true;
        fireLevelInserted(oldSize, levels.size() - 1);
    }

    public void setLevel(Level level, int index) {
        level.index = index;
        Level old = levels.set(index, level);
        old.index = -1;

        modified = true;
        fireLevelChanged(index);
    }

    public void removeLevel(int index) {
        Level old = levels.remove(index);
        old.index = 0;

        for (int i = index; i < levels.size(); i++) {
            levels.get(i).index = i;
        }

        modified = true;
        fireLevelRemoved(index);
    }

    public void removeLevel(Level level) {
        removeLevel(levels.indexOf(level));
    }

    public void swapLevels(int index1, int index2) {
        Level level1 = levels.get(index1);
        level1.index = index2;

        Level level2 = levels.get(index2);
        level2.index = index1;

        levels.set(index1, level2);
        levels.set(index2, level1);

        modified = true;
        fireLevelsSwapped(index1, index2);
    }

    public Level getLevel(int index) {
        return levels.get(index);
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        if (levels.size() > 0) {
            ArrayList<Level> old = this.levels;
            old.forEach((level) -> level.index = -1);

            this.levels = levels;

            for (int i = 0; i < levels.size(); i++) {
                levels.get(i).index = i;
            }

            modified = true;
            fireNewLevels();
        }
    }

    public void setSaveLocation(Path saveLocation) {
        this.saveLocation = saveLocation;
    }

    public Path getSaveLocation() {
        return saveLocation;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean isModified() {
        return modified;
    }

    /**
     *  LISTENERS
     */
    public void addLevelsOrganisationListener(LevelsOrganisationListener listener) {
        listenerList.add(LevelsOrganisationListener.class, listener);
    }

    public void removeLevelsOrganisationListener(LevelsOrganisationListener listener) {
        listenerList.remove(LevelsOrganisationListener.class, listener);
    }

    private void fireLevelInserted(int start, int end) {
        fireListener(LevelsOrganisationListener.class, (t) -> t.levelInserted(start, end));
    }

    private void fireLevelRemoved(int index) {
        fireListener(LevelsOrganisationListener.class, (t) -> t.levelRemoved(index));
    }

    private void fireLevelChanged(int index) {
        fireListener(LevelsOrganisationListener.class, (t) -> t.levelChanged(index));
    }

    private void fireLevelsSwapped(int index1, int index2) {
        fireListener(LevelsOrganisationListener.class, (t) -> t.levelsSwapped(index1, index2));
    }

    private void fireNewLevels() {
        fireListener(LevelsOrganisationListener.class, LevelsOrganisationListener::newLevels);
    }
}