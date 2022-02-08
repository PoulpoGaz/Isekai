package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.commons.Math2;
import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.LevelModel;
import fr.poulpogaz.isekai.editor.pack.LevelSizeListener;
import fr.poulpogaz.isekai.editor.pack.PackModel;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.utils.Bounds;
import fr.poulpogaz.isekai.editor.utils.GraphicsUtils;
import org.joml.Vector2i;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

public class TileMapPanel extends JPanel {

    protected final PackModel pack;

    protected final LevelEditorModel editor;
    protected LevelModel map;

    protected LevelSizeListener mapSizeListener;
    protected ChangeListener mapChangedListener;

    protected final int pixelSize = 32;

    protected int hoverX;
    protected int hoverY;

    protected boolean showCursor;

    public TileMapPanel(PackModel pack, LevelEditorModel editor) {
        this.pack = pack;
        this.editor = editor;
        this.map = editor.getSelectedLevel();

        MouseAdapter listener = createMouseAdapter();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(listener);

        mapSizeListener = createMapSizeListener();
        mapChangedListener = createMapChangedListener();

        map.addSizeListener(mapSizeListener);
        map.addChangeListener(mapChangedListener);

        addSelectedMapListener();
        addShowGridListener();

        setPreferredSize();
    }

    protected LevelSizeListener createMapSizeListener() {
        return this::mapSizeChanged;
    }

    protected ChangeListener createMapChangedListener() {
        return this::mapChanged;
    }

    protected void setPreferredSize() {
        setPreferredSize(new Dimension(map.getWidth() * pixelSize, map.getHeight() * pixelSize));
    }

    protected void addSelectedMapListener() {
        editor.addPropertyChangeListener(LevelEditorModel.SELECTED_MAP_PROPERTY, this::selectedMapChanged);
    }

    protected void addShowGridListener() {
        editor.addPropertyChangeListener(LevelEditorModel.SHOW_GRID_PROPERTY, (e) -> repaint());
    }

    protected MouseAdapter createMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                moveCursor(e);
                editor.pressTool(hoverX, hoverY);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                moveCursor(e);
                editor.pressTool(hoverX, hoverY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                editor.releaseTool(hoverX, hoverY);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                showCursor = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showCursor = false;
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                moveCursor(e);
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            paint(g2d);
        } finally {
            g2d.dispose();
        }
    }

    protected void paint(Graphics2D g2d) {
        Rectangle visibleRect = getVisibleRect();
        Point offset = getOffset(visibleRect);
        Bounds mapBounds = getMapBounds(visibleRect);

        paintMap(g2d, offset, visibleRect, mapBounds);

        if (editor.isShowGrid()) {
            paintGrid(g2d, offset, visibleRect, mapBounds);
        }

        if (showCursor) {
            paintCursor(g2d, offset, visibleRect, mapBounds);
        }
    }

    protected Bounds getMapBounds(Rectangle visibleRect) {
        int x = visibleRect.x / pixelSize;
        int y = visibleRect.y / pixelSize;
        int w = (int) Math.ceil(visibleRect.getWidth() / pixelSize) + 1;
        int h = (int) Math.ceil(visibleRect.getHeight() / pixelSize) + 1;

        return new Bounds(Math2.clamp(x, 0, map.getWidth()),
                Math2.clamp(y, 0, map.getHeight()),
                Math2.clamp(w + x, 0, map.getWidth()),
                Math2.clamp(h + y, 0, map.getHeight()));
    }

    protected Point getOffset(Rectangle visible) {
        Dimension dim = getSize();
        Insets insets = getInsets();

        Dimension in = GraphicsUtils.sub(dim, insets);

        Point offset = new Point();

        int lvlWidth = map.getWidth();
        int lvlHeight = map.getHeight();


        // Apply offset only if the dimension of the component match the visible rect
        // because when they are equals, we try to center the tile map
        if (visible.width == dim.width) {
            offset.x = (in.width - lvlWidth * pixelSize) / 2;
        }
        if (visible.height == dim.height) {
            offset.y = (in.height - lvlHeight * pixelSize) / 2;
        }

        return offset;
    }

    protected void paintMap(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds mapBounds) {
        Vector2i player = map.getPlayerPos();

        for (int y = mapBounds.getMinY(); y < mapBounds.getMaxY(); y++) {
            for (int x = mapBounds.getMinX(); x < mapBounds.getMaxX(); x++) {
                Tile t = map.get(x, y);

                int drawX = offset.x + x * pixelSize;
                int drawY = offset.y + y * pixelSize;

                g2d.drawImage(PackSprites.img(t), drawX, drawY, pixelSize, pixelSize, null);

                if (player.equals(x, y)) {
                    g2d.drawImage(PackSprites.getPlayer(), drawX, drawY, pixelSize, pixelSize, null);
                }
            }
        }
    }

    protected void paintGrid(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds mapBounds) {
        g2d.setColor(Color.BLACK);

        int minX = Math.max(mapBounds.getMinX() * pixelSize + offset.x, visibleRect.x);
        int minY = Math.max(mapBounds.getMinY() * pixelSize + offset.y, visibleRect.y);
        int maxX = Math.min(mapBounds.getMaxX() * pixelSize + offset.x, visibleRect.x + visibleRect.width);
        int maxY = Math.min(mapBounds.getMaxY() * pixelSize + offset.y, visibleRect.y + visibleRect.height);

        for (int x = mapBounds.getMinX(); x <= mapBounds.getMaxX(); x ++) { // Vertical lines
            int x_ = offset.x + x * pixelSize;

            g2d.drawLine(x_, minY, x_, maxY);
        }

        for (int y = mapBounds.getMinY(); y <= mapBounds.getMaxY(); y ++) { // Horizontal lines
            int y_ = offset.y + y * pixelSize;

            g2d.drawLine(minX, y_, maxX, y_);
        }
    }

    protected void paintCursor(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds bounds) {
        Composite old = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));

        int x = offset.x + hoverX * pixelSize;
        int y = offset.y + hoverY * pixelSize;

        g2d.drawImage(editor.getToolSprite(), x, y, pixelSize, pixelSize, null);

        g2d.setComposite(old);
    }

    protected void moveCursor(MouseEvent e) {
        Point offset = getOffset(getVisibleRect());

        hoverX = (e.getX() - offset.x) / pixelSize;
        if (e.getX() < offset.x) {
            hoverX--;
        }

        hoverY = (e.getY() - offset.y) / pixelSize;
        if (e.getY() < offset.y) {
            hoverY--;
        }

        repaint();
    }

    protected void selectedMapChanged(PropertyChangeEvent evt) {
        map.removeSizeListener(mapSizeListener);
        map.removeChangeListener(mapChangedListener);

        map = editor.getSelectedLevel();
        map.addSizeListener(mapSizeListener);
        map.addChangeListener(mapChangedListener);

        setPreferredSize();
        revalidate();
        repaint();
    }

    protected void mapChanged(ChangeEvent e) {
        repaint();
    }

    protected void mapSizeChanged(LevelModel map, int newWidth, int newHeight) {
        setPreferredSize();
        revalidate();
        repaint();
    }
}