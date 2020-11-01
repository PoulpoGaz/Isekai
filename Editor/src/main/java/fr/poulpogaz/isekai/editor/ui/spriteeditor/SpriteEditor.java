package fr.poulpogaz.isekai.editor.ui.spriteeditor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.tools.ImageFillTool;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.ui.colorpicker.ColorPicker;
import fr.poulpogaz.isekai.editor.ui.editorbase.EditorPanelBase;
import fr.poulpogaz.isekai.editor.ui.editorbase.MapPanelBase;
import fr.poulpogaz.isekai.editor.ui.editorbase.ToolBar;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import javax.swing.border.Border;

public class SpriteEditor extends EditorPanelBase<SpriteEditorModel> {

    private ImageViewPanel imageViewPanel;

    private ColorPicker colorPicker;
    private SpritePanel spritePanel;
    private SubSpriteEditionPanel subSpriteEditionPanel;
    private AnimatedSpriteEditionPanel animatedSpriteEditionPanel;

    public SpriteEditor(Pack pack) {
        super(pack, new SpriteEditorModel(pack));

        initListeners();
    }

    @Override
    protected ToolBar<SpriteEditorModel> createToolBar() {
        ToolBar<SpriteEditorModel> toolBar = new ToolBar<>(editor);

        toolBar.addTool(PaintTool.getInstance());
        toolBar.addTool(ImageFillTool.getInstance());

        return toolBar;
    }

    @Override
    protected MapPanelBase<SpriteEditorModel, ?, ?> createMapPanel() {
        imageViewPanel = new ImageViewPanel(pack, editor);

        return imageViewPanel;
    }

    @Override
    protected JPanel createEastPanel() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new VerticalLayout());

        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        colorPicker = new ColorPicker();
        colorPicker.setBorder(BorderFactory.createTitledBorder("Color"));

        spritePanel = new SpritePanel(pack, editor);
        spritePanel.setBorder(BorderFactory.createTitledBorder("Sprites"));

        Border border = BorderFactory.createTitledBorder("Edit sprite");
        subSpriteEditionPanel = new SubSpriteEditionPanel(editor);
        subSpriteEditionPanel.setBorder(border);
        animatedSpriteEditionPanel = new AnimatedSpriteEditionPanel(editor);
        animatedSpriteEditionPanel.setBorder(border);

        eastPanel.add(colorPicker, constraint);
        eastPanel.add(spritePanel, constraint);
        eastPanel.add(subSpriteEditionPanel, constraint);
        eastPanel.add(animatedSpriteEditionPanel, constraint);

        return eastPanel;
    }

    private void initListeners() {
        colorPicker.addColorListener((e) -> {
            editor.setSelectedElement(colorPicker.getColor());
        });
    }
}