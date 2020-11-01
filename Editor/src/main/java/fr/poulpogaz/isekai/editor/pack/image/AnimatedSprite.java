package fr.poulpogaz.isekai.editor.pack.image;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnimatedSprite extends AbstractSprite implements IAnimatedSprite {

    private final ArrayList<AbstractSprite> sprites;
    private int delay;

    public AnimatedSprite(String name) {
        this(name, 250);
    }

    public AnimatedSprite(String name, int delay) {
        super(name);
        this.delay = delay;

        sprites = new ArrayList<>();
    }

    @Override
    public void paint(Graphics2D g2d, int x, int y) {
        paint(g2d, 0, x, y);
    }

    @Override
    public void paint(Graphics2D g2d, int x, int y, int width, int height) {
        paint(g2d, 0, x, y, width, height);
    }

    @Override
    public void paint(Graphics2D g2d, int index, int x, int y) {
        if (index < 0 || index >= sprites.size()) {
            return;
        }

        AbstractSprite sprite = sprites.get(index);
        sprite.paint(g2d, x, y);
    }

    @Override
    public void paint(Graphics2D g2d, int index, int x, int y, int width, int height) {
        if (index < 0 || index >= sprites.size()) {
            return;
        }

        AbstractSprite sprite = sprites.get(index);
        sprite.paint(g2d, x, y, width, height);
    }

    @Override
    public int getWidth() {
        return sprites.get(0).getWidth(); // all sprites have the same dimension
    }

    @Override
    public int getHeight() {
        return sprites.get(0).getHeight(); // all sprites have the same dimension
    }

    @Override
    public void addFrame(AbstractSprite sprite) {
        if (isValid(sprite)) {
            sprites.add(sprite);

            fireChangeListener();
        }
    }

    @Override
    public void removeFrame(AbstractSprite sprite) {
        if (sprites.remove(sprite)) {
            fireChangeListener();
        }
    }

    @Override
    public void insertFrame(AbstractSprite sprite, int index) {
        if (isValid(sprite)) {
            sprites.add(index, sprite);

            fireChangeListener();
        }
    }

    @Override
    public void removeFrame(int index) {
        if (sprites.remove(index) != null) {
            fireChangeListener();
        }
    }

    @Override
    public int size() {
        return sprites.size();
    }

    @Override
    public AbstractSprite getFrame(int index) {
        return sprites.get(index);
    }

    @Override
    public List<AbstractSprite> getFrames() {
        return sprites;
    }

    protected boolean isValid(AbstractSprite sprite) {
        if (sprite instanceof IAnimatedSprite) {
            return false;
        }

        if (sprites.size() == 0) {
            return true;
        }

        return getWidth() == sprite.getWidth() && sprite.getHeight() == 0;
    }

    @Override
    public void setDelay(int delay) {
        if (delay > 0 && this.delay != delay) {
            this.delay = delay;

            fireChangeListener();
        }
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimatedSprite)) return false;
        if (!super.equals(o)) return false;

        AnimatedSprite that = (AnimatedSprite) o;

        if (delay != that.delay) return false;
        return sprites.equals(that.sprites);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + sprites.hashCode();
        result = 31 * result + delay;
        return result;
    }
}