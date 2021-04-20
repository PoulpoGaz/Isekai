package fr.poulpogaz.isekai.editor.pack.checker;

import java.util.Arrays;

public class State {

    public State parent;

    public int number;
    public int childrenStart = -1;
    public int childrenEnd = -1;

    public int[] cratesIndex;
    public int playerIndex;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State state)) return false;

        if (playerIndex != state.playerIndex) return false;
        return Arrays.equals(cratesIndex, state.cratesIndex);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(cratesIndex);
        result = 31 * result + playerIndex;
        return result;
    }

    @Override
    public String toString() {
        return "State n°" + number;
    }
}