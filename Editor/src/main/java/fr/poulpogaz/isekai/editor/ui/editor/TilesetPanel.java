package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TilesetPanel extends JPanel {

    public static final String SELECTED_TILE_PROPERTY = "SelectedTileProperty";

    private final Dimension PREFERRED_TILESET_DIMENSION = new Dimension(128, 128);

    private BufferedImage tileset;

    private int tileWidth;
    private int tileHeight;

    private int selectedTileX = 0;
    private int selectedTileY = 0;
    private Tile selectedTile = Tile.FLOOR;

    public TilesetPanel() {
        createTileset(IsekaiEditor.getPack());
        setDimension();

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
        Insets insets = getInsets();

        Dimension dim = new Dimension(tileset.getWidth() + insets.left + insets.right, tileset.getHeight() + insets.top + insets.bottom);

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
                Tile old = selectedTile;

                if (selectedTileY == 0) {
                    if (selectedTileX == 0) {
                        selectedTile = Tile.FLOOR;
                    } else if (selectedTileX == 1) {
                        selectedTile = Tile.WALL;
                    } else if (selectedTileX == 2) {
                        selectedTile = Tile.TARGET;
                    }
                } else if (selectedTileY == 1) {
                    if (selectedTileX == 0) {
                        selectedTile = Tile.CRATE;
                    } else if (selectedTileX == 1) {
                        selectedTile = Tile.CRATE_ON_TARGET;
                    }
                }

                firePropertyChange(SELECTED_TILE_PROPERTY, old, selectedTile);

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

        tileWidth = width / 3;
        tileHeight = height / 3;

        tileset = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tileset.createGraphics();

        try {
            pack.getSprite(Pack.FLOOR_SPRITE).paint(g, 0, 0, tileWidth, tileHeight);
            pack.getSprite(Pack.WALL_SPRITE).paint(g, tileWidth, 0, tileWidth, tileHeight);
            pack.getSprite(Pack.TARGET_SPRITE).paint(g, tileWidth * 2, 0, tileWidth, tileHeight);
            pack.getSprite(Pack.CRATE_SPRITE).paint(g, 0, tileHeight, tileWidth, tileHeight);
            pack.getSprite(Pack.CRATE_ON_TARGET_SPRITE).paint(g, tileWidth, tileHeight, tileWidth, tileHeight);
        } finally {
            g.dispose();
        }
    }

    private void paintSpriteAt(Graphics2D g, AbstractSprite sprite, int x, int y, int width, int height) {
        BufferedImage img = sprite.getSprite();

        g.drawImage(img, x, y, width, height, null);
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }
}