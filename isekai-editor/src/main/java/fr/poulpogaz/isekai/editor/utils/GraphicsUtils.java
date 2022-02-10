package fr.poulpogaz.isekai.editor.utils;

import fr.poulpogaz.isekai.editor.ui.Icons;
import fr.poulpogaz.isekai.editor.ui.layout.SplitLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class GraphicsUtils {

    /**
     * @see Color#brighter()
     */
    public static Color brighter(Color color, double factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();

        int i = (int)(1.0/(1.0-factor));

        if ( r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }

        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;

        return new Color(Math.min((int)(r/factor), 255),
                Math.min((int)(g/factor), 255),
                Math.min((int)(b/factor), 255),
                alpha);
    }

    /**
     * @see Color#darker()
     */
    public static Color darker(Color color, double factor) {
        return new Color(Math.max((int)(color.getRed() * factor), 0),
                Math.max((int)(color.getGreen() * factor), 0),
                Math.max((int)(color.getBlue() * factor), 0),
                color.getAlpha());
    }

    public static BufferedImage makeRoundedCorner(BufferedImage in, int radius) {
        int width = in.getWidth();
        int height = in.getHeight();

        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) out.getGraphics();

        g2d.setPaint(new TexturePaint(in, new Rectangle2D.Float(0, 0, in.getWidth(), in.getHeight())));

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fill(new RoundRectangle2D.Float(0, 0, width, height, radius, radius));

        g2d.dispose();

        return out;
    }

    public static void deriveAndSetFont(Component c, int style, float size) {
        c.setFont(c.getFont().deriveFont(style, size));
    }

    public static void deriveAndSetFont(Graphics g, int style, float size) {
        g.setFont(g.getFont().deriveFont(style, size));
    }

    public static void deriveAndSetFont(Component c, float size) {
        c.setFont(c.getFont().deriveFont(size));
    }

    public static void deriveAndSetFont(Graphics g, float size) {
        g.setFont(g.getFont().deriveFont(size));
    }

    public static BufferedImage toBufferedImage(Image in) {
        if (in instanceof BufferedImage) {
            return (BufferedImage) in;
        }

        BufferedImage out = new BufferedImage(in.getWidth(null), in.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = out.createGraphics();
        g2d.drawImage(in, 0, 0, null);

        g2d.dispose();

        return out;
    }

    public static Graphics2D getGraphics(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        setRenderingHints(g2d);

        return g2d;
    }

    public static void setRenderingHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    }

    public static String asHtmlColor(Color color) {
        if (color != null) {
            return String.format("#%02x%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        } else {
            return "#000000"; // black
        }
    }

    public static Dimension sub(Dimension dim, Insets insets) {
        Dimension out = new Dimension();

        out.width = dim.width - insets.left - insets.right;
        out.height = dim.height - insets.top - insets.bottom;

        return out;
    }

    public static Dimension from(Insets insets) {
        return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
    }

    public static JPanel split(JComponent left, JComponent right) {
        JPanel panel = new JPanel();
        panel.setLayout(new SplitLayout(1, 0.5f));

        panel.add(left);
        panel.add(right);

        return panel;
    }

    public static JButton createButton(String icon, String text, ActionListener listener) {
        JButton button = new JButton();
        button.addActionListener(listener);
        button.setText(text);
        button.setIcon(Icons.get(icon));

        return button;
    }

    public static JPanel wrap(Component component, int top, int left,
                              int bottom, int right) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));

        return panel;
    }

    public static void fillTriangle(Graphics2D g2d, int x, int y, int x2, int y2, int x3, int y3) {
        g2d.fillPolygon(new int[] {x, x2, x3},
                new int[]{y, y2, y3},
                3);
    }
}