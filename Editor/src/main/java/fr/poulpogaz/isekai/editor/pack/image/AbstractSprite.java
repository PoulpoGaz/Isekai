package fr.poulpogaz.isekai.editor.pack.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class AbstractSprite {

    protected String name;

    public AbstractSprite(String name) {
        setName(name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }
}
