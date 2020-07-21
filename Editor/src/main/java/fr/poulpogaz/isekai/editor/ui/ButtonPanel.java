package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import fr.poulpogaz.isekai.editor.ui.area.BRegionView;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends BRegionView {

    private JMenuBar bar;

    public ButtonPanel() {
        setLayout(new GridBagLayout());
        add(new JButton("Hello world"));

        bar = new JMenuBar();
        JMenu helloWorld = new JMenu("Hello world");

        char[] array = {'H', 'E', 'L', 'L', '0', ' ', 'W', 'O', 'R', 'L', 'D'};

        for (char c : array) {
            helloWorld.add(new JMenuItem(String.valueOf(c)));
        }

        bar.add(helloWorld);
    }

    @Override
    public JMenuBar getMenuBar() {
        return bar;
    }

    @Override
    public Icon getIcon() {
        return new FlatTreeOpenIcon();
    }

    @Override
    public String getText() {
        return "Button";
    }

    @Override
    public BRegionView copyWithDataLink() {
        return new ButtonPanel();
    }
}