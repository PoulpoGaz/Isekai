package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.ui.Model;

import javax.swing.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Pack extends Model {

    public static final int MAX_FILE_NAME_SIZE = 8;
    public static final int MAX_PACK_NAME_SIZE = 32;
    public static final int MAX_AUTHOR_SIZE = 32;

    public static final String FILE_NAME_PROPERTY = "FileNameProperty";
    public static final String PACK_NAME_PROPERTY = "PackNameProperty";
    public static final String AUTHOR_PROPERTY = "AuthorProperty";

    protected String fileName;
    protected String packName;
    protected String author;

    protected ArrayList<Level> levels;

    protected Path saveLocation;

    protected boolean modified = true;

    public Pack() {
        this.levels = new ArrayList<>();

        addLevel(new Level());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (!Objects.equals(this.fileName, fileName)) {
            String old = this.fileName;

            if (fileName.length() > MAX_FILE_NAME_SIZE) {
                this.fileName = fileName.substring(0, MAX_FILE_NAME_SIZE);
            } else {
                this.fileName = fileName;
            }

            modified = true;
            firePropertyChange(FILE_NAME_PROPERTY, old, fileName);
        }
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        if (!Objects.equals(this.packName, packName)) {
            String old = this.packName;

            if (packName.length() > MAX_PACK_NAME_SIZE) {
                this.packName = packName.substring(0, MAX_PACK_NAME_SIZE);
            } else {
                this.packName = packName;
            }

            modified = true;
            firePropertyChange(PACK_NAME_PROPERTY, old, packName);
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (!Objects.equals(this.author, author)) {
            String old = this.author;

            if (author.length() > MAX_AUTHOR_SIZE) {
                this.author = author.substring(0, MAX_AUTHOR_SIZE);
            } else {
                this.author = author;
            }

            modified = true;
            firePropertyChange(AUTHOR_PROPERTY, old, author);
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

    public Level removeLevel(int index) {
        Level old = levels.remove(index);
        old.index = 0;

        for (int i = index; i < levels.size(); i++) {
            levels.get(i).index = i;
        }

        modified = true;
        fireLevelRemoved(index);

        return old;
    }

    public void removeLevel(Level level) {
        removeLevel(levels.indexOf(level));
    }

    public void removeAll(List<Level> toRemove) {
        if (toRemove != null && toRemove.size() > 0) {
            boolean removed = false;

            for (Level level : toRemove) {
                if (levels.remove(level)) {
                    removed = true;
                }
            }

            if (!removed) {
                return;
            }

            for (int i = 0; i < levels.size(); i++) {
                Level level = levels.get(i);

                level.index = i;
            }

            modified = true;
            fireOrganisationChanged();
        }
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
            fireOrganisationChanged();
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

    private void fireOrganisationChanged() {
        fireListener(LevelsOrganisationListener.class, LevelsOrganisationListener::organisationChanged);
    }
}