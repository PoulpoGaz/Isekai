package fr.poulpogaz.isekai.editor.ui.editorbase;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.MapSizeListener;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.utils.Bounds;
import fr.poulpogaz.isekai.editor.utils.Math2;
import fr.poulpogaz.isekai.editor.utils.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public abstract class MapPanelBase<M extends Map<M, T>, T> extends JPanel {

    protected final Pack pack;

    protected M map;
    protected MapSizeListener<M> mapSizeListener;
    protected ChangeListener mapChangedListener;

    protected int pixelSize = 4;

    public MapPanelBase(Pack pack, M map) {
        this.pack = pack;
        this.map = map;

        MouseAdapter listener = createMouseAdapter();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(listener);

        mapSizeListener = createMapSizeListener();
        mapChangedListener = createMapChangedListener();

        map.addSizeListener(mapSizeListener);
        map.addChangeListener(mapChangedListener);

        setPreferredSize();
    }

    protected MapSizeListener<M> createMapSizeListener() {
        return this::mapSizeChanged;
    }

    protected ChangeListener createMapChangedListener() {
        return this::mapChanged;
    }

    protected abstract MouseAdapter createMouseAdapter();

    protected void setPreferredSize() {
        setPreferredSize(new Dimension(map.getWidth() * pixelSize, map.getHeight() * pixelSize));
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
    }

    protected abstract void paintMap(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds mapBounds);

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

    protected void mapChanged(ChangeEvent e) {
        repaint();
    }

    protected void mapSizeChanged(M map, int newWidth, int newHeight) {
        setPreferredSize();
        revalidate();
        repaint();
    }
}