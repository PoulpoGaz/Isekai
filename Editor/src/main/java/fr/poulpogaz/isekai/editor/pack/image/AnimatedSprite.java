package fr.poulpogaz.isekai.editor.pack.image;

import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnimatedSprite extends AbstractSprite {

    private final ChangeListener redirect = (e) -> fireChangeListener();

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

    public void paint(Graphics2D g2d, int index, int x, int y) {
        if (index < 0 || index >= sprites.size()) {
            return;
        }

        AbstractSprite sprite = sprites.get(index);
        sprite.paint(g2d, x, y);
    }

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
    public SubSprite toSubSprite() {
        AbstractSprite sprite = sprites.get(0);

        if (sprite instanceof SubSprite) {
            return (SubSprite) sprite;
        } else if (sprite instanceof BasicSprite) {
            return new SubSprite((BasicSprite) sprite);
        } else {
            return null;
        }
    }

    @Override
    public BasicSprite toSprite() {
        AbstractSprite sprite = sprites.get(0);

        if (sprite instanceof BasicSprite) {
            return (BasicSprite) sprite;
        } else if (sprite instanceof SubSprite) {
            return new SpriteFromSubSprite((SubSprite) sprite);
        } else {
            return null;
        }
    }

    @Override
    public AnimatedSprite toAnimatedSprite() {
        return this;
    }

    public void addFrame(AbstractSprite sprite) {
        if (isValid(sprite)) {
            sprites.add(sprite);

            sprite.addChangeListener(redirect);

            fireChangeListener();
        }
    }

    public void removeFrame(AbstractSprite sprite) {
        if (sprites.remove(sprite)) {
            sprite.removeChangeListener(redirect);

            fireChangeListener();
        }
    }

    public void insertFrame(AbstractSprite sprite, int index) {
        if (isValid(sprite)) {
            sprites.add(index, sprite);

            sprite.addChangeListener(redirect);

            fireChangeListener();
        }
    }

    public void removeFrame(int index) {
        AbstractSprite sprite = sprites.remove(index);

        if (sprite != null) {
            sprite.removeChangeListener(redirect);

            fireChangeListener();
        }
    }

    public int size() {
        return sprites.size();
    }

    public AbstractSprite getFrame(int index) {
        return sprites.get(index);
    }

    public List<AbstractSprite> getFrames() {
        return sprites;
    }

    protected boolean isValid(AbstractSprite sprite) {
        if (sprite instanceof AnimatedSprite) {
            return false;
        }

        if (sprites.size() == 0) {
            return true;
        }

        return getWidth() == sprite.getWidth() && getHeight() == sprite.getHeight();
    }

    public void setDelay(int delay) {
        if (delay > 0 && this.delay != delay) {
            this.delay = delay;

            fireChangeListener();
        }
    }

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