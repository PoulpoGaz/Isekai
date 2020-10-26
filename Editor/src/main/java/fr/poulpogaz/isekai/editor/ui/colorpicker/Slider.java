package fr.poulpogaz.isekai.editor.ui.colorpicker;

import fr.poulpogaz.isekai.editor.utils.Math2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Slider extends JComponent {

    private static final int BORDER_SIZE = 3;

    public static final String ORIENTATION_PROPERTY = "OrientationProperty";
    public static final String VALUE_PROPERTY = "ValueProperty";
    public static final String INVERT_PROPERTY = "InvertProperty";

    private float value;
    private boolean vertical;
    private boolean invert;

    public Slider() {
        this(0, true, false);
    }

    public Slider(float value) {
        this(value, true, false);
    }

    public Slider(float value, boolean vertical) {
        this(value, vertical, false);
    }

    public Slider(float value, boolean vertical, boolean invert) {
        setValue(value);
        setVertical(vertical);
        setInvert(invert);

        if (vertical) {
            setPreferredSize(new Dimension(25, 100));
        } else {
            setPreferredSize(new Dimension(100, 25));
        }

        initListeners();
    }

    protected void initListeners() {
        MouseListener listener = new MouseListener();

        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    private float cursorToValue(int cursor) {
        Rectangle bounds = getValidBounds();

        float value;
        if (vertical) {
            value = (float) (cursor - bounds.y) / bounds.height;
        } else {
            value = (float) (cursor - bounds.x) / bounds.width;
        }

        if (invert) {
            value = 1 - value;
        }

        return value;
    }

    private int valueToCursor(float value) {
        Rectangle bounds = getValidBounds();

        if (invert) {
            value = 1 - value;
        }

        if (vertical) {
            return (int) (value * bounds.height + bounds.y);
        } else {
            return (int) (value * bounds.width + bounds.x);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Rectangle bounds = getValidBounds();

        int cursor = valueToCursor(value);

        Graphics2D g2d = (Graphics2D) g;
        if(vertical) {
            paintVerticalTrack(g2d, bounds);
            paintVerticalCursor(g2d, bounds, cursor);
        } else {
            paintHorizontalTrack(g2d, bounds);
            paintHorizontalCursor(g2d, bounds, cursor);
        }
    }

    protected void paintVerticalTrack(Graphics2D g2d, Rectangle bounds) {
        GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, 0, bounds.height, Color.BLACK);
        g2d.setPaint(gradient);

        g2d.fill(bounds);
    }

    protected void paintHorizontalTrack(Graphics2D g2d, Rectangle bounds) {
        GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, bounds.width, 0, Color.BLACK);
        g2d.setPaint(gradient);

        g2d.fill(bounds);
    }

    protected void paintVerticalCursor(Graphics2D g2d, Rectangle bounds, int cursorPos) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(bounds.x - 2, cursorPos - 2, bounds.width + 3, 4);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(bounds.x - 1, cursorPos - 1, bounds.width + 1, 2);
    }

    protected void paintHorizontalCursor(Graphics2D g2d, Rectangle bounds, int cursorPos) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(cursorPos - 2, bounds.y - 2, 4, bounds.height + 3);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(cursorPos - 1, bounds.y - 1, 2, bounds.height + 1);
    }

    private Rectangle getValidBounds() {
        Rectangle rectangle = getBounds();
        Insets insets = getInsets();

        rectangle.x = insets.left + BORDER_SIZE;
        rectangle.y = insets.top + BORDER_SIZE;
        rectangle.width = rectangle.width - insets.left - insets.right - 2 * BORDER_SIZE;
        rectangle.height = rectangle.height - insets.top - insets.bottom - 2 * BORDER_SIZE;

        return rectangle;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        if (value >= 0 && value <= 1 && this.value != value) {
            float old = this.value;

            this.value = value;

            firePropertyChange(VALUE_PROPERTY, old, value);
            repaint();
        }
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        if (this.vertical != vertical) {
            boolean old = this.vertical;

            this.vertical = vertical;

            firePropertyChange(ORIENTATION_PROPERTY, old, vertical);
            repaint();
        }
    }

    public boolean isInvert() {
        return invert;
    }

    public void setInvert(boolean invert) {
        if (this.invert != invert) {
            boolean old = this.invert;

            this.invert = invert;

            firePropertyChange(INVERT_PROPERTY, old, invert);
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

            int cursorPos;

            if(vertical) {
                cursorPos = Math2.clamp(point.y, bounds.y, bounds.height + bounds.y);
            } else {
                cursorPos = Math2.clamp(point.x, bounds.x, bounds.width + bounds.x);
            }

            setValue(cursorToValue(cursorPos));
        }
    }
}