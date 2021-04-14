package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.EditorMenuBar;
import fr.poulpogaz.isekai.editor.ui.NoPackLoadedPanel;
import fr.poulpogaz.isekai.editor.ui.leveleditor.LevelEditor;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class IsekaiEditor extends JFrame {

    private static final IsekaiEditor INSTANCE = new IsekaiEditor();

    private Pack pack;

    private IsekaiEditor() {
        super("Editor");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();

        setJMenuBar(new EditorMenuBar(this));

        pack();

        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setContentPane(new NoPackLoadedPanel());
    }

    public void setPack(Pack pack) {
        this.pack = Objects.requireNonNull(pack);

        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Map editor", new LevelEditor(pack));

        setContentPane(pane);

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