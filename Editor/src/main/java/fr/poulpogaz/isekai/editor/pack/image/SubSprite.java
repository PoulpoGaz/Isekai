package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SubSprite extends AbstractSprite {

    private BufferedImage parent;

    private int x;
    private int y;
    private int width;
    private int height;

    private BufferedImage subSprite;

    public SubSprite(Pack pack, String texture) {
        this(pack, texture, 0, 0, 0, 0);
    }

    public SubSprite(Pack pack, String texture, int x, int y, int width, int height) {
        super(pack, texture);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public BufferedImage getSprite() {
        if (subSprite == null) {
            try {
                subSprite = parent.getSubimage(x, y, width, height);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(parent.getWidth() + " " + parent.getHeight() + " " + x + " " + y + " " + width + " " + height);

                try {
                    ImageIO.write(parent, "png", new File("t.png"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                System.exit((int) (Math.random() * 123456789));
            }
        }

        return subSprite;
    }

    @Override
    public void setTexture(String texture) {
        BufferedImage image = pack.getImage(texture);

        if (image == null) {
            return;
        }

        this.texture = texture;

        parent = image;

        if (x + width >= parent.getWidth()) {
            x = 0;
            width = parent.getWidth();
        }

        if (y + height >= parent.getHeight()) {
            y = 0;
            height = parent.getHeight();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (this.x != x) {
            this.x = Utils.clamp(x, 0, parent.getWidth());

            subSprite = null;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (this.y != y) {
            this.y = Utils.clamp(y, 0, parent.getHeight());

            subSprite = null;
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (this.width != width) {
            this.width = Utils.clamp(width, 0, parent.getWidth());

            subSprite = null;
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (this.height != height) {
            this.height = Utils.clamp(height, 0, parent.getHeight());

            subSprite = null;
        }
    }
}