package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.pack.Pack;

import java.awt.image.BufferedImage;

public class Sprite extends AbstractSprite {

    protected BufferedImage sprite;

    public Sprite(Pack pack) {
        super(pack);
    }

    public Sprite(Pack pack, String texture) {
        super(pack, texture);
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

    @Override
    public void setTexture(String texture) {
        BufferedImage image = pack.getImage(texture);

        if (image == null) {
            return;
        }

        this.texture = texture;
        sprite = image;
    }
}
