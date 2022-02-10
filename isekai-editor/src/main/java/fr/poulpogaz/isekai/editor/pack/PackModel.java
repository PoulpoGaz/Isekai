package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.commons.pack.Level;
import fr.poulpogaz.isekai.commons.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.Model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PackModel extends Model {

    public static final int MAX_FILE_NAME_SIZE = 8;
    public static final int MAX_PACK_NAME_SIZE = 32;
    public static final int MAX_AUTHOR_SIZE = 32;

    public static final String FILE_NAME_PROPERTY = "FileNameProperty";
    public static final String PACK_NAME_PROPERTY = "PackNameProperty";
    public static final String AUTHOR_PROPERTY = "AuthorProperty";

    protected Pack pack;
    protected ArrayList<LevelModel> models;

    protected Path saveLocation;

    protected boolean modified = true;

    public PackModel() {
        this(new Pack());
    }

    public PackModel(Pack pack) {
        this.pack = pack;
        models = new ArrayList<>();

        for (Level level : pack.getLevels()) {
            models.add(new LevelModel(level, this));
        }
    }

    public String getFileName() {
        return pack.getFileName();
    }

    public void setFileName(String fileName) {
        String old = pack.getFileName();

        if (!Objects.equals(old, fileName)) {
            pack.setFileName(fileName);

            modified = true;
            firePropertyChange(FILE_NAME_PROPERTY, old, fileName);
        }
    }

    public String getPackName() {
        return pack.getPackName();
    }

    public void setPackName(String packName) {
        String old = pack.getPackName();

        if (!Objects.equals(old, packName)) {
            String newPackName;
            if (packName.length() > MAX_PACK_NAME_SIZE) {
                newPackName = packName.substring(0, MAX_PACK_NAME_SIZE);
            } else {
                newPackName = packName;
            }

            pack.setPackName(newPackName);

            modified = true;
            firePropertyChange(PACK_NAME_PROPERTY, old, newPackName);
        }
    }

    public String getAuthor() {
        return pack.getAuthor();
    }

    public void setAuthor(String author) {
        String old = pack.getAuthor();

        if (!Objects.equals(old, author)) {
            String newAuthor;
            if (author.length() > MAX_AUTHOR_SIZE) {
                newAuthor = author.substring(0, MAX_AUTHOR_SIZE);
            } else {
                newAuthor = author;
            }

            pack.setAuthor(newAuthor);

            modified = true;
            firePropertyChange(AUTHOR_PROPERTY, old, newAuthor);
        }
    }

    public int getNumberOfLevels() {
        return pack.getNumberOfLevels();
    }

    public void addLevel(LevelModel level) {
        pack.addLevel(level.getLevel());
        models.add(level);
        level.pack = this;

        modified = true;
        fireLevelInserted(level.getIndex(), level.getIndex());
    }

    public void addLevel(LevelModel level, int index) {
        pack.addLevel(level.getLevel(), index);
        models.add(index, level);
        level.pack = this;

        modified = true;
        fireLevelInserted(index, index);
    }

    public void addAll(Collection<LevelModel> newLevels) {
        int oldSize = pack.getNumberOfLevels();
        pack.addAll(modelToLevel(newLevels));
        models.addAll(newLevels);
        newLevels.forEach((level) -> level.pack = this);

        modified = true;
        fireLevelInserted(oldSize, pack.getNumberOfLevels() - 1);
    }

    public void setLevel(LevelModel level, int index) {
        pack.setLevel(level.getLevel(), index);
        models.set(index, level);
        level.pack = this;

        modified = true;
        fireLevelChanged(index);
    }

    public LevelModel removeLevel(int index) {
        LevelModel old = models.get(index);
        pack.removeLevel(index);
        models.remove(index);
        old.pack = null;

        modified = true;
        fireLevelRemoved(index);

        return old;
    }

    public void removeLevel(LevelModel level) {
        removeLevel(level.getIndex());
    }

    public void removeAll(List<LevelModel> toRemove) {
        if (toRemove != null && toRemove.size() > 0) {
            pack.removeAll(modelToLevel(toRemove));
            models.removeAll(toRemove);
            toRemove.forEach((level) -> level.pack = null);

            modified = true;
            fireOrganisationChanged();
        }
    }

    public void swapLevels(int index1, int index2) {
        pack.swapLevels(index1, index2);

        LevelModel temp = models.get(index1);
        models.set(index1, models.get(index2));
        models.set(index2, temp);

        modified = true;
        fireLevelsSwapped(index1, index2);
    }

    public LevelModel getLevel(int index) {
        return models.get(index);
    }

    public List<LevelModel> getLevels() {
        return levelToModel(pack.getLevels());
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

    public Pack getPack() {
        return pack;
    }

    private List<Level> modelToLevel(Collection<LevelModel> models) {
        return models.stream()
                .map(LevelModel::getLevel)
                .collect(Collectors.toList());
    }

    private List<LevelModel> levelToModel(Collection<Level> levels) {
        return levels.stream()
                .map((l) -> models.get(l.getIndex()))
                .collect(Collectors.toList());
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