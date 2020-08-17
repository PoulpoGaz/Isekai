package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.poulpogaz.isekai.editor.ui.editor.Constant.*;

public class TileMapPanel extends JPanel implements TilesetListener, LevelListener {

    public static final int SCALE_FACTOR = 2;

    public static final int SCALED_TILE_WIDTH = TILE_WIDTH * SCALE_FACTOR;
    public static final int SCALED_TILE_HEIGHT = TILE_HEIGHT * SCALE_FACTOR;

    private Level level;

    private int hoverX;
    private int hoverY;

    private Tile selectedTile = Tile.CRATE;
    private boolean hideTileCursor = true;

    public TileMapPanel(Level level) {
        this.level = level;

        initComponent();
    }

    private void initComponent() {
        MouseAdapter adapter = getMouseAdapter();
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        setPreferredSize();
    }

    private void setPreferredSize() {
        setPreferredSize(new Dimension(level.getWidth() * SCALED_TILE_WIDTH, level.getHeight() * SCALED_TILE_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            Point offset = getOffset();

            int w = level.getWidth();
            int h = level.getHeight();

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    Tile t = level.getTile(x, y);

                    drawTile(g2d, offset.x + x * SCALED_TILE_WIDTH, offset.y + y * SCALED_TILE_HEIGHT, t);
                }
            }

            if (!hideTileCursor) {
                Composite old = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));

                drawTile(g2d, offset.x + hoverX * SCALED_TILE_WIDTH, offset.y + hoverY * SCALED_TILE_HEIGHT, selectedTile);

                g2d.setComposite(old);
            }
        } finally {
            g2d.dispose();
        }
    }

    private void drawTile(Graphics2D g2d, int x, int y, Tile t) {
        int texX = t.getTexX();
        int texY = t.getTexY();

        texX *= TILE_WIDTH;
        texY *= TILE_HEIGHT;

        g2d.drawImage(TILESET, x, y, x + SCALED_TILE_WIDTH, y + SCALED_TILE_HEIGHT, texX, texY, texX + TILE_WIDTH, texY + TILE_HEIGHT, null);
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                move(e);
                level.setTile(hoverX, hoverY, selectedTile);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                move(e);
                level.setTile(hoverX, hoverY, selectedTile);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                move(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hideTileCursor = false;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideTileCursor = true;
                repaint();
            }
        };
    }

    private void move(MouseEvent e) {
        Point offset = getOffset();

        hoverX = (e.getX() - offset.x) / SCALED_TILE_WIDTH;
        hoverY = (e.getY() - offset.y) / SCALED_TILE_HEIGHT;

        repaint();
    }

    private Point getOffset() {
        Rectangle visible = getVisibleRect();

        Dimension dim = getSize();
        Insets insets = getInsets();

        Dimension in = Utils.sub(dim, insets);

        Point offset = new Point();

        int lvlWidth = level.getWidth();
        int lvlHeight = level.getHeight();


        // Apply offset only if the dimension of the component match the visible rect
        // because when they are equals, we try to center the tile map
        if (visible.width == dim.width) {
            offset.x = (in.width - lvlWidth * SCALED_TILE_WIDTH) / 2;
        }
        if (visible.height == dim.height) {
            offset.y = (in.height - lvlHeight * SCALED_TILE_HEIGHT) / 2;
        }

        return offset;
    }

    @Override
    public void selectedTileChanged(Tile tile) {
        selectedTile = tile;
    }

    @Override
    public void levelChanged(Level newLevel) {
        this.level = newLevel;

        repaint();
    }
}