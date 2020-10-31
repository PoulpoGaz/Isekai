package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public abstract class FillTool implements Tool {

    private static final Point[] directions = new Point[] {
        new Point(-1, 0), // LEFT
        new Point(1, 0), // RIGHT
        new Point(0, -1), // DOWN
        new Point(0, 1) // UP
    };

    public FillTool() {

    }

    @Override
    public <M extends Map<M, E>, E> void apply(M map, E element, int x, int y) {
        E toBeReplaced = map.get(x, y);

        Point initialPoint = new Point(x, y);

        if (equals(map, element, initialPoint, toBeReplaced)) {
            return;
        }

        Stack<Point> points = new Stack<>();
        points.push(initialPoint);
        map.set(element, x, y);

        while (!points.isEmpty()) {
            Point point = points.pop();

            for (Point direction : directions) {
                Point p2 = new Point(point.x + direction.x, point.y + direction.y);

                if (p2.x < 0 || p2.y < 0 || p2.x >= map.getWidth() || p2.y >= map.getHeight()) {
                    continue;
                }

                if (equals(map, map.get(p2.x, p2.y), p2, toBeReplaced)) {
                    map.set(element, p2.x, p2.y);

                    points.push(p2);
                }
            }
        }
    }

    protected abstract <M extends Map<M, E>, E> boolean equals(M map, E currentElement, Point currentElementPos, E elementToBeReplaced);

    @Override
    public Icon getIcon() {
        return IconLoader.loadSVGIcon("/icons/fill.svg");
    }
}