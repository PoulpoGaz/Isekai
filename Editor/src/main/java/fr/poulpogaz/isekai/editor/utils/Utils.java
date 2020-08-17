package fr.poulpogaz.isekai.editor.utils;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class Utils {

    public static Dimension sub(Dimension dim, Insets insets) {
        Dimension out = new Dimension();

        out.width = dim.width - insets.left - insets.right;
        out.height = dim.height - insets.top - insets.bottom;

        return out;
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
        if (value < minInclusive && value >= maxExclusive) {
            throw new IllegalStateException();
        }

        return value;
    }

    public static int requireValueBetween(int value, int minInclusive, int maxExclusive, String message) {
        if (value < minInclusive && value >= maxExclusive) {
            throw new IllegalStateException(message);
        }

        return value;
    }
}