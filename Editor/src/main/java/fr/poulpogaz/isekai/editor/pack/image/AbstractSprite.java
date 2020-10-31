package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.pack.Pack;

import java.awt.*;

public abstract class AbstractSprite {

    protected final Pack pack;
    protected String texture;

    public AbstractSprite(Pack pack) {
        this.pack = pack;
    }

    public AbstractSprite(Pack pack, String texture) {
        this.pack = pack;
        setTexture(texture);
    }

    public void paint(Graphics2D g2d, int x, int y) {
        getSprite().draw(g2d, x, y);
    }

    public void paint(Graphics2D g2d, int x, int y, int width, int height) {
        getSprite().draw(g2d, x, y, width, height);
    }

    public abstract PackImage getSprite();

    public abstract int getWidth();

    public abstract int getHeight();

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
