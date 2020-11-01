package fr.poulpogaz.isekai.editor.pack.image;

import java.awt.*;

public class Animator {

    private AnimatedSprite sprite;

    private long time;
    private int currentFrame = -1;
    private boolean running = false;

    public Animator(AnimatedSprite sprite) {
        this.sprite = sprite;
    }

    public void paint(Graphics2D g2d, int x, int y) {
        update();

        sprite.paint(g2d, currentFrame, x, y);
    }

    public void paint(Graphics2D g2d, int x, int y, int width, int height) {
        update();

        sprite.paint(g2d, currentFrame, x, y, width, height);
    }

    protected void update() {
        if (running) {
            if (System.currentTimeMillis() + sprite.getDelay() > time) {
                time = System.currentTimeMillis();

                currentFrame++;

                if (currentFrame >= sprite.size()) {
                    currentFrame = 0;
                }
            }
        }
    }

    public void start() {
        currentFrame = 0;
        time = System.currentTimeMillis();
        running = true;
    }

    public void stop() {
        currentFrame = -1;
        running = false;
    }

    public void pause() {
        running = false;
        time = System.currentTimeMillis() - time;
    }

    public void resume() {
        running = true;
        time = System.currentTimeMillis() - time;
    }

    public boolean isRunning() {
        return running;
    }

    public AnimatedSprite getSprite() {
        return sprite;
    }

    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }
}