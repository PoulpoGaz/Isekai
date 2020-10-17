package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import com.sun.jdi.InternalException;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackBuilder;
import fr.poulpogaz.isekai.editor.pack.PackIO;
import fr.poulpogaz.isekai.editor.pack.TIPackIO;
import fr.poulpogaz.isekai.editor.utils.Utils;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Path;

public class EditorMenuBar extends JMenuBar {

    private static final FileFilter FILTER = new FileNameExtensionFilter("SKB files", "skb");
    private final JFileChooser chooser = new JFileChooser();

    public EditorMenuBar() {
        initMenuBar();
    }

    private void initMenuBar() {
        JMenu file = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.setIcon(IconLoader.loadSVGIcon("/icons/new.svg"));
        newItem.addActionListener((e) -> {
            Pack pack = PackBuilder.loadDefaultPack();

            if (pack == null) {
                throw new InternalException();
            }

            IsekaiEditor.getInstance().setPack(pack);
        });

        JMenuItem open = new JMenuItem("Open");
        open.setIcon(new FlatTreeOpenIcon());
        open.addActionListener((e) -> open());

        JMenuItem save = new JMenuItem("Save");
        save.setIcon(IconLoader.loadSVGIcon("/icons/save.svg"));
        save.addActionListener((e) -> save());

        JMenuItem saveAs = new JMenuItem("Save as");

        JMenuItem importFile = new JMenuItem("Import");
        JMenuItem exportFile = new JMenuItem("Export");
        exportFile.addActionListener((e) -> export());

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

    private void initFileChooserForSKB() {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(FILTER);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setSelectedFile(Utils.getJARLocation());
    }

    private void open() {
        initFileChooserForSKB();

        int result = chooser.showOpenDialog(IsekaiEditor.getInstance());

        if (result == JFileChooser.APPROVE_OPTION) {
            Pack pack = PackIO.deserialize(chooser.getSelectedFile().toPath());

            if (pack != null) {
                IsekaiEditor.getInstance().setPack(pack);
            } else {
                JOptionPane.showMessageDialog(IsekaiEditor.getInstance(), "Corrupted file", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void save() {
        initFileChooserForSKB();

        int result = chooser.showSaveDialog(IsekaiEditor.getInstance());

        if (result == JFileChooser.APPROVE_OPTION) {
            Path selectedFile = chooser.getSelectedFile().toPath();
            String fileName = selectedFile.getFileName().toString();

            if (Utils.getExtension(fileName).equals(fileName)) {
                selectedFile = selectedFile.getParent().resolve(fileName + ".skb");
            }

            if (!PackIO.serialize(IsekaiEditor.getPack(), selectedFile)) {
                JOptionPane.showMessageDialog(IsekaiEditor.getInstance(), "Failed to save the pack.\nSorry", "Error", JOptionPane.ERROR_MESSAGE);
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
        
        int result = chooser.showSaveDialog(IsekaiEditor.getInstance());

        if (result == JFileChooser.APPROVE_OPTION) {
            Path directory = chooser.getSelectedFile().toPath();

            /*if (!TIPackIO.serialize(IsekaiEditor.getPack(), directory)) {
                JOptionPane.showMessageDialog(IsekaiEditor.getInstance(), "Failed to save the pack.\nSorry", "Error", JOptionPane.ERROR_MESSAGE);
            }*/
        }
    }
}