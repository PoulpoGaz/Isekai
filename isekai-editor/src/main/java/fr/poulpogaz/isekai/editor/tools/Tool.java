package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.LevelModel;

import javax.swing.*;
import javax.swing.undo.UndoableEdit;

public interface Tool {

    void press(LevelModel level, Tile tile, int x, int y);

    UndoableEdit release(LevelModel level, Tile tile, int x, int y);

    Icon getIcon();
}
