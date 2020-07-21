package fr.poulpogaz.isekai.editor.ui.area;

import javax.swing.*;
import java.awt.*;

public class BSplitPane extends JSplitPane {

    private static final String uiClassID = "BSplitPaneUI";

    public BSplitPane() {
    }

    public BSplitPane(int newOrientation) {
        super(newOrientation);
    }

    public BSplitPane(int newOrientation, boolean newContinuousLayout) {
        super(newOrientation, newContinuousLayout);
    }

    public BSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent) {
        super(newOrientation, newLeftComponent, newRightComponent);
    }

    public BSplitPane(int newOrientation, boolean newContinuousLayout, Component newLeftComponent, Component newRightComponent) {
        super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
    }

    @Override
    public void updateUI() {
        setUI((BSplitPaneUI) UIManager.getUI(this));
    }

    @Override
    public BSplitPaneUI getUI() {
        return (BSplitPaneUI) ui;
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }
}