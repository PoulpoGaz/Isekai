package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.utils.Utils;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class SubSprite extends AbstractSprite {

    private BufferedImage parent;

    private int x;
    private int y;
    private int width;
    private int height;

    private BufferedImage subSprite;

    public SubSprite(String name, BufferedImage parent) {
        this(name, parent, 0, 0, parent.getWidth(), parent.getHeight());
    }

    public SubSprite(String name, BufferedImage parent, int x, int y, int width, int height) {
        super(name);
        setParent(parent);
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

    public void setParent(BufferedImage sprite) {
        parent = Objects.requireNonNull(sprite);
        subSprite = null;
    }

    public BufferedImage getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = Utils.requireValueBetween(x, 0, parent.getWidth());
        subSprite = null;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = Utils.requireValueBetween(y, 0, parent.getHeight());
        subSprite = null;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = Utils.requireValueBetween(width, 0, parent.getWidth() - x);
        subSprite = null;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = Utils.requireValueBetween(height, 0, parent.getHeight() - y);
        subSprite = null;
    }
}