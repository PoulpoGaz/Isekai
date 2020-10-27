package fr.poulpogaz.isekai.editor.ui.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

public class PaintTool implements Tool {

    private static final PaintTool INSTANCE = new PaintTool();

    private PaintTool() {

    }

    @Override
    public void apply(Level level, Tile tile, int tileX, int tileY) {
        level.setTile(tileX, tileY, tile);
    }

    @Override
    public AbstractSprite getToolSprite(Pack pack, Tile tile) {
        return tile.getSprite(pack);
    }

    public static PaintTool getInstance() {
        return INSTANCE;
    }
}