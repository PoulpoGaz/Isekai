package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

public enum Tile {

    FLOOR(Pack.FLOOR_SPRITE),
    WALL(Pack.WALL_SPRITE),
    CRATE(Pack.CRATE_SPRITE),
    CRATE_ON_TARGET(Pack.CRATE_ON_TARGET_SPRITE),
    TARGET(Pack.TARGET_SPRITE);

    private final String sprite;

    Tile(String sprite) {
        this.sprite = sprite;
    }

    public AbstractSprite getSprite(Pack pack) {
        return pack.getSprite(sprite);
    }

    public static Tile of(char value) {
        return switch (value) {
            case '#' -> Tile.WALL;
            case ' ', '@' -> Tile.FLOOR;
            case '$' -> Tile.CRATE;
            case '.', '+' -> Tile.TARGET;
            case '*' -> Tile.CRATE_ON_TARGET;
            default -> throw new IllegalStateException();
        };
    }

    public String getSprite() {
        return sprite;
    }
}
