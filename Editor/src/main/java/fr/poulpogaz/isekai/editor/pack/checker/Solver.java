package fr.poulpogaz.isekai.editor.pack.checker;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static fr.poulpogaz.isekai.editor.pack.Tile.*;

public class Solver {

    private static final int[][] MOVES = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private final Level level;
    private final Tile[] map;
    private final int width;
    private final int height;

    private final State defaultState;

    private int numberOfWalls;
    private int numberOfFloors;
    private int numberOfTargets;
    private int numberOfCrates;

    private final List<Integer> reachableCrates;
    private final List<Integer> tileVisitedByPlayer;
    private final Queue<Integer> tileToVisit;

    public Solver(Level level) {
        this.level = level;
        this.width = level.getWidth();
        this.height = level.getHeight();

        map = new Tile[width * height];

        initializeMap();

        defaultState = new State();
        initializeState();

        tileVisitedByPlayer = new ArrayList<>(numberOfFloors + numberOfTargets);
        tileToVisit = new ArrayBlockingQueue<>(numberOfFloors + numberOfTargets);
        reachableCrates = new ArrayList<>(numberOfCrates);
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

    // BFS algorithm
    public boolean check() {
        List<State> visited = new ArrayList<>();
        Queue<State> states = new ArrayDeque<>();

        states.offer(defaultState);
        visited.add(defaultState);

        Tile[] mapWithCrates = map.clone();
        while (!states.isEmpty()) {
            State state = states.poll();

            if (isSolution(state)) {
                return true;
            }

            fillMapWithCrates(state, mapWithCrates);

            List<Integer> reachableCrates = getReachableCrates(state, mapWithCrates);

            System.out.println("-".repeat(Math.max(height, 16)));
            System.out.println("State: \n" + asString(state));

            for (int i = 0; i < reachableCrates.size(); i++) {
                Integer cratePos = reachableCrates.get(i);

                boolean xAxisDeadlock = xAxisDeadlock(map, cratePos);
                boolean yAxisDeadlock = yAxisDeadlock(map, cratePos);

               // if (!(xAxisDeadlock && yAxisDeadlock)) {
               //     if (!xAxisDeadlock) {
                        System.out.println("Crate: " + cratePos);
                        System.out.println("Direction: -1, 0");

                        State child = createChildState(mapWithCrates, state, cratePos, i, -1, 0);

                        if (child != null && !visited.contains(child)) {
                            states.offer(child);
                            visited.add(child);

                            System.out.println(asString(child));
                        }

                        System.out.println("Direction: 1, 0");
                        child = createChildState(mapWithCrates, state, cratePos, i, 1, 0);

                        if (child != null && !visited.contains(child)) {
                            states.offer(child);
                            visited.add(child);

                            System.out.println(asString(child));
                        }
                //    }
                //    if (!yAxisDeadlock) {
                        System.out.println("Direction: 0, -1");
                        /*State*/ child = createChildState(mapWithCrates, state, cratePos, i, 0, -1);

                        if (child != null && !visited.contains(child)) {
                            states.offer(child);
                            visited.add(child);

                            System.out.println(asString(child));
                        }

                        System.out.println("Direction: 0, 1");
                        child = createChildState(mapWithCrates, state, cratePos, i, 0, 1);

                        if (child != null && !visited.contains(child)) {
                            states.offer(child);
                            visited.add(child);

                            System.out.println(asString(child));
                        }
                    // }
                // }
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

    // BFS in BFS
    protected List<Integer> getReachableCrates(State state, Tile[] mapWithCrates) {
        reachableCrates.clear();
        tileVisitedByPlayer.clear();
        tileToVisit.clear();

        tileVisitedByPlayer.add(state.playerIndex);
        tileToVisit.offer(state.playerIndex);

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
                    } else if (mapWithCrates[i].isCrate()) {
                        reachableCrates.add(i);
                    }

                    tileVisitedByPlayer.add(i);
                }
            }
        }

        return reachableCrates;
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

        System.out.println("New pos: " + newPos + ", Backward pos: " + backwardPos);
        System.out.println("Outside: " + outside(newPos) + ", " + outside(backwardPos));
        System.out.println("Tile: " + map[newPos] + ", " + map[backwardPos]);

        if (outside(newPos) || outside(backwardPos) || map[backwardPos].isSolid() || map[newPos].isSolid() || newPos == state.playerIndex) {
            return null;
        }

        State newState = new State();
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
}
