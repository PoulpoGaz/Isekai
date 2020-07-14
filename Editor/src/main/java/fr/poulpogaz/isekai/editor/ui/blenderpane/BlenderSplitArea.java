package fr.poulpogaz.isekai.editor.ui.blenderpane;

import javax.swing.*;
import java.awt.*;

import static com.formdev.flatlaf.FlatClientProperties.BUTTON_TYPE;
import static com.formdev.flatlaf.FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON;

public class BlenderSplitArea extends JSplitPane {

    private static final String uiClassID = "BlenderSplitAreaUI";

    protected static final boolean LEFT = false;

    protected static final boolean RIGHT = true;

    protected boolean isRoot;
    protected boolean hasChildren;


    public BlenderSplitArea(BlenderArea area) {
        isRoot = true;
        hasChildren = false;

        init(new BlenderAreaImpl(area.getModel().shallowCopy()));
    }

    protected BlenderSplitArea(BlenderAreaImpl area) {
        isRoot = false;
        hasChildren = false;

        init(area);
    }

    protected void init(BlenderAreaImpl area) {
        if (!isRoot) {
            area.setCloseButtonVisible(true);
        }

        setLeftComponent(area);
        setRightComponent(null);

        setDividerSize(0);
    }

    protected void split(int orientation) {
        setOrientation(orientation);
        setDividerLocation(0.5);

        BlenderAreaImpl left = getChild();
        BlenderAreaImpl right = (BlenderAreaImpl) left.shallowCopy();

        setLeftComponent(new BlenderSplitArea(left));
        setRightComponent(new BlenderSplitArea(right));

        setDividerSize(UIManager.getInt("BlenderSplitArea.dividerSize"));

        hasChildren = true;

        revalidate();
        repaint();
    }

    protected void close() {
        BlenderSplitArea parent = (BlenderSplitArea) getParent();

        boolean leftOrRight = parent.getLeftComponent() == this ? RIGHT : LEFT;

        parent.close(leftOrRight);
    }

    protected void close(boolean leftOrRight) {
        if (leftOrRight == LEFT) {
            close((BlenderSplitArea) getLeftComponent());
        } else {
            close((BlenderSplitArea) getRightComponent());
        }

        revalidate();
        repaint();
    }

    protected void close(BlenderSplitArea splitArea) {
        if (splitArea.hasChildren()) {
            setDividerLocation(splitArea.getDividerLocation());
            setOrientation(splitArea.getOrientation());

            setLeftComponent(splitArea.getLeftComponent());
            setRightComponent(splitArea.getRightComponent());

        } else {
            BlenderAreaImpl area = splitArea.getChild();

            if (isRoot()) {
                area.setCloseButtonVisible(false);
            }

            setLeftComponent(area);
            setRightComponent(null);

            setDividerSize(0);

            hasChildren = false;
        }
    }

    protected BlenderAreaImpl getChild() {
        Component left = getLeftComponent();

        if (left != null) {
            return (BlenderAreaImpl) left;
        } else {
            return (BlenderAreaImpl) getRightComponent();
        }
    }

    @Override
    public void updateUI() {
        setUI((BlenderSplitAreaUI) UIManager.getUI(this));
    }

    @Override
    public BlenderSplitAreaUI getUI() {
        return (BlenderSplitAreaUI) ui;
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean hasChildren() {
        return hasChildren;
    }

    protected static class BlenderAreaImpl extends BlenderArea {

        protected JButton closeButton;

        public BlenderAreaImpl(BlenderAreaModel model) {
            super(model);
        }

        @Override
        public BlenderArea shallowCopy() {
            return new BlenderAreaImpl(model.shallowCopy());
        }

        protected void setCloseButtonVisible(boolean visible) {
            closeButton.setVisible(visible);

            revalidate();
            repaint();
        }

        @Override
        protected JMenuBar createMenuBar() {
            JMenuBar menuBar = super.createMenuBar();

            menuBar.add(createHorizontalSplitButton());
            menuBar.add(createVerticalSplitButton());

            closeButton = createCloseButton();
            closeButton.setVisible(false);

            menuBar.add(closeButton);

            return menuBar;
        }

        protected JButton createCloseButton() {
            JButton closeButton = new JButton("C");

            closeButton.putClientProperty(BUTTON_TYPE, BUTTON_TYPE_TOOLBAR_BUTTON);

            closeButton.addActionListener((e) -> {
                Container parent = getParent();

                if (parent instanceof BlenderSplitArea) {
                    ((BlenderSplitArea) parent).close();
                }
            });

            return closeButton;
        }

        protected JButton createHorizontalSplitButton() {
            JButton horizontalButton = new JButton("H");

            horizontalButton.putClientProperty(BUTTON_TYPE, BUTTON_TYPE_TOOLBAR_BUTTON);

            horizontalButton.addActionListener((e) -> {
                Container parent = getParent();

                if (parent instanceof BlenderSplitArea) {
                    ((BlenderSplitArea) parent).split(HORIZONTAL_SPLIT);
                }
            });

            return horizontalButton;
        }

        protected JButton createVerticalSplitButton() {
            JButton verticalButton = new JButton("V");

            verticalButton.putClientProperty(BUTTON_TYPE, BUTTON_TYPE_TOOLBAR_BUTTON);

            verticalButton.addActionListener((e) -> {
                Container parent = getParent();

                if (parent instanceof BlenderSplitArea) {
                    ((BlenderSplitArea) parent).split(VERTICAL_SPLIT);
                }
            });

            return verticalButton;
        }
    }
}