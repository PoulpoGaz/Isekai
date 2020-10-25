package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

public class PlayerTool implements Tool {

    private static final PlayerTool INSTANCE = new PlayerTool();

    @Override
    public void apply(Level level, Tile tile, int tileX, int tileY) {
        Tile current = level.getTile(tileX, tileY);

        if (current.isSolid() && !tile.isSolid()) {
            level.setTile(tileX, tileY, tile);
            level.setPlayerPos(new Vector2i(tileX, tileY));
        } else if (!current.isSolid()) {
            level.setPlayerPos(new Vector2i(tileX, tileY));
        }
    }

    @Override
    public AbstractSprite getToolSprite(Pack pack, Tile tile) {
        return pack.getSprite(PackSprites.PLAYER_DOWN_STATIC);
    }

    public static PlayerTool getInstance() {
        return INSTANCE;
    }
}