package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.pack.Pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;

public abstract class LevelEditorBase extends JPanel {

    protected final Pack pack;
    protected final LevelEditorModel editor;

    protected ToolBar toolbar;
    protected TileMapPanel mapPanel;
    protected JPanel eastPanel;

    public LevelEditorBase(Pack pack, LevelEditorModel editor) {
        this.pack = pack;
        this.editor = editor;

        initComponents();
    }

    protected void initComponents() {
        setLayout(new BorderLayout());

        toolbar = createToolBar();
        toolbar.setSelectedTool(editor.getTool());

        mapPanel = createMapPanel();
        eastPanel = createEastPanel();

        add(toolbar, BorderLayout.NORTH);
        add(wrap(mapPanel), BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
    }

    protected abstract ToolBar createToolBar();

    protected abstract TileMapPanel createMapPanel();

    protected abstract JPanel createEastPanel();

    protected JScrollPane wrap(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        AdjustmentListener repaint = (e) -> component.repaint();

        scrollPane.getVerticalScrollBar().addAdjustmentListener(repaint);
        scrollPane.getHorizontalScrollBar().addAdjustmentListener(repaint);

        return scrollPane;
    }
}