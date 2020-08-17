package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Tile;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.poulpogaz.isekai.editor.ui.editor.Constant.*;

public class TilesetPanel extends JPanel {

    private static final int TILESET_SCALE_FACTOR = 4;

    private static final int SCALED_TILE_WIDTH  = Constant.TILE_WIDTH  * TILESET_SCALE_FACTOR;
    private static final int SCALED_TILE_HEIGHT = Constant.TILE_HEIGHT * TILESET_SCALE_FACTOR;

    private static final int SCALED_TILESET_WIDTH = TILESET_SCALE_FACTOR * TILESET_WIDTH;
    private static final int SCALED_TILESET_HEIGHT = TILESET_SCALE_FACTOR * TILESET_HEIGHT;

    private int selectedTileX;
    private int selectedTileY;

    public TilesetPanel() {
        setDimension();

        MouseAdapter adapter = getMouseAdapter();

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Insets insets = getInsets();

        g.drawImage(TILESET, insets.left, insets.top, SCALED_TILESET_WIDTH, SCALED_TILESET_HEIGHT, null);

        if (selectedTileX >= 0 && selectedTileY >= 0) {
            g.setColor(new Color(0, 217, 255, 64));
            g.fillRect(selectedTileX * SCALED_TILE_WIDTH + insets.left, selectedTileY * SCALED_TILE_HEIGHT + insets.top, SCALED_TILE_WIDTH, SCALED_TILE_HEIGHT);
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

        Dimension dim = new Dimension(SCALED_TILESET_WIDTH + insets.left + insets.right, SCALED_TILESET_HEIGHT + insets.top + insets.bottom);

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
        return wPoint.getX() >= 0 && wPoint.getX() < SCALED_TILESET_WIDTH &&
                wPoint.getY() >= 0 && wPoint.getY() < SCALED_TILESET_HEIGHT;
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

            selectedTileX = (int) (point.getX()) / SCALED_TILE_WIDTH;
            selectedTileY = (int) (point.getY()) / SCALED_TILE_HEIGHT;

            if (oldX != selectedTileX || oldY != selectedTileY) {
                TilesetListener[] listeners = listenerList.getListeners(TilesetListener.class);

                Tile newTile = Tile.values()[selectedTileX + selectedTileY * COLUMNS];

                for (TilesetListener listener : listeners) {
                    listener.selectedTileChanged(newTile);
                }

                repaint();
            }
        }
    }

    public int getSelectedTileX() {
        return selectedTileX;
    }

    public int getSelectedTileY() {
        return selectedTileY;
    }

    public void addTilesetListener(TilesetListener listener) {
        listenerList.add(TilesetListener.class, listener);
    }

    public void removeTilesetListener(TilesetListener listener) {
        listenerList.remove(TilesetListener.class, listener);
    }
}