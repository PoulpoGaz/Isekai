package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.json.IJsonWriter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Pack {

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

    public void putSprite(AbstractSprite sprite) {
        sprites.put(sprite.getName(), sprite);
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
}