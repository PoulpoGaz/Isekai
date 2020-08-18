package fr.poulpogaz.isekai.editor.ui;

import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;

public class NoPackLoadedPanel extends JPanel {

    public NoPackLoadedPanel() {
        setLayout(new GridBagLayout());

        JPanel inside = new JPanel();

        inside.setLayout(new VerticalLayout(10));

        inside.add(createLabel("Create a new pack in file/new"));
        inside.add(createLabel("Open pack in file/open"));
        inside.add(createLabel("Import pack in file/import"));

        add(inside);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(20f));

        return label;
    }
}