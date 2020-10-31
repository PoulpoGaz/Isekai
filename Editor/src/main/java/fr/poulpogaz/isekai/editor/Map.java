package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.ui.Model;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class Map<THIS extends Map<THIS, E>, E> extends Model {

    protected final List<MapSizeListener<THIS>> sizeListeners = new ArrayList<>();
    protected boolean modifyingMap = false;

    public abstract void set(E element, int x, int y);

    public abstract E get(int x, int y);

    public abstract int getWidth();

    public abstract int getHeight();

    protected void fireChangeListener() {
        if (!modifyingMap) {
            ChangeEvent event = new ChangeEvent(this);

            fireListener(ChangeListener.class, (l) -> l.stateChanged(event));
        }
    }

    public void addSizeListener(MapSizeListener<THIS> listener) {
        sizeListeners.add(listener);
    }

    public void removeSizeListener(MapSizeListener<THIS> listener) {
        sizeListeners.remove(listener);
    }

    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    public boolean isModifyingMap() {
        return modifyingMap;
    }

    public void setModifyingMap(boolean modifyingMap) {
        if (this.modifyingMap != modifyingMap) {
            this.modifyingMap = modifyingMap;

            fireChangeListener();
        }
    }
}
