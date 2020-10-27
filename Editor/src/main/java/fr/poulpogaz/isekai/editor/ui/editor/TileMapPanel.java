package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.model.EditorModel;
import fr.poulpogaz.isekai.editor.model.LevelSizeListener;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.utils.Bounds;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.isekai.editor.utils.Vector2i;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TileMapPanel extends JPanel implements PropertyChangeListener {

    private static final int TILE_SIZE = 32;

    private final Pack pack;
    private Level level;
    private EditorModel editor;

    private LevelSizeListener levelSizeListener;
    private ChangeListener mapChanged;
    
    private int hoverX;
    private int hoverY;

    private boolean hideTileCursor = true;

    private Rectangle cachedVisibleRect;

    public TileMapPanel(Pack pack, EditorModel editor) {
        this.pack = pack;
        this.editor = editor;
        editor.addPropertyChangeListener(EditorModel.SELECTED_LEVEL_PROPERTY, this);
        editor.addPropertyChangeListener(EditorModel.SHOW_GRID_PROPERTY, (e) -> repaint());

        levelSizeListener = this::levelResized;
        mapChanged = (e) -> repaint();

        level = editor.getSelectedLevel();
        level.addLevelSizeListener(levelSizeListener);
        level.addChangeListener(mapChanged);

        initComponent();
    }

    private void initComponent() {
        MouseAdapter adapter = getMouseAdapter();
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        setPreferredSize();
    }

    private void setPreferredSize() {
        setPreferredSize(new Dimension(level.getWidth() * TILE_SIZE, level.getHeight() * TILE_SIZE));

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            Point offset = getOffset();

            Bounds bounds = getTileBounds();

            Vector2i player = level.getPlayerPos();

            for (int y = bounds.getMinY(); y < bounds.getMaxY(); y++) {
               for (int x = bounds.getMinX(); x < bounds.getMaxX(); x++) {
                    Tile t = level.getTile(x, y);

                    int drawX = offset.x + x * TILE_SIZE;
                    int drawY = offset.y + y * TILE_SIZE;

                    drawTile(g2d, drawX, drawY, t);

                    if (player.equals(x, y)) {
                        drawSprite(g2d, drawX, drawY, pack.getSprite(PackSprites.PLAYER_DEFAULT_STATIC));
                    }
                }
            }

            if (!hideTileCursor) {
                Composite old = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));

                int x = offset.x + hoverX * TILE_SIZE;
                int y = offset.y + hoverY * TILE_SIZE;

                drawSprite(g2d, x, y, editor.getToolSprite(pack));

                g2d.setComposite(old);
            }

            if (editor.isShowGrid()) {
                drawGrid(g2d, offset, bounds);
            }

        } finally {
            g2d.dispose();
        }

        cachedVisibleRect = null; // clear cache, some changes can happen
    }

    private void drawGrid(Graphics2D g2d, Point offset, Bounds bounds) {
        g2d.setColor(Color.BLACK);

        Rectangle visible = getVisibleRect();

        // minimum and maximum value in screen coordinate
        int boundsMinX = offset.x;
        int boundsMinY = offset.y;
        int boundsMaxX = offset.x + bounds.getMaxX() * TILE_SIZE;
        int boundsMaxY = offset.y + bounds.getMaxY() * TILE_SIZE;

        int maxX = visible.x + visible.width;
        int maxY = visible.y + visible.height;

        for (int x = bounds.getMinX(); x <= bounds.getMaxX(); x++) { // Vertical lines
            int x_ = offset.x + x * TILE_SIZE;

            g2d.drawLine(x_, Math.max(visible.y, boundsMinY), x_, Math.min(maxY, boundsMaxY));
        }

        for (int y = bounds.getMinY(); y <= bounds.getMaxY(); y++) { // Horizontal lines
            int y_ = offset.y + y * TILE_SIZE;

            g2d.drawLine(Math.max(visible.x, boundsMinX), y_, Math.min(maxX, boundsMaxX), y_);
        }
    }

    private Bounds getTileBounds() {
        Rectangle visible = getVisibleRect();

        int x = visible.x / TILE_SIZE;
        int y = visible.y / TILE_SIZE;
        int w = (int) Math.ceil((float) visible.width / TILE_SIZE) + 1;
        int h = (int) Math.ceil((float) visible.height / TILE_SIZE) + 1;

        return new Bounds(Utils.clamp(x, 0, level.getWidth()),
                Utils.clamp(y, 0, level.getHeight()),
                Utils.clamp(w + x, 0, level.getWidth()),
                Utils.clamp(h + y, 0, level.getHeight()));
    }

    private void drawTile(Graphics2D g2d, int x, int y, Tile t) {
        AbstractSprite sprite = pack.getSprite(t.getSprite());

        drawSprite(g2d, x, y, sprite);
    }

    private void drawSprite(Graphics2D g2d, int x, int y, AbstractSprite sprite) {
        sprite.paint(g2d, x, y, TILE_SIZE, TILE_SIZE);
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                move(e);

                if (isCursorInsideMap()) {
                    level.setModifyingMap(true);
                    editor.applyTool(hoverX, hoverY);
                    level.setModifyingMap(false);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                move(e);

                if (isCursorInsideMap()) {
                    level.setModifyingMap(true);
                    editor.applyTool(hoverX, hoverY);
                    level.setModifyingMap(false);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                move(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hideTileCursor = false;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideTileCursor = true;
                repaint();
            }
        };
    }

    private boolean isCursorInsideMap() {
        return hoverX >= 0 && hoverX < level.getWidth() &&
                hoverY >= 0 && hoverY < level.getHeight();
    }

    private void move(MouseEvent e) {
        Point offset = getOffset();

        hoverX = (e.getX() - offset.x) / TILE_SIZE;
        hoverY = (e.getY() - offset.y) / TILE_SIZE;

        repaint();
    }

    private Point getOffset() {
        Rectangle visible = getVisibleRect();

        Dimension dim = getSize();
        Insets insets = getInsets();

        Dimension in = Utils.sub(dim, insets);

        Point offset = new Point();

        int lvlWidth = level.getWidth();
        int lvlHeight = level.getHeight();


        // Apply offset only if the dimension of the component match the visible rect
        // because when they are equals, we try to center the tile map
        if (visible.width == dim.width) {
            offset.x = (in.width - lvlWidth * TILE_SIZE) / 2;
        }
        if (visible.height == dim.height) {
            offset.y = (in.height - lvlHeight * TILE_SIZE) / 2;
        }

        return offset;
    }

    @Override
    public Rectangle getVisibleRect() {
        if (cachedVisibleRect == null) {
            cachedVisibleRect = super.getVisibleRect();
        }

        return cachedVisibleRect;
    }

    private void levelResized(Level level, int width, int height) {
        setPreferredSize();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(EditorModel.SELECTED_LEVEL_PROPERTY)) {
            level.removeLevelSizeListener(levelSizeListener);
            level.removeChangeListener(mapChanged);

            level = editor.getSelectedLevel();
            level.addLevelSizeListener(levelSizeListener);
            level.addChangeListener(mapChanged);

            repaint();
        }
    }
}