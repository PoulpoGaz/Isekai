package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.controller.EditorModel;
import fr.poulpogaz.isekai.editor.controller.PackController;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.SpriteIcon;
import fr.poulpogaz.isekai.editor.tools.*;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class ToolBar extends JToolBar {

    private final PackController controller;
    private JToggleButton edit;
    private JToggleButton fill;
    private JToggleButton playerPos;

    public ToolBar(PackController controller) {
        this.controller = controller;
        controller.addEditorPropertyChangeListener(EditorModel.TOOL_PROPERTY, this::switchTool);

        initComponents();
    }

    private void initComponents() {
        ButtonGroup buttonGroup = new ButtonGroup();

        edit = new JToggleButton(IconLoader.loadSVGIcon("/icons/edit.svg"));
        edit.addActionListener((e) -> controller.setTool(PaintTool.getInstance()));
        edit.getModel().setSelected(controller.getTool() instanceof PaintTool);

        fill = new JToggleButton(IconLoader.loadSVGIcon("/icons/fill.svg"));
        fill.addActionListener((e) -> controller.setTool((FillTool.getInstance())));
        fill.getModel().setSelected(controller.getTool() instanceof FillTool);

        playerPos = new JToggleButton(createPlayerIcon());
        playerPos.addActionListener((e) -> controller.setTool(PlayerTool.getInstance()));
        playerPos.getModel().setSelected(controller.getTool() instanceof PlayerTool);

        buttonGroup.add(edit);
        buttonGroup.add(fill);
        buttonGroup.add(playerPos);

        JToggleButton showGridButton = new JToggleButton(IconLoader.loadSVGIcon("/icons/grid.svg"));
        showGridButton.addActionListener((e) -> {
            controller.setShowGrid(showGridButton.getModel().isSelected());
        });
        showGridButton.getModel().setSelected(controller.isShowGrid());

        add(edit);
        add(fill);
        add(playerPos);
        addSeparator();
        add(showGridButton);
    }

    private SpriteIcon createPlayerIcon() {
        AbstractSprite sprite = controller.getSprite(PackSprites.DEFAULT_STATIC);

        return new SpriteIcon(sprite);
    }

    private void switchTool(PropertyChangeEvent e) {
        Tool tool = (Tool) e.getNewValue();

        if (tool instanceof PaintTool) {
            edit.setSelected(true);
        } else if (tool instanceof FillTool) {
            fill.setSelected(true);
        } else if (tool instanceof PlayerTool) {
            playerPos.setSelected(true);
        }
    }
}