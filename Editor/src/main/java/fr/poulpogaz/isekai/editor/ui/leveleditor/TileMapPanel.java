package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.ui.editorbase.MapPanelBase;
import fr.poulpogaz.isekai.editor.utils.Bounds;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

import java.awt.*;

public class TileMapPanel extends MapPanelBase<LevelEditorModel, Level, Tile> {

    public TileMapPanel(Pack pack, LevelEditorModel editor) {
        super(pack, editor);

        pixelSize = 32;
        canZoom(false);
        setPreferredSize();
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

    @Override
    protected void paintCursor(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds bounds) {
        Composite old = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));

        int x = offset.x + hoverX * pixelSize;
        int y = offset.y + hoverY * pixelSize;

        g2d.drawImage(editor.getToolSprite(), x, y, pixelSize, pixelSize, null);

        g2d.setComposite(old);
    }
}