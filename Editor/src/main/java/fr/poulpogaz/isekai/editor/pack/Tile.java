package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

public enum Tile {

    FLOOR(PackSprites.FLOOR, false),
    WALL(PackSprites.WALL, true),
    CRATE(PackSprites.CRATE, true),
    CRATE_ON_TARGET(PackSprites.CRATE_ON_TARGET, true),
    TARGET(PackSprites.TARGET, false);

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
