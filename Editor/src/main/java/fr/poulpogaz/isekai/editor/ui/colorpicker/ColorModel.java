package fr.poulpogaz.isekai.editor.ui.colorpicker;

import fr.poulpogaz.isekai.editor.model.Model;

import java.awt.*;
import java.util.Objects;

public class ColorModel extends Model {

    public static final String COLOR_PROPERTY = "ColorProperty";

    private Color color;
    private float[] hsb;

    public ColorModel() {
        this(Color.WHITE);
    }

    public ColorModel(Color color) {
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (!Objects.equals(this.color, color)) {
            Color old = this.color;

            this.color = color;

            hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

            firePropertyChange(COLOR_PROPERTY, old, color);
        }
    }

    public float[] getHSB() {
        return hsb;
    }

    public void setHSB(float hue, float saturation, float brightness) {
        int rgba = Color.HSBtoRGB(hue, saturation, brightness);
        rgba = ((getAlpha() & 0xFF) << 24) | (rgba & 0x00FFFFFF);

        Color color = new Color(rgba, true);

        if (!color.equals(this.color)) {
            Color old = this.color;

            this.color = color;
            this.hsb = new float[] {hue, saturation, brightness};

            firePropertyChange(COLOR_PROPERTY, old, color);
        }
    }

    public int getRed() {
        return color.getRed();
    }

    public void setRed(int red) {
        setColor(new Color(red, getGreen(), getBlue(), getAlpha()));
    }

    public int getGreen() {
        return color.getGreen();
    }

    public void setGreen(int green) {
        setColor(new Color(getRed(), green, getBlue(), getAlpha()));
    }

    public int getBlue() {
        return color.getBlue();
    }

    public void setBlue(int blue) {
        setColor(new Color(getRed(), getGreen(), blue, getAlpha()));
    }

    public int getAlpha() {
        return color.getAlpha();
    }

    public void setAlpha(int alpha) {
        setColor(new Color(getRed(), getGreen(), getBlue(), alpha));
    }

    public float getHue() {
        return hsb[0];
    }

    public void setHue(float hue) {
        setHSB(hue, hsb[1], hsb[2]);
    }

    public float getSaturation() {
        return hsb[1];
    }

    public void setSaturation(float saturation) {
        setHSB(hsb[0], saturation, hsb[2]);
    }

    public float getBrightness() {
        return hsb[2];
    }

    public void setBrightness(float brightness) {
        setHSB(hsb[0], hsb[1], brightness);
    }
}