package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackIO;
import fr.poulpogaz.isekai.editor.ui.EditorMenuBar;
import fr.poulpogaz.isekai.editor.ui.NoPackLoadedPanel;
import fr.poulpogaz.isekai.editor.ui.editor.EditorPanel;
import fr.poulpogaz.isekai.editor.ui.editor.TileMapPanel;
import fr.poulpogaz.isekai.editor.ui.editor.TilesetPanel;
import fr.poulpogaz.json.JsonException;
import org.apache.batik.ext.awt.image.rendered.TileMap;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class IsekaiEditor extends JFrame {

    private static final IsekaiEditor EDITOR = new IsekaiEditor();

    private Pack pack;

    private IsekaiEditor() {
        super("Editor");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();

        setJMenuBar(new EditorMenuBar());

        pack();

        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setContentPane(new NoPackLoadedPanel());
    }

    public void setPack(Pack pack) {
        this.pack = Objects.requireNonNull(pack);


        setContentPane(new TilesetPanel());
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