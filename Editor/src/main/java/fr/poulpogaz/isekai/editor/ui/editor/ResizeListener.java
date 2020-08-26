package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Level;

import java.util.EventListener;

public interface ResizeListener extends EventListener {

    void levelResized(Level level, int width, int height);
}
