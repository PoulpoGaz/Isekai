package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.commons.pack.Level;
import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.ui.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2i;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class LevelModel extends Model {

    private static final Logger LOGGER = LogManager.getLogger(LevelModel.class);

    protected final List<LevelSizeListener> sizeListeners = new ArrayList<>();
    protected boolean modifyingMap = false;

    protected PackModel pack;

    protected Level level;

    public LevelModel() {
        level = new Level();
    }

    public LevelModel(int width, int height) {
        level = new Level(width, height);
    }

    public LevelModel(Level level) {
        this.level = level;
    }

    public boolean resize(int newWidth, int newHeight) {
        if (level.resize(newWidth, newHeight)) {
            setModified(true);
            fireSizeListener(newWidth, newHeight);

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
        if (level.set(tile, x, y)) {
            setModified(true);
            fireChangeListener();

            return true;
        }

        return false;
    }

    public Tile get(int x, int y) {
        return level.get(x, y);
    }

    public boolean isInside(int x, int y) {
        return level.isInside(x, y);
    }

    public Tile[][] getTiles() {
        return level.getTiles();
    }

    public int getWidth() {
        return level.getWidth();
    }

    public void setWidth(int width) {
        resize(width, level.getHeight());
    }

    public int getHeight() {
        return level.getHeight();
    }

    public void setHeight(int height) {
        resize(level.getWidth(), height);
    }

    public Vector2i getPlayerPos() {
        return level.getPlayerPos();
    }

    public boolean setPlayerPos(Vector2i pos) {
        if (level.setPlayerPos(pos)) {
            setModified(true);
            fireChangeListener();

            return true;
        }

        return false;
    }

    public void setModified(boolean modified) {
        if (pack != null) {
            pack.setModified(modified);
        }
    }

    /**
     * A level is playable when:
     *  - the number of crates is equal to the number of target and strictly superior to 0
     */
    public boolean isPlayable() {
        int nCrates = 0;
        int nTarget = 0;

        for (Tile[] tile : level.getTiles()) {
            for (Tile t : tile) {
                if (t.isCrate()) {
                    nCrates++;
                }

                if (t.isTarget()) {
                    nTarget++;
                }
            }
        }

        return nCrates == nTarget && nCrates > 0;
    }

    /**
     * Methods used by reader
     * They don't fire any view
     */
    void setTile(Tile tile, int x, int y) {
        level.set(tile, x, y);
    }

    void setPlayerPos(int x, int y) {
        level.setPlayerPos(new Vector2i(x, y));
    }

    public int getIndex() {
        return level.getIndex();
    }

    public PackModel getPack() {
        return pack;
    }

    Level getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LevelModel that = (LevelModel) o;

        return level.equals(that.level);
    }

    @Override
    public int hashCode() {
        return level.hashCode();
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