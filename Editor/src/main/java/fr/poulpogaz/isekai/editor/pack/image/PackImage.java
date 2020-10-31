package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class PackImage extends Map<PackImage, Color> {

    private final BufferedImage image;
    private final DataBufferInt buffer;

    public PackImage(BufferedImage image) {
        this.image = toARGB(Objects.requireNonNull(image));

        buffer = (DataBufferInt) this.image.getRaster().getDataBuffer();
    }

    protected BufferedImage toARGB(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_INT_ARGB) {
            return image;
        }

        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = copy.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return copy;
    }

    @Override
    public void set(Color element, int x, int y) {
        int i = y * getWidth() + x;

        int oldValue = buffer.getElem(i);
        int newValue = element.getRGB();

        if (oldValue != newValue) {
            buffer.setElem(i, newValue);

            fireChangeListener();
        }
    }

    @Override
    public Color get(int x, int y) {
        int i = y * getWidth() + x;

        return new Color(buffer.getElem(i), true);
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    public PackImage getSubimage(int x, int y, int width, int height) {
        return new PackImage(image.getSubimage(x, y, width, height));
    }

    public void draw(Graphics2D g2d, int x, int y) {
        g2d.drawImage(image, x, y, null);
    }

    public void draw(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.drawImage(image, x, y, width, height, null);
    }

    public boolean write(String formatName, OutputStream output) throws IOException {
        return ImageIO.write(image, formatName, output);
    }
}