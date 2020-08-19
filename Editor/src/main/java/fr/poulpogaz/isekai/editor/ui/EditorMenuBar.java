package fr.poulpogaz.isekai.editor.ui;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackBuilder;
import fr.poulpogaz.isekai.editor.pack.PackIO;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.json.JsonException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class EditorMenuBar extends JMenuBar {

    private final JFileChooser chooser;

    public EditorMenuBar() {
        initMenuBar();

        chooser = new JFileChooser();
        try {
            chooser.setCurrentDirectory(new File(EditorMenuBar.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        chooser.setFileFilter(new FileNameExtensionFilter("SKB files", "skb"));
    }

    private void initMenuBar() {
        JMenu file = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener((e) -> {
            Pack pack = PackBuilder.loadDefaultPack();

            IsekaiEditor.getInstance().setPack(pack);
        });

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener((e) -> open());

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener((e) -> save());

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

    private void save() {
        int result = chooser.showSaveDialog(IsekaiEditor.getInstance());

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Path selectedFile = chooser.getSelectedFile().toPath();
                String fileName = selectedFile.getFileName().toString();

                if (Utils.getExtension(fileName).equals(fileName)) {
                    selectedFile = selectedFile.getParent().resolve(fileName + ".skb");
                }

                PackIO.serialize(IsekaiEditor.getPack(), selectedFile);
            } catch (IOException | JsonException ioException) {
                ioException.printStackTrace();

                JOptionPane.showMessageDialog(IsekaiEditor.getInstance(), "Failed to save the pack.\nSorry", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}