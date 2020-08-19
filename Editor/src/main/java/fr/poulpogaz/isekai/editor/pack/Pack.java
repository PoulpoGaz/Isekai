package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Pack {

    public static final char PLAYER = '@';
    public static final char WALL = '#';
    public static final char FLOOR = ' ';
    public static final char CRATE = '$';
    public static final char TARGET = '.';
    public static final char CRATE_ON_TARGET = '*';
    public static final char PLAYER_ON_TARGET = '+';

    public static final String FLOOR_SPRITE = "floor";
    public static final String WALL_SPRITE = "wall";
    public static final String TARGET_SPRITE = "target";
    public static final String CRATE_SPRITE = "crate";
    public static final String CRATE_ON_TARGET_SPRITE = "crate_on_target";

    private HashMap<String, BufferedImage> images;

    private HashMap<String, AbstractSprite> sprites;

    private String packName;
    private String author;
    private String version;

    private int tileWidth;
    private int tileHeight;

    private int gameWidth;
    private int gameHeight;

    private ArrayList<Level> levels;

    private Timeline timeline;

    public Pack() {
        this.levels = new ArrayList<>();
        images = new HashMap<>();
        sprites = new HashMap<>();

        levels.add(new Level());
    }

    public Level newLevel() {
        Level newLevel = new Level();

        levels.add(newLevel);

        return newLevel;
    }

    public boolean removeLevel(Level level) {
        return levels.remove(level);
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public HashMap<String, BufferedImage> getImages() {
        return images;
    }
}