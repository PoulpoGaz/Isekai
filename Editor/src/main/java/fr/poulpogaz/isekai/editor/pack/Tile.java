package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

public enum Tile {

    CRATE("crate", Pack.CRATE),
    FLOOR("floor", Pack.FLOOR),
    TARGET("target", Pack.TARGET),
    CRATE_ON_TARGET("crate_on_target", Pack.CRATE_ON_TARGET),
    WALL("wall", Pack.WALL);

    private final String sprite;
    private final char symbol;

    Tile(String sprite, char symbol) {
        this.sprite = sprite;
        this.symbol = symbol;
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

    public char getSymbol() {
        return symbol;
    }
}
