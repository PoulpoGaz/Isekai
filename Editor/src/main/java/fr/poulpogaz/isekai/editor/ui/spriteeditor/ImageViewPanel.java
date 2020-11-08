package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.image.*;
import fr.poulpogaz.isekai.editor.ui.editorbase.MapPanelBase;
import fr.poulpogaz.isekai.editor.utils.Bounds;

import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class ImageViewPanel extends MapPanelBase<SpriteEditorModel, PackImage, Color> {

    private static final int SPRITE = 0;
    private static final int SUB_SPRITE = 1;
    private static final int ANIMATED_SPRITE = 2;

    private final ColorProducer colorProducer = new ColorProducer();

    private final ChangeListener spriteChanged;

    private AbstractSprite sprite;
    private int type;

    public ImageViewPanel(Pack pack, SpriteEditorModel editor) {
        super(pack, editor);

        spriteChanged = (e) -> repaint();

        sprite = editor.getSelectedSprite();
        sprite.addChangeListener(spriteChanged);
        setSpriteType();
    }

    @Override
    protected void addSelectedMapListener() {
        super.addSelectedMapListener();

        editor.addPropertyChangeListener(SpriteEditorModel.SELECTED_SPRITE_PROPERTY, this::switchSprite);
    }

    private void setSpriteType() {
        if (sprite instanceof BasicSprite) {
            type = SPRITE;
        } else if (sprite instanceof SubSprite) {
            type = SUB_SPRITE;
        } else if (sprite instanceof AnimatedSprite) {
            type = ANIMATED_SPRITE;
        }
    }

    @Override
    protected void paintMap(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds mapBounds) {
        drawCheckerboard(g2d, offset, visibleRect, mapBounds);

        map.paint(g2d, offset.x, offset.y, map.getWidth() * pixelSize, map.getHeight() * pixelSize);

        if (type == SUB_SPRITE) {
            SubSprite sprite = (SubSprite) this.sprite;

            Stroke old = g2d.getStroke();

            g2d.setColor(new Color(26, 22, 22));
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {6f}, 0.0f));

            drawSubSpriteRect(sprite, g2d, offset);

            g2d.setStroke(old);
        } else if (type == ANIMATED_SPRITE) {
            AnimatedSprite animSprite = (AnimatedSprite) this.sprite;

            Stroke old = g2d.getStroke();

            g2d.setColor(new Color(26, 22, 22));
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {6f}, 0.0f));

            List<AbstractSprite> sprites = animSprite.getFrames();
            for (int i = 0; i < sprites.size(); i++) {
                AbstractSprite sprite = sprites.get(i);

                if (sprite instanceof SubSprite) {
                    SubSprite s = (SubSprite) sprite;

                    if (s.getParent().equals(map)) {
                        g2d.setColor(colorProducer.produce(i));
                        drawSubSpriteRect(s, g2d, offset);
                    }
                }
            }

            g2d.setStroke(old);
        }
    }

    /**
     * Only draw the rectangle. This method
     * doesn't set the stroke.
     */
    protected void drawSubSpriteRect(SubSprite sprite, Graphics2D g2d, Point offset) {
        int x = offset.x + sprite.getX() * pixelSize;
        int y = offset.y + sprite.getY() * pixelSize;
        int width = sprite.getWidth() * pixelSize;
        int height = sprite.getHeight() * pixelSize;

        g2d.drawRect(x, y, width, height);
    }

    protected void drawCheckerboard(Graphics2D g2d, Point offset, Rectangle visibleRect, Bounds mapBounds) {
        Color white = new Color(152, 152, 152);
        Color black = new Color(101, 101, 102);

        int minX = Math.max(mapBounds.getMinX() * pixelSize + offset.x, visibleRect.x);
        int minY = Math.max(mapBounds.getMinY() * pixelSize + offset.y, visibleRect.y);
        int maxX = Math.min(mapBounds.getMaxX() * pixelSize + offset.x, visibleRect.x + visibleRect.width);
        int maxY = Math.min(mapBounds.getMaxY() * pixelSize + offset.y, visibleRect.y + visibleRect.height);

        final int size = 8;

        boolean yPair = false;
        for (int y = minY; y < maxY; y += size) {
            boolean xPair = false;

            for (int x = minX; x < maxX; x += size) {
                if (yPair) {
                    if (xPair) {
                        g2d.setColor(black);
                    } else {
                        g2d.setColor(white);
                    }
                } else {
                    if (xPair) {
                        g2d.setColor(white);
                    } else {
                        g2d.setColor(black);
                    }
                }

                int width = Math.min(maxX - x, size);
                int height = Math.min(maxY - y, size);

                g2d.fillRect(x, y, width, height);

                xPair = !xPair;
            }

            yPair = !yPair;
        }
    }

    private void switchSprite(PropertyChangeEvent evt) {
        sprite.removeChangeListener(spriteChanged);
        sprite = editor.getSelectedSprite();
        sprite.addChangeListener(spriteChanged);

        setSpriteType();
        repaint();
    }

    private static class ColorProducer {

        private final List<Color> colors = new ArrayList<>();

        public Color produce(int index) {
            for (int i = colors.size(); i <= index; i ++) {
                Color last = colors.size() == 0 ? Color.RED : colors.get(i - 1);

                float hue = Color.RGBtoHSB(last.getRed(), last.getGreen(), last.getBlue(), null) [0];

                hue += 0.142;

                colors.add(Color.getHSBColor(hue, 0.9f, 0.9f));
            }

            return colors.get(index);
        }
    }
}