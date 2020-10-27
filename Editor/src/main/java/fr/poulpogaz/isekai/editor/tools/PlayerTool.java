package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

public class PlayerTool implements Tool {

    private static final PlayerTool INSTANCE = new PlayerTool();

    @Override
    public <M extends Map<E>, E> void apply(M map, E element, int x, int y) {
        if (map instanceof Level) {
            Level level = (Level) map;

            Tile tile = (Tile) element;
            Tile current = level.get(x, y);

            if (current.isSolid() && !tile.isSolid()) {
                level.set(tile, x, y);
                level.setPlayerPos(new Vector2i(x, y));
            } else if (!current.isSolid()) {
                level.setPlayerPos(new Vector2i(x, y));
            }
        }
    }

    /*@Override
    public AbstractSprite getToolSprite(Pack pack, Tile tile) {
        return pack.getSprite(PackSprites.PLAYER_DOWN_STATIC);
    }*/

    public static PlayerTool getInstance() {
        return INSTANCE;
    }
}