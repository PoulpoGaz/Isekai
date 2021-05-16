package fr.poulpogaz.isekai.editor.pack.solver;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import org.apache.commons.collections4.set.ListOrderedSet;

import java.util.List;
import java.util.Stack;

public class DFSSolver extends AbstractSolver {

    private final ListOrderedSet<State> visited = new ListOrderedSet<>();

    public DFSSolver(Level level) {
        super(level);
    }

    @Override
    public boolean check() {
        if (status == TRUE) {
            return true;
        } else if (status == FALSE || status == CANCELED) {
            return false;
        }

        findDeadlockTiles();

        visited.clear();
        Stack<State> states = new Stack<>();

        states.push(defaultState);
        visited.add(defaultState);

        Tile[] mapWithCrates = map.clone();

        int stateNumber = 0;
        while (!states.isEmpty() && status != CANCELED) {
            State state = states.pop();

            if (isSolution(state)) {
                return true;
            }

            fillMapWithCrates(state, mapWithCrates);
            getReachableTiles(state, mapWithCrates, reachableTiles);

            if (checkDeadlock(state, mapWithCrates)) {
                unfillMapWithCrates(state, mapWithCrates);

                continue;
            }

            state.childrenStart = stateNumber + 1;

            for (int i = 0; i < state.cratesIndex.length; i++) {
                int cratePos = state.cratesIndex[i];

                for (int[] move : MOVES) {
                    if (isCrateReachableFrom(cratePos, -move[0], -move[1])) {
                        State child = createChildState(mapWithCrates, state, cratePos, i, move[0], move[1]);

                        if (child != null && visited.add(child)) {
                            stateNumber++;
                            child.number = stateNumber;

                            if (isSolution(child)) {
                                state.childrenEnd = stateNumber;

                                status = TRUE;

                                return true;
                            }

                            states.push(child);
                        }
                    }
                }
            }

            state.childrenEnd = stateNumber;

            unfillMapWithCrates(state, mapWithCrates);
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