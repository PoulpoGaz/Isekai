package fr.poulpogaz.isekai.editor.ui.icons;

import fr.poulpogaz.isekai.editor.utils.icons.AbstractIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class CloseIcon extends AbstractIcon {

    public static final String IDENTIFIER = "CloseIcon";

    public CloseIcon() {
        super(UIManager.getColor("Actions.Red"));
    }

    public CloseIcon(Color color) {
        super(color);
    }

    public CloseIcon(int size, Color color) {
        super(size, color);
    }

    public CloseIcon(int width, int height) {
        super(width, height, UIManager.getColor("Actions.Red"));
    }

    public CloseIcon(int width, int height, Color color) {
        super(width, height, color);
    }

    @Override
    protected void paintIcon(Component c, Graphics2D g2) {
        float x1 = width / 4f;
        float x2 = 3 * width / 4f;

        float y1 = height / 4f;
        float y2 = 3 * height / 4f;

        g2.draw(new Line2D.Float(x1, y1, x2, y2));
        g2.draw(new Line2D.Float(x1, y2, x2, y1));
    }
}