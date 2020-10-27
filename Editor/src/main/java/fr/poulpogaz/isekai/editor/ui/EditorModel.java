package fr.poulpogaz.isekai.editor.ui;

import fr.poulpogaz.isekai.editor.tools.Map;
import fr.poulpogaz.isekai.editor.tools.PaintTool;
import fr.poulpogaz.isekai.editor.tools.Tool;

import java.util.Objects;

public class EditorModel<M extends Map<E>, E> extends Model {

    public static final String SELECTED_MAP_PROPERTY = "SelectedMapProperty";
    public static final String SELECTED_ELEMENT_PROPERTY = "SelectedElementProperty";
    public static final String TOOL_PROPERTY = "ToolProperty";
    public static final String SHOW_GRID_PROPERTY = "ShowGridProperty";

    protected Tool tool = PaintTool.getInstance();
    protected M selectedMap;
    protected E selectedElement;

    protected boolean showGrid = true;

    protected EditorModel(M selectedMap, E selectedElement) {
        this.selectedMap = Objects.requireNonNull(selectedMap);
        this.selectedElement = Objects.requireNonNull(selectedElement);
    }

    public void setSelectedMap(M selectedMap) {
        if (this.selectedMap != selectedMap) {
            M old = this.selectedMap;

            this.selectedMap = selectedMap;

            firePropertyChange(SELECTED_MAP_PROPERTY, old, selectedMap);
        }
    }

    public M getSelectedMap() {
        return selectedMap;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        if (this.showGrid != showGrid) {
            boolean old = this.showGrid;

            this.showGrid = showGrid;

            firePropertyChange(SHOW_GRID_PROPERTY, old, showGrid);
        }
    }

    public void applyTool(int x, int y) {
        tool.apply(selectedMap, selectedElement, x, y);
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        if (this.tool != tool) {
            Tool old = this.tool;

            this.tool = tool;

            firePropertyChange(TOOL_PROPERTY, old, tool);
        }
    }

    public E getSelectedElement() {
        return selectedElement;
    }

    public void setSelectedElement(E selectedElement) {
        if (selectedElement != null && this.selectedElement != selectedElement) {
            E old = this.selectedElement;

            this.selectedElement = selectedElement;

            firePropertyChange(SELECTED_MAP_PROPERTY, old, selectedElement);
        }
    }
}