package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.solver.AbstractSolver;
import fr.poulpogaz.isekai.editor.ui.Model;
import fr.poulpogaz.isekai.editor.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Level extends Model {

    private static final Logger LOGGER = LogManager.getLogger(Level.class);

    public static final int MINIMUM_MAP_WIDTH = 5;
    public static final int MINIMUM_MAP_HEIGHT = 5;

    public static final int MAXIMUM_MAP_WIDTH = 255;
    public static final int MAXIMUM_MAP_HEIGHT = 255;

    public static final int DEFAULT_MAP_WIDTH = 9;
    public static final int DEFAULT_MAP_HEIGHT = 9;

    protected final List<LevelSizeListener> sizeListeners = new ArrayList<>();
    protected boolean modifyingMap = false;

    protected Pack pack;
    int index = -1;
    
    private Tile[][] tiles;

    protected int width;
    protected int height;

    protected Point playerPos;

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

        playerPos = new Point(0, 0);
    }

    public boolean resize(int newWidth, int newHeight) {
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

            setModified(true);
            fireSizeListener(width, height);

            return true;
        }

        return false;
    }

    protected void fireSizeListener(int width, int height) {
        for (LevelSizeListener listener : sizeListeners) {
            listener.levelResized(this, width, height);
        }
    }

    public boolean set(Tile tile, int x, int y) {
        if (isInside(x, y)) {
            if (tile.isSolid() && Utils.equals(playerPos, x, y)) {
                return false;
            }

            tiles[y][x] = tile;

            setModified(true);
            fireChangeListener();

            return true;
        }

        return false;
    }

    public Tile get(int x, int y) {
        if (isInside(x, y)) {
            return tiles[y][x];
        } else {
            LOGGER.warn("Coordinates out of bounds (x = {}, y = {}, w = {}, h = {})", x, y, width, height);

            return null;
        }
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        resize(width, height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        resize(width, height);
    }

    public Point getPlayerPos() {
        return playerPos;
    }

    public boolean setPlayerPos(Point pos) {
        if (pos != null && isInside(pos.x, pos.y)) {
            Tile tile = tiles[pos.y][pos.x];

            if (!tile.isSolid()) {
                this.playerPos = pos;

                setModified(true);
                fireChangeListener();

                return true;
            }
        }

        return false;
    }

    public void setModified(boolean modified) {
        if (pack != null) {
            pack.setModified(modified);
        }
    }

    /**
     * A level is valid when:
     *  - the player can't go outside
     *  - the number of crates is equal to the number of target and strictly superior to 0
     *  - all crates can move except when the crate is on a target
     */
    public boolean isValid() {
        if (canGoOutside()) {
            return false;
        }

        int nCrates = 0;
        int nTarget = 0;

        for (int y = 0; y < tiles.length; y++) {
            Tile[] tile = tiles[y];

            for (int x = 0; x < tile.length; x++) {
                Tile t = tile[x];

                if (t.isCrate()) {
                    nCrates++;
                }

                if (t.isTarget()) {
                    nTarget++;
                }

                if (t == Tile.CRATE) {
                    if (!canMove(x, y)) {
                        return false;
                    }
                }
            }
        }

        return nCrates == nTarget && nCrates > 0;
    }

    private boolean canGoOutside() {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> positions = new ArrayDeque<>();

        positions.offer(playerPos.y * width + playerPos.x);
        visited.add(positions.peek());

        while (!positions.isEmpty()) {
            int pos = positions.poll();

            int x = pos % width;
            int y = pos / width;

            for (int[] move : AbstractSolver.MOVES) {
                int x2 = x + move[0];
                int y2 = y + move[1];

                if (isInside(x2, y2)) {
                    if (tiles[y2][x2] != Tile.WALL) {
                        int pos2 = y2 * width + x2;

                        if (visited.add(pos2)) {
                            positions.offer(pos2);
                        }
                    }
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canMove(int x, int y) {
        for (int[] move : AbstractSolver.MOVES) {
            int destX = x + move[0];
            int destY = y + move[1];

            int backX = x - move[0];
            int backY = y - move[1];

            if (tiles[backY][backX] != Tile.WALL && tiles[destY][destX] != Tile.WALL) {
                return true;
            }
        }

        return false;
    }

    /**
     * Methods used by reader
     */
    void setTile(Tile tile, int x, int y) {
        tiles[y][x] = tile;
    }

    void setPlayerPos(int x, int y) {
        playerPos = new Point(x, y);
    }

    public int getIndex() {
        return index;
    }

    public Pack getPack() {
        return pack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level level)) return false;

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

    protected void fireChangeListener() {
        if (!modifyingMap) {
            ChangeEvent event = new ChangeEvent(this);

            fireListener(ChangeListener.class, (l) -> l.stateChanged(event));
        }
    }

    public void addSizeListener(LevelSizeListener listener) {
        sizeListeners.add(listener);
    }

    public void removeSizeListener(LevelSizeListener listener) {
        sizeListeners.remove(listener);
    }

    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    public boolean isModifyingMap() {
        return modifyingMap;
    }

    public void setModifyingMap(boolean modifyingMap) {
        if (this.modifyingMap != modifyingMap) {
            this.modifyingMap = modifyingMap;

            fireChangeListener();
        }
    }
}