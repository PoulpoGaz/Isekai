package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;

public class PaintTool implements Tool {

    private static final PaintTool INSTANCE = new PaintTool();

    private PaintTool() {

    }

    @Override
    public void apply(Level level, Tile tile, int tileX, int tileY) {
        level.setTile(tileX, tileY, tile);
    }

    public static PaintTool getInstance() {
        return INSTANCE;
    }
}