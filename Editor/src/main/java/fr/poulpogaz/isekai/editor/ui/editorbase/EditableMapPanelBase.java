package fr.poulpogaz.isekai.editor.ui.editorbase;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.utils.Bounds;
import fr.poulpogaz.isekai.editor.utils.Math2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.beans.PropertyChangeEvent;

// TODO: Make a better zoom
public abstract class EditableMapPanelBase<E extends EditorModel<M, T>, M extends Map<M, T>, T> extends MapPanelBase<M, T> {

    protected final E editor;

    protected int hoverX;
    protected int hoverY;

    protected boolean showCursor;

    public EditableMapPanelBase(Pack pack, E editor) {
        super(pack, editor.getSelectedMap());
        this.editor = editor;

        addSelectedMapListener();
        addShowGridListener();
    }

    protected void addSelectedMapListener() {
        editor.addPropertyChangeListener(EditorModel.SELECTED_MAP_PROPERTY, this::selectedMapChanged);
    }

    protected void addShowGridListener() {
        editor.addPropertyChangeListener(EditorModel.SHOW_GRID_PROPERTY, (e) -> repaint());
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

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                zoom(e);
            }
        };
    }

    @Override
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

    protected void move(MouseEvent e) {
        Point offset = getOffset(getVisibleRect());

        hoverX = (e.getX() - offset.x) / pixelSize;
        hoverY = (e.getY() - offset.y) / pixelSize;

        repaint();
    }

    protected void zoom(MouseWheelEvent e) {
        if (canZoom() && e.isControlDown()) {
            pixelSize = Math2.clamp(pixelSize - e.getWheelRotation(), minZoom, maxZoom);

            move(e); // update cursor

            setPreferredSize();
            repaint();
        }
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
}