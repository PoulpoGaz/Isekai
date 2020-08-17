package fr.poulpogaz.isekai.editor.ui.editor;

import java.util.EventListener;

public interface ResizeListener extends EventListener {

    void sizeChanged(int newWidth, int newHeight);
}