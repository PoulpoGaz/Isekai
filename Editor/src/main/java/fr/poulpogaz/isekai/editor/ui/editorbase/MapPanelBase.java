package fr.poulpogaz.isekai.editor.ui.editorbase;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.MapSizeListener;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.utils.Bounds;
import fr.poulpogaz.isekai.editor.utils.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

public abstract class MapPanelBase<E extends EditorModelBase<M, T>, M extends Map<M, T>, T> extends JPanel {

    protected final Pack pack;
    protected final E editor;

    protected M map;
    protected MapSizeListener<M> mapSizeListener;
    protected ChangeListener mapChangedListener;

    protected int pixelSize = 4;

    protected int hoverX;
    protected int hoverY;

    protected boolean showCursor;

    public MapPanelBase(Pack pack, E editor) {
        this.pack = pack;
        this.editor = editor;

        MouseAdapter listener = createMouseAdapter();
        addMouseListener(listener);
        addMouseMotionListener(listener);

        addSelectedMapListener();
        addShowGridListener();

        mapSizeListener = createMapSizeListener();
        mapChangedListener = createMapChangedListener();

        map = editor.getSelectedMap();
        map.addSizeListener(mapSizeListener);
        map.addChangeListener(mapChangedListener);

        setPreferredSize();
    }

    protected void addSelectedMapListener() {
        editor.addPropertyChangeListener(EditorModelBase.SELECTED_MAP_PROPERTY, this::selectedMapChanged);
    }

    protected void addShowGridListener() {
        editor.addPropertyChangeListener(EditorModelBase.SHOW_GRID_PROPERTY, (e) -> repaint());
    }

    protected MapSizeListener<M> createMapSizeListener() {
        return this::mapSizeChanged;
    }

    protected ChangeListener createMapChangedListener() {
        return this::mapChanged;
    }

    protected MouseAdapter createMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                move(e);
                applyTool();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                move(e);
                applyTool();
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
                move(e);
            }
        };
    }

    protected void setPreferredSize() {
        setPreferredSize(new Dimension(map.getWidth() * pixelSize, map.getHeight() * pixelSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Rectangle visibleRect = getVisibleRect();
        Point offset = getOffset(visibleRect);
        Bounds mapBounds = getMapBounds(visibleRect);

        try {
            paintMap(g2d, offset, visibleRect, mapBounds);

            if (editor.isShowGrid()) {
                paintGrid(g2d, offset, visibleRect, mapBounds);
            }

            if (showCursor) {
                paintCursor(g2d, offset, visibleRect, mapBounds);
            }

        } finally {
            g2d.dispose();
        }
    }

    protected abstract void paintMap(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds mapBounds);

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

    protected void paintCursor(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds mapBounds) {

    }

    protected Bounds getMapBounds(Rectangle visibleRect) {
        int x = visibleRect.x / pixelSize;
        int y = visibleRect.y / pixelSize;
        int w = (int) Math.ceil(visibleRect.getWidth() / pixelSize) + 1;
        int h = (int) Math.ceil(visibleRect.getHeight() / pixelSize) + 1;

        return new Bounds(Utils.clamp(x, 0, map.getWidth()),
                Utils.clamp(y, 0, map.getHeight()),
                Utils.clamp(w + x, 0, map.getWidth()),
                Utils.clamp(h + y, 0, map.getHeight()));
    }

    protected Point getOffset(Rectangle visible) {
        Dimension dim = getSize();
        Insets insets = getInsets();

        Dimension in = Utils.sub(dim, insets);

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

    protected void move(MouseEvent e) {
        Point offset = getOffset(getVisibleRect());

        hoverX = (e.getX() - offset.x) / pixelSize;
        hoverY = (e.getY() - offset.y) / pixelSize;

        repaint();
    }

    protected void applyTool() {
        if (isCursorInsideMap()) {
            map.setModifyingMap(true);
            editor.applyTool(hoverX, hoverY);
            map.setModifyingMap(false);
        }
    }

    protected boolean isCursorInsideMap() {
        return hoverX >= 0 && hoverX < map.getWidth() &&
                hoverY >= 0 && hoverY < map.getHeight();
    }

    protected void selectedMapChanged(PropertyChangeEvent evt) {
        map.removeSizeListener(mapSizeListener);
        map.removeChangeListener(mapChangedListener);

        map = editor.getSelectedMap();
        map.addSizeListener(mapSizeListener);
        map.addChangeListener(mapChangedListener);

        repaint();
    }

    protected void mapChanged(ChangeEvent e) {
        repaint();
    }

    protected void mapSizeChanged(M map, int newWidth, int newHeight) {
        setPreferredSize();
        repaint();
    }
}