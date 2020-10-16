package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.Tool;

public interface Default {

    Tile SELECTED_TILE = Tile.FLOOR;

    Tool TOOL = PaintTool.getInstance();

    boolean SHOW_GRID = true;
}
