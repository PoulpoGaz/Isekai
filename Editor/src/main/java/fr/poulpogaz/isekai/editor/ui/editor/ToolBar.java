package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.tools.FillTool;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.ToolHelper;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;

public class ToolBar extends JToolBar {

    public static final String SHOW_GRID_PROPERTY = "ShowGridProperty";

    private final ToolHelper toolHelper;

    private boolean showGrid = Default.SHOW_GRID;

    public ToolBar(MapEditor editor) {
        toolHelper = editor.getToolHelper();

        initComponents();
    }

    private void initComponents() {
        ButtonGroup buttonGroup = new ButtonGroup();

        JToggleButton edit = new JToggleButton(IconLoader.loadSVGIcon("/icons/edit.svg"));
        edit.addActionListener((e) -> toolHelper.setTool(PaintTool.getInstance()));
        edit.getModel().setSelected(Default.TOOL instanceof PaintTool);

        JToggleButton fill = new JToggleButton(IconLoader.loadSVGIcon("/icons/fill.svg"));
        fill.addActionListener((e) -> toolHelper.setTool((FillTool.getInstance())));
        fill.getModel().setSelected(Default.TOOL instanceof FillTool);

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

    private void showGrid(boolean showGrid) {
        if (showGrid != this.showGrid) {
            boolean old = this.showGrid;

            this.showGrid = showGrid;

            firePropertyChange(SHOW_GRID_PROPERTY, old, showGrid);
        }
    }

    public boolean isShowingGrid() {
        return showGrid;
    }
}