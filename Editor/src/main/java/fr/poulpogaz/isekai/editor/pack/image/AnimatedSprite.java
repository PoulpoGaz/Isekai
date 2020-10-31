package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.pack.Pack;

import java.util.ArrayList;

public class AnimatedSprite extends AbstractSprite {

    private final ArrayList<AbstractSprite> frames;
    private int delay;

    public AnimatedSprite(Pack pack, int delay) {
        super(pack);
        this.frames = new ArrayList<>();
        this.delay = delay;
    }

    @Override
    public PackImage getSprite() {
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

    @Override
    public void setTexture(String texture) {
        throw new IllegalStateException();
    }

    @Override
    public String getTexture() {
        throw new IllegalStateException();
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