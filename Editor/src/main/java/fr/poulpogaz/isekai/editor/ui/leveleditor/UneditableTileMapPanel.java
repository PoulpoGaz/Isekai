package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.ui.editorbase.MapPanelBase;
import fr.poulpogaz.isekai.editor.utils.Bounds;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

import java.awt.*;

public class UneditableTileMapPanel extends MapPanelBase<Level, Tile> {

    public UneditableTileMapPanel(Pack pack, Level map) {
        super(pack, map);
    }

    @Override
    protected void paintMap(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds mapBounds) {
        Vector2i player = map.getPlayerPos();

        for (int y = mapBounds.getMinY(); y < mapBounds.getMaxY(); y++) {
            for (int x = mapBounds.getMinX(); x < mapBounds.getMaxX(); x++) {
                Tile t = map.get(x, y);

                int drawX = offset.x + x * pixelSize;
                int drawY = offset.y + y * pixelSize;

                g2d.drawImage(t.getSprite(), drawX, drawY, pixelSize, pixelSize, null);

                if (player.equals(x, y)) {
                    g2d.drawImage(PackSprites.getPlayer(), drawX, drawY, pixelSize, pixelSize, null);
                }
            }
        }
    }
}