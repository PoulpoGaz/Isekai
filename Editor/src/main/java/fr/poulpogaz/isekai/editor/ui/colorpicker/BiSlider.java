package fr.poulpogaz.isekai.editor.ui.colorpicker;

import fr.poulpogaz.isekai.editor.utils.Math2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class BiSlider extends JComponent {

    public static final String POSITION_PROPERTY = "PositionProperty";

    private static final int BORDER_SIZE = 3;

    private Point2D position;

    public BiSlider() {
        this(new Point2D.Double(0, 0));
    }

    public BiSlider(Point2D position) {
        this.position = position;

        setPreferredSize(new Dimension(100, 100));

        MouseListener listener = new MouseListener();

        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    private Point2D cursorToValue(Point cursor) {
        Rectangle bounds = getValidBounds();

        double x = (cursor.x - bounds.getX()) / bounds.getWidth();
        double y = (cursor.y - bounds.getY()) / bounds.getHeight();

        return new Point2D.Double(x, y);
    }

    private Point valueToCursor(Point2D value) {
        Rectangle bounds = getValidBounds();

        int x = (int) (value.getX() * bounds.width + bounds.x);
        int y = (int) (value.getY() * bounds.height + bounds.y);

        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        paintContent(g2d, getValidBounds());
        paintCursor(g2d, valueToCursor(position));
    }

    protected void paintContent(Graphics2D g2d, Rectangle bounds) {
        g2d.setColor(Color.BLACK);
        g2d.fill(bounds);
    }

    protected void paintCursor(Graphics2D g2d, Point cursor) {
        g2d.setColor(Color.WHITE);
        g2d.drawRect(cursor.x - 1, cursor.y - 1, 2, 2);
    }

    private Rectangle getValidBounds() {
        Rectangle bounds = getBounds();
        Insets insets = getInsets();

        return new Rectangle(insets.left + BORDER_SIZE,
                insets.top + BORDER_SIZE,
                bounds.width - insets.left - insets.right - BORDER_SIZE * 2,
                bounds.height - insets.top - insets.bottom - BORDER_SIZE * 2);
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        if (this.position != position &&
                position.getX() >= 0 && position.getX() <= 1 &&
                position.getY() >= 0 && position.getY() <= 1) {
            Point2D old = this.position;

            this.position = position;

            firePropertyChange(POSITION_PROPERTY, old, position);
            repaint();
        }
    }

    private class MouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            processMouseEvent(e.getPoint());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            processMouseEvent(e.getPoint());
        }

        private void processMouseEvent(Point point) {
            Rectangle bounds = getValidBounds();

            point.x = Math2.clamp(point.x, bounds.x, bounds.width + bounds.x);
            point.y = Math2.clamp(point.y, bounds.y, bounds.height + bounds.y);

            setPosition(cursorToValue(point));
        }
    }
}