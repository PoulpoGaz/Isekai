package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;

public interface Tool {

    void apply(Level level, Tile tile, int tileX, int tileY);
}