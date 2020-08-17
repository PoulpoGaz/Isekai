package fr.poulpogaz.isekai.editor.ui.editor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Constant {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    public static final BufferedImage TILESET;

    public static final int TILESET_WIDTH;
    public static final int TILESET_HEIGHT;

    public static final int COLUMNS;
    public static final int ROWS;

    static {
        BufferedImage temp = null;
        int tilesetWidth = 0;
        int tilesetHeight = 0;
        int columns = 0;
        int rows = 0;

        try {
            temp = ImageIO.read(TilesetPanel.class.getResourceAsStream("/editor/map_tileset.png"));

            tilesetWidth = temp.getWidth();
            tilesetHeight = temp.getHeight();

            columns = tilesetWidth / TILE_WIDTH;
            rows = tilesetHeight / TILE_HEIGHT;
        } catch (IOException e) {
            e.printStackTrace();
        }

        TILESET = temp;
        TILESET_WIDTH = tilesetWidth;
        TILESET_HEIGHT = tilesetHeight;
        COLUMNS = columns;
        ROWS = rows;
    }
}