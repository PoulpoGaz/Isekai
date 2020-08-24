package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Level;

import java.util.EventListener;

public interface LevelListener extends EventListener {

    void levelInserted(Level insertedLevel, int index);

    void levelDeleted(Level deletedLevel, int index);

    void levelMoved(int from, int to);

    void selectedLevelChanged(Level newLevel, int index);
}
