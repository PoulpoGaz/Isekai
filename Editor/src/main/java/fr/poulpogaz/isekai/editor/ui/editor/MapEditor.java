package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.model.EditorModel;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;

public class MapEditor extends JPanel {

    private final Pack pack;
    private final EditorModel editor;

    private TileMapPanel tileMapPanel;
    private TilesetPanel tilesetPanel;
    private PackPropertiesPanel packPropertiesPanel;
    private LevelPanel levelPanel;
    private ResizePanel resizePanel;

    private ToolBar toolBar;

    public MapEditor(Pack pack) {
        this.pack = pack;
        editor = new EditorModel();
        editor.setSelectedLevel(pack.getLevel(0));

        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new VerticalLayout());
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        // init components
        tileMapPanel = new TileMapPanel(pack, editor);
        tilesetPanel = new TilesetPanel(pack, editor);
        packPropertiesPanel = new PackPropertiesPanel(pack);
        resizePanel = new ResizePanel(editor);
        levelPanel = new LevelPanel(pack, editor);

        toolBar = new ToolBar(pack, editor);

        // layout
        eastPanel.add(packPropertiesPanel, constraint);
        eastPanel.add(tilesetPanel, constraint);
        eastPanel.add(resizePanel, constraint);
        eastPanel.add(levelPanel, constraint);

        add(toolBar, BorderLayout.NORTH);
        add(wrap(tileMapPanel), BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
    }

    private JScrollPane wrap(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        AdjustmentListener repaint = (e) -> tileMapPanel.repaint();

        scrollPane.getVerticalScrollBar().addAdjustmentListener(repaint);
        scrollPane.getHorizontalScrollBar().addAdjustmentListener(repaint);

        return scrollPane;
    }
}