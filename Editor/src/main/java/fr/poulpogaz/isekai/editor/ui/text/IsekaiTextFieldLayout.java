package fr.poulpogaz.isekai.editor.ui.text;

import javax.swing.*;
import java.awt.*;

public class IsekaiTextFieldLayout implements LayoutManager2 {

    public static final String TRAIL = "Trail";
    public static final String LEAD = "Lead";

    private Component trail;
    private Component lead;

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    /**
     * @see IsekaiTextFieldUI#getPreferredSize(JComponent)
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    /**
     * @see IsekaiTextFieldUI#getMinimumSize(JComponent)
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    private Dimension getDimOf(Component c, boolean preferred) {
        if (c == null) {
            return new Dimension();
        } else if (preferred) {
            return c.getPreferredSize();
        } else {
            return c.getMinimumSize();
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        JIsekaiTextField field = (JIsekaiTextField) parent;
        Insets insets = field.getInsets();
        Dimension size = field.getSize();

        boolean leftToRight = field.getComponentOrientation().isLeftToRight();

        if (!leftToRight) {
            Component temp = trail;
            trail = lead;
            lead = temp;
        }

        int height = size.height - insets.top - insets.bottom;

        if (lead != null) {
            int width = lead.getPreferredSize().width;

            lead.setBounds(insets.left, insets.top, width, height);
        }

        if (trail != null) {
            int width = trail.getPreferredSize().width;

            trail.setBounds(size.width - insets.right - width, insets.top, width, height);
        }
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof String) {
            String constraint = (String) constraints;

            if (constraint.equals(TRAIL)) {
                trail = comp;
            } else if (constraint.equals(LEAD)) {
                lead = comp;
            }
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {

    }
}