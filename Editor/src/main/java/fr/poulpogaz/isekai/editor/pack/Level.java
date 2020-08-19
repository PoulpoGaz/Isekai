package fr.poulpogaz.isekai.editor.pack;

public class Level {

    public static final int MINIMUM_MAP_WIDTH = 5;
    public static final int MINIMUM_MAP_HEIGHT = 5;

    public static final int MAXIMUM_MAP_WIDTH = 64;
    public static final int MAXIMUM_MAP_HEIGHT = 64;

    public static final int DEFAULT_MAP_WIDTH = 16;
    public static final int DEFAULT_MAP_HEIGHT = 16;

    private Tile[][] tiles;

    private int width;
    private int height;

    private int playerX;
    private int playerY;

    public Level() {
        this(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);
    }

    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new Tile[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = Tile.WALL;
            }
        }
    }

    public void resize(int newWidth, int newHeight) {
        Tile[][] newArray = new Tile[newHeight][newWidth];

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {

                if (y < this.height && x < this.width) {
                    newArray[y][x] = tiles[y][x];
                } else {
                    newArray[y][x] = Tile.WALL;
                }
            }
        }

        tiles = newArray;
        this.width = newWidth;
        this.height = newHeight;
    }

    public void setTile(int x, int y, Tile tile) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[y][x] = tile;
        }
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public void setPlayer(int x, int y) {
        playerX = x;
        playerY = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        resize(width, height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        resize(width, height);
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}