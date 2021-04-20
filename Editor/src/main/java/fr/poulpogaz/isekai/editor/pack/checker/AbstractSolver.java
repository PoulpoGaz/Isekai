package fr.poulpogaz.isekai.editor.pack.checker;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import static fr.poulpogaz.isekai.editor.pack.Tile.*;

public abstract class AbstractSolver implements ISolver {

    protected static final int[][] MOVES = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    protected final Level level;
    protected final Tile[] map;
    protected final int width;
    protected final int height;

    protected final State defaultState;

    protected int numberOfWalls;
    protected int numberOfFloors;
    protected int numberOfTargets;
    protected int numberOfCrates;

    protected int status = CHECKING;

    protected final List<Integer> tileVisitedByPlayer;
    protected final Queue<Integer> tileToVisit;

    protected final boolean[] reachableTiles;

    public AbstractSolver(Level level) {
        this.level = level;
        this.width = level.getWidth();
        this.height = level.getHeight();

        map = new Tile[width * height];

        initializeMap();

        defaultState = new State();
        initializeState();

        tileVisitedByPlayer = new ArrayList<>(numberOfFloors + numberOfTargets);
        tileToVisit = new ArrayBlockingQueue<>(numberOfFloors + numberOfTargets);

        reachableTiles = new boolean[map.length];
    }

    protected void initializeMap() {
        for (int i = 0; i < map.length; i++) {
            Tile tile = level.get(i % width, i / width);

            switch (tile) {
                case WALL -> {
                    numberOfWalls++;
                    map[i] = tile;
                }
                case FLOOR -> {
                    numberOfFloors++;
                    map[i] = tile;
                }
                case TARGET -> {
                    numberOfTargets++;
                    map[i] = tile;
                }
                case CRATE_ON_TARGET -> {
                    map[i] = TARGET;
                    numberOfCrates++;
                }
                case CRATE -> {
                    map[i] = FLOOR;
                    numberOfCrates++;
                }
            }
        }
    }

    protected void initializeState() {
        Vector2i player = level.getPlayerPos();

        defaultState.playerIndex = player.y * width + player.x;
        defaultState.cratesIndex = new int[numberOfCrates];

        int n = 0;
        for (int i = 0; i < map.length; i++) {
            if (level.get(i % width, i / width).isCrate()) {
                defaultState.cratesIndex[n] = i;
                n++;
            }
        }
    }

    @Override
    public abstract boolean check();

    protected boolean checkDeadlock(State state, Tile[] mapWithCrates) {
        //System.out.println("Checking deadlock(s)");
        for (int cratePos : state.cratesIndex) {
            if (map[cratePos] == TARGET) {
                continue;
            }

            boolean xDeadlock = xAxisDeadlock(mapWithCrates, cratePos);
            boolean yDeadlock = yAxisDeadlock(mapWithCrates, cratePos);

            //System.out.printf("Crate at %d %d (%d)%n", cratePos % width, cratePos / width, cratePos);
            //System.out.printf("X axis deadlock: %s, Y axis deadlock: %s%n", xDeadlock, yDeadlock);

            if (xDeadlock && yDeadlock) {
                //System.out.println("Deadlock");
                return true;
            }
        }

        return false;
    }

    protected void fillMapWithCrates(State state, Tile[] map) {
        for (int pos : state.cratesIndex) {
            switch (map[pos]) {
                case FLOOR -> map[pos] = CRATE;
                case TARGET -> map[pos] = CRATE_ON_TARGET;
            }
        }
    }

    protected void unfillMapWithCrates(State state, Tile[] map) {
        for (int pos : state.cratesIndex) {
            switch (map[pos]) {
                case CRATE -> map[pos] = FLOOR;
                case CRATE_ON_TARGET -> map[pos] = TARGET;
            }
        }
    }

    protected void getReachableTiles(State state, Tile[] mapWithCrates, boolean[] reachableTiles) {
        Arrays.fill(reachableTiles, false);
        tileVisitedByPlayer.clear();
        tileToVisit.clear();

        tileVisitedByPlayer.add(state.playerIndex);
        tileToVisit.offer(state.playerIndex);
        reachableTiles[state.playerIndex] = true;

        while (!tileToVisit.isEmpty()) {
            int pos = tileToVisit.poll();

            for (int[] move : MOVES) {
                int i = adjacentPos(pos, move[0], move[1]);

                if (outside(i)) {
                    continue;
                }

                if (!tileVisitedByPlayer.contains(i)) {
                    if (!mapWithCrates[i].isSolid()) {
                        tileToVisit.offer(i);
                        reachableTiles[i] = true;
                    }

                    tileVisitedByPlayer.add(i);
                }
            }
        }
    }

    protected boolean isCrateReachableFrom(int crate, int relativeX, int relativeY) {
        int adjacent = adjacentPos(crate, relativeX, relativeY);

        return reachableTiles[adjacent];
    }

    // http://sokobano.de/wiki/index.php?title=How_to_detect_deadlocks#Detecting_freeze_deadlocks
    protected boolean xAxisDeadlock(Tile[] map, int cratePos) {
        if (isWall(map, cratePos, 1, 0) || isWall(map, cratePos, -1, 0)) {
            return true;
        }

        return false;
    }

    protected boolean yAxisDeadlock(Tile[] map, int cratePos) {
        if (isWall(map, cratePos, 0, 1) || isWall(map, cratePos, 0, -1)) {
            return true;
        }

        return false;
    }

    protected boolean isWall(Tile[] map, int index, int dirX, int dirY) {
        int i = adjacentPos(index, dirX, dirY);

        return outside(i) || map[i] == WALL;
    }

    protected int adjacentPos(int index, int dirX, int dirY) {
        int x = index % width + dirX;
        int y = index / width + dirY; // important integer division

        return y * width + x;
    }

    protected State createChildState(Tile[] map, State state, int crateToMove, int crateIndex, int dirX, int dirY) {
        int newPos = adjacentPos(crateToMove, dirX, dirY);
        int backwardPos = adjacentPos(crateToMove, -dirX, -dirY); // dirX = 0 or dirY = 0

        //System.out.printf("New pos: %d %d (%d), Backward pos: %d %d (%d)%n", newPos % width, newPos / width, newPos, backwardPos % width, backwardPos / width, backwardPos);
        //System.out.printf("Outside: %s, %s%n", outside(newPos), outside(backwardPos));
        //System.out.printf("Tile: %s, %s%n", map[newPos], map[backwardPos]);

        if (outside(newPos) || outside(backwardPos) || map[backwardPos].isSolid() || map[newPos].isSolid()) {
            //System.out.println("No");
            return null;
        }

        State newState = new State();
        newState.parent = state;
        newState.playerIndex = crateToMove;
        newState.cratesIndex = state.cratesIndex.clone();
        newState.cratesIndex[crateIndex] = newPos;

        return newState;
    }

    protected boolean outside(int index) {
        return index < 0 || index >= map.length;
    }

    protected boolean isSolution(State state) {
        for (int i = 0; i < state.cratesIndex.length; i++) {
            int pos = state.cratesIndex[i];

            if (map[pos] != TARGET) {
                return false;
            }
        }

        return true;
    }

    // For debug
    protected String asString(State state) {
        Tile[] map = this.map.clone();
        fillMapWithCrates(state, map);

        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = y * width + x;

                boolean player = i == state.playerIndex;

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
    public void cancel() {
        synchronized (this) {
            if (status == CHECKING) {
                status = CANCELED;
            }
        }
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public Level getLevel() {
        return level;
    }
}