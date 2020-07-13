package fr.poulpogaz.isekai.editor.ui.blenderpane;

import javax.swing.*;

public abstract class BlenderPanel extends JComponent {

    public abstract JMenuBar getMenuBar();

    public abstract Icon getIcon();

    public abstract String getText();

    public abstract BlenderPanel shallowCopy(); // for BlenderSplitPane
}