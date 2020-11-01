package fr.poulpogaz.isekai.editor.pack.image;

import java.awt.*;
import java.util.List;

public interface IAnimatedSprite {

    void paint(Graphics2D g2d, int index, int x, int y);

    void paint(Graphics2D g2d, int index, int x, int y, int width, int height);

    void addFrame(AbstractSprite sprite);

    void removeFrame(AbstractSprite sprite);

    void insertFrame(AbstractSprite sprite, int index);

    void removeFrame(int index);

    int size();

    AbstractSprite getFrame(int index);

    List<AbstractSprite> getFrames();

    void setDelay(int delay);

    int getDelay();
}
