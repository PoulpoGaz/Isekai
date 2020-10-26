package fr.poulpogaz.isekai.editor.ui.colorpicker;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;

public class ColorPicker extends JPanel {

    private ColorModel model;

    private SaturationBrightnessChooser chooser;
    private AlphaSlider alphaSlider;
    private HueSlider hueSlider;
    private PreviewComponent previewComponent;

    private JTextField red;
    private JTextField green;
    private JTextField blue;
    private JTextField alpha;

    public ColorPicker() {
        model = new ColorModel();

        initComponents();
        initListeners();
    }

    private void initComponents() {
        setLayout(new MigLayout());

        chooser = new SaturationBrightnessChooser();
        chooser.setHue(model.getHue());
        chooser.setPosition(new Point2D.Float(model.getSaturation(), model.getBrightness()));
        chooser.setPreferredSize(new Dimension(150, 150));

        alphaSlider = new AlphaSlider(model.getAlpha() / 255f, false);
        alphaSlider.setPreferredSize(new Dimension(150, 25));

        hueSlider = new HueSlider(model.getHue(), true);
        hueSlider.setPreferredSize(new Dimension(25, 150));

        previewComponent = new PreviewComponent();
        previewComponent.setPreferredSize(new Dimension(25, 25));

        add(chooser, "cell 0 0");
        add(previewComponent, "cell 1 1, align center");
        add(alphaSlider, "cell 0 1, align center");
        add(hueSlider, "cell 1 0, align center");
    }

    private void initListeners() {
        chooser.addPropertyChangeListener(BiSlider.POSITION_PROPERTY, (evt) -> {
            Point2D point = (Point2D) evt.getNewValue();
            model.setHSB(model.getHue(), (float) point.getX(), (float) point.getY());
        });

        alphaSlider.addPropertyChangeListener(Slider.VALUE_PROPERTY, (evt) -> {
            int alpha = (int) ((float) evt.getNewValue() * 255);
            model.setAlpha(alpha);
        });

        hueSlider.addPropertyChangeListener(Slider.VALUE_PROPERTY, (evt) -> {
            model.setHue((float) evt.getNewValue());
        });

        model.addPropertyChangeListener(ColorModel.COLOR_PROPERTY, this::syncComponents);
    }

    private void syncComponents(PropertyChangeEvent evt) {
        hueSlider.setValue(model.getHue());
        alphaSlider.setValue(model.getAlpha() / 255f);

        chooser.setHue(model.getHue());
        chooser.setPosition(new Point2D.Float(model.getSaturation(), model.getBrightness()));

        previewComponent.repaint();
    }

    private class PreviewComponent extends JComponent {

        @Override
        protected void paintComponent(Graphics g) {
            Rectangle bounds = getBounds();
            Insets insets = getInsets();

            RoundRectangle2D.Float rectangle = new RoundRectangle2D.Float();
            rectangle.x = insets.left;
            rectangle.y = insets.top;
            rectangle.width = bounds.width - insets.left - insets.right;
            rectangle.height = bounds.height - insets.top - insets.bottom;
            rectangle.archeight = 2;
            rectangle.arcwidth = 2;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(model.getColor());
            g2d.fill(rectangle);
        }
    }
}