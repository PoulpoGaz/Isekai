package fr.poulpogaz.isekai.editor.ui.colorpicker;

import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.isekai.editor.utils.concurrent.Alarm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.basic.BasicRootPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.TimeUnit;

public class Pipette implements IPipette {

    public static final String PIXEL_SIZE_PROPERTY = "PixelSizeProperty";
    public static final String SCREEN_CAPTURE_SIZE_PROPERTY = "ScreenCaptureSizeProperty";
    public static final String SELECTED_COLOR_PROPERTY = "SelectedColorProperty";

    private static final Logger LOGGER = LogManager.getLogger(Pipette.class);

    private static final int BORDER_SIZE = 2;

    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private final JDialog window;
    private final Preview view;
    private final Robot robot;

    private Alarm alarm;

    private int pixelSize = 16;
    private Dimension screenCaptureSize = new Dimension(9, 9);

    private Color targetedPixelColor;
    private Color selectedColor;

    public Pipette() {
        robot = createRobot();
        view = new Preview();
        window = createWindow();
    }

    protected Robot createRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            LOGGER.warn("Failed to create robot", e);
            return null;
        }
    }

    protected JDialog createWindow() {
        JDialog window = new JDialog((Frame) null, "", true);

        window.setSize(getWindowSize());
        window.setAlwaysOnTop(true);
        window.setUndecorated(true);
        window.getRootPane().setUI(new BasicRootPaneUI());
        window.setCursor(Utils.getBlankCursor());
        window.setBackground(new Color(0, 0, 0, 0));

        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pick();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updatePipette();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                updatePipette();
            }
        };

        window.addMouseListener(listener);
        window.addMouseMotionListener(listener);

        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pick();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });

        window.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (e.isTemporary()) {
                    pick();
                } else {
                    dispose();
                }
            }
        });

        window.setContentPane(view);

        return window;
    }

    protected void updatePipette() {
        Point mouse = MouseInfo.getPointerInfo().getLocation();

        Dimension size = window.getSize();

        Point location = new Point(mouse.x - size.width / 2, mouse.y - size.height / 2);
        window.setLocation(location);

        Rectangle rectangle = new Rectangle();

        rectangle.x = location.x + BORDER_SIZE;
        rectangle.y = location.y + BORDER_SIZE;
        rectangle.width = screenCaptureSize.width;
        rectangle.height = screenCaptureSize.height;

        BufferedImage screenCapture = robot.createScreenCapture(rectangle);

        Point target = getTargetedPixel();
        targetedPixelColor = robot.getPixelColor(target.x, target.y);

        view.setScreenShot(screenCapture);

        window.repaint();
    }

    @Override
    public void show() {
        if (!window.isVisible()) {
            if (alarm == null) {
                alarm = new Alarm();
            }
            alarm.schedule(this::updatePipette, 0, 1, TimeUnit.SECONDS);

            window.setVisible(true);
        }
    }

    public boolean isVisible() {
        return window.isVisible();
    }

    @Override
    public void dispose() {
        if (window.isVisible()) {
            alarm.cancelAllTasks();
            window.dispose();
        }
    }

    @Override
    public Color pick() {
        if (window.isVisible()) {
            setSelectedColor(targetedPixelColor);
            dispose();

            return selectedColor;
        }

        return null;
    }

    protected void setSelectedColor(Color color) {
        if (color != null && selectedColor != color) {
            Color old = selectedColor;

            this.selectedColor = color;

            firePropertyChange(SELECTED_COLOR_PROPERTY, old, color);
        }
    }

    @Override
    public Color getSelectedColor() {
        return selectedColor;
    }

    private Point getTargetedPixel() {
        Point location = window.getLocation();

        int x = location.x + BORDER_SIZE + screenCaptureSize.width / 2;
        int y = location.y + BORDER_SIZE + screenCaptureSize.height / 2;

        return new Point(x, y);
    }

    private Dimension getWindowSize() {
        Dimension cursor = getCursorSize();
        Dimension preview = getPreviewSize();

        return new Dimension(cursor.width + preview.width, cursor.height + preview.height);
    }

    private Dimension getPreviewSize() {
        return new Dimension(screenCaptureSize.width * pixelSize, screenCaptureSize.height * pixelSize);
    }

    private Dimension getCursorSize() {
        return new Dimension(BORDER_SIZE * 2 + screenCaptureSize.width, BORDER_SIZE * 2 + screenCaptureSize.height);
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(int pixelSize) {
        if (pixelSize >= 1 && this.pixelSize != pixelSize) {
            int old = this.pixelSize;

            this.pixelSize = pixelSize;
            window.setSize(getWindowSize());

            firePropertyChange(PIXEL_SIZE_PROPERTY, old, pixelSize);
        }
    }

    public Dimension getScreenCaptureSize() {
        return screenCaptureSize;
    }

    public void setScreenCaptureSize(Dimension screenCaptureSize) {
        if (screenCaptureSize != null
                && screenCaptureSize.width >= 1 && screenCaptureSize.height >= 1
                && this.screenCaptureSize != screenCaptureSize) {

            Dimension old = this.screenCaptureSize;

            this.screenCaptureSize = screenCaptureSize;
            window.setSize(getWindowSize());

            if (isVisible()) {
                updatePipette();
            }

            firePropertyChange(SCREEN_CAPTURE_SIZE_PROPERTY, old, screenCaptureSize);
        }
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(property, listener);
    }

    protected void firePropertyChange(String property, Object oldValue, Object newValue) {
        changeSupport.firePropertyChange(property, oldValue, newValue);
    }

    private class Preview extends JPanel {

        private BufferedImage image = null;

        public Preview() {

        }

        @Override
        protected void paintComponent(Graphics g) {
            if (image == null) {
                return;
            }

            Rectangle outerBorder = new Rectangle(0, 0,
                    screenCaptureSize.width + BORDER_SIZE, screenCaptureSize.height + BORDER_SIZE);

            Rectangle innerBorder = new Rectangle(BORDER_SIZE / 2, BORDER_SIZE / 2,
                    screenCaptureSize.width + BORDER_SIZE / 2, screenCaptureSize.height + BORDER_SIZE / 2);

            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(Color.BLACK);
            g2d.draw(outerBorder);

            g2d.setColor(Color.WHITE);
            g2d.draw(innerBorder);

            Dimension size = Pipette.this.getPreviewSize();

            g2d.drawImage(image, outerBorder.width + 1, outerBorder.height + 1, size.width, size.height, null);

            g2d.setColor(new Color(187, 45, 62));

            if (pixelSize > 2) {
                g2d.setStroke(new BasicStroke(2f));
            }

            int x = outerBorder.width + 1 + pixelSize * (screenCaptureSize.width / 2);
            int y = outerBorder.height + 1 + pixelSize * (screenCaptureSize.height / 2);

            g2d.drawRect(x, y, pixelSize, pixelSize);
        }

        private void setScreenShot(BufferedImage image) {
            this.image = image;
        }
    }
}