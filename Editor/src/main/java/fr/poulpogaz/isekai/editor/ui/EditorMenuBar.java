package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.TIPackIO;
import fr.poulpogaz.isekai.editor.pack.TIPackIOException;
import fr.poulpogaz.isekai.editor.ui.importer.LevelImporter;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import java.nio.file.Path;

public class EditorMenuBar extends JMenuBar {

    private final IsekaiEditor editor;

    private final JFileChooser chooser;
    private final SettingsDialog settingsDialog;

    public EditorMenuBar(IsekaiEditor editor) {
        this.editor = editor;

        chooser = new JFileChooser();
        settingsDialog = new SettingsDialog(editor);

        initMenuBar();
    }

    private void initMenuBar() {
        JMenu file = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.setIcon(IconLoader.loadSVGIcon("/icons/new.svg"));
        newItem.addActionListener((e) -> editor.setPack(new Pack()));

        JMenuItem open = new JMenuItem("Open");
        open.setIcon(new FlatTreeOpenIcon());
        open.addActionListener((e) -> open());

        JMenuItem openTemplate = new JMenuItem("Open template");
        openTemplate.addActionListener((e) -> {
            JOptionPane.showMessageDialog(editor, "Template not available right now. Sorry", "No template", JOptionPane.INFORMATION_MESSAGE);
        });

        JMenuItem save = new JMenuItem("Save");
        save.setIcon(IconLoader.loadSVGIcon("/icons/save.svg"));
        save.addActionListener((e) -> save());

        JMenuItem saveAs = new JMenuItem("Save as");

        JMenu importMI = new JMenu("Import");
        JMenuItem fromSokobanInfo = new JMenuItem("From sokoban.info");
        fromSokobanInfo.addActionListener((e) -> {
            if (editor.getPack() == null) {
                Pack pack = new Pack();
                LevelImporter.showDialog(pack);
                editor.setPack(pack);
            } else {
                LevelImporter.showDialog(editor.getPack());
            }
        });

        JMenuItem local = new JMenuItem("From your computer");

        importMI.add(fromSokobanInfo);
        importMI.add(local);

        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener((e) -> settingsDialog.setVisible(true));

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener((e) -> editor.dispose());

        file.add(newItem);
        file.add(open);
        file.add(openTemplate);
        file.add(save);
        file.add(saveAs);
        file.add(importMI);
        file.addSeparator();
        file.add(settings);
        file.addSeparator();
        file.add(quit);

        add(file);
    }

    private void open() {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setSelectedFile(Utils.getJARLocation());

        int result = chooser.showOpenDialog(editor);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Pack pack = TIPackIO.deserialize(chooser.getSelectedFile().toPath());

                editor.setPack(pack);
            } catch (TIPackIOException e) {
                e.printStackTrace();

                JOptionPane.showMessageDialog(editor, "Corrupted file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void save() {
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setSelectedFile(Utils.getJARLocation());

        int result = chooser.showSaveDialog(editor);

        if (result == JFileChooser.APPROVE_OPTION) {
            Path directory = chooser.getSelectedFile().toPath();

            try {
                TIPackIO.serialize(editor.getPack(), directory);
            } catch (TIPackIOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(editor, "Failed to save the pack.\nError(s):\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}