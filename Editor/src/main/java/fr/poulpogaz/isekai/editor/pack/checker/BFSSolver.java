package fr.poulpogaz.isekai.editor.pack.checker;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;
import org.apache.commons.collections4.set.ListOrderedSet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static fr.poulpogaz.isekai.editor.pack.Tile.*;

public class BFSSolver implements ISolver {

    public static final int CHECKING = 0;
    public static final int TRUE = 1;
    public static final int FALSE = 2;

    private static final int[][] MOVES = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private final Level level;
    private final Tile[] map;
    private final int width;
    private final int height;

    private final ListOrderedSet<State> visited = new ListOrderedSet<>();
    private final State defaultState;

    private int numberOfWalls;
    private int numberOfFloors;
    private int numberOfTargets;
    private int numberOfCrates;

    private final List<Integer> tileVisitedByPlayer;
    private final Queue<Integer> tileToVisit;

    private final boolean[] reachableTiles;

    private int status;

    public BFSSolver(Level level) {
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

        //System.out.println("Number of floors: " + numberOfFloors);
        //System.out.println("Number of crates: " + numberOfCrates);
        //System.out.println("Number of targets: "+ numberOfTargets);
        //System.out.println("Number of walls: "+ numberOfWalls);
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
    public boolean check() {
        if (status == TRUE) {
            return true;
        } else if (status == FALSE || status == CANCELED) {
            return false;
        }

        visited.clear();
        Queue<State> states = new ArrayDeque<>();

        states.offer(defaultState);
        visited.add(defaultState);

        Tile[] mapWithCrates = map.clone();

        int stateNumber = 0;
        while (!states.isEmpty() && status != CANCELED) {
            State state = states.poll();

            if (isSolution(state)) {
                return true;
            }

            //System.out.println("Base:\n" + Arrays.toString(mapWithCrates));
            fillMapWithCrates(state, mapWithCrates);
            //System.out.println("fill:\n" + Arrays.toString(mapWithCrates));
            getReachableTiles(state, mapWithCrates, reachableTiles);

            //System.out.println("-".repeat(Math.max(width, 16)));
            //System.out.printf("State number: %d%n", state.number);
            //System.out.printf("Map:%n%s%n", asString(state));

            if (checkDeadlock(state, mapWithCrates)) {
                unfillMapWithCrates(state, mapWithCrates);

                continue;
            }

            state.childrenStart = stateNumber + 1;

            for (int i = 0; i < state.cratesIndex.length; i++) {
                int cratePos = state.cratesIndex[i];

                //System.out.printf("Processing crate at %d %d (%d)%n", cratePos % width, cratePos / width, cratePos);

                for (int[] move : MOVES) {
                    //System.out.printf("Direction: %d %d%n", move[0], move[1]);

                    if (isCrateReachableFrom(cratePos, -move[0], -move[1])) {
                        State child = createChildState(mapWithCrates, state, cratePos, i, move[0], move[1]);

                        if (child != null && !visited.contains(child)) {
                            //writeImage(child, move[0], move[1], stateNumber, false);
                            stateNumber++;
                            child.number = stateNumber;

                            visited.add(child);

                            if (isSolution(child)) {
                                state.childrenEnd = stateNumber;

                                status = TRUE;

                                return true;
                            }

                            states.offer(child);
                        }
                    } else {
                        //System.out.println("Not reachable");
                    }
                    //System.out.println();
                }
                //System.out.println();
            }

            state.childrenEnd = stateNumber;

            unfillMapWithCrates(state, mapWithCrates);
            //System.out.println("unfill:\n" + Arrays.toString(mapWithCrates));
        }

        if (status == CHECKING) {
            status = FALSE;
        }

        return false;
    }

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

    // BFS in BFS
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

    private int i = 0;

    protected void writeImage(State state, int dirX, int dirY, int fromState, boolean deadlock) {
        BufferedImage image = new BufferedImage(width * 16, height * 16, BufferedImage.TYPE_INT_ARGB);

        Tile[] map = this.map.clone();
        fillMapWithCrates(state, map);

        boolean[] reachableTiles = new boolean[map.length];
        getReachableTiles(state, map, reachableTiles);

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.black);
        for (int i = 0; i < map.length; i++) {
            Tile tile = map[i];

            int x = (i % width) * 16;
            int y = (i / width) * 16;

            g2d.drawImage(tile.getSprite(), x, y, null);

            if (reachableTiles[i]) {
                g2d.drawRect(x, y, 15, 15);
            }
        }
        int x = (state.playerIndex % width) * 16;
        int y = (state.playerIndex / width) * 16;

        g2d.drawImage(PackSprites.getPlayer(), x, y, null);

        if (!deadlock) {
            if (dirX == 1) {
                g2d.rotate(Math.PI / 2, x + 8, y + 8);
            } else if (dirX == -1) {
                g2d.rotate(-Math.PI / 2, x + 8, y + 8);
            } else if (dirY == 1) {
                g2d.rotate(Math.PI, x + 8, y + 8);
            }

            int[] xPoints = new int[]{
                    x + 6, x + 10, x + 10, x + 13, x + 8, x + 3, x + 6
            };

            int[] yPoints = new int[]{
                    y + 7, y + 7, y - 1, y - 1, y - 6, y - 1, y - 1
            };
            g2d.setColor(Color.RED);
            g2d.fillPolygon(xPoints, yPoints, 7);
        }

        g2d.dispose();

        try {
            String str = "img/%d, state=%d dir_x=%d dir_y=%d".formatted(i, fromState, dirX, dirY);

            if (deadlock) {
                str += " deadlock.png";
            } else {
                str += ".png";
            }

            ImageIO.write(image, "png", new File(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        i++;
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
    public Level getLevel() {
        return level;
    }

    @Override
    public List<State> visited() {
        return visited.asList();
    }

    @Override
    public int status() {
        return status;
    }
}
