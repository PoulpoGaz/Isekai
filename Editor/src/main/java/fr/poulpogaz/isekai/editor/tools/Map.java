package fr.poulpogaz.isekai.editor.tools;

public interface Map<E> {

    void set(E element, int x, int y);

    E get(int x, int y);

    int getWidth();

    int getHeight();
}
