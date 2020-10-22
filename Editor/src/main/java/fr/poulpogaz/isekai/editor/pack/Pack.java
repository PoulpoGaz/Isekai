package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.controller.LevelsOrganisationListener;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

import javax.swing.event.EventListenerList;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public class Pack {

    public static final String FLOOR_SPRITE = "floor";
    public static final String WALL_SPRITE = "wall";
    public static final String TARGET_SPRITE = "target";
    public static final String CRATE_SPRITE = "crate";
    public static final String CRATE_ON_TARGET_SPRITE = "crate_on_target";

    public static final String NAME_PROPERTY = "NameProperty";
    public static final String AUTHOR_PROPERTY = "AuthorProperty";
    public static final String VERSION_PROPERTY = "VersionProperty";

    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private final EventListenerList listenerList = new EventListenerList();

    private HashMap<String, BufferedImage> images;

    private HashMap<String, AbstractSprite> sprites;

    private String name;
    private String author;
    private String version;

    private int tileWidth;
    private int tileHeight;

    private int gameWidth;
    private int gameHeight;

    private ArrayList<Level> levels;

    public Pack() {
        this.levels = new ArrayList<>();
        images = new HashMap<>();
        sprites = new HashMap<>();

        levels.add(new Level());
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
        levels.add(level);

        fireLevelInserted(getNumberOfLevels() - 1);
    }

    public void addLevel(Level level, int index) {
        levels.add(index, level);

        fireLevelInserted(index);
    }

    public void setLevel(Level level, int index) {
        levels.set(index, level);

        fireLevelChanged(index);
    }

    public void removeLevel(int index) {
        levels.remove(index);

        fireLevelRemoved(index);
    }

    public void removeLevel(Level level) {
        removeLevel(levels.indexOf(level));
    }

    public void swapLevels(int index1, int index2) {
        Level level1 = levels.get(index1);
        Level level2 = levels.get(index2);

        levels.set(index1, level2);
        levels.set(index2, level1);

        fireLevelsSwapped(index1, index2);
    }

    public Level getLevel(int index) {
        return levels.get(index);
    }


















    public BufferedImage getImage(String name) {
        return images.get(name);
    }

    public void putImage(String name, BufferedImage image) {
        images.put(name, image);
    }

    public AbstractSprite getSprite(String name) {
        return sprites.get(name);
    }

    public void putSprite(String name, AbstractSprite sprite) {
        sprites.put(name, sprite);
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public HashMap<String, BufferedImage> getImages() {
        return images;
    }









    /**
     *  LISTENERS
     */

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(property, listener);
    }

    private void firePropertyChange(String property, Object oldValue, Object newValue) {
        changeSupport.firePropertyChange(property, oldValue, newValue);
    }


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

    private <T extends EventListener> void fireListener(Class<T> type, Consumer<T> action) {
        T[] listeners = listenerList.getListeners(type);

        for (T listener : listeners) {
            action.accept(listener);
        }
    }
}