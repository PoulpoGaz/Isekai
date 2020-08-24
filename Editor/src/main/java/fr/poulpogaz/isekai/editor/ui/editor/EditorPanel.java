package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    private TileMapPanel tileMapPanel;
    private TilesetPanel tilesetPanel;
    private PackPropertiesPanel packPropertiesPanel;
    private LevelPanel levelPanel;

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
        levelPanel = new LevelPanel();

        tileMapPanel.setSelectedTile(tilesetPanel.getSelectedTile());

        // layout
        eastPanel.add(packPropertiesPanel, constraint);
        eastPanel.add(tilesetPanel, constraint);
        eastPanel.add(levelPanel, constraint);

        add(tileMapPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
    }

    private void initListeners() {
        tilesetPanel.addPropertyChangeListener(TilesetPanel.SELECTED_TILE_PROPERTY, (e) -> {
            tileMapPanel.setSelectedTile(tilesetPanel.getSelectedTile());
        });

        levelPanel.addLevelListener(tileMapPanel);
    }
}