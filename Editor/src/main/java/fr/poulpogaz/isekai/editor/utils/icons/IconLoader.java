package fr.poulpogaz.isekai.editor.utils.icons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class IconLoader {

    private static final Logger LOGGER = LogManager.getLogger(IconLoader.class);

    private static final HashMap<String, AbstractIcon> ICONS = new HashMap<>();

    public static final ImagePostProcess TO_LAF_ICON_COLOR;

    public static ImageIcon loadSVGIcon(String resource) {
        BufferedImage image;

        try {
            image = GlobalImageLoader.loadSVG(resource);
        } catch (IOException e) {
            e.printStackTrace();

            image = GlobalImageLoader.ERROR_IMAGE;
        }

        return new ImageIcon(image);
    }

    public static ImageIcon loadSVGIcon(String resource, int size) {
        BufferedImage image;

        try {
            image = GlobalImageLoader.loadSVG(resource, size, size);
        } catch (IOException e) {
            e.printStackTrace();

            image = GlobalImageLoader.ERROR_IMAGE;
        }

        return new ImageIcon(image);
    }

    public static ImageIcon loadSVGIcon(String resource, int size, ImagePostProcess[] processes) {
        BufferedImage image;

        try {
            image = GlobalImageLoader.loadSVG(resource, size, size, processes);
        } catch (IOException e) {
            e.printStackTrace();

            image = GlobalImageLoader.ERROR_IMAGE;
        }

        return new ImageIcon(image);
    }

    public static <T extends AbstractIcon> AbstractIcon load(String identifier, Class<T> iconClass) {
        if (ICONS.containsKey(identifier)) {
            return ICONS.get(identifier);
        } else {
            LOGGER.info("Loading icon at {}", iconClass.getName());

            try {
                Constructor<T> constructor = iconClass.getDeclaredConstructor();

                T icon = constructor.newInstance();

                ICONS.put(identifier, icon);

                return icon;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                LOGGER.warn("Failed to load icon", e);
            }
        }

        return null;
    }

    public static <T extends AbstractIcon> AbstractIcon load(String identifier, int width, int height, Class<T> iconClass) {
        if (ICONS.containsKey(identifier)) {
            return ICONS.get(identifier);
        } else {

            LOGGER.info("Loading icon at {}", iconClass.getName());

            try {
                Constructor<T> constructor = iconClass.getDeclaredConstructor(int.class, int.class);

                T icon = constructor.newInstance(width, height);

                ICONS.put(identifier, icon);

                return icon;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                LOGGER.warn("Failed to load icon", e);
            }
        }

        return null;
    }

    public static <T extends AbstractIcon> AbstractIcon load(String identifier, int width, int height, Color color, Class<T> iconClass) {
        if (ICONS.containsKey(identifier)) {
            return ICONS.get(identifier);
        } else {

            LOGGER.info("Loading icon at {}", iconClass.getName());

            try {
                Constructor<T> constructor = iconClass.getDeclaredConstructor(int.class, int.class, Color.class);

                T icon = constructor.newInstance(width, height, color);

                ICONS.put(identifier, icon);

                return icon;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                LOGGER.warn("Failed to load icon", e);
            }
        }

        return null;
    }

    static {
        TO_LAF_ICON_COLOR = new ImagePostProcess() {
            @Override
            public void process(BufferedImage input) {
                Graphics2D g2d = input.createGraphics();

                try {
                    g2d.setComposite(AlphaComposite.SrcIn);
                    g2d.setColor(UIManager.getColor("Icon.color"));
                    g2d.fillRect(0, 0, input.getWidth(), input.getHeight());
                } finally {
                    g2d.dispose();
                }
            }

            @Override
            public String getName() {
                return "IconPostProcessing";
            }
        };
    }
}