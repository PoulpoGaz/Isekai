package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import com.sun.jdi.InternalException;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.io.PackBuilder;
import fr.poulpogaz.isekai.editor.pack.io.PackIO;
import fr.poulpogaz.isekai.editor.pack.io.TIPackIO;
import fr.poulpogaz.isekai.editor.pack.io.TIPackIOException;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Path;

public class EditorMenuBar extends JMenuBar {

    private final IsekaiEditor editor;

    private static final FileFilter FILTER = new FileNameExtensionFilter("SKB files", "skb");

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
        newItem.addActionListener((e) -> editor.setPack(PackBuilder.emptyPack()));

        JMenuItem open = new JMenuItem("Open");
        open.setIcon(new FlatTreeOpenIcon());
        open.addActionListener((e) -> open());

        JMenuItem openTemplate = new JMenuItem("Open template");
        openTemplate.addActionListener((e) -> {
            Pack pack = PackBuilder.loadDefaultPack();

            if (pack == null) {
                throw new InternalException();
            }

            editor.setPack(pack);
        });

        JMenuItem save = new JMenuItem("Save");
        save.setIcon(IconLoader.loadSVGIcon("/icons/save.svg"));
        save.addActionListener((e) -> save());

        JMenuItem saveAs = new JMenuItem("Save as");

        JMenuItem importFile = new JMenuItem("Import");
        JMenuItem exportFile = new JMenuItem("Export");
        exportFile.addActionListener((e) -> export());

        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener((e) -> settingsDialog.setVisible(true));

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener((e) -> editor.dispose());

        file.add(newItem);
        file.add(open);
        file.add(openTemplate);
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

    private void initFileChooserForSKB() {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(FILTER);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setSelectedFile(Utils.getJARLocation());
    }

    private void open() {
        initFileChooserForSKB();

        int result = chooser.showOpenDialog(editor);

        if (result == JFileChooser.APPROVE_OPTION) {
            Pack pack = PackIO.deserialize(chooser.getSelectedFile().toPath());

            if (pack != null) {
                editor.setPack(pack);
            } else {
                JOptionPane.showMessageDialog(editor, "Corrupted file", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void save() {
        initFileChooserForSKB();

        int result = chooser.showSaveDialog(editor);

        if (result == JFileChooser.APPROVE_OPTION) {
            Path selectedFile = chooser.getSelectedFile().toPath();
            String fileName = selectedFile.getFileName().toString();

            if (Utils.getExtension(fileName).equals(fileName)) {
                selectedFile = selectedFile.getParent().resolve(fileName + ".skb");
            }

            if (!PackIO.serialize(editor.getPack(), selectedFile)) {
                JOptionPane.showMessageDialog(editor, "Failed to save the pack.\nSorry", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void initFileChooserForX8V() {
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.removeChoosableFileFilter(FILTER);
        chooser.setSelectedFile(Utils.getJARLocation());
    }

    private void export() {
        initFileChooserForX8V();
        
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