package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TilesetPanel extends JPanel {

    private final Dimension PREFERRED_TILESET_DIMENSION = new Dimension(128, 128);

    private BufferedImage tileset;

    private int tileWidth;
    private int tileHeight;

    private int selectedTileX;
    private int selectedTileY;

    public TilesetPanel() {
        createTileset(IsekaiEditor.getInstance().getPack());
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

        System.out.println(point + " " + isOnImage(point));

        if (isOnImage(point)) {
            int oldX = selectedTileX;
            int oldY = selectedTileY;

            selectedTileX = (int) (point.getX()) / tileWidth;
            selectedTileY = (int) (point.getY()) / tileHeight;

            if (oldX != selectedTileX || oldY != selectedTileY) {
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
            paintSpriteAt(g, pack.getSprite(Pack.FLOOR_SPRITE), 0, 0, tileWidth, tileHeight);
            paintSpriteAt(g, pack.getSprite(Pack.WALL_SPRITE), tileWidth, 0, tileWidth, tileHeight);
            paintSpriteAt(g, pack.getSprite(Pack.TARGET_SPRITE), tileWidth * 2, 0, tileWidth, tileHeight);
            paintSpriteAt(g, pack.getSprite(Pack.CRATE_SPRITE), 0, tileHeight, tileWidth, tileHeight);
            paintSpriteAt(g, pack.getSprite(Pack.CRATE_ON_TARGET_SPRITE), tileWidth, tileHeight, tileWidth, tileHeight);
        } finally {
            g.dispose();
        }
    }

    private void paintSpriteAt(Graphics2D g, AbstractSprite sprite, int x, int y, int width, int height) {
        BufferedImage img = sprite.getSprite();

        g.drawImage(img, x, y, width, height, null);
    }

    public int getSelectedTileX() {
        return selectedTileX;
    }

    public int getSelectedTileY() {
        return selectedTileY;
    }
}