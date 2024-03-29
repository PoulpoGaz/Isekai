package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.LevelModel;
import fr.poulpogaz.isekai.editor.ui.Icons;

import javax.swing.*;
import javax.swing.undo.UndoableEdit;

public class PaintTool implements Tool {

    private static final PaintTool INSTANCE = new PaintTool();

    private PaintEdit.Builder builder = null;

    private PaintTool() {

    }

    private boolean apply(LevelModel level, Tile tile, int x, int y) {
        if (level.get(x, y) == tile) {
            return false;
        }

        return level.set(tile, x, y);
    }

    @Override
    public void press(LevelModel level, Tile tile, int x, int y) {
        Tile old = level.get(x, y);

        if (builder == null) {
            builder = new PaintEdit.Builder(level, old, tile);
        }

        if (apply(level, tile, x, y)) {
            builder.add(y * level.getWidth() + x);
        }
    }

    @Override
    public UndoableEdit release(LevelModel level, Tile tile, int x, int y) {
        if (builder != null) {
            PaintEdit action = builder.build();
            builder = null;

            return action;
        }
        return null;
    }

    @Override
    public Icon getIcon() {
        return Icons.get("icons/edit.svg");
    }

    public static PaintTool getInstance() {
        return INSTANCE;
    }
}