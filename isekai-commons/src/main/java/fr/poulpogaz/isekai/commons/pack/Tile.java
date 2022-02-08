package fr.poulpogaz.isekai.commons.pack;

public enum Tile {

    FLOOR("floor", false, false, false),
    WALL("wall", true, false, false),
    CRATE("crate", true, true, false),
    CRATE_ON_TARGET("crate_on_target", true, true, true),
    TARGET("target", false, false, true);

    private final String sprite;
    private final boolean solid;
    private final boolean crate;
    private final boolean target;

    Tile(String sprite, boolean solid, boolean crate, boolean target) {
        this.sprite = sprite;
        this.solid = solid;
        this.crate = crate;
        this.target = target;
    }

    public String getSprite() {
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
