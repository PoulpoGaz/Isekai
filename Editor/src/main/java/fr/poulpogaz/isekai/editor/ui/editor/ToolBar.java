package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.SpriteIcon;
import fr.poulpogaz.isekai.editor.ui.editor.tools.FillTool;
import fr.poulpogaz.isekai.editor.ui.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.ui.editor.tools.PlayerTool;
import fr.poulpogaz.isekai.editor.ui.editor.tools.Tool;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class ToolBar extends JToolBar {

    private final MapEditorModel editor;
    private final Pack pack;

    private JToggleButton edit;
    private JToggleButton fill;
    private JToggleButton playerPos;

    public ToolBar(Pack pack, MapEditorModel editor) {
        this.pack = pack;
        this.editor = editor;
        editor.addPropertyChangeListener(MapEditorModel.TOOL_PROPERTY, this::switchTool);

        initComponents();
    }

    private void initComponents() {
        ButtonGroup buttonGroup = new ButtonGroup();

        edit = new JToggleButton(IconLoader.loadSVGIcon("/icons/edit.svg"));
        edit.addActionListener((e) -> editor.setTool(PaintTool.getInstance()));
        edit.getModel().setSelected(editor.getTool() instanceof PaintTool);

        fill = new JToggleButton(IconLoader.loadSVGIcon("/icons/fill.svg"));
        fill.addActionListener((e) -> editor.setTool((FillTool.getInstance())));
        fill.getModel().setSelected(editor.getTool() instanceof FillTool);

        playerPos = new JToggleButton(createPlayerIcon());
        playerPos.addActionListener((e) -> editor.setTool(PlayerTool.getInstance()));
        playerPos.getModel().setSelected(editor.getTool() instanceof PlayerTool);

        buttonGroup.add(edit);
        buttonGroup.add(fill);
        buttonGroup.add(playerPos);

        JToggleButton showGridButton = new JToggleButton(IconLoader.loadSVGIcon("/icons/grid.svg"));
        showGridButton.addActionListener((e) -> {
            editor.setShowGrid(showGridButton.getModel().isSelected());
        });
        showGridButton.getModel().setSelected(editor.isShowGrid());

        add(edit);
        add(fill);
        add(playerPos);
        addSeparator();
        add(showGridButton);
    }

    private SpriteIcon createPlayerIcon() {
        AbstractSprite sprite = pack.getSprite(PackSprites.PLAYER_DEFAULT_STATIC);

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