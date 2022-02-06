package fr.poulpogaz.isekai.editor.pack;

import java.util.EventListener;

public interface LevelsOrganisationListener extends EventListener {

    void levelInserted(int start, int end);

    void levelRemoved(int index);

    void levelChanged(int index);

    void levelsSwapped(int index1, int index2);

    void organisationChanged();
}
