package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.Animator;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.Tool;
import fr.poulpogaz.isekai.editor.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TileMapPanel extends JPanel implements LevelListener, ResizeListener {

    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    private final Pack pack = IsekaiEditor.getPack();

    private Level level;
    private int index;

    private int hoverX;
    private int hoverY;

    private Tile selectedTile;
    private boolean hideTileCursor = true;

    private Tool tool;

    public TileMapPanel() {
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

            Rectangle visible = getVisibleTiles();

            for (int y = visible.y; y < visible.y + visible.height; y++) {
                for (int x = visible.x; x < visible.x + visible.width; x++) {
                    Tile t = level.getTile(x, y);

                    drawTile(g2d, offset.x + x * TILE_WIDTH, offset.y + y * TILE_HEIGHT, t);
                }
            }

            if (!hideTileCursor) {
                Composite old = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));

                int x = offset.x + hoverX * TILE_WIDTH;
                int y = offset.y + hoverY * TILE_HEIGHT;

                drawTile(g2d, x, y, selectedTile);

                g2d.setComposite(old);
            }
        } finally {
            g2d.dispose();
        }
    }

    private Rectangle getVisibleTiles() {
        Rectangle visible = getVisibleRect();

        int x = visible.x / TILE_WIDTH;
        int y = visible.y / TILE_HEIGHT;
        int w = (int) Math.ceil((float) visible.width / TILE_WIDTH);
        int h = (int) Math.ceil((float) visible.height / TILE_HEIGHT);

        return new Rectangle(Utils.clamp(x, 0, level.getWidth()),
                Utils.clamp(y, 0, level.getHeight()),
                Utils.clamp(w, 0, level.getWidth()),
                Utils.clamp(h, 0, level.getHeight()));
    }

    private void drawTile(Graphics2D g2d, int x, int y, Tile t) {
        AbstractSprite sprite = t.getSprite(IsekaiEditor.getPack());

        sprite.paint(g2d, x, y, TILE_WIDTH, TILE_HEIGHT);
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                move(e);

                if (isCursorInsideMap()) {
                    tool.apply(level, selectedTile, hoverX, hoverY);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                move(e);

                if (isCursorInsideMap()) {
                    tool.apply(level, selectedTile, hoverX, hoverY);
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

    public void setSelectedTile(Tile newValue) {
        selectedTile = newValue;
        repaint();
    }

    @Override
    public void levelInserted(Level insertedLevel, int index) {
        // does nothing because when the LevelPanel class adds a level, it changes the selected level
    }

    @Override
    public void levelDeleted(Level deletedLevel, int index) {
        int size = pack.getLevels().size() - 1;

        level = pack.getLevel(Math.min(index, size));

        setPreferredSize();
        repaint();
    }

    @Override
    public void levelMoved(int from, int to) {
        // does nothing because when the LevelPanel class moves a level, it changes the selected level
    }

    @Override
    public void selectedLevelChanged(Level newLevel, int index) {
        this.level = newLevel;
        this.index = index;

        setPreferredSize();

        repaint();
    }

    @Override
    public void levelResized(Level level, int width, int height) {
        setPreferredSize();
        repaint();
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }
}