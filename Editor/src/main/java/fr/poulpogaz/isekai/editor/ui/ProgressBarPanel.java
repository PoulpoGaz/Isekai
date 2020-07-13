package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.icons.FlatTreeClosedIcon;
import fr.poulpogaz.isekai.editor.ui.blenderpane.BlenderPanel;

import javax.swing.*;
import java.awt.*;

public class ProgressBarPanel extends BlenderPanel {

    private JProgressBar bar;

    private JMenuBar menuBar;

    public ProgressBarPanel() {
        this(new JProgressBar());
    }

    private ProgressBarPanel(JProgressBar bar) {
        this.bar = bar;
        setLayout(new GridBagLayout());
        add(bar);

        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Progress");

        JMenuItem zero = new JMenuItem("0%");
        zero.addActionListener((e) -> {
            bar.setValue(0);
            bar.setIndeterminate(false);
        });

        JMenuItem twenty_five = new JMenuItem("25%");
        twenty_five.addActionListener((e) -> {
            bar.setValue(25);
            bar.setIndeterminate(false);
        });

        JMenuItem fifty = new JMenuItem("50%");
        fifty.addActionListener((e) -> {
            bar.setValue(50);
            bar.setIndeterminate(false);
        });

        JMenuItem seventy_five = new JMenuItem("75%");
        seventy_five.addActionListener((e) -> {
            bar.setValue(75);
            bar.setIndeterminate(false);
        });

        JMenuItem hundred = new JMenuItem("100%");
        hundred.addActionListener((e) -> {
            bar.setValue(100);
            bar.setIndeterminate(false);
        });

        JMenuItem indeterminate = new JMenuItem("Indeterminate");
        indeterminate.addActionListener((e) -> bar.setIndeterminate(true));

        menu.add(zero);
        menu.add(twenty_five);
        menu.add(fifty);
        menu.add(seventy_five);
        menu.add(hundred);
        menu.addSeparator();
        menu.add(indeterminate);
        menuBar.add(menu);
    }

    @Override
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    @Override
    public Icon getIcon() {
        return new FlatTreeClosedIcon();
    }

    @Override
    public String getText() {
        return "Progress bar";
    }

    @Override
    public BlenderPanel shallowCopy() {
        JProgressBar bar = new JProgressBar();
        bar.setModel(this.bar.getModel());

        return new ProgressBarPanel(bar);
    }
}