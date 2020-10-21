package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.tools.ToolHelper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TilesetPanel extends JPanel {

    private static final Dimension PREFERRED_TILESET_DIMENSION = new Dimension(128, 128);
    private static final int NUMBER_OF_TILE_PER_ROW = 3;

    private final ToolHelper toolHelper;

    private BufferedImage tileset;

    private int tileWidth;
    private int tileHeight;

    private int selectedTileX = 0;
    private int selectedTileY = 0;

    public TilesetPanel(MapEditor editor) {
        toolHelper = editor.getToolHelper();

        createTileset(IsekaiEditor.getInstance().getPack());

        setBorder(BorderFactory.createTitledBorder("Tileset"));

        MouseAdapter adapter = getMouseAdapter();

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Insets insets = getInsets();

        g.drawImage(tileset, insets.left, insets.top, null);

        if (selectedTileX >= 0 && selectedTileY >= 0) {
            g.setColor(new Color(0, 217, 255, 64));
            g.fillRect(selectedTileX * tileWidth + insets.left, selectedTileY  * tileHeight + insets.top, tileWidth, tileHeight);
        }
    }

    @Override
    public void setBorder(Border border) {
        super.setBorder(border);

        if (border != null) {
            setDimension();
        }
    }

    private void setDimension() {
        Dimension dim = new Dimension(tileset.getWidth(), tileset.getHeight());

        Insets insets = getInsets();
        if (insets != null) {
            dim.width += insets.left + insets.right;
            dim.height += insets.top + insets.bottom;
        }

        setMinimumSize(dim);
        setPreferredSize(dim);
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseEvent(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseEvent(e);
            }
        };
    }

    private boolean isOnImage(Point wPoint) {
        return wPoint.getX() >= 0 && wPoint.getX() < tileset.getWidth() &&
                wPoint.getY() >= 0 && wPoint.getY() < tileset.getHeight();
    }

    private Point toView(Point point) {
        Insets insets = getInsets();

        point.x -= insets.left;
        point.y -= insets.top;

        return point;
    }

    private void handleMouseEvent(MouseEvent e) {
        Point point = toView(e.getPoint());

        if (isOnImage(point)) {
            int oldX = selectedTileX;
            int oldY = selectedTileY;

            selectedTileX = (int) (point.getX()) / tileWidth;
            selectedTileY = (int) (point.getY()) / tileHeight;

            if (oldX != selectedTileX || oldY != selectedTileY) {
                int i = selectedTileY * NUMBER_OF_TILE_PER_ROW + selectedTileX;

                Tile[] tiles = Tile.values();

                if (i < 0 || i >= tiles.length) {
                    return;
                }

                toolHelper.setSelectedTile(tiles[i]);

                repaint();
            }
        }
    }

    private void createTileset(Pack pack) {
        int width;
        int height;

        if (pack.getTileWidth() > pack.getTileHeight()) {
            width = PREFERRED_TILESET_DIMENSION.width;
            height = width * pack.getTileHeight() / pack.getTileWidth();
        } else {
            height = PREFERRED_TILESET_DIMENSION.height;
            width = height * pack.getTileWidth() / pack.getTileHeight();
        }

        tileWidth = width / NUMBER_OF_TILE_PER_ROW;
        tileHeight = height / NUMBER_OF_TILE_PER_ROW;

        tileset = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tileset.createGraphics();

        try {
            int x = 0;
            int y = 0;

            for (Tile tile : Tile.values()) {
                AbstractSprite sprite = tile.getSprite(pack);

                sprite.paint(g, x, y, tileWidth, tileHeight);

                x += tileWidth;

                if (x + tileWidth >= width) {
                    x = 0;
                    y += tileHeight;
                }
            }
        } finally {
            g.dispose();
        }
    }
}