package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

public class Player {

    public static final String DOWN_STATIC = "down_static";
    public static final String UP_STATIC = "down_static";
    public static final String LEFT_STATIC = "down_static";
    public static final String RIGHT_STATIC = "down_static";

    public static final String DOWN_WALK = "down_static";
    public static final String UP_WALK = "down_static";
    public static final String LEFT_WALK = "down_static";
    public static final String RIGHT_WALK = "down_static";

    public static final String DEFAULT_STATIC = DOWN_STATIC;

    private final Level level;
    private Vector2i pos;

    public Player(Level level) {
        this(level, new Vector2i());
    }

    public Player(Level level, Vector2i pos) {
        this.level = level;
        this.pos = pos;
    }

    public AbstractSprite getDefaultSprite(Pack pack) {
        return pack.getSprite(DOWN_STATIC);
    }

    public Vector2i getPos() {
        return pos;
    }

    public void setPos(Vector2i pos) {
        if (pos.x >= 0 && pos.x < level.getWidth() && pos.y >= 0 && pos.y < level.getHeight()) {
            Tile tile = level.getTile(pos.x, pos.y);

            if (!tile.isSolid()) {
                this.pos = pos;

            }
        }
    }
}