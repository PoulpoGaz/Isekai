package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.tools.Tool;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    private TileMapPanel tileMapPanel;
    private TilesetPanel tilesetPanel;
    private PackPropertiesPanel packPropertiesPanel;
    private LevelPanel levelPanel;
    private ResizePanel resizePanel;

    private ToolBar toolBar;

    public EditorPanel() {
        setLayout(new BorderLayout());
        initComponents();
        initListeners();
    }

    private void initComponents() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new VerticalLayout());
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        // init components
        tileMapPanel = new TileMapPanel();
        tilesetPanel = new TilesetPanel();
        packPropertiesPanel = new PackPropertiesPanel();
        resizePanel = new ResizePanel();
        levelPanel = new LevelPanel();

        toolBar = new ToolBar();

        tileMapPanel.setSelectedTile(tilesetPanel.getSelectedTile());
        tileMapPanel.setTool(toolBar.getTool());

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
        return new JScrollPane(component);
    }

    private void initListeners() {
        tilesetPanel.addPropertyChangeListener(TilesetPanel.SELECTED_TILE_PROPERTY, (e) -> {
            tileMapPanel.setSelectedTile(tilesetPanel.getSelectedTile());
        });
        toolBar.addPropertyChangeListener(ToolBar.TOOL_PROPERTY, (e) -> {
            tileMapPanel.setTool((Tool) e.getNewValue());
        });

        levelPanel.addLevelListener(tileMapPanel);
        resizePanel.addResizeListener(tileMapPanel);
    }
}