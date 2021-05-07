package fr.poulpogaz.isekai.editor.ui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class Dialogs {

    public static Path showFileChooser(Frame parent, int mode, FileFilter fileFilter) {
        return showFileChooser(parent, mode, fileFilter, false);
    }

    public static Path showFileChooser(Frame parent, int mode, FileFilter fileFilter, boolean allFilter) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(mode);
        chooser.setFileFilter(fileFilter);
        chooser.setAcceptAllFileFilterUsed(allFilter);

        int result = chooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            return file.toPath();
        }

        return null;
    }

    public static void showError(Frame parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}