package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.EditorMenuBar;
import fr.poulpogaz.isekai.editor.ui.NoPackLoadedPanel;
import fr.poulpogaz.isekai.editor.ui.layout.HCOrientation;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import fr.poulpogaz.isekai.editor.ui.leveleditor.LevelEditor;
import fr.poulpogaz.isekai.editor.ui.progressbar.JMemoryBar;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class IsekaiEditor extends JFrame {

    private static final IsekaiEditor INSTANCE = new IsekaiEditor();

    private Pack pack;

    private JPanel content;

    private final NoPackLoadedPanel noPackLoadedPanel = new NoPackLoadedPanel();
    private JPanel bottomPanel;
    private JMemoryBar memoryBar;

    private JTabbedPane tab;

    private IsekaiEditor() {
        super("Editor");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();

        setJMenuBar(new EditorMenuBar(this));

        pack();

        setLocationRelativeTo(null);
    }

    private void initComponents() {
        tab = new JTabbedPane();

        memoryBar = new JMemoryBar();
        memoryBar.showMaximum(true);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new HorizontalLayout());

        HorizontalConstraint constraint = new HorizontalConstraint();
        constraint.orientation = HCOrientation.RIGHT;

        bottomPanel.add(memoryBar, constraint);

        content = new JPanel();
        content.setLayout(new BorderLayout());

        content.add(noPackLoadedPanel, BorderLayout.CENTER);
        content.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(content);
    }

    public void setPack(Pack pack) {
        this.pack = Objects.requireNonNull(pack);

        if (tab.getTabCount() == 0) {
            tab.addTab("Map editor", new LevelEditor(pack));

            content.remove(noPackLoadedPanel);
            content.add(tab, BorderLayout.CENTER);
        } else {
            tab.removeAll();
            tab.addTab("Map editor", new LevelEditor(pack));
        }

        if (getExtendedState() != MAXIMIZED_BOTH) {
            Dimension screen = getScreenDimension();

            Dimension dimension = getPreferredSize();

            if (dimension.width > screen.width || dimension.height > screen.height) {
                setExtendedState(MAXIMIZED_BOTH);
            } else {
                pack();
                setLocationRelativeTo(null);
            }
        }

        revalidate();
        repaint();
    }

    private Dimension getScreenDimension() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screen = toolkit.getScreenSize();
        Insets insets = toolkit.getScreenInsets(gc);

        screen.width = screen.width - insets.left - insets.right;
        screen.height = screen.height - insets.top - insets.bottom;

        return screen;
    }

    public Pack getPack() {
        return pack;
    }

    public static IsekaiEditor getInstance() {
        return INSTANCE;
    }
}