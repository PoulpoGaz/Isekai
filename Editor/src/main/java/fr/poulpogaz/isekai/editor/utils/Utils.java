package fr.poulpogaz.isekai.editor.utils;

import fr.poulpogaz.isekai.editor.ui.Icons;
import fr.poulpogaz.isekai.editor.ui.layout.SplitLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;

public class Utils {

    private static final Logger LOGGER = LogManager.getLogger(Utils.class);

    private static final Object LOCK = new Object();
    private static Cursor BLANK_CURSOR;

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

    public static String getExtension(File file) {
        return getExtension(file.getName());
    }

    public static String getExtension(Path file) {
        return getExtension(file.getFileName().toString());
    }

    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');

        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(index + 1);
        }
    }

    public static boolean checkExtension(File file, String extension) {
        return getExtension(file).equals(extension);
    }

    public static boolean checkExtension(Path file, String extension) {
        return getExtension(file).equals(extension);
    }

    public static boolean checkExtension(String resource, String extension) {
        return getExtension(resource).equals(extension);
    }

    public static int requireValueBetween(int value, int minInclusive, int maxExclusive) {
        if (isBetween(value, minInclusive, maxExclusive)) {
            return value;
        }

        throw new IllegalStateException();
    }

    public static int requireValueBetween(int value, int minInclusive, int maxExclusive, String message) {
        if (isBetween(value, minInclusive, maxExclusive)) {
            return value;
        }

        throw new IllegalStateException(message);
    }

    public static boolean isBetween(int value, int minInclusive, int maxExclusive) {
        return value >= minInclusive && value < maxExclusive;
    }

    public static int clamp(int value, int minInclusive, int maxInclusive) {
        if (value < minInclusive) {
            return minInclusive;
        } else return Math.min(value, maxInclusive);
    }

    public static boolean browse(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));

            return true;
        } catch (URISyntaxException | IOException e) {
            LOGGER.warn("Failed to browse", e);

            return false;
        }
    }

    public static File getJARLocation() {
        try {
            return new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Cursor getBlankCursor() {
        synchronized (LOCK) {
            if (BLANK_CURSOR == null) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

                BLANK_CURSOR = toolkit.createCustomCursor(cursor, new Point(0, 0), "blank cursor");
            }

            return BLANK_CURSOR;
        }
    }

    public static void fill(boolean[][] array, boolean object) {
        for (boolean[] booleans : array) {
            Arrays.fill(booleans, object);
        }
    }

    public static boolean equals(Point point, int x, int y) {
        return point.x == x && point.y == y;
    }
}