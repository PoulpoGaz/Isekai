package fr.poulpogaz.isekai.editor.utils.icons;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;

public class ImageLoader extends AbstractLoader {

    @Override
    public BufferedImage load(InputStream stream) throws IOException {
        byte[] bytes = stream.readAllBytes();
        Path cachePath = getCachePath(bytes);

        stream = new ByteArrayInputStream(bytes);

        if (processes != null) {
            BufferedImage image = readCache(cachePath);

            if (image != null) {
                reset();
                return image;
            }
        }

        boolean writeCache = false;
        BufferedImage image = ImageIO.read(stream);

        if ((width > 0 && width != image.getWidth()) || (height > 0 && height != image.getHeight())) {

            int w = width > 0 ? width : image.getWidth();
            int h = height > 0 ? height : image.getHeight();

            image = resize(image, w, h);
            writeCache = true;
        }

        if (processes != null) {
            for (ImagePostProcess process : processes) {
                process.process(image);
            }

            writeCache = true;
        }

        if (writeCache) {
            writeCache(cachePath, image);
        }

        reset();
        return image;
    }

    protected BufferedImage resize(BufferedImage image, int width, int height) {
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = newImage.getGraphics();
        g.drawImage(temp, 0, 0, null);

        return newImage;
    }

    protected BufferedImage readImage(InputStream stream) throws IOException {
        if (width > 0 && height > 0) {
            try (stream; ImageInputStream input = ImageIO.createImageInputStream(stream)) {
                // Get the reader
                Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

                if (!readers.hasNext()) {
                    throw new IllegalArgumentException("No reader for: " + stream);
                }

                ImageReader reader = readers.next();

                try {
                    reader.setInput(input);

                    ImageReadParam param = reader.getDefaultReadParam();

                    param.setSourceRenderSize(new Dimension(width, height));

                    return reader.read(0, param);
                } finally {
                    reader.dispose();
                }
            }
        }

        return ImageIO.read(stream);
    }
}