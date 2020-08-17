package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    private Pack pack;
    private Level currentLevel;

    private TilesetPanel tilesetPanel;
    private TileMapPanel tileMapPanel;
    private ResizePanel resizePanel;
    private PackPropertiesPanel packPropertiesPanel;

    public EditorPanel() {
        pack = new Pack();
        currentLevel = pack.getLevel(0);

        initComponents();
        initListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tilesetPanel = new TilesetPanel();
        tilesetPanel.setBorder(BorderFactory.createTitledBorder("Tileset"));

        resizePanel = new ResizePanel();
        resizePanel.setBorder(BorderFactory.createTitledBorder("Resize"));

        tileMapPanel = new TileMapPanel(currentLevel);
        packPropertiesPanel = new PackPropertiesPanel(pack);

        JPanel rightContent = new JPanel();
        rightContent.setLayout(new VerticalLayout());

        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        rightContent.add(packPropertiesPanel, constraint);
        rightContent.add(resizePanel, constraint);
        rightContent.add(tilesetPanel, constraint);

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());

        right.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.WEST);
        right.add(rightContent, BorderLayout.CENTER);

        add(tileMapPanel, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
    }

    private void initListeners() {
        tilesetPanel.addTilesetListener(tileMapPanel);
    }
}