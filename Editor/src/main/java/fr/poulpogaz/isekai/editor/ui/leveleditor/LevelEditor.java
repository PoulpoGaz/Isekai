package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.tools.LevelFillTool;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.PlayerTool;
import fr.poulpogaz.isekai.editor.ui.Icons;
import fr.poulpogaz.isekai.editor.ui.check.CheckLevelDialog;
import fr.poulpogaz.isekai.editor.ui.editorbase.EditableMapPanelBase;
import fr.poulpogaz.isekai.editor.ui.editorbase.EditorPanelBase;
import fr.poulpogaz.isekai.editor.ui.editorbase.ToolBar;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;

public class LevelEditor extends EditorPanelBase<LevelEditorModel> {

    private EditableTileMapPanel tileMapPanel;
    private TilesetPanel tilesetPanel;
    private PackPropertiesPanel packPropertiesPanel;
    private LevelPanel levelPanel;
    private ResizePanel resizePanel;

    private ToolBar<LevelEditorModel> toolBar;

    public LevelEditor(Pack pack) {
        super(pack, new LevelEditorModel(pack.getLevel(0)));
    }

    @Override
    protected ToolBar<LevelEditorModel> createToolBar() {
        toolBar = new ToolBar<>(editor);

        toolBar.addTool(PaintTool.getInstance());
        toolBar.addTool(LevelFillTool.getInstance());
        toolBar.addTool(PlayerTool.getInstance(pack));

        JButton check = new JButton();
        check.setIcon(Icons.get("icons/check.svg"));
        check.addActionListener((e) -> CheckLevelDialog.showDialog());
        toolBar.add(check);

        return toolBar;
    }

    @Override
    protected EditableMapPanelBase<LevelEditorModel, ?, ?> createMapPanel() {
        tileMapPanel = new EditableTileMapPanel(pack, editor);

        return tileMapPanel;
    }

    @Override
    protected JPanel createEastPanel() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new VerticalLayout());
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        packPropertiesPanel = new PackPropertiesPanel(pack);
        tilesetPanel = new TilesetPanel(pack, editor);
        resizePanel = new ResizePanel(editor);
        levelPanel = new LevelPanel(pack, editor);

        eastPanel.add(packPropertiesPanel, constraint);
        eastPanel.add(tilesetPanel, constraint);
        eastPanel.add(resizePanel, constraint);
        eastPanel.add(levelPanel, constraint);

        return eastPanel;
    }
}