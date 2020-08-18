package fr.poulpogaz.isekai.editor.ui;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackBuilder;
import fr.poulpogaz.isekai.editor.pack.PackIO;
import fr.poulpogaz.json.JsonException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EditorMenuBar extends JMenuBar {

    private static final File HOME_DIRECTORY = new File(System.getProperty("user.home"));
    private final JFileChooser chooser;

    public EditorMenuBar() {
        initMenuBar();

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(HOME_DIRECTORY);
        chooser.setFileFilter(new FileNameExtensionFilter("SKB files", "skb"));
    }

    private void initMenuBar() {
        JMenu file = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener((e) -> {
            Pack pack = PackBuilder.createDefaultPack();

            IsekaiEditor.getInstance().setPack(pack);
        });

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener((e) -> EventQueue.invokeLater(this::open));

        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save as");

        JMenuItem importFile = new JMenuItem("Import");
        JMenuItem exportFile = new JMenuItem("Export");

        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem quit = new JMenuItem("Quit");

        file.add(newItem);
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

    private void open() {
        int result = chooser.showOpenDialog(IsekaiEditor.getInstance());

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Pack pack = PackIO.deserialize(chooser.getSelectedFile().toPath());
                IsekaiEditor.getInstance().setPack(pack);

            } catch (IOException | JsonException ioException) {
                ioException.printStackTrace();

                JOptionPane.showMessageDialog(IsekaiEditor.getInstance(), "Corrupted file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}