package fr.poulpogaz.isekai.editor.controller;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

public class LevelController {

    private Level level;

    public LevelController(Level level) {
        this.level = level;
    }

    public void resize(int newWidth, int newHeight) {

    }

    public Tile getTile(int x, int y) {
        return level.getTile(x, y);
    }

    public Vector2i getPlayerPos() {
        return new Vector2i(level.getPlayerPos());
    }

    public int getWidth() {
        return level.getWidth();
    }

    public int getHeight() {
        return level.getHeight();
    }
}