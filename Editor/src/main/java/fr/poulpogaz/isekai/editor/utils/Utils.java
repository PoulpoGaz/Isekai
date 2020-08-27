package fr.poulpogaz.isekai.editor.utils;

import fr.poulpogaz.json.IJsonReader;
import fr.poulpogaz.json.JsonException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Utils {

    public static Dimension sub(Dimension dim, Insets insets) {
        Dimension out = new Dimension();

        out.width = dim.width - insets.left - insets.right;
        out.height = dim.height - insets.top - insets.bottom;

        return out;
    }

    public static Dimension from(Insets insets) {
        return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
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

    public static IJsonReader assertKeyEquals(IJsonReader reader, String expectedKey) throws IOException, JsonException {
        String key = reader.nextKey();

        if (!key.equals(expectedKey)) {
            throw new JsonException(String.format("Invalid key. Expected \"%s\" but was \"%s\"", expectedKey, key));
        }

        return reader;
    }
}