package fr.poulpogaz.isekai.editor.utils.icons;

import com.formdev.flatlaf.icons.FlatAbstractIcon;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractIcon extends FlatAbstractIcon implements Icon, UIResource {

    public AbstractIcon() {
        this(16, UIManager.getColor("Icon.color"));
    }

    public AbstractIcon(Color color) {
        this(16, color);
    }

    public AbstractIcon(int size, Color color) {
        super(size, size, color);
    }

    public AbstractIcon(int width, int height) {
        super(width, height, UIManager.getColor("Icon.color"));
    }

    public AbstractIcon(int width, int height, Color color) {
        super(width, height, color);
    }

    @Override
    protected abstract void paintIcon(Component c, Graphics2D g2);

    public AbstractIcon derive(Color color) {
        return new DelegateIcon(width, height, color, this);
    }

    public AbstractIcon derive(int width, int height) {
        return new DelegateIcon(width, height, color, this);
    }

    public AbstractIcon derive(int width, int height, Color color) {
        return new DelegateIcon(width, height, color, this);
    }

    public BufferedImage getAsImage(Component c) {
        BufferedImage image = new BufferedImage(getIconWidth(), getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        paintIcon(c, image.getGraphics(), 0, 0);

        return image;
    }

    public BufferedImage getAsImage() {
        return getAsImage(null);
    }

    public Color getIconColor() {
        return color;
    }
}