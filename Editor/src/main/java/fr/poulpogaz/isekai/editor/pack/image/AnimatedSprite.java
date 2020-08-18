package fr.poulpogaz.isekai.editor.pack.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimatedSprite extends AbstractSprite {

    private final ArrayList<AbstractSprite> frames;
    private int delay;

    public AnimatedSprite(String name, int delay) {
        super(name);
        this.frames = new ArrayList<>();
        this.delay = delay;
    }

    @Override
    public BufferedImage getSprite() {
        return frames.get(0).getSprite();
    }

    public boolean addFrame(AbstractSprite frame) {
        if (frame instanceof AnimatedSprite) {
            return false;
        }

        if (isValid(frame)) {
            frames.add(frame);

            return true;
        }

        return false;
    }

    public AbstractSprite removeFrame(int index) {
        return frames.remove(index);
    }

    public boolean insertFrame(AbstractSprite frame, int index) {
        if (frame instanceof AnimatedSprite) {
            return false;
        }

        if (isValid(frame)) {
            frames.add(index, frame);

            return true;
        }

        return false;
    }

    private boolean isValid(AbstractSprite sprite) {
        return frames.size() == 0 || (sprite.getWidth() == getWidth() && sprite.getHeight() == getHeight());
    }

    @Override
    public int getWidth() {
        if (frames.size() == 0) {
            return 0;
        }

        return getSprite().getHeight();
    }

    @Override
    public int getHeight() {
        if (frames.size() == 0) {
            return 0;
        }

        return getSprite().getWidth();
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public ArrayList<AbstractSprite> getFrames() {
        return frames;
    }
}