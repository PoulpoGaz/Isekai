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

    protected ComboBoxModel<BlenderPanel> model;

    protected BlenderPanel selected;

    public BlenderArea() {
        init();

        setModel(new DefaultComboBoxModel<>());
    }

    public BlenderArea(BlenderPanel[] panels) {
        init();

        setModel(new DefaultComboBoxModel<>(panels));
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
            menuBar.remove(old.getMenuBar());

            remove(old);
        }

        menuBar.add(newPanel.getMenuBar(), MENU_CONSTRAINT);

        add(newPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
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
        ((MutableComboBoxModel<BlenderPanel>) model).addElement(panel);
    }

    public void removePanel(BlenderPanel panel) {
        if (panel != null) {
            checkMutableComboBoxModel();
            ((MutableComboBoxModel<BlenderPanel>) model).removeElement(panel);
        }
    }

    public void insertPanel(BlenderPanel panel, int index) {
        Objects.requireNonNull(panel, "Attempt to add a null component");

        checkMutableComboBoxModel();
        ((MutableComboBoxModel<BlenderPanel>) model).insertElementAt(panel, index);
    }

    public void removePanelAt(int index) {
        checkMutableComboBoxModel();
        ((MutableComboBoxModel<BlenderPanel>) model).removeElementAt(index);
    }

    protected final void checkMutableComboBoxModel() {
        if (!(model instanceof MutableComboBoxModel)) {
            throw new RuntimeException("Cannot use this method with a non-Mutable data model.");
        }
    }

    public void setModel(ComboBoxModel<BlenderPanel> model) {
        if (model != this.model && model != null) {
            ComboBoxModel<BlenderPanel> old = this.model;

            this.model = model;

            comboBox.setModel(model);

            selected = (BlenderPanel) model.getSelectedItem();

            firePropertyChange(MODEL_PROPERTY, old, model);
        }
    }

    public ComboBoxModel<BlenderPanel> getModel() {
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

    /*protected void initBar() {
        setLayout(new BorderLayout());

        bar = new BlenderPanelBar(this);

        add(bar, BorderLayout.SOUTH);

        show(0);
    }

    public void show(int index) {
        if (index >= 0 && index < panels.size()) {
            showImpl(panels.get(index));
        }
    }

    public void show(AbstractBlenderPanel panel) {
        if (panels.contains(panel)) {
            showImpl(panel);
        }
    }

    protected void showImpl(AbstractBlenderPanel panel) {
        if (current != null) {
            remove(current);
        }

        add(panel, BorderLayout.CENTER);

        bar.reset();
        bar.construct(panel);

        this.current = panel;

        revalidate();
        repaint();
    }

    public void addPanel(AbstractBlenderPanel panel) {
        panels.add(panel);
        bar.add(panel);

        if (panelSize() == 1) { // first component
            show(panel);
            bar.setSelected(panel);
        }
    }

    public void removePanel(AbstractBlenderPanel panel) {
        panels.remove(panel);
        bar.remove(panel);
    }

    public boolean isEmpty() {
        return panels.size() == 0;
    }

    public int panelSize() {
        return panels.size();
    }

    public int getCurrentPaneIndex() {
        return panels.indexOf(current);
    }

    public AbstractBlenderPanel getCurrentPanel() {
        return current;
    }*/
}