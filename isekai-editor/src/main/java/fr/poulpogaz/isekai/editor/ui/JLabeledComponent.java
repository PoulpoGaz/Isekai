package fr.poulpogaz.isekai.editor.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JLabeledComponent extends JComponent {

    public static final String GAP_CHANGED_PROPERTY = "GapChanged";
    public static final String LABEL_CHANGED_PROPERTY = "LabelChanged";
    public static final String COMPONENT_CHANGED_PROPERTY = "ComponentChanged";

    private JLabel label;
    private JComponent component;
    private int gap;

    public JLabeledComponent(String text, JComponent component) {
        this(new JLabel(text), component, 5);
    }

    public JLabeledComponent(JLabel label, JComponent component) {
        this(label, component, 5);
    }

    public JLabeledComponent(JLabel label, JComponent component, int gap) {
        this.label = Objects.requireNonNull(label);
        this.component = Objects.requireNonNull(component);
        this.gap = Math.max(gap, 0);

        setLayout(new InnerLayout());

        add(label);
        add(component);
    }

    public void setGap(int gap) {
        if (gap != this.gap && gap >= 0) {
            int old = this.gap;

            this.gap = gap;

            revalidate();
            repaint();

            firePropertyChange(GAP_CHANGED_PROPERTY, old, gap);
        }
    }

    public int getGap() {
        return gap;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        if (label != this.label && label != null) {
            JLabel old = this.label;

            remove(this.label);
            this.label = label;
            add(this.label);

            firePropertyChange(LABEL_CHANGED_PROPERTY, old, label);
        }
    }

    public JComponent getComponent() {
        return component;
    }

    public void setComponent(JComponent component) {
        if (component != this.component && component != null) {
            JComponent old = this.component;

            remove(this.component);
            this.component = component;
            add(this.component);

            firePropertyChange(COMPONENT_CHANGED_PROPERTY, old, component);
        }
    }

    public void setText(String text) {
        label.setText(text);
    }

    public String getText() {
        return label.getText();
    }

    private class InnerLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {

        }

        @Override
        public void removeLayoutComponent(Component comp) {

        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Dimension dimension = new Dimension();
            Insets insets = parent.getInsets();

            Dimension compDim = component.getPreferredSize();
            Dimension labelDim = label.getPreferredSize();

            dimension.width += compDim.width + labelDim.width + gap + insets.left + insets.right;
            dimension.height = Math.max(compDim.height, labelDim.height) + insets.top + insets.bottom;

            return dimension;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return preferredLayoutSize(parent);
        }

        @Override
        public void layoutContainer(Container parent) {
            Dimension parentDim = parent.getSize();
            Insets insets = parent.getInsets();

            int height = parentDim.height - insets.top - insets.bottom;

            Dimension labelDim = label.getPreferredSize();
            int x = insets.left;
            int y = align(labelDim.height, height);
            label.setBounds(x, y, labelDim.width, labelDim.height);


            Dimension compDim = component.getPreferredSize();
            x += labelDim.width + gap;
            y = align(compDim.height, height);
            component.setBounds(x, y, compDim.width, compDim.height);
        }

        private int align(int compHeight, int parentHeight) {
            return (int) (0.5f * (parentHeight - compHeight));
        }
    }
}