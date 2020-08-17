package fr.poulpogaz.isekai.editor.pack;

public enum Tile {

    CRATE(0, 0),
    GRASS(1, 0),
    TARGET(2, 0),
    CRATE_ON_TARGET(0, 1),
    WALL(1, 1),
    VOID(2, 1);

    private final int texX;
    private final int texY;

    Tile(int texX, int texY) {
        this.texX = texX;
        this.texY = texY;
    }

    public static Tile of(char value) {
        return switch (value) {
            case '#' -> Tile.WALL;
            case ' ', '@' -> Tile.GRASS;
            case '$' -> Tile.CRATE;
            case '.', '+' -> Tile.TARGET;
            case '*' -> Tile.CRATE_ON_TARGET;
            default -> throw new IllegalStateException();
        };
    }

    public int getTexX() {
        return texX;
    }

    public int getTexY() {
        return texY;
    }
}
