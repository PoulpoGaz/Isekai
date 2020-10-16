package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.tools.ToolHelper;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;

public class EditorPanel extends JPanel {

    private final ToolHelper toolHelper;

    private TileMapPanel tileMapPanel;
    private TilesetPanel tilesetPanel;
    private PackPropertiesPanel packPropertiesPanel;
    private LevelPanel levelPanel;
    private ResizePanel resizePanel;
    private PlayerPosPanel playerPosPanel;

    private ToolBar toolBar;

    public EditorPanel() {
        toolHelper = new ToolHelper();

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
        tileMapPanel = new TileMapPanel(this);
        tilesetPanel = new TilesetPanel(this);
        packPropertiesPanel = new PackPropertiesPanel();
        resizePanel = new ResizePanel();
        levelPanel = new LevelPanel();
        playerPosPanel = new PlayerPosPanel(this);

        toolBar = new ToolBar(this);

        tileMapPanel.setShowGrid(toolBar.isShowingGrid());

        // layout
        eastPanel.add(packPropertiesPanel, constraint);
        eastPanel.add(tilesetPanel, constraint);
        eastPanel.add(resizePanel, constraint);
        eastPanel.add(levelPanel, constraint);
        eastPanel.add(playerPosPanel, constraint);

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

    private void initListeners() {
        toolBar.addPropertyChangeListener(ToolBar.SHOW_GRID_PROPERTY, (e) -> {
            tileMapPanel.setShowGrid((boolean) e.getNewValue());
        });

        levelPanel.addLevelListener(tileMapPanel);
        resizePanel.addResizeListener(tileMapPanel);
    }

    public ToolHelper getToolHelper() {
        return toolHelper;
    }
}