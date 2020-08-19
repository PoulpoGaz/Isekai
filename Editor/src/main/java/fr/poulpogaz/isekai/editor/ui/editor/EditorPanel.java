package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    public EditorPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new VerticalLayout());
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;


        TileMapPanel tileMapPanel = new TileMapPanel();
        TilesetPanel tilesetPanel = new TilesetPanel();
        PackPropertiesPanel packPropertiesPanel = new PackPropertiesPanel();

        eastPanel.add(packPropertiesPanel, constraint);
        eastPanel.add(tilesetPanel, constraint);


        tilesetPanel.addPropertyChangeListener(TilesetPanel.SELECTED_TILE_PROPERTY, (e) -> {
            tileMapPanel.setSelectedTile(tilesetPanel.getSelectedTile());
        });
        tileMapPanel.setSelectedTile(tilesetPanel.getSelectedTile());

        add(tileMapPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
    }

}