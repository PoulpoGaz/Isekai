package fr.poulpogaz.isekai.editor.pack;

import java.awt.image.BufferedImage;

public enum Tile {

    FLOOR(PackSprites.getFloor(), false, false),
    WALL(PackSprites.getWall(), true, false),
    CRATE(PackSprites.getCrate(), true, true),
    CRATE_ON_TARGET(PackSprites.getCrateOnTarget(), true, true),
    TARGET(PackSprites.getTarget(), false, false);

    private final BufferedImage sprite;
    private final boolean solid;
    private final boolean crate;

    Tile(BufferedImage sprite, boolean solid, boolean crate) {
        this.sprite = sprite;
        this.solid = solid;
        this.crate = crate;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isCrate() {
        return crate;
    }
}
