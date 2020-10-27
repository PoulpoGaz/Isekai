package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

import java.awt.*;
import java.util.Stack;

public class FillTool implements Tool {

    private static final FillTool INSTANCE = new FillTool();
    private static final Point[] directions = new Point[] {
        new Point(-1, 0), // LEFT
        new Point(1, 0), // RIGHT
        new Point(0, -1), // DOWN
        new Point(0, 1) // UP
    };

    private FillTool() {

    }

    @Override
    public <M extends Map<E>, E> void apply(M map, E element, int x, int y) {
        E toBeReplaced = map.get(x, y);

        if (element == toBeReplaced) {
            return;
        }

        Stack<Point> points = new Stack<>();
        points.push(new Point(x, y));
        map.set(element, x, y);

        boolean isLevel = map instanceof Level;
        Level level = isLevel ? (Level) map : null;
        Tile tile = isLevel ? (Tile) element : null;

        while (!points.isEmpty()) {
            Point point = points.pop();

            for (Point direction : directions) {
                Point p2 = new Point(point.x + direction.x, point.y + direction.y);

                if (p2.x < 0 || p2.y < 0 || p2.x >= map.getWidth() || p2.y >= map.getHeight()) {
                    continue;
                }

                if (map.get(p2.x, p2.y) == toBeReplaced) {
                    if (isLevel) {
                        Vector2i pos = level.getPlayerPos();

                        if (pos.x == p2.x && pos.y == p2.y && tile.isSolid()) {
                            continue;
                        }
                    }

                    map.set(element, p2.x, p2.y);

                    points.push(p2);
                }
            }
        }
    }

    /*@Override
    public AbstractSprite getToolSprite(Pack pack, Tile tile) {
        return tile.getSprite(pack);
    }*/

    public static FillTool getInstance() {
        return INSTANCE;
    }
}