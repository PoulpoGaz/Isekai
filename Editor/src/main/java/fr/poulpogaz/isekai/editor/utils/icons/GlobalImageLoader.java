package fr.poulpogaz.isekai.editor.utils.icons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

public class GlobalImageLoader {

    public static final BufferedImage ERROR_IMAGE = loadErrorImage();

    private static final Logger LOGGER = LogManager.getLogger(GlobalImageLoader.class);

    private static final HashMap<String, BufferedImage> IMAGES = new HashMap<>();

    private static final ImageLoader imageLoader = new ImageLoader();
    private static final SVGLoader svgLoader = new SVGLoader();

    public static BufferedImage loadSVG(String resource) throws IOException {
        if (IMAGES.containsKey(resource)) {
            return IMAGES.get(resource);
        }

        LOGGER.info("Loading svg at {}", resource);

        BufferedImage svg = svgLoader.load(newInputStream(resource));

        IMAGES.put(resource, svg);

        return svg;
    }

    public static BufferedImage loadSVG(String resource, int width, int height) throws IOException {
        String key = String.format("%s%d%d", resource, width, height);

        if (IMAGES.containsKey(key)) {
            return IMAGES.get(key);
        }

        LOGGER.info("Loading svg at {} with size {}x{}", resource, width, height);

        svgLoader.setWidth(width);
        svgLoader.setHeight(height);

        BufferedImage svg = svgLoader.load(newInputStream(resource));

        IMAGES.put(String.format("%s%d%d", resource, width, height), svg);

        return svg;
    }

    public static BufferedImage loadSVG(String resource, int width, int height, ImagePostProcess... processes) throws IOException {
        String key = String.format("%s%d%d%s", resource, width, height, Arrays.hashCode(processes));

        if (IMAGES.containsKey(key)) {
            return IMAGES.get(key);
        }

        LOGGER.info("Loading svg at {} with size {}x{}", resource, width, height);

        svgLoader.setWidth(width);
        svgLoader.setHeight(height);
        svgLoader.setProcesses(processes);

        BufferedImage svg = svgLoader.load(newInputStream(resource));

        IMAGES.put(String.format("%s%d%d%s", resource, width, height, Arrays.hashCode(processes)), svg);

        return svg;
    }

    public static BufferedImage loadImage(String resource) throws IOException {
        if (IMAGES.containsKey(resource)) {
            return IMAGES.get(resource);
        }

        LOGGER.info("Loading image at {}", resource);

        BufferedImage image = imageLoader.load(newInputStream(resource));

        IMAGES.put(resource, image);

        return image;
    }

    public static BufferedImage loadImage(String resource, int width, int height) throws IOException {
        String key = String.format("%s%d%d", resource, width, height);

        if (IMAGES.containsKey(key)) {
            return IMAGES.get(key);
        }

        LOGGER.info("Loading image at {} with size {}x{}", resource, width, height);

        imageLoader.setWidth(width);
        imageLoader.setHeight(height);

        BufferedImage image = imageLoader.load(newInputStream(resource));

        IMAGES.put(String.format("%s%d%d", resource, width, height), image);

        return image;
    }

    public static BufferedImage loadImage(String resource, int width, int height, ImagePostProcess... processes) throws IOException {
        String key = String.format("%s%d%d%s", resource, width, height, Arrays.hashCode(processes));

        if (IMAGES.containsKey(key)) {
            return IMAGES.get(key);
        }

        LOGGER.info("Loading image at {} with size {}x{}", resource, width, height);

        imageLoader.setWidth(width);
        imageLoader.setHeight(height);
        imageLoader.setProcesses(processes);

        BufferedImage image = imageLoader.load(newInputStream(resource));

        IMAGES.put(String.format("%s%d%d%s", resource, width, height, Arrays.hashCode(processes)), image);

        return image;
    }

    private static InputStream newInputStream(String resource) throws IOException {
        InputStream stream = GlobalImageLoader.class.getResourceAsStream(resource);

        if (stream == null) {
            throw new IOException(String.format("Cannot input stream of %s", resource));
        }

        return stream;
    }

    private static BufferedImage loadErrorImage() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        Graphics g = image.getGraphics();

        g.setColor(new Color(255, 0, 0));

        g.drawLine(0, 0, 16, 16);
        g.drawLine(0, 16, 16, 0);

        return image;
    }
}