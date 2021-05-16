package fr.poulpogaz.isekai.editor.pack.solver;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;

import java.awt.*;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static fr.poulpogaz.isekai.editor.pack.Tile.*;

public abstract class AbstractSolver implements ISolver {

    public static final int[][] MOVES = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

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

    protected final Set<Integer> tileVisitedByPlayer;
    protected final Queue<Integer> tileToVisit;

    protected final boolean[] reachableTiles;
    protected final boolean[] deadlockTiles;

    public AbstractSolver(Level level) {
        this.level = level;
        this.width = level.getWidth();
        this.height = level.getHeight();

        map = new Tile[width * height];
        initializeMap();

        defaultState = new State();
        initializeState();

        tileVisitedByPlayer = new HashSet<>(numberOfFloors + numberOfTargets);
        tileToVisit = new ArrayBlockingQueue<>(numberOfFloors + numberOfTargets);

        reachableTiles = new boolean[map.length];
        deadlockTiles = new boolean[map.length];
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
        Point player = level.getPlayerPos();

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

    /**
     * We simulate a crate pushed by a player from
     * a target to all possible tiles.
     * All tiles where the crate can't go are deadlocks.
     * And we are repeating this for each crates.
     */
    protected void findDeadlockTiles() {
        Arrays.fill(deadlockTiles, true);

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> toVisit = new ArrayDeque<>();

        for (int target = 0; target < map.length; target++) {
            if (map[target] != TARGET) {
                continue;
            }

            visited.clear();

            visited.add(target);
            toVisit.offer(target);

            deadlockTiles[target] = false;

            while (!toVisit.isEmpty()) {
                int cratePos = toVisit.poll();

                for (int[] move : MOVES) {
                    int player = adjacentPos(cratePos, move[0], move[1]);
                    if (outside(player)) {
                        continue;
                    }

                    int newPlayerPos = adjacentPos(player, move[0], move[1]);
                    if (outside(newPlayerPos)) {
                        continue;
                    }

                    if (!map[player].isSolid()) {
                        if (!map[newPlayerPos].isSolid()) {
                            deadlockTiles[player] = false;
                        }

                        if (visited.add(player)) {
                            toVisit.offer(player);
                        }
                    }
                }
            }
        }
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

                if (tileVisitedByPlayer.add(i)) {
                    if (!mapWithCrates[i].isSolid()) {
                        tileToVisit.offer(i);
                        reachableTiles[i] = true;
                    }
                }
            }
        }
    }

    protected boolean isCrateReachableFrom(int crate, int relativeX, int relativeY) {
        int adjacent = adjacentPos(crate, relativeX, relativeY);

        return reachableTiles[adjacent];
    }

    // http://www.sokobano.de/wiki/index.php?title=How_to_detect_deadlocks
    protected boolean checkDeadlock(State state, Tile[] mapWithCrates) {
        for (int cratePos : state.cratesIndex) { // 8, 14, 26, 27, 33
            if (map[cratePos] == TARGET) {
                continue;
            }

            if (deadlockTiles[cratePos]) {
                return true;
            }

            if (checkBoxDeadlock(mapWithCrates, cratePos)) {
                return true;
            }
        }

        return false;
    }

    protected boolean checkBoxDeadlock(Tile[] map, int crate) {
        if (!xAxisDeadlock(map, crate)) {
            if (!(checkCrateBlockedByCrate(map, crate, -1, 0) || checkCrateBlockedByCrate(map, crate, 1, 0))) {
                return false;
            }
        }

        if (!yAxisDeadlock(map, crate)) {
            return checkCrateBlockedByCrate(map, crate, 0, -1) || checkCrateBlockedByCrate(map, crate, 0, 1);
        }

        return true;
    }

    protected boolean checkCrateBlockedByCrate(Tile[] map, int crate, int dirX, int dirY) {
        int adjacent = adjacentPos(crate, dirX, dirY);

        if (outside(adjacent) || map[adjacent] == WALL) {
            return true;
        } else if (!map[adjacent].isCrate()) {
            return false;
        } else {
            Tile old = map[crate];
            map[crate] = WALL;

            boolean returnValue = checkBoxDeadlock(map, adjacent);

            map[crate] = old;

            return returnValue;
        }
    }

    // http://sokobano.de/wiki/index.php?title=How_to_detect_deadlocks#Detecting_freeze_deadlocks
    protected boolean xAxisDeadlock(Tile[] map, int cratePos) {
        int left  = adjacentPos(cratePos, -1,  0);
        int right = adjacentPos(cratePos,  1,  0);

        if (outside(left) || map[left] == WALL) {
            return true;
        }
        if (outside(right) || map[right] == WALL) {
            return true;
        }

        return deadlockTiles[left] && deadlockTiles[right];
    }

    protected boolean yAxisDeadlock(Tile[] map, int cratePos) {
        int up    = adjacentPos(cratePos,  0, -1);
        int down  = adjacentPos(cratePos,  0,  1);

        if (outside(up) || map[up] == WALL) {
            return true;
        }
        if (outside(down) || map[down] == WALL) {
            return true;
        }

        return deadlockTiles[up] && deadlockTiles[down];
    }

    protected int adjacentPos(int index, int dirX, int dirY) {
        int x = index % width + dirX;
        int y = index / width + dirY; // important integer division

        return y * width + x;
    }

    protected State createChildState(Tile[] map, State state, int crateToMove, int crateIndex, int dirX, int dirY) {
        int newPos = moveCrate(map, crateToMove, dirX, dirY);

        if (newPos < 0) {
            return null;
        }

        State newState = new State();
        newState.parent = state;
        newState.playerIndex = crateToMove;
        newState.cratesIndex = state.cratesIndex.clone();
        newState.cratesIndex[crateIndex] = newPos;

        return newState;
    }

    /**
     * @return the new position of the crate or -1 if the crate can't be moved
     *          in the specified direction
     */
    protected int moveCrate(Tile[] map, int crateToMove, int dirX, int dirY) {
        int newPos = adjacentPos(crateToMove, dirX, dirY);
        int backwardPos = adjacentPos(crateToMove, -dirX, -dirY); // dirX = 0 or dirY = 0

        if (outside(newPos) || outside(backwardPos) || map[backwardPos].isSolid() || map[newPos].isSolid()) {
            return -1;
        }

        return newPos;
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