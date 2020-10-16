package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

public interface Tool {

    void apply(Level level, Tile tile, int tileX, int tileY);

    AbstractSprite getToolSprite(Pack pack, Tile tile);
}