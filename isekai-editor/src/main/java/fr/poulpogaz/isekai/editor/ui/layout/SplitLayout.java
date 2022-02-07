package fr.poulpogaz.isekai.editor.ui.layout;

import fr.poulpogaz.isekai.editor.utils.GraphicsUtils;

import java.awt.*;

public class SplitLayout implements LayoutManager {

    private int gap;
    private float percent = 0.5f;

    public SplitLayout() {
    }

    public SplitLayout(int gap, float percent) {
        this.gap = gap;
        this.percent = percent;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        if (parent.getComponentCount() == 0) {
            return GraphicsUtils.from(parent.getInsets());
        } else {
            Dimension max = new Dimension();

            for (int i = 0; i < Math.min(parent.getComponentCount(), 2); i++) {
                Component component = parent.getComponent(i);
                Dimension compDim = component.getPreferredSize();

                max.width = Math.max(max.width, compDim.width);
                max.height = Math.max(max.height, compDim.height);
            }

            Insets insets = parent.getInsets();

            max.width *= 2;

            max.width += insets.left + insets.right;
            max.height += insets.top + insets.bottom;

            return max;
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        if (parent.getComponentCount() == 0) {
            return;
        }

        Insets insets = parent.getInsets();
        Dimension dimension = parent.getSize();

        Dimension inner = GraphicsUtils.sub(dimension, insets);

        if (parent.getComponentCount() == 1) {
            Component component = parent.getComponent(0);

            component.setBounds(insets.left, insets.top, inner.width, inner.height);
        } else {
            inner.width -= gap;

            Component left = parent.getComponent(0);
            Component right = parent.getComponent(1);

            int w1 = (int) (inner.width * percent);
            int w2 = inner.width - w1;

            left.setBounds(insets.left, insets.top, w1, inner.height);
            right.setBounds(insets.left + w1 + gap, insets.top, w2, inner.height);
        }
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}