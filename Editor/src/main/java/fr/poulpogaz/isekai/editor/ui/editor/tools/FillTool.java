package fr.poulpogaz.isekai.editor.ui.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
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
    public void apply(Level level, Tile tile, int tileX, int tileY) {
        Tile toBeReplaced = level.getTile(tileX, tileY);

        if (tile == toBeReplaced) {
            return;
        }

        Stack<Point> points = new Stack<>();
        points.push(new Point(tileX, tileY));
        level.setTile(tileX, tileY, tile);

        while (!points.isEmpty()) {
            Point point = points.pop();

            for (Point direction : directions) {
                Point p2 = new Point(point.x + direction.x, point.y + direction.y);

                if (p2.x < 0 || p2.y < 0 || p2.x >= level.getWidth() || p2.y >= level.getHeight()) {
                    continue;
                }

                if (level.getTile(p2.x, p2.y) == toBeReplaced) {
                    Vector2i pos = level.getPlayerPos();

                    if (pos.x == p2.x && pos.y == p2.y && toBeReplaced.isSolid()) {
                        continue;
                    }

                    level.setTile(p2.x, p2.y, tile);

                    points.push(p2);
                }
            }
        }
    }

    @Override
    public AbstractSprite getToolSprite(Pack pack, Tile tile) {
        return tile.getSprite(pack);
    }

    public static FillTool getInstance() {
        return INSTANCE;
    }
}