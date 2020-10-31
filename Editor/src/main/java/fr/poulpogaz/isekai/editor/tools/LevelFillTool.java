package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

import java.awt.*;

public class LevelFillTool extends FillTool {

    private static final LevelFillTool INSTANCE = new LevelFillTool();

    private LevelFillTool() {

    }

    @Override
    protected <M extends Map<M, E>, E> boolean equals(M map, E currentElement, Point currentElementPos, E elementToBeReplaced) {
        if (map instanceof Level) {
            Level level = (Level) map;
            Tile toBeReplaced = (Tile) elementToBeReplaced;

            if (toBeReplaced == currentElement) {
                Vector2i playerPos = level.getPlayerPos();

                return currentElementPos.x != playerPos.x || currentElementPos.y != playerPos.y || !toBeReplaced.isSolid();
            }
        }

        return false;
    }

    public static LevelFillTool getInstance() {
        return INSTANCE;
    }
}