package fr.poulpogaz.isekai.editor.pack;

import java.util.EventListener;

public interface LevelSizeListener extends EventListener {

    void levelResized(Level level, int newWidth, int newHeight);
}