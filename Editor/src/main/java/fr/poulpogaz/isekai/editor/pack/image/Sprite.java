package fr.poulpogaz.isekai.editor.pack.image;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Sprite extends AbstractSprite {

    protected BufferedImage sprite;

    public Sprite(String name, BufferedImage sprite) {
        super(name);
        setSprite(sprite);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    @Override
    public int getWidth() {
        return sprite.getWidth();
    }

    @Override
    public int getHeight() {
        return sprite.getHeight();
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = Objects.requireNonNull(sprite);
    }
}
