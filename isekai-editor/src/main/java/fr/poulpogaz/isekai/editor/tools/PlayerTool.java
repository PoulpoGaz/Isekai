package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.LevelModel;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2i;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

public class PlayerTool implements Tool {

    private static final PlayerTool INSTANCE = new PlayerTool();

    private static final Logger LOGGER = LogManager.getLogger(PlayerTool.class);

    private PlayerTool() {
    }

    private Builder builder = null;

    @Override
    public void press(LevelModel level, Tile tile, int x, int y) {
        Tile old = level.get(x, y);
        Vector2i oldPlayerPos = level.getPlayerPos();
        if (apply(level, tile, x, y)) {
            if (builder == null) {
                builder = new Builder(level, oldPlayerPos);
            }

            builder.set(x, y, tile, old);
        }
    }

    @Override
    public UndoableEdit release(LevelModel level, Tile tile, int x, int y) {
        if (builder != null) {
            PlayerEdit action = builder.build();
            builder = null;

            return action;
        }
        return null;
    }

    private boolean apply(LevelModel level, Tile tile, int x, int y) {
        Tile current = level.get(x, y);

        if (current.isSolid() && !tile.isSolid()) {
            level.set(tile, x, y);
            level.setPlayerPos(new Vector2i(x, y));

            return true;
        } else if (!current.isSolid()) {
            level.setPlayerPos(new Vector2i(x, y));

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

        private final LevelModel level;
        private final int x;
        private final int y;
        private final Vector2i oldPlayerPos;
        private final Tile tile;
        private final Tile oldTile;

        public PlayerEdit(LevelModel level, int x, int y, Vector2i oldPlayerPos, Tile tile, Tile oldTile) {
            this.level = level;
            this.x = x;
            this.y = y;
            this.oldPlayerPos = oldPlayerPos;
            this.tile = tile;
            this.oldTile = oldTile;
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();

            LOGGER.info("Redo PlayerEdit. Player pos: ({}; {}), Old player pos ({}). Replacing {} by {}", x, y, oldPlayerPos, oldTile, tile);
            apply(level, tile, x, y);
        }

        @Override
        public void undo() throws CannotUndoException {
            super.undo();

            LOGGER.info("Undo PlayerEdit. Player pos: ({}; {}), Old player pos ({}). Replacing {} by {}", x, y, oldPlayerPos, tile, oldTile);
            level.setPlayerPos(oldPlayerPos);
            level.set(oldTile, x, y);
        }
    }

    private class Builder {

        private final LevelModel level;
        private final Vector2i oldPlayerPos;
        private int x;
        private int y;
        private Tile tile;
        private Tile oldTile;

        public Builder(LevelModel level, Vector2i oldPlayerPos) {
            this.level = level;
            this.oldPlayerPos = new Vector2i(oldPlayerPos);
        }

        public PlayerEdit build() {
            return new PlayerEdit(level, x, y, oldPlayerPos, tile, oldTile);
        }

        public void set(int x, int y, Tile tile, Tile oldTile) {
            this.x = x;
            this.y = y;
            this.tile = tile;
            this.oldTile = oldTile;
        }
    }
}