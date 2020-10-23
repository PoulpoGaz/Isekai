package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.controller.LevelsOrganisationListener;
import fr.poulpogaz.isekai.editor.controller.Model;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Pack extends Model {

    public static final String NAME_PROPERTY = "NameProperty";
    public static final String AUTHOR_PROPERTY = "AuthorProperty";
    public static final String VERSION_PROPERTY = "VersionProperty";

    private HashMap<String, BufferedImage> images;

    private HashMap<String, AbstractSprite> sprites;

    private String name;
    private String author;
    private String version;

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
}