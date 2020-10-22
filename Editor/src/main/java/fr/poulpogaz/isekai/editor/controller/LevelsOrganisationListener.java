package fr.poulpogaz.isekai.editor.controller;

import java.util.EventListener;

public interface LevelsOrganisationListener extends EventListener {

    void levelInserted(int index);

    void levelRemoved(int index);

    void levelChanged(int index);

    void levelsSwapped(int index1, int index2);
}
