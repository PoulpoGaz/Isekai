package fr.poulpogaz.isekai.editor.ui.blenderpane;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BlenderSplitArea extends JSplitPane {

    public BlenderSplitArea(BlenderArea area) {
        init(new BlenderAreaImpl(area.getModel().shallowCopy(), this));
    }

    private BlenderSplitArea(BlenderAreaImpl area) {
        init(area);

        area.setParent(this);
    }

    protected void init(BlenderAreaImpl area) {
        setLeftComponent(area);
        setRightComponent(null);

    }

    protected void verticalSplit(ActionEvent e) {
        setOrientation(VERTICAL_SPLIT);
        setDividerLocation(0.5);

        BlenderAreaImpl left = (BlenderAreaImpl) getLeftComponent();

        setLeftComponent(new BlenderSplitArea(left));
        setRightComponent(new BlenderSplitArea(left.shallowCopy()));
    }

    protected void horizontalSplit(ActionEvent e) {
        setOrientation(HORIZONTAL_SPLIT);
        setDividerLocation(0.5);

        BlenderAreaImpl left = (BlenderAreaImpl) getLeftComponent();

        setLeftComponent(new BlenderSplitArea(left));
        setRightComponent(new BlenderSplitArea(left.shallowCopy()));
    }

    protected class BlenderAreaImpl extends BlenderArea {

        private BlenderSplitArea parent;

        public BlenderAreaImpl(BlenderAreaModel model, BlenderSplitArea parent) {
            super(model);
            this.parent = parent;
        }

        @Override
        public BlenderArea shallowCopy() {
            return new BlenderAreaImpl(model.shallowCopy(), parent);
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

            horizontalButton.addActionListener((e) -> parent.horizontalSplit(e));

            return horizontalButton;
        }

        protected JButton createVerticalSplitButton() {
            JButton verticalButton = new JButton("V");

            verticalButton.addActionListener((e) -> parent.verticalSplit(e));

            return verticalButton;
        }

        protected void setParent(BlenderSplitArea parent) {
            this.parent = parent;
        }
    }
}