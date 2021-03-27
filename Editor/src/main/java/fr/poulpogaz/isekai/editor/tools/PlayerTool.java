package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

import javax.swing.*;
import java.util.HashMap;

public class PlayerTool implements Tool {

    private static final HashMap<Pack, PlayerTool> INSTANCES = new HashMap<>();

    private final Pack pack;

    private PlayerTool(Pack pack) {
        this.pack = pack;
    }

    @Override
    public <M extends Map<M, E>, E> void apply(M map, E element, int x, int y) {
        if (map instanceof Level) {
            Level level = (Level) map;

            Tile tile = (Tile) element;
            Tile current = level.get(x, y);

            if (current.isSolid() && !tile.isSolid()) {
                level.set(tile, x, y);
                level.setPlayerPos(new Vector2i(x, y));
            } else if (!current.isSolid()) {
                level.setPlayerPos(new Vector2i(x, y));
            }
        }
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(PackSprites.getPlayer());
    }

    public static PlayerTool getInstance(Pack pack) {
        PlayerTool tool = INSTANCES.get(pack);

        if (tool == null) {
            tool = new PlayerTool(pack);

            INSTANCES.put(pack, tool);
        }

        return tool;
    }

    public static void removeInstance(Pack pack) {
        INSTANCES.remove(pack);
    }
}