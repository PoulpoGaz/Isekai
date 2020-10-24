package fr.poulpogaz.isekai.editor.model;

import fr.poulpogaz.isekai.editor.pack.Level;

import java.util.EventListener;

public interface LevelSizeListener extends EventListener {

    void levelResized(Level level, int newWidth, int newHeight);
}