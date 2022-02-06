package fr.poulpogaz.isekai.editor.pack;

import java.awt.image.BufferedImage;

public enum Tile {

    FLOOR(PackSprites.getFloor(), false, false, false),
    WALL(PackSprites.getWall(), true, false, false),
    CRATE(PackSprites.getCrate(), true, true, false),
    CRATE_ON_TARGET(PackSprites.getCrateOnTarget(), true, true, true),
    TARGET(PackSprites.getTarget(), false, false, true);

    private final BufferedImage sprite;
    private final boolean solid;
    private final boolean crate;
    private final boolean target;

    Tile(BufferedImage sprite, boolean solid, boolean crate, boolean target) {
        this.sprite = sprite;
        this.solid = solid;
        this.crate = crate;
        this.target = target;
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

    public boolean isTarget() {
        return target;
    }
}
