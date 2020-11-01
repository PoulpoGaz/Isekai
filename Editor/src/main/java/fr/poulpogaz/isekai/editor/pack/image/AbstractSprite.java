package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.ui.Model;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Objects;

public abstract class AbstractSprite extends Model {

    protected final String name;

    public AbstractSprite(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public abstract void paint(Graphics2D g2d, int x, int y);

    public abstract void paint(Graphics2D g2d, int x, int y, int width, int height);

    public abstract int getWidth();

    public abstract int getHeight();

    public String getName() {
        return name;
    }

    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    protected void fireChangeListener() {
        ChangeEvent evt = new ChangeEvent(this);

        fireListener(ChangeListener.class, (l) -> l.stateChanged(evt));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractSprite)) return false;

        AbstractSprite sprite = (AbstractSprite) o;

        return name.equals(sprite.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}