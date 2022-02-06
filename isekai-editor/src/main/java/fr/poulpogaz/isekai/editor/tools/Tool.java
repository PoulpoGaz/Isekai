package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;

import javax.swing.*;
import javax.swing.undo.UndoableEdit;

public interface Tool {

    void press(Level level, Tile tile, int x, int y);

    UndoableEdit release(Level level, Tile tile, int x, int y);

    Icon getIcon();
}
