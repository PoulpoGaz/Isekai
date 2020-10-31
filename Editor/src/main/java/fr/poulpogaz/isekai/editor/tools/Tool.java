package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.Map;

import javax.swing.*;

public interface Tool {

    <M extends Map<M, E>, E> void apply(M map, E element, int x, int y);

    Icon getIcon();
}
