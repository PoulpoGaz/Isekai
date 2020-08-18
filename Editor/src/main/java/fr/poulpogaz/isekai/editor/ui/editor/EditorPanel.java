package fr.poulpogaz.isekai.editor.ui.editor;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    public EditorPanel() {
        setLayout(new BorderLayout());

        TileMapPanel tileMapPanel = new TileMapPanel();
        TilesetPanel tilesetPanel = new TilesetPanel();
        tilesetPanel.addPropertyChangeListener(TilesetPanel.SELECTED_TILE_PROPERTY, (e) -> {
            tileMapPanel.setSelectedTile(tilesetPanel.getSelectedTile());
        });

        tileMapPanel.setSelectedTile(tilesetPanel.getSelectedTile());

        add(tileMapPanel, BorderLayout.CENTER);
        add(tilesetPanel, BorderLayout.EAST);
    }


}