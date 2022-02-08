package fr.poulpogaz.isekai.editor.ui.solver;

import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.LevelModel;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.solver.ISolver;
import fr.poulpogaz.isekai.editor.pack.solver.State;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static fr.poulpogaz.isekai.commons.pack.Tile.FLOOR;
import static fr.poulpogaz.isekai.commons.pack.Tile.TARGET;

public class StateView extends JPanel {

    private final Analyser analyser;
    private final ISolver solver;

    private final int width;
    private final int height;
    private final Tile[] map;
    private final BufferedImage image;

    private State state;

    private JLabel stateNumberLabel;
    private JButton viewParent;

    private Drawer drawer;
    private JList<State> children;

    private boolean adjusting = true;

    public StateView(ISolver solver, Analyser analyser) {
        this.solver = solver;
        this.analyser = analyser;
        this.width = solver.getLevel().getWidth();
        this.height = solver.getLevel().getHeight();

        map = new Tile[width * height];
        initializeMap(solver.getLevel());
        image = new BufferedImage(width * 16, height * 16, BufferedImage.TYPE_INT_ARGB);

        initComponents();
    }

    protected void initializeMap(LevelModel level) {
        for (int i = 0; i < map.length; i++) {
            Tile tile = level.get(i % width, i / width);

            switch (tile) {
                case WALL, FLOOR, TARGET -> {
                    map[i] = tile;
                }
                case CRATE_ON_TARGET -> {
                    map[i] = TARGET;
                }
                case CRATE -> {
                    map[i] = FLOOR;
                }
            }
        }
    }

    protected void initComponents() {
        stateNumberLabel = new JLabel();
        viewParent = new JButton("View");
        viewParent.addActionListener((e) -> {
            if (state != null && state.parent != null) {
                setState(state.parent);
            }
        });

        drawer = new Drawer();
        children = new JList<>();
        children.setModel(new DefaultListModel<>());
        children.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        children.addListSelectionListener((e) -> {
            if (!adjusting && children.getSelectedIndex() >= 0) {
                setState(children.getSelectedValue());
            }
        });

        setLayout(new BorderLayout());

        // TOP
        JPanel top = new JPanel();
        top.setLayout(new HorizontalLayout());
        top.add(stateNumberLabel);
        add(top, BorderLayout.NORTH);

        // LEFT
        JPanel left = new JPanel();
        left.setLayout(new HorizontalLayout());
        left.add(new JLabel("Parent: "));
        left.add(viewParent);

        add(left, BorderLayout.WEST);

        // CENTER
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(drawer);

        add(scrollPane, BorderLayout.CENTER);

        // RIGHT
        JPanel right = new JPanel();
        right.setLayout(new VerticalLayout());
        right.add(new JLabel("Children:"));

        JScrollPane childrenScrollPane = new JScrollPane();
        childrenScrollPane.setViewportView(children);

        right.add(children);

        add(right, BorderLayout.EAST);
    }

    public void setState(State state) {
        this.state = state;

        stateNumberLabel.setText("State n°" + state.number);

        adjusting = true;
        DefaultListModel<State> model = (DefaultListModel<State>) children.getModel();

        model.removeAllElements();
        if (state.childrenStart >= 0) {

            for (int i = state.childrenStart; i <= state.childrenEnd; i++) {
                model.addElement(analyser.getStateAt(i));
            }
        }
        adjusting = false;

        drawImage();
        repaint();
    }

    public State getState() {
        return state;
    }

    protected void drawImage() {
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.black);
        for (int i = 0; i < map.length; i++) {
            Tile tile = map[i];

            int x = (i % width) * 16;
            int y = (i / width) * 16;

            g2d.drawImage(PackSprites.img(tile), x, y, null);
        }
        for (int cratePos : state.cratesIndex) {
            int x = (cratePos % width) * 16;
            int y = (cratePos / width) * 16;

            if (map[cratePos] == FLOOR) {
                g2d.drawImage(PackSprites.getCrate(), x, y, null);
            } else if (map[cratePos] == TARGET) {
                g2d.drawImage(PackSprites.getCrateOnTarget(), x, y, null);
            }
        }

        int x = (state.playerIndex % width) * 16;
        int y = (state.playerIndex / width) * 16;

        g2d.drawImage(PackSprites.getPlayer(), x, y, null);

        g2d.dispose();
    }

    private class Drawer extends JComponent {

        private int width = image.getWidth() * 2;
        private int height = image.getHeight() * 2;

        public Drawer() {
            setPreferredSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Dimension size = getSize();
            Insets insets = getInsets();

            size.width = size.width - insets.left - insets.right;
            size.height = size.height - insets.top - insets.bottom;

            int x = (size.width - width) / 2;
            int y = (size.height - height) / 2;

            g.drawImage(image, x, y, width, height, null);
        }
    }
}