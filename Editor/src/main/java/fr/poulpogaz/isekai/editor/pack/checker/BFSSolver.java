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

public class BFSSolver extends AbstractSolver {

    private final ListOrderedSet<State> visited = new ListOrderedSet<>();

    public BFSSolver(Level level) {
        super(level);
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
                    } /*else {
                        //System.out.println("Not reachable");
                    }*/
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

    @Override
    public List<State> visited() {
        return visited.asList();
    }
}
