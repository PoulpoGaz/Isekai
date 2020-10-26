package fr.poulpogaz.isekai.editor.ui.colorpicker;

import fr.poulpogaz.isekai.editor.model.Model;

import java.awt.*;
import java.util.Objects;

public class ColorModel extends Model {

    public static final String COLOR_PROPERTY = "ColorProperty";

    private Color color;
    private final int[] hsb = new int[3];

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

            float[] floats = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
            hsb[0] = (int) (floats[0] * 360);
            hsb[1] = (int) (floats[1] * 100);
            hsb[2] = (int) (floats[2] * 100);

            firePropertyChange(COLOR_PROPERTY, old, color);
        }
    }

    public int[] getHSB() {
        return hsb;
    }

    public void setHSB(int hue, int saturation, int brightness) {
        if ((hsb[0] != hue || hsb[1] != saturation || hsb[2] != brightness) && checkHSBRange(hue, saturation, brightness)) {
            int rgba = Color.HSBtoRGB(hue / 360f, saturation / 100f, brightness / 100f);
            rgba = ((getAlpha() & 0xFF) << 24) | (rgba & 0x00FFFFFF);

            Color old = this.color;

            this.color = new Color(rgba, true);
            this.hsb[0] = hue;
            this.hsb[1] = saturation;
            this.hsb[2] = brightness;

            firePropertyChange(COLOR_PROPERTY, old, color);
        }
    }

    private boolean checkHSBRange(int hue, int saturation, int brightness) {
        if (hue < 0 || hue > 360) {
            return false;
        }

        if (saturation < 0 || saturation > 100) {
            return false;
        }

        return brightness >= 0 && brightness <= 100;
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

    public int getHue() {
        return hsb[0];
    }

    public void setHue(int hue) {
        setHSB(hue, hsb[1], hsb[2]);
    }

    public int getSaturation() {
        return hsb[1];
    }

    public void setSaturation(int saturation) {
        setHSB(hsb[0], saturation, hsb[2]);
    }

    public int getBrightness() {
        return hsb[2];
    }

    public void setBrightness(int brightness) {
        setHSB(hsb[0], hsb[1], brightness);
    }
}