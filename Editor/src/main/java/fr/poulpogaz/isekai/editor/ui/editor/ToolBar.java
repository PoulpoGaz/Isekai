package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.tools.FillTool;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.Tool;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;

public class ToolBar extends JToolBar {

    public static final String TOOL_PROPERTY = "ToolProperty";
    public static final String SHOW_GRID_PROPERTY = "ShowGridProperty";

    private Tool tool = PaintTool.getInstance();
    private boolean showGrid = true;

    public ToolBar() {
        initComponents();
    }

    private void initComponents() {
        ButtonGroup buttonGroup = new ButtonGroup();

        JToggleButton edit = new JToggleButton(IconLoader.loadSVGIcon("/icons/edit.svg"));
        edit.addActionListener((e) -> changeTool(PaintTool.getInstance()));
        edit.getModel().setSelected(true); // selected by default
        JToggleButton fill = new JToggleButton(IconLoader.loadSVGIcon("/icons/fill.svg"));
        fill.addActionListener((e) -> changeTool(FillTool.getInstance()));

        buttonGroup.add(edit);
        buttonGroup.add(fill);

        JToggleButton showGridButton = new JToggleButton(IconLoader.loadSVGIcon("/icons/grid.svg"));
        showGridButton.addActionListener((e) -> {
            showGrid(showGridButton.getModel().isSelected());
        });
        showGridButton.getModel().setSelected(showGrid);

        add(edit);
        add(fill);
        addSeparator();
        add(showGridButton);
    }

    private void changeTool(Tool newTool) {
        if (tool != newTool) {
            Tool old = tool;

            tool = newTool;

            firePropertyChange(TOOL_PROPERTY, old, tool);
        }
    }

    private void showGrid(boolean showGrid) {
        if (showGrid != this.showGrid) {
            boolean old = this.showGrid;

            this.showGrid = showGrid;

            firePropertyChange(SHOW_GRID_PROPERTY, old, showGrid);
        }
    }

    public Tool getTool() {
        return tool;
    }

    public boolean isShowingGrid() {
        return showGrid;
    }
}