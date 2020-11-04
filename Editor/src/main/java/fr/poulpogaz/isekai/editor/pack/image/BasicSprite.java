package fr.poulpogaz.isekai.editor.pack.image;

import java.awt.*;
import java.util.Objects;

public class BasicSprite extends AbstractSprite {

    private PackImage image;

    public BasicSprite(String name, PackImage image) {
        super(name);
        this.image = Objects.requireNonNull(image);
    }

    @Override
    public void paint(Graphics2D g2d, int x, int y) {
        image.paint(g2d, x, y);
    }

    @Override
    public void paint(Graphics2D g2d, int x, int y, int width, int height) {
        image.paint(g2d, x, y, width, height);
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public SubSprite toSubSprite() {
        return new SubSprite(this);
    }

    @Override
    public BasicSprite toSprite() {
        return this;
    }

    @Override
    public AnimatedSprite toAnimatedSprite() {
        AnimatedSprite sprite = new AnimatedSprite(name);
        sprite.addFrame(this);

        return sprite;
    }

    public PackImage getImage() {
        return image;
    }

    public void setImage(PackImage image) {
        if (image != null && !this.image.equals(image)) {
            this.image = image;

            fireChangeListener();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicSprite)) return false;
        if (!super.equals(o)) return false;

        BasicSprite that = (BasicSprite) o;

        return image.equals(that.image);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + image.hashCode();
        return result;
    }
}