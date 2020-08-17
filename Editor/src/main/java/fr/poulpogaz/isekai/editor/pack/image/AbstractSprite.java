package fr.poulpogaz.isekai.editor.pack.image;

import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class AbstractSprite {

    protected String name;

    public AbstractSprite(String name) {
        setName(name);
    }

    public abstract BufferedImage getSprite();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }
}
