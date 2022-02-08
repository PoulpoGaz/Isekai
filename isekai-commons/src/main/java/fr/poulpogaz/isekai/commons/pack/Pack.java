package fr.poulpogaz.isekai.commons.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pack {

    protected String fileName;
    protected String packName;
    protected String author;

    protected ArrayList<Level> levels;

    public Pack() {
        this.levels = new ArrayList<>();
        addLevel(new Level());
    }

    public boolean isPlayable() {
        for (Level level : levels) {
            if (!level.isPlayable()) {
                return false;
            }
        }

        return true;
    }

    public int getNumberOfLevels() {
        return levels.size();
    }

    public void addLevel(Level level) {
        level.index = levels.size();
        level.pack = this;
        levels.add(level);
    }

    public void addLevel(Level level, int index) {
        level.index = index;
        level.pack = this;
        levels.add(index, level);

        for (int i = index + 1; i < levels.size(); i++) {
            levels.get(i).index = i;
        }
    }

    public void addAll(Collection<Level> newLevels) {
        int oldSize = levels.size();

        int index = levels.size();
        for (Level level : newLevels) {
            level.index = index;
            level.pack = this;
            index++;

            levels.add(level);
        }
    }

    public void setLevel(Level level, int index) {
        level.index = index;
        level.pack = this;
        Level old = levels.set(index, level);
        old.pack = null;
        old.index = -1;
    }

    public Level removeLevel(int index) {
        Level old = levels.remove(index);
        old.index = -1;
        old.pack = null;

        for (int i = index; i < levels.size(); i++) {
            levels.get(i).index = i;
        }

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
                    level.index = -1;
                    level.pack = null;

                    removed = true;
                }
            }

            if (!removed) {
                return;
            }

            for (int i = 0; i < levels.size(); i++) {
                Level level = levels.get(i);

                level.index = i;
                level.pack = this;
            }
        }
    }

    public void swapLevels(int index1, int index2) {
        Level level1 = levels.get(index1);
        level1.index = index2;

        Level level2 = levels.get(index2);
        level2.index = index1;

        levels.set(index1, level2);
        levels.set(index2, level1);
    }

    public Level getLevel(int index) {
        return levels.get(index);
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> newLevels) {
        if (newLevels.size() > 0) {
            levels.forEach((level) -> {
                level.index = -1;
                level.pack = null;
            });
            levels.clear();

            for (int i = 0; i < newLevels.size(); i++) {
                Level level = newLevels.get(i);
                level.index = i;
                levels.add(level);
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
