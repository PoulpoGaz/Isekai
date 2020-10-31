package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.pack.Pack;

public class Sprite extends AbstractSprite {

    protected PackImage sprite;

    public Sprite(Pack pack) {
        super(pack);
    }

    public Sprite(Pack pack, String texture) {
        super(pack, texture);
    }

    public PackImage getSprite() {
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
        PackImage image = pack.getImage(texture);

        if (image == null) {
            return;
        }

        this.texture = texture;
        sprite = image;
    }
}
