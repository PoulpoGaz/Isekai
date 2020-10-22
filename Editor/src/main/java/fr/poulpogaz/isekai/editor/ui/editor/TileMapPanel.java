package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.controller.LevelsOrganisationListener;
import fr.poulpogaz.isekai.editor.controller.PackController;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Player;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.tools.ToolHelper;
import fr.poulpogaz.isekai.editor.utils.Bounds;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.isekai.editor.utils.Vector2i;
import fr.poulpogaz.isekai.editor.utils.concurrent.AppExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TileMapPanel extends JPanel implements ResizeListener, PropertyChangeListener {

    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    private final PackController controller;

    private final Pack pack = IsekaiEditor.getInstance().getPack();
    private final ToolHelper toolHelper;

    private Level level;
    private int index;

    private int hoverX;
    private int hoverY;

    private boolean hideTileCursor = true;

    private boolean showGrid = Default.SHOW_GRID;

    private Rectangle cachedVisibleRect;

    public TileMapPanel(MapEditor editor, PackController controller) {
        this.controller = controller;
        controller.addSelectedLevelListener(this);
        controller.addLevelsOrganisationListener(new LevelRemovedListener());

        toolHelper = editor.getToolHelper();
        index = 0;
        level = pack.getLevel(index);
        initComponent();
    }

    private void initComponent() {
        MouseAdapter adapter = getMouseAdapter();
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        setPreferredSize();
    }

    private void setPreferredSize() {
        setPreferredSize(new Dimension(level.getWidth() * TILE_WIDTH, level.getHeight() * TILE_HEIGHT));

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

            Player player = level.getPlayer();
            Vector2i pos = player.getPos();

            for (int y = bounds.getMinY(); y < bounds.getMaxY(); y++) {
               for (int x = bounds.getMinX(); x < bounds.getMaxX(); x++) {
                    Tile t = level.getTile(x, y);

                    int drawX = offset.x + x * TILE_WIDTH;
                    int drawY = offset.y + y * TILE_WIDTH;

                    drawTile(g2d, drawX, drawY, t);

                    if (x == pos.x && y == pos.y) {
                        drawSprite(g2d, drawX, drawY, player.getDefaultSprite(pack));
                    }
                }
            }

            if (!hideTileCursor) {
                Composite old = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));

                int x = offset.x + hoverX * TILE_WIDTH;
                int y = offset.y + hoverY * TILE_HEIGHT;

                drawSprite(g2d, x, y, toolHelper.getToolSprite(pack));

                g2d.setComposite(old);
            }

            if (showGrid) {
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
        int boundsMaxX = offset.x + bounds.getMaxX() * TILE_WIDTH;
        int boundsMaxY = offset.y + bounds.getMaxY() * TILE_HEIGHT;

        int maxX = visible.x + visible.width;
        int maxY = visible.y + visible.height;

        for (int x = bounds.getMinX(); x <= bounds.getMaxX(); x++) { // Vertical lines
            int x_ = offset.x + x * TILE_WIDTH;

            g2d.drawLine(x_, Math.max(visible.y, boundsMinY), x_, Math.min(maxY, boundsMaxY));
        }

        for (int y = bounds.getMinY(); y <= bounds.getMaxY(); y++) { // Horizontal lines
            int y_ = offset.y + y * TILE_HEIGHT;

            g2d.drawLine(Math.max(visible.x, boundsMinX), y_, Math.min(maxX, boundsMaxX), y_);
        }
    }

    private Bounds getTileBounds() {
        Rectangle visible = getVisibleRect();

        int x = visible.x / TILE_WIDTH;
        int y = visible.y / TILE_HEIGHT;
        int w = (int) Math.ceil((float) visible.width / TILE_WIDTH) + 1;
        int h = (int) Math.ceil((float) visible.height / TILE_HEIGHT) + 1;

        return new Bounds(Utils.clamp(x, 0, level.getWidth()),
                Utils.clamp(y, 0, level.getHeight()),
                Utils.clamp(w + x, 0, level.getWidth()),
                Utils.clamp(h + y, 0, level.getHeight()));
    }

    private void drawTile(Graphics2D g2d, int x, int y, Tile t) {
        AbstractSprite sprite = t.getSprite(pack);

        drawSprite(g2d, x, y, sprite);
    }

    private void drawSprite(Graphics2D g2d, int x, int y, AbstractSprite sprite) {
        sprite.paint(g2d, x, y, TILE_WIDTH, TILE_HEIGHT);
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                move(e);

                if (isCursorInsideMap()) {
                    AppExecutor.getExecutor().submit(() -> {
                        toolHelper.apply(level, hoverX, hoverY);

                        repaint();
                    });
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                move(e);

                if (isCursorInsideMap()) {
                    AppExecutor.getExecutor().submit(() -> {
                        toolHelper.apply(level, hoverX, hoverY);

                        repaint();
                    });
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

        hoverX = (e.getX() - offset.x) / TILE_WIDTH;
        hoverY = (e.getY() - offset.y) / TILE_HEIGHT;

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
            offset.x = (in.width - lvlWidth * TILE_WIDTH) / 2;
        }
        if (visible.height == dim.height) {
            offset.y = (in.height - lvlHeight * TILE_HEIGHT) / 2;
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

    @Override
    public void levelResized(Level level, int width, int height) {
        setPreferredSize();
        repaint();
    }

    public void setShowGrid(boolean showGrid) {
        if (this.showGrid != showGrid) {
            this.showGrid = showGrid;

            repaint();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PackController.SELECTED_LEVEL_PROPERTY)) {
            index = (int) evt.getNewValue();
            level = pack.getLevel(index);
            repaint();
        }
    }

    private class LevelRemovedListener implements LevelsOrganisationListener {

        @Override
        public void levelInserted(int index) {

        }

        @Override
        public void levelRemoved(int i) {
            index = Math.max(index - 1, 0);
            level = pack.getLevel(index);
            repaint();
        }

        @Override
        public void levelChanged(int index) {

        }

        @Override
        public void levelsSwapped(int index1, int index2) {

        }
    }
}