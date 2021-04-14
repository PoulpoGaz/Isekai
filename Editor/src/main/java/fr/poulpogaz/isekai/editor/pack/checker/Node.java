package fr.poulpogaz.isekai.editor.pack.checker;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;

import java.util.Arrays;

import static fr.poulpogaz.isekai.editor.pack.Tile.*;

public class Node {

    private final Tile[] map;
    private final int width;
    private final int height;
    private final int playerX;
    private final int playerY;
    private final int nbTargets;
    private final int nbCrateOnTargets;

    public Node(Level level) {
        this.width = level.getWidth();
        this.height = level.getHeight();
        this.playerX = level.getPlayerPos().x;
        this.playerY = level.getPlayerPos().y;
        this.map = new Tile[width * height];

        int tempTarget = 0;
        int tempCrateOnTarget = 0;

        for (int i = 0; i < map.length; i++) {
            map[i] = level.get(i % width, i / width);

            if (map[i] == CRATE_ON_TARGET) {
                tempTarget++;
                tempCrateOnTarget++;
            }
            if (map[i] == TARGET) {
                tempTarget++;
            }
        }

        this.nbTargets = tempTarget;
        this.nbCrateOnTargets = tempCrateOnTarget;
    }

    public Node(Tile[] map, int width, int height, int playerX, int playerY, int nbTargets, int nbCrateOnTargets) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.playerX = playerX;
        this.playerY = playerY;
        this.nbTargets = nbTargets;
        this.nbCrateOnTargets = nbCrateOnTargets;
    }

    protected Node createChild(int moveX, int moveY) {
        int newX = playerX + moveX;
        int newY = playerY + moveY;

        if (isOutside(newX, newY)) {
            return null;
        }

        int i = newY * width + newX;

        return switch (map[i]) {
            case FLOOR, TARGET -> new Node(map, width, height, newX, newY, nbTargets, nbCrateOnTargets);
            case CRATE -> {
                int x2 = newX + moveX;
                int y2 = newY + moveY;

                if (isOutside(x2, y2)) {
                    yield null;
                }

                int i2 = y2 * width + x2;

                if (map[i2] == FLOOR) {
                    Tile[] newMap = map.clone();

                    newMap[i]  = FLOOR;
                    newMap[i2] = CRATE;

                    yield new Node(newMap, width, height, newX, newY, nbTargets, nbCrateOnTargets);
                } else if (map[i2] == TARGET) {
                    Tile[] newMap = map.clone();

                    newMap[i]  = FLOOR;
                    newMap[i2] = CRATE_ON_TARGET;

                    yield new Node(newMap, width, height, newX, newY, nbTargets, nbCrateOnTargets + 1);
                }

                yield null;
            }
            case CRATE_ON_TARGET -> {
                int x2 = newX + moveX;
                int y2 = newY + moveY;

                if (isOutside(x2, y2)) {
                    yield null;
                }

                int i2 = y2 * width + x2;

                if (map[i2] == FLOOR) {
                    Tile[] newMap = map.clone();

                    newMap[i]  = TARGET;
                    newMap[i2] = CRATE;

                    yield new Node(newMap, width, height, newX, newY, nbTargets, nbCrateOnTargets - 1);
                } else if (map[i2] == TARGET) {
                    Tile[] newMap = map.clone();

                    newMap[i]  = TARGET;
                    newMap[i2] = CRATE_ON_TARGET;

                    yield new Node(newMap, width, height, newX, newY, nbTargets, nbCrateOnTargets);
                }

                yield null;
            }
            default -> null;
        };
    }

    private boolean isOutside(int x, int y) {
        return x < 0 || y < 0 || x >= width || y >= height;
    }

    public boolean isSolution() {
        return nbCrateOnTargets == nbTargets;
    }

    public boolean isDeadlock() {


        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = y * width + x;

                boolean player = x == playerX && y == playerY;

                char c = switch (map[i]) {
                    case WALL -> '#';
                    case CRATE -> '$';
                    case TARGET -> player ? '+' : '.';
                    case CRATE_ON_TARGET -> '*';
                    case FLOOR -> player ? '@' : ' ';
                };

                builder.append(c);
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node node)) {
            return false;
        }

        if (width != node.width) return false;
        if (height != node.height) return false;
        if (playerX != node.playerX) return false;
        if (playerY != node.playerY) return false;
        if (nbTargets != node.nbTargets) return false;
        if (nbCrateOnTargets != node.nbCrateOnTargets) return false;

        return Arrays.equals(map, node.map);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(map);
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + playerX;
        result = 31 * result + playerY;
        result = 31 * result + nbTargets;
        result = 31 * result + nbCrateOnTargets;
        return result;
    }
}