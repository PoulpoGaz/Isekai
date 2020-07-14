package fr.poulpogaz.isekai.editor.ui.blenderpane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BlenderSplitArea extends JSplitPane {

    private static final String uiClassID = "BlenderSplitAreaUI";

    public BlenderSplitArea(BlenderArea area) {
        init(new BlenderAreaImpl(area.getModel().shallowCopy()));
    }

    private BlenderSplitArea(BlenderAreaImpl area) {
        init(area);
    }

    protected void init(BlenderAreaImpl area) {
        setLeftComponent(area);
        setRightComponent(null);

        setDividerSize(0);
    }

    protected void verticalSplit(ActionEvent e) {
        setOrientation(HORIZONTAL_SPLIT);
        setDividerLocation(0.5);

        BlenderAreaImpl left = (BlenderAreaImpl) getLeftComponent();

        setLeftComponent(new BlenderSplitArea(left));
        setRightComponent(new BlenderSplitArea(left.shallowCopy()));

        setDividerSize(UIManager.getInt("BlenderSplitArea.dividerSize"));
    }

    protected void horizontalSplit(ActionEvent e) {
        setOrientation(VERTICAL_SPLIT);
        setDividerLocation(0.5);

        BlenderAreaImpl left = (BlenderAreaImpl) getLeftComponent();

        setLeftComponent(new BlenderSplitArea(left));
        setRightComponent(new BlenderSplitArea(left.shallowCopy()));

        setDividerSize(UIManager.getInt("BlenderSplitArea.dividerSize"));
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

    protected static class BlenderAreaImpl extends BlenderArea {

        public BlenderAreaImpl(BlenderAreaModel model) {
            super(model);
        }

        @Override
        public BlenderArea shallowCopy() {
            return new BlenderAreaImpl(model.shallowCopy());
        }

        @Override
        protected JMenuBar createMenuBar() {
            JMenuBar menuBar = super.createMenuBar();

            menuBar.add(createHorizontalSplitButton());
            menuBar.add(createVerticalSplitButton());

            return menuBar;
        }

        protected JButton createHorizontalSplitButton() {
            JButton horizontalButton = new JButton("H");

            horizontalButton.addActionListener((e) -> {
                Container parent = getParent();

                if (parent instanceof BlenderSplitArea) {
                    ((BlenderSplitArea) parent).horizontalSplit(e);
                }
            });

            return horizontalButton;
        }

        protected JButton createVerticalSplitButton() {
            JButton verticalButton = new JButton("V");

            verticalButton.addActionListener((e) -> {
                Container parent = getParent();

                if (parent instanceof BlenderSplitArea) {
                    ((BlenderSplitArea) parent).verticalSplit(e);
                }
            });

            return verticalButton;
        }
    }
}