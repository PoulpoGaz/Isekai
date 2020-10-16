package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

public enum Tile {

    FLOOR(Pack.FLOOR_SPRITE, false),
    WALL(Pack.WALL_SPRITE, true),
    CRATE(Pack.CRATE_SPRITE, true),
    CRATE_ON_TARGET(Pack.CRATE_ON_TARGET_SPRITE, true),
    TARGET(Pack.TARGET_SPRITE, false);

    private final String sprite;
    private final boolean solid;

    Tile(String sprite, boolean solid) {
        this.sprite = sprite;
        this.solid = solid;
    }

    public AbstractSprite getSprite(Pack pack) {
        return pack.getSprite(sprite);
    }

    public String getSprite() {
        return sprite;
    }

    public boolean isSolid() {
        return solid;
    }
}
