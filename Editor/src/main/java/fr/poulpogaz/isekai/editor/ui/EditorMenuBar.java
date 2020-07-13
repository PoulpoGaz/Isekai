package fr.poulpogaz.isekai.editor.ui;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {

    public EditorMenuBar() {
        initMenuBar();
    }

    private void initMenuBar() {
        JMenu file = new JMenu("File");

        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save as");

        JMenuItem importFile = new JMenuItem("Import");
        JMenuItem exportFile = new JMenuItem("Export");

        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem quit = new JMenuItem("Quit");

        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(importFile);
        file.add(exportFile);
        file.addSeparator();
        file.add(settings);
        file.addSeparator();
        file.add(quit);

        add(file);
    }
}