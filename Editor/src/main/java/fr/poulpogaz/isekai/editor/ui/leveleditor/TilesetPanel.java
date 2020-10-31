package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TilesetPanel extends JPanel {

    private static final int WIDTH = 3;
    private static final int HEIGHT = 2;

    private static final int tileSize = 48;
    private static final Dimension SIZE = new Dimension(tileSize * WIDTH, tileSize * HEIGHT);

    private final Pack pack;
    private final LevelEditorModel editor;

    public TilesetPanel(Pack pack, LevelEditorModel editor) {
        this.pack = pack;
        this.editor = editor;
        editor.addPropertyChangeListener(LevelEditorModel.TOOL_PROPERTY, (e) -> repaint());
        editor.addPropertyChangeListener(LevelEditorModel.SELECTED_ELEMENT_PROPERTY, (e) -> repaint());

        setBorder(BorderFactory.createTitledBorder("Tileset"));

        MouseAdapter adapter = getMouseAdapter();

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Insets insets = getInsets();

        Tile[] tiles = Tile.values();

        int x = 0;
        int y = 0;
        for (Tile tile : tiles) {
            AbstractSprite sprite = pack.getSprite(tile.getSprite());

            int xDraw = x * tileSize + insets.left;
            int yDraw = y * tileSize + insets.top;

            sprite.paint(g2d, xDraw, yDraw, tileSize, tileSize);

            if (editor.getSelectedElement() == tile) {
                g.setColor(new Color(0, 217, 255, 64));
                g.fillRect(xDraw, yDraw, tileSize, tileSize);
            }

            x++;

            if (x % WIDTH == 0) {
                x = 0;
                y++;
            }
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
        Dimension dim = new Dimension(SIZE);

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
        return wPoint.getX() >= 0 && wPoint.getX() < SIZE.width &&
                wPoint.getY() >= 0 && wPoint.getY() < SIZE.height;
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
            int x = (int) (point.getX()) / tileSize;
            int y = (int) (point.getY()) / tileSize;

            int i = y * WIDTH + x;

            Tile[] tiles = Tile.values();

            if (i < 0 || i >= tiles.length) {
                return;
            }

            editor.setSelectedElement(tiles[i]);

            repaint();
        }
    }
}