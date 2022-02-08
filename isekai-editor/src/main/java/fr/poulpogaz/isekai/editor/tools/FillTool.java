package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.LevelModel;
import fr.poulpogaz.isekai.editor.ui.Icons;
import org.joml.Vector2i;

import javax.swing.*;
import javax.swing.undo.UndoableEdit;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FillTool implements Tool {

    private static final FillTool INSTANCE = new FillTool();

    private static final Vector2i[] directions = new Vector2i[] {
            new Vector2i(-1, 0), // LEFT
            new Vector2i(1, 0), // RIGHT
            new Vector2i(0, -1), // DOWN
            new Vector2i(0, 1) // UP
    };

    private PaintEdit.Builder builder = null;

    private FillTool() {

    }

    @Override
    public void press(LevelModel level, Tile tile, int x, int y) {
        Tile old = level.get(x, y);

        if (builder == null) {
            builder = new PaintEdit.Builder(level, old, tile);
        }

        List<Integer> replaced = apply(level, tile, x, y);
        if (replaced != null) {
            builder.addAll(replaced);
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

    private List<Integer> apply(LevelModel level, Tile tile, int x, int y) {
        Tile toBeReplaced = level.get(x, y);

        Vector2i initialPos = new Vector2i(x, y);
        if (canReplace(level.getPlayerPos(), initialPos, tile, toBeReplaced)) {
            return null;
        }

        ArrayList<Integer> replaced = new ArrayList<>();
        Stack<Vector2i> points = new Stack<>();
        points.push(initialPos);

        level.setModifyingMap(true);
        level.set(tile, x, y);

        while (!points.isEmpty()) {
            Vector2i point = points.pop();

            replaced.add(point.y * level.getWidth() + point.x);

            for (Vector2i direction : directions) {
                Vector2i p2 = new Vector2i(point.x + direction.x, point.y + direction.y);

                if (p2.x < 0 || p2.y < 0 || p2.x >= level.getWidth() || p2.y >= level.getHeight()) {
                    continue;
                }

                if (canReplace(level.getPlayerPos(), p2, level.get(p2.x, p2.y), toBeReplaced)) {
                    level.set(tile, p2.x, p2.y);

                    points.push(p2);
                }
            }
        }
        level.setModifyingMap(false);

        return replaced;
    }

    private boolean canReplace(Vector2i playerPos, Vector2i pos, Tile current, Tile toBeReplaced) {
        if (toBeReplaced == current) {
            if (toBeReplaced.isSolid()) {
                return true;
            }

            return !playerPos.equals(pos);
        }

        return false;
    }

    @Override
    public Icon getIcon() {
        return Icons.get("icons/fill.svg");
    }

    public static FillTool getInstance() {
        return INSTANCE;
    }
}