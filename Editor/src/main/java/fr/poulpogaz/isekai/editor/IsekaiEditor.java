package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.EditorMenuBar;
import fr.poulpogaz.isekai.editor.ui.NoPackLoadedPanel;
import fr.poulpogaz.isekai.editor.ui.editor.MapEditor;
import fr.poulpogaz.isekai.editor.ui.sprite_editor.SpriteEditor;

import javax.swing.*;
import java.util.Objects;

public class IsekaiEditor extends JFrame {

    private static final IsekaiEditor EDITOR = new IsekaiEditor();

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
        pane.addTab("Map editor", new MapEditor());
        pane.addTab("Sprite editor", new SpriteEditor());

        setContentPane(pane);
        revalidate();
        repaint();
    }

    public Pack getPack() {
        return pack;
    }

    public static IsekaiEditor getInstance() {
        return EDITOR;
    }
}