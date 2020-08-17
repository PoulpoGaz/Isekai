package fr.poulpogaz.isekai.editor.pack.image;

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

    public void addFrame(AbstractSprite frame) {
        if (frame instanceof AnimatedSprite) {
            return;
        }

        frames.add(frame);
    }

    public void removeFrame(int index) {
        frames.remove(index);
    }

    public void insertFrame(AbstractSprite frame, int index) {
        if (frame instanceof AnimatedSprite) {
            return;
        }

        frames.add(index, frame);
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