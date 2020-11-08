package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SubSprite extends AbstractSprite {

    private PackImage parent;

    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

    private BufferedImage subImage;

    public SubSprite(String name) {
        super(name);
    }

    public SubSprite(String name, PackImage parent) {
        super(name);
        this.parent = parent;

        width = parent.getWidth();
        height = parent.getHeight();
    }

    public SubSprite(String name, PackImage parent, int x, int y, int width, int height) {
        super(name);
        this.parent = parent;

        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    public SubSprite(BasicSprite sprite) {
        this(sprite.getName(), sprite.getImage());
    }

    @Override
    public void paint(Graphics2D g2d, int x, int y) {
        BufferedImage sub = getSubImage();

        g2d.drawImage(sub, x, y, null);
    }

    @Override
    public void paint(Graphics2D g2d, int x, int y, int width, int height) {
        BufferedImage sub = getSubImage();

        g2d.drawImage(sub, x, y, width, height, null);
    }

    protected BufferedImage getSubImage() {
        if (subImage == null) {
            subImage = parent.getSubImage(x, y, width, height);
        }

        return subImage;
    }

    public PackImage getParent() {
        return parent;
    }

    public void setParent(PackImage parent) {
        if (!this.parent.equals(parent)) {
            this.parent = parent;

            if (x + width >= parent.getWidth()) { // reset
                x = 0;
                width = parent.getWidth();
            }

            if (y + height >= parent.getHeight()) { // reset
                y = 0;
                height = parent.getHeight();
            }

            subImage = null;

            fireChangeListener();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (this.x != x) {
            x = Utils.clamp(x, 0, parent.getWidth() - width);

            if (this.x != x) {
                this.x = x;

                fireChangeListener();

                subImage = null;
            }
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (this.y != y) {
            y = Utils.clamp(y, 0, parent.getHeight() - height);

            if (this.y != y) {
                this.y = y;

                fireChangeListener();

                subImage = null;
            }
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (this.width != width) {
            width = Utils.clamp(width, 0, parent.getWidth() - x);

            if (this.width != width) {
                this.width = width;

                fireChangeListener();

                subImage = null;
            }
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public SubSprite toSubSprite() {
        return this;
    }

    @Override
    public BasicSprite toSprite() {
        if (x == 0 && y == 0 && width == parent.getWidth() && height == parent.getHeight()) {
            return new BasicSprite(name, parent);
        }

        return new SpriteFromSubSprite(this);
    }

    @Override
    public AnimatedSprite toAnimatedSprite() {
        AnimatedSprite sprite = new AnimatedSprite(name);
        sprite.addFrame(this);

        return sprite;
    }

    public void setHeight(int height) {
        if (this.height != height) {
            height = Utils.clamp(height, 0, parent.getHeight() - y);

            if (this.height != height) {
                this.height = height;

                fireChangeListener();

                subImage = null;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubSprite)) return false;
        if (!super.equals(o)) return false;

        SubSprite subSprite = (SubSprite) o;

        if (x != subSprite.x) return false;
        if (y != subSprite.y) return false;
        if (width != subSprite.width) return false;
        if (height != subSprite.height) return false;
        return parent.equals(subSprite.parent);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + parent.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
}