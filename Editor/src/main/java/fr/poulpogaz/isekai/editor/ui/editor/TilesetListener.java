package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Tile;

import java.util.EventListener;

public interface TilesetListener extends EventListener {

    void selectedTileChanged(Tile tile);
}
