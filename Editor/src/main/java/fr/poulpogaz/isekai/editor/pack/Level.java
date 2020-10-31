package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.MapSizeListener;
import fr.poulpogaz.isekai.editor.utils.Vector2i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Level extends Map<Level, Tile> {

    private static final Logger LOGGER = LogManager.getLogger(Level.class);

    public static final int MINIMUM_MAP_WIDTH = 5;
    public static final int MINIMUM_MAP_HEIGHT = 5;

    public static final int MAXIMUM_MAP_WIDTH = 64;
    public static final int MAXIMUM_MAP_HEIGHT = 64;

    public static final int DEFAULT_MAP_WIDTH = 9;
    public static final int DEFAULT_MAP_HEIGHT = 9;

    int index = -1;
    
    private Tile[][] tiles;

    private int width;
    private int height;

    private Vector2i playerPos;

    public Level() {
        this(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);
    }

    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new Tile[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = Tile.FLOOR;
            }
        }

        playerPos = new Vector2i(0, 0);
    }

    public void resize(int newWidth, int newHeight) {
        if (newWidth >= MINIMUM_MAP_WIDTH && newHeight >= MINIMUM_MAP_HEIGHT
                && (newWidth != width || newHeight != height)) {

            Tile[][] newArray = new Tile[newHeight][newWidth];

            for (int y = 0; y < newHeight; y++) {
                for (int x = 0; x < newWidth; x++) {

                    if (y < this.height && x < this.width) {
                        newArray[y][x] = tiles[y][x];
                    } else {
                        newArray[y][x] = Tile.WALL;
                    }
                }
            }

            tiles = newArray;
            this.width = newWidth;
            this.height = newHeight;

            fireSizeListener(width, height);
        }
    }

    protected void fireSizeListener(int width, int height) {
        for (MapSizeListener<Level> listener : sizeListeners) {
            listener.mapResized(this, width, height);
        }
    }

    @Override
    public void set(Tile tile, int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {

            if (tile.isSolid() && playerPos.equals(x, y)) {
                return;
            }

            tiles[y][x] = tile;

            fireChangeListener();
        }
    }

    @Override
    public Tile get(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[y][x];
        } else {
            LOGGER.warn("x or y out of bounds (x = {}, y = {}, w = {}, h = {}", x, y, width, height);

            return null;
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        resize(width, height);
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        resize(width, height);
    }

    public Vector2i getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(Vector2i pos) {
        if (pos != null && pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height) {
            Tile tile = tiles[pos.y][pos.x];

            if (!tile.isSolid()) {
                this.playerPos = pos;
            }

            fireChangeListener();
        }
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level)) return false;

        Level level = (Level) o;

        if (index != level.index) return false;
        if (width != level.width) return false;
        if (height != level.height) return false;
        if (!Arrays.deepEquals(tiles, level.tiles)) return false;
        return playerPos.equals(level.playerPos);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + Arrays.deepHashCode(tiles);
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + playerPos.hashCode();
        return result;
    }
}