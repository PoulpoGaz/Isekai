package fr.poulpogaz.isekai.editor.pack.checker;

import fr.poulpogaz.isekai.editor.pack.Level;

import java.util.List;

public interface ISolver {

    int CHECKING = 0;
    int TRUE = 1;
    int FALSE = 2;
    int CANCELED = 3;

    boolean check();

    void cancel();

    int status();

    List<State> visited();

    Level getLevel();
}
