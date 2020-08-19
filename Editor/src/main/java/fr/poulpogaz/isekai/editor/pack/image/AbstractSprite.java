package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.pack.Pack;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        g2d.drawImage(getSprite(), x, y, null);
    }

    public void paint(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.drawImage(getSprite(), x, y, width, height, null);
    }

    public abstract BufferedImage getSprite();

    public abstract int getWidth();

    public abstract int getHeight();

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
