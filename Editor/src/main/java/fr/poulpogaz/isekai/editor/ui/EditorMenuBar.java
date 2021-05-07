package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.*;
import fr.poulpogaz.isekai.editor.ui.importer.LevelImporterDialog;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class EditorMenuBar extends JMenuBar {

    private static final FileFilter _8XV = new FileNameExtensionFilter("8xv", "8xv");
    private static final FileFilter ALL_EXCEPT_8XV = new Filter();

    private final IsekaiEditor editor;

    private final JFileChooser chooser;
    private final SettingsDialog settingsDialog;

    public EditorMenuBar(IsekaiEditor editor) {
        this.editor = editor;

        chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);

        settingsDialog = new SettingsDialog(editor);

        initMenuBar();
    }

    private void initMenuBar() {
        JMenu file = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newItem.setIcon(IconLoader.loadSVGIcon("/icons/new.svg"));
        newItem.addActionListener((e) -> editor.setPack(new Pack()));

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        open.setIcon(new FlatTreeOpenIcon());
        open.addActionListener((e) -> open());

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        save.setIcon(IconLoader.loadSVGIcon("/icons/save.svg"));
        save.addActionListener((e) -> save());

        JMenuItem saveAs = new JMenuItem("Save as");
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        saveAs.addActionListener((e) -> saveAs());

        JMenu importMI = new JMenu("Import");
        JMenuItem fromSokobanInfo = new JMenuItem("From sokoban.info");
        fromSokobanInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        fromSokobanInfo.addActionListener((e) -> {
            importLevels();
        });

        JMenuItem local = new JMenuItem("From your computer");
        local.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.SHIFT_DOWN_MASK));
        local.addActionListener((e) -> {
            importLevelsLocal();
        });

        importMI.add(fromSokobanInfo);
        importMI.add(local);

        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener((e) -> settingsDialog.setVisible(true));

        JMenuItem quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        quit.addActionListener((e) -> editor.dispose());

        file.add(newItem);
        file.add(open);
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
        chooser.setFileFilter(_8XV);

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
        Pack pack = editor.getPack();

        if (pack != null) {
            if (isPackValid(pack)) {
                Path save = pack.getSaveLocation();

                if (save == null) {
                    saveAs();
                } else {
                    save(pack, save);
                }
            }
        }
    }

    private void saveAs() {
        Pack pack = editor.getPack();

        if (pack != null) {
            if (isPackValid(pack)) {
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int result = chooser.showSaveDialog(editor);
                if (result == JFileChooser.APPROVE_OPTION) {
                    Path directory = chooser.getSelectedFile().toPath();

                    save(pack, directory);

                    pack.setSaveLocation(directory);
                }
            }
        }
    }

    private void save(Pack pack, Path out) {
        try {
            TIPackIO.serialize(pack, out);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to save the pack.\nError(s):\n" + e);
        }
    }

    private boolean isPackValid(Pack pack) {
        if (pack.getName() == null || pack.getName().isEmpty()) {
            showError("The pack doesn't have a name");
            return false;
        }

        if (pack.getAuthor() == null || pack.getAuthor().isEmpty()) {
            showError("The pack doesn't have an author");
            return false;
        }

        if (pack.getNumberOfLevels() >= 65_536) {
            showError("Too many levels!");
            return false;
        }

        if (pack.getVersion() == null || pack.getVersion().isEmpty()) {
            pack.setVersion("1.0");
        }

        return true;
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(editor, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void importLevels() {
        List<Level> levels = LevelImporterDialog.showDialog();

        if (levels != null && levels.size() > 0) {
            addLevelsToPack(levels);
        }
    }

    private void importLevelsLocal() {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(ALL_EXCEPT_8XV);

        int result = chooser.showSaveDialog(editor);
        if (result == JFileChooser.APPROVE_OPTION) {
            Path file = chooser.getSelectedFile().toPath();

            List<Level> levels = SOKReader.read(file);

            if (levels != null && levels.size() > 0) {
                addLevelsToPack(levels);
            } else {
                JOptionPane.showMessageDialog(editor, "Can't read levels from this file: " + file, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addLevelsToPack(List<Level> levels) {
        if (editor.getPack() == null) {
            Pack pack = new Pack();

            pack.setLevel(levels.get(0), 0);

            if (levels.size() > 1) {
                pack.addAll(levels.subList(1, levels.size()));
            }

            editor.setPack(pack);
        } else {
            Pack pack = editor.getPack();

            pack.addAll(levels);
        }
    }

    private static class Filter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return !Utils.getExtension(f).equals("8xv");
        }

        @Override
        public String getDescription() {
            return "All except 8xv";
        }
    }
}