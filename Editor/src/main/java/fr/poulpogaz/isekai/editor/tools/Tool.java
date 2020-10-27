package fr.poulpogaz.isekai.editor.tools;

public interface Tool {

    <M extends Map<E>, E> void apply(M map, E element, int x, int y);
}
