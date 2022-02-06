package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.tools.Tool;
import fr.poulpogaz.isekai.editor.ui.Icons;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

public class ToolBar extends JToolBar {

    protected final LevelEditorModel editor;

    protected final HashMap<Tool, JToggleButton> tools;
    protected ButtonGroup group;

    public ToolBar(LevelEditorModel editor) {
        this.editor = editor;

        editor.addPropertyChangeListener(LevelEditorModel.TOOL_PROPERTY, this::switchTool);

        tools = new HashMap<>();

        initComponents();
    }

    protected void initComponents() {
        initToolButtons();
        addSeparator();
        initOtherButtons();
    }

    protected void initToolButtons() {
        group = new ButtonGroup();
    }

    protected void initOtherButtons() {
        JToggleButton showGridButton = new JToggleButton(Icons.get("icons/grid.svg"));
        showGridButton.addActionListener((e) -> {
            editor.setShowGrid(showGridButton.getModel().isSelected());
        });
        showGridButton.getModel().setSelected(editor.isShowGrid());

        add(showGridButton);
    }

    public void addTool(Tool tool) {
        if (!tools.containsKey(tool)) {
            JToggleButton button = new JToggleButton(tool.getIcon());
            button.addActionListener((l) -> editor.setTool(tool));

            group.add(button);
            add(button, tools.size());

            tools.put(tool, button);
        }
    }

    public void removeTool(Tool tool) {
        if (tools.containsKey(tool)) {
            JToggleButton button = tools.get(tool);

            group.remove(button);
            remove(button);

            tools.remove(tool, button);
        }
    }

    private void switchTool(PropertyChangeEvent propertyChangeEvent) {
        setSelectedTool(editor.getTool());
    }

    public void setSelectedTool(Tool tool) {
        JToggleButton button = tools.get(tool);

        if (button != null) {
            button.setSelected(true);
        }
    }
}