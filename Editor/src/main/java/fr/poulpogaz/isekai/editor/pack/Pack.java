package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.*;
import fr.poulpogaz.isekai.editor.ui.Model;

import java.util.*;

public class Pack extends Model {

    public static final String NAME_PROPERTY = "NameProperty";
    public static final String AUTHOR_PROPERTY = "AuthorProperty";
    public static final String VERSION_PROPERTY = "VersionProperty";

    private HashSet<PackImage> images;
    private HashMap<String, AbstractSprite> sprites;

    private String name;
    private String author;
    private String version;

    private ArrayList<Level> levels;

    public Pack() {
        this.levels = new ArrayList<>();
        images = new HashSet<>();
        sprites = new HashMap<>();

        addLevel(new Level());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            String old = this.name;

            this.name = name;

            changeSupport.firePropertyChange(NAME_PROPERTY, old, name);
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (!Objects.equals(this.author, author)) {
            String old = this.author;

            this.author = author;

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

            firePropertyChange(VERSION_PROPERTY, old, version);
        }
    }

    public int getNumberOfLevels() {
        return levels.size();
    }

    public void addLevel(Level level) {
        level.index = levels.size();
        levels.add(level);

        fireLevelInserted(getNumberOfLevels() - 1);
    }

    public void addLevel(Level level, int index) {
        level.index = index;
        levels.add(index, level);

        for (int i = index + 1; i < levels.size(); i++) {
            levels.get(i).index = i;
        }

        fireLevelInserted(index);
    }

    public void setLevel(Level level, int index) {
        level.index = index;
        Level old = levels.set(index, level);
        old.index = -1;

        fireLevelChanged(index);
    }

    public void removeLevel(int index) {
        Level old = levels.remove(index);
        old.index = 0;

        for (int i = index; i < levels.size(); i++) {
            levels.get(i).index = i;
        }

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

            fireNewLevels();
        }
    }

    public void addImage(PackImage image) {
        images.add(image);
    }

    public PackImage getImage(String name) {
        for (PackImage image : images) {
            if (image.getName().equals(name)) {
                return image;
            }
        }

        return null;
    }

    public HashSet<PackImage> getImages() {
        return images;
    }

    public AbstractSprite getSprite(String name) {
        return sprites.get(name);
    }

    public void addSprite(AbstractSprite sprite) {
        sprites.put(sprite.getName(), sprite);
    }

    public Collection<AbstractSprite> getSprites() {
        return sprites.values();
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

    private void fireLevelInserted(int index) {
        fireListener(LevelsOrganisationListener.class, (t) -> t.levelInserted(index));
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

    /**
     * A pack is valid for TI calculators if
     *  -the dimension of the sprites is 16x16
     *
     * @return true if the pack is valid for calculators
     */
    public boolean isTIPack() {
        for (AbstractSprite sprite : sprites.values()) {
            int w = 0;
            int h = 0;

            if (sprite instanceof BasicSprite) {
                BasicSprite s = (BasicSprite) sprite;
                w = s.getWidth();
                h = s.getHeight();
            } else if (sprite instanceof SubSprite) {
                SubSprite s = (SubSprite) sprite;

                w = s.getWidth();
                h = s.getHeight();
            } else if (sprite instanceof AnimatedSprite) {
                return false; // TODO: add animated sprite support
            }

            if (w != 16 || h != 16) {
                return false;
            }
        }

        return true;
    }
}