package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.ui.Icons;

import javax.swing.*;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FillTool implements Tool {

    private static final FillTool INSTANCE = new FillTool();

    private static final Point[] directions = new Point[] {
            new Point(-1, 0), // LEFT
            new Point(1, 0), // RIGHT
            new Point(0, -1), // DOWN
            new Point(0, 1) // UP
    };

    private PaintEdit.Builder builder = null;

    private FillTool() {

    }

    @Override
    public void press(Level level, Tile tile, int x, int y) {
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
    public UndoableEdit release(Level level, Tile tile, int x, int y) {
        if (builder != null) {
            PaintEdit action = builder.build();
            builder = null;

            return action;
        }
        return null;
    }

    private List<Integer> apply(Level level, Tile tile, int x, int y) {
        Tile toBeReplaced = level.get(x, y);

        Point initialPos = new Point(x, y);
        if (canReplace(level.getPlayerPos(), initialPos, tile, toBeReplaced)) {
            return null;
        }

        ArrayList<Integer> replaced = new ArrayList<>();
        Stack<Point> points = new Stack<>();
        points.push(initialPos);

        level.setModifyingMap(true);
        level.set(tile, x, y);

        while (!points.isEmpty()) {
            Point point = points.pop();

            replaced.add(point.y * level.getWidth() + point.x);

            for (Point direction : directions) {
                Point p2 = new Point(point.x + direction.x, point.y + direction.y);

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

    private boolean canReplace(Point playerPos, Point pos, Tile current, Tile toBeReplaced) {
        if (toBeReplaced == current) {
            if (toBeReplaced.isSolid()) {
                return true;
            }

            return pos.x != playerPos.x || pos.y != playerPos.y;
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