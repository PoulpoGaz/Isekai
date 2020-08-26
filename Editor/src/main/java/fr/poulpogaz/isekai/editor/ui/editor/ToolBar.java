package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.tools.FillTool;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.Tool;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;

public class ToolBar extends JToolBar {

    public static final String TOOL_PROPERTY = "ToolProperty";

    private Tool tool = PaintTool.getInstance();

    public ToolBar() {
        initComponents();
    }

    private void initComponents() {
        JButton edit = new JButton(IconLoader.loadSVGIcon("/icons/edit.svg"));
        edit.addActionListener((e) -> changeTool(PaintTool.getInstance()));
        JButton fill = new JButton(IconLoader.loadSVGIcon("/icons/fill.svg"));
        fill.addActionListener((e) -> changeTool(FillTool.getInstance()));

        add(edit);
        add(fill);
    }

    private void changeTool(Tool newTool) {
        if (tool != newTool) {
            Tool old = tool;

            tool = newTool;

            firePropertyChange(TOOL_PROPERTY, old, tool);
        }
    }

    public Tool getTool() {
        return tool;
    }
}