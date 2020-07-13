package fr.poulpogaz.isekai.editor.ui.blenderpane;

import javax.swing.*;

public class BasicBlenderPanel extends BlenderPanel {

    private JMenuBar menuBar;

    private String text;

    private Icon icon;

    public BasicBlenderPanel() {
        menuBar = new JMenuBar();
        text = "";
        icon = null;
    }

    public BasicBlenderPanel(JMenuBar menuBar, String text, Icon icon) {
        this.menuBar = menuBar;
        this.text = text;
        this.icon = icon;
    }

    @Override
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public BlenderPanel shallowCopy() {
        return null;
    }
}