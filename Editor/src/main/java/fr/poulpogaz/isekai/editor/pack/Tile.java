package fr.poulpogaz.isekai.editor.pack;

import java.awt.image.BufferedImage;

public enum Tile {

    FLOOR(PackSprites.getFloor(), false),
    WALL(PackSprites.getWall(), true),
    CRATE(PackSprites.getCrate(), true),
    CRATE_ON_TARGET(PackSprites.getCrateOnTarget(), true),
    TARGET(PackSprites.getTarget(), false);

    private final BufferedImage sprite;
    private final boolean solid;

    Tile(BufferedImage sprite, boolean solid) {
        this.sprite = sprite;
        this.solid = solid;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean isSolid() {
        return solid;
    }
}
