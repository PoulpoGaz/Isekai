package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import java.awt.*;

public class PlayerTool implements Tool {

    private static final PlayerTool INSTANCE = new PlayerTool();

    private static final Logger LOGGER = LogManager.getLogger(PlayerTool.class);

    private PlayerTool() {
    }

    private Builder builder = null;

    @Override
    public void press(Level level, Tile tile, int x, int y) {
        Tile old = level.get(x, y);
        Point oldPlayerPos = level.getPlayerPos();
        if (apply(level, tile, x, y)) {
            if (builder == null) {
                builder = new Builder(level, oldPlayerPos);
            }

            builder.set(x, y, tile, old);
        }
    }

    @Override
    public UndoableEdit release(Level level, Tile tile, int x, int y) {
        if (builder != null) {
            PlayerEdit action = builder.build();
            builder = null;

            return action;
        }
        return null;
    }

    private boolean apply(Level level, Tile tile, int x, int y) {
        Tile current = level.get(x, y);

        if (current.isSolid() && !tile.isSolid()) {
            level.set(tile, x, y);
            level.setPlayerPos(new Point(x, y));

            return true;
        } else if (!current.isSolid()) {
            level.setPlayerPos(new Point(x, y));

            return true;
        }

        return false;
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(PackSprites.getPlayer());
    }

    public static PlayerTool getInstance() {
        return INSTANCE;
    }

    private class PlayerEdit extends AbstractUndoableEdit {

        private final Level level;
        private final int x;
        private final int y;
        private final int oldPlayerX;
        private final int oldPlayerY;
        private final Tile tile;
        private final Tile oldTile;

        public PlayerEdit(Level level, int x, int y, int oldPlayerX, int oldPlayerY, Tile tile, Tile oldTile) {
            this.level = level;
            this.x = x;
            this.y = y;
            this.oldPlayerX = oldPlayerX;
            this.oldPlayerY = oldPlayerY;
            this.tile = tile;
            this.oldTile = oldTile;
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();

            LOGGER.info("Redo PlayerEdit. Player pos: ({}; {}), Old player pos ({}; {}). Replacing {} by {}", x, y, oldPlayerX, oldPlayerY, oldTile, tile);
            apply(level, tile, x, y);
        }

        @Override
        public void undo() throws CannotUndoException {
            super.undo();

            LOGGER.info("Undo PlayerEdit. Player pos: ({}; {}), Old player pos ({}; {}). Replacing {} by {}", x, y, oldPlayerX, oldPlayerY, tile, oldTile);
            level.setPlayerPos(new Point(oldPlayerX, oldPlayerY));
            level.set(oldTile, x, y);
        }
    }

    private class Builder {

        private final Level level;
        private final int oldPlayerX;
        private final int oldPlayerY;
        private int x;
        private int y;
        private Tile tile;
        private Tile oldTile;

        public Builder(Level level, Point oldPlayerPos) {
            this.level = level;
            this.oldPlayerX = oldPlayerPos.x;
            this.oldPlayerY = oldPlayerPos.y;
        }

        public PlayerEdit build() {
            return new PlayerEdit(level, x, y, oldPlayerX, oldPlayerY, tile, oldTile);
        }

        public void set(int x, int y, Tile tile, Tile oldTile) {
            this.x = x;
            this.y = y;
            this.tile = tile;
            this.oldTile = oldTile;
        }
    }
}