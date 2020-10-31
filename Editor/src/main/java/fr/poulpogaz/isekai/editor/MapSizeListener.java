package fr.poulpogaz.isekai.editor;

import java.util.EventListener;

public interface MapSizeListener<M extends Map<M, ?>> extends EventListener {

    void mapResized(M map, int newWidth, int newHeight);
}