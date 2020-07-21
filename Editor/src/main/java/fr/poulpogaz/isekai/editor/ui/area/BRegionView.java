package fr.poulpogaz.isekai.editor.ui.area;

import javax.swing.*;

public abstract class BRegionView extends JComponent {

    public abstract JMenuBar getMenuBar();

    public abstract Icon getIcon();

    public abstract String getText();

    public abstract BRegionView copyWithDataLink();
}