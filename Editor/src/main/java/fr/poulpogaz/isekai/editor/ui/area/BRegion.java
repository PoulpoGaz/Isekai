package fr.poulpogaz.isekai.editor.ui.area;

import fr.poulpogaz.isekai.editor.ui.layout.HCOrientation;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.Objects;

import static fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint.DEFAULT_GAP;

public class BRegion extends JComponent implements ItemListener {

    public static final String MODEL_PROPERTY = "BlenderModelProperty";
    public static final String MENU_BAR_POSITION = "MenuBarPositionProperty";

    public static final boolean BOTTOM = false;
    public static final boolean TOP = true;

    protected static final HorizontalConstraint MENU_CONSTRAINT = new HorizontalConstraint(HCOrientation.LEFT, DEFAULT_GAP, DEFAULT_GAP, true, 0.5f, true);

    protected JComboBox<BRegionView> viewComboBox;
    protected JScrollPane scrollPane; // the menu bar is wrapped inside this scrollPane
    protected JMenuBar menuBar;
    protected JMenuItem flipMenuBarTo;

    protected boolean menuBarPosition;

    protected BRegionModel model;

    protected BRegionView selected;

    public BRegion() {
        init();

        setModel(new DefaultBRegionModel());
    }

    public BRegion(BRegionView[] views) {
        init();

        setModel(new DefaultBRegionModel(views));
    }

    public BRegion(BRegionModel model) {
        init();

        setModel(model);
    }

    protected void init() {
        viewComboBox = createViewComboBox();
        menuBar = createMenuBar();

        scrollPane = new HorizontalScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        Dimension menuBarPrefSize = menuBar.getPreferredSize();

        scrollPane.setPreferredSize(new Dimension(16, menuBarPrefSize.height));
        scrollPane.setViewportView(menuBar);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.SOUTH);
    }

    protected JComboBox<BRegionView> createViewComboBox() {
        JComboBox<BRegionView> comboBox = new JComboBox<>();
        comboBox.setRenderer(new ComboBoxRenderer());
        comboBox.addItemListener(this);

        return comboBox;
    }

    protected JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        bar.setLayout(new HorizontalLayout());
        bar.add(viewComboBox);
        bar.setComponentPopupMenu(createMenuBarPopupMenu());

        return bar;
    }

    protected JPopupMenu createMenuBarPopupMenu() {
        JPopupMenu menu = new JPopupMenu();

        flipMenuBarTo = new JMenuItem();
        setFlipMenuBarToText();

        flipMenuBarTo.addActionListener((e) -> setMenuBarPosition(!menuBarPosition));

        menu.add(flipMenuBarTo);

        return menu;
    }

    protected void setFlipMenuBarToText() {
        if (menuBarPosition == TOP) {
            flipMenuBarTo.setText("Flip to bottom");
        } else {
            flipMenuBarTo.setText("Flip to top");
        }
    }

    protected void switchComponents(BRegionView old, BRegionView newView) {
        if (old != null) {
            if (old.getMenuBar() != null) {
                menuBar.remove(old.getMenuBar());
            }

            remove(old);
        }

        if (newView.getMenuBar() != null) {
            menuBar.add(newView.getMenuBar(), MENU_CONSTRAINT);
        }

        add(newView, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public BRegion copy() {
        return new BRegion(model.copy());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        switchComponents(selected, (BRegionView) e.getItem());

        selected = (BRegionView) e.getItem();
    }

    public void addItemListener(ItemListener listener) {
        viewComboBox.addItemListener(listener);
    }

    public void removeItemListener(ItemListener listener) {
        viewComboBox.removeItemListener(listener);
    }

    public void addView(BRegionView view) {
        Objects.requireNonNull(view, "Attempt to add a null component");

        model.addElement(view);
    }

    public void removeView(BRegionView view) {
        if (view != null) {
            model.removeElement(view);
        }
    }

    public void insertView(BRegionView view, int index) {
        Objects.requireNonNull(view, "Attempt to add a null component");

        model.insertElementAt(view, index);
    }

    public void removeViewAt(int index) {
        model.removeElementAt(index);
    }

    public void setSelected(BRegionView view) {
        viewComboBox.setSelectedItem(view);
    }

    public void setSelectedIndex(int index) {
        viewComboBox.setSelectedIndex(index);
    }

    public BRegionView getSelected() {
        return selected;
    }

    public void setModel(BRegionModel model) {
        if (model != this.model && model != null) {
            BRegionModel old = this.model;

            this.model = model;

            viewComboBox.setModel(model);

            BRegionView selected = (BRegionView) model.getSelectedItem();

            if (selected != null) {
                switchComponents(this.selected, selected);
            }

            firePropertyChange(MODEL_PROPERTY, old, model);
        }
    }

    public BRegionModel getModel() {
        return model;
    }

    public boolean getMenuBarPosition() {
        return menuBarPosition;
    }

    public void setMenuBarPosition(boolean menuBarPosition) {
        if (menuBarPosition != this.menuBarPosition) {
            boolean old = this.menuBarPosition;

            this.menuBarPosition = menuBarPosition;

            if (menuBarPosition == TOP) {
                add(scrollPane, BorderLayout.NORTH);
            } else {
                add(scrollPane, BorderLayout.SOUTH);
            }

            setFlipMenuBarToText();

            firePropertyChange(MENU_BAR_POSITION, old, menuBarPosition);

            revalidate();
            repaint();
        }
    }

    protected static class ComboBoxRenderer extends BasicComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            BRegionView view = (BRegionView) value;

            label.setText(view.getText());
            label.setIcon(view.getIcon());

            return label;
        }
    }

    protected static class HorizontalScrollPane extends JScrollPane {

        public HorizontalScrollPane() {
            super();

            setWheelScrollingEnabled(false);
            initListeners();
        }

        protected void initListeners() {
            JScrollBar horizontalScrollBar = getHorizontalScrollBar();

            addMouseWheelListener(new MouseAdapter() {

                public void mouseWheelMoved(MouseWheelEvent evt) {
                    int scrollAmount = evt.getScrollAmount();
                    int newValue = horizontalScrollBar.getValue() +
                            horizontalScrollBar.getBlockIncrement() * scrollAmount * evt.getWheelRotation();

                    if (evt.getWheelRotation() >= 1) {
                        horizontalScrollBar.setValue(Math.min(newValue, horizontalScrollBar.getMaximum()));

                    } else if (evt.getWheelRotation() <= -1) {
                        horizontalScrollBar.setValue(Math.max(newValue, 0));
                    }
                }
            });
        }
    }
}