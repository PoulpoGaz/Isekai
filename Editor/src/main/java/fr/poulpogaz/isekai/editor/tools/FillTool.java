package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class FillTool implements Tool {

    private static final FillTool INSTANCE = new FillTool();

    private FillTool() {

    }

    @Override
    public void apply(Level level, Tile tile, int tileX, int tileY) {
        Tile toBeReplaced = level.getTile(tileX, tileY);

        if (tile == toBeReplaced) {
            return;
        }

        ArrayList<Point> visitedPoints = new ArrayList<>();

        Stack<Point> points = new Stack<>();
        points.push(new Point(tileX, tileY));

        while (!points.isEmpty()) {
            Point point = points.pop();

            visitedPoints.add(point);
            level.setTile(point.x, point.y, tile);

            for (int y = -1; y < 2; y++) {
                int y2 = y + point.y;

                if (y2 < 0 || y2 >= level.getHeight()) {
                    continue;
                }

                for (int x = -1; x < 2; x++) {
                    if (x == 0 && y == 0) {
                        continue;
                    }

                    int x2 = x + point.x;
                    if (x2 < 0 || x2 >= level.getWidth()) {
                        continue;
                    }

                    Point p = new Point(x2, y2);

                    if (level.getTile(x2, y2) == toBeReplaced && !visitedPoints.contains(p)) {
                        points.push(p);
                    }
                }
            }
        }
    }

    public static FillTool getInstance() {
        return INSTANCE;
    }
}