package fr.poulpogaz.isekai.editor.ui.colorpicker;

import java.awt.*;

public class AlphaSlider extends Slider {

    public AlphaSlider() {

    }

    public AlphaSlider(float value) {
        super(value);
    }

    public AlphaSlider(float value, boolean vertical) {
        super(value, vertical);
    }

    public AlphaSlider(float value, boolean vertical, boolean invert) {
        super(value, vertical, invert);
    }

    @Override
    protected void paintHorizontalTrack(Graphics2D g2d, Rectangle bounds) {
        if (isInvert()) {
            g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, bounds.width, 0, new Color(0, 0, 0, 0)));
        } else {
            g2d.setPaint(new GradientPaint(bounds.width, 0, Color.WHITE, 0, 0, new Color(0, 0, 0, 0)));
        }

        g2d.fill(bounds);
    }

    @Override
    protected void paintVerticalTrack(Graphics2D g2d, Rectangle bounds) {
        if (isInvert()) {
            g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, 0, bounds.height, new Color(0, 0, 0, 0)));
        } else {
            g2d.setPaint(new GradientPaint(0, bounds.height, Color.WHITE, 0, 0, new Color(0, 0, 0, 0)));
        }

        g2d.fill(bounds);
    }
}