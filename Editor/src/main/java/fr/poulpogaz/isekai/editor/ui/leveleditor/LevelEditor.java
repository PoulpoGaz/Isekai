package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.tools.FillTool;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.PlayerTool;
import fr.poulpogaz.isekai.editor.ui.Icons;
import fr.poulpogaz.isekai.editor.ui.check.CheckLevelDialog;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;

public class LevelEditor extends LevelEditorBase {

    private TileMapPanel tileMapPanel;
    private TilesetPanel tilesetPanel;
    private PackPropertiesPanel packPropertiesPanel;
    private LevelPanel levelPanel;
    private ResizePanel resizePanel;

    private ToolBar toolBar;

    public LevelEditor(Pack pack) {
        super(pack, new LevelEditorModel(pack, Tile.FLOOR));
    }

    @Override
    protected ToolBar createToolBar() {
        toolBar = new ToolBar(editor);

        toolBar.addTool(PaintTool.getInstance());
        toolBar.addTool(FillTool.getInstance());
        toolBar.addTool(PlayerTool.getInstance());

        JButton check = new JButton();
        check.setIcon(Icons.get("icons/check.svg"));
        check.addActionListener((e) -> CheckLevelDialog.showDialog());
        toolBar.add(check);

        return toolBar;
    }

    @Override
    protected TileMapPanel createMapPanel() {
        tileMapPanel = new TileMapPanel(pack, editor);

        return tileMapPanel;
    }

    @Override
    protected JPanel createEastPanel() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new VerticalLayout());
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        packPropertiesPanel = new PackPropertiesPanel(pack);
        tilesetPanel = new TilesetPanel(editor);
        resizePanel = new ResizePanel(editor);
        levelPanel = new LevelPanel(pack, editor);

        eastPanel.add(packPropertiesPanel, constraint);
        eastPanel.add(tilesetPanel, constraint);
        eastPanel.add(resizePanel, constraint);
        eastPanel.add(levelPanel, constraint);

        return eastPanel;
    }
}