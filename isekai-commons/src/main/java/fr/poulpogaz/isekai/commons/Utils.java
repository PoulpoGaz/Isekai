package fr.poulpogaz.isekai.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;

public class Utils {

    private static final Logger LOGGER = LogManager.getLogger(Utils.class);

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

    public static boolean browse(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));

            return true;
        } catch (URISyntaxException | IOException e) {
            LOGGER.warn("Failed to browse", e);

            return false;
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