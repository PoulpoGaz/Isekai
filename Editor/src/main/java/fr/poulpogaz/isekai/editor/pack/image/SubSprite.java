package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.utils.Utils;

import java.awt.image.BufferedImage;

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
            subSprite = parent.getSubimage(x, y, width, height);
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