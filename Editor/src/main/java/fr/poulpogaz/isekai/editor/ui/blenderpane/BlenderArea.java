package fr.poulpogaz.isekai.editor.ui.blenderpane;

import fr.poulpogaz.isekai.editor.ui.layout.HCOrientation;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

import static fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint.DEFAULT_GAP;

public class BlenderArea extends JComponent implements ItemListener {

    public static final String MODEL_PROPERTY = "BlenderModelProperty";

    protected static final HorizontalConstraint MENU_CONSTRAINT = new HorizontalConstraint(HCOrientation.LEFT, DEFAULT_GAP, DEFAULT_GAP, true, 0.5f, true);

    protected JComboBox<BlenderPanel> comboBox;
    protected JMenuBar menuBar;

    protected BlenderAreaModel model;

    protected BlenderPanel selected;

    public BlenderArea() {
        init();

        setModel(new DefaultBlenderAreaModel());
    }

    public BlenderArea(BlenderPanel[] panels) {
        init();

        setModel(new DefaultBlenderAreaModel(panels));
    }

    // For shallow copy
    protected BlenderArea(BlenderAreaModel model) {
        init();

        setModel(model);
    }

    protected void init() {
        comboBox = createComboBox();
        menuBar = createMenuBar();

        setLayout(new BorderLayout());
        add(menuBar, BorderLayout.SOUTH);
    }

    protected JComboBox<BlenderPanel> createComboBox() {
        JComboBox<BlenderPanel> comboBox = new JComboBox<>();
        comboBox.setRenderer(new ComboBoxRenderer());
        comboBox.addItemListener(this);

        return comboBox;
    }

    protected JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        bar.setLayout(new HorizontalLayout());
        bar.add(comboBox);

        return bar;
    }

    protected void switchComponents(BlenderPanel old, BlenderPanel newPanel) {
        if (old != null) {
            if (old.getMenuBar() != null) {
                menuBar.remove(old.getMenuBar());
            }

            remove(old);
        }

        if (newPanel.getMenuBar() != null) {
            menuBar.add(newPanel.getMenuBar(), MENU_CONSTRAINT);
        }

        add(newPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public BlenderArea shallowCopy() {
        return new BlenderArea(model.shallowCopy());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        switchComponents(selected, (BlenderPanel) e.getItem());

        selected = (BlenderPanel) e.getItem();
    }

    public void addItemListener(ItemListener listener) {
        comboBox.addItemListener(listener);
    }

    public void removeItemListener(ItemListener listener) {
        comboBox.removeItemListener(listener);
    }

    public void addPanel(BlenderPanel panel) {
        Objects.requireNonNull(panel, "Attempt to add a null component");

        checkMutableComboBoxModel();
        ((MutableBlenderAreaModel) model).addElement(panel);
    }

    public void removePanel(BlenderPanel panel) {
        if (panel != null) {
            checkMutableComboBoxModel();
            ((MutableBlenderAreaModel) model).removeElement(panel);
        }
    }

    public void insertPanel(BlenderPanel panel, int index) {
        Objects.requireNonNull(panel, "Attempt to add a null component");

        checkMutableComboBoxModel();
        ((MutableBlenderAreaModel) model).insertElementAt(panel, index);
    }

    public void removePanelAt(int index) {
        checkMutableComboBoxModel();
        ((MutableBlenderAreaModel) model).removeElementAt(index);
    }

    protected final void checkMutableComboBoxModel() {
        if (!(model instanceof MutableBlenderAreaModel)) {
            throw new RuntimeException("Cannot use this method with a non-Mutable data model.");
        }
    }

    public void setSelected(BlenderPanel panel) {
        comboBox.setSelectedItem(panel);
    }

    public void setSelectedIndex(int index) {
        comboBox.setSelectedIndex(index);
    }

    public BlenderPanel getSelected() {
        return selected;
    }

    public void setModel(BlenderAreaModel model) {
        if (model != this.model && model != null) {
            BlenderAreaModel old = this.model;

            this.model = model;

            comboBox.setModel(model);

            BlenderPanel selected = (BlenderPanel) model.getSelectedItem();

            if (selected != null) {
                switchComponents(this.selected, selected);
            }

            firePropertyChange(MODEL_PROPERTY, old, model);
        }
    }

    public BlenderAreaModel getModel() {
        return model;
    }

    protected class ComboBoxRenderer extends BasicComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            BlenderPanel panel = (BlenderPanel) value;

            label.setText(panel.getText());
            label.setIcon(panel.getIcon());

            return label;
        }
    }
}