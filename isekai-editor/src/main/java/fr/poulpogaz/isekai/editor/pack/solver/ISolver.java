package fr.poulpogaz.isekai.editor.pack.solver;

import fr.poulpogaz.isekai.editor.pack.LevelModel;

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

    LevelModel getLevel();
}
