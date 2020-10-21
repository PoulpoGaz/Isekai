package fr.poulpogaz.isekai.editor.ui.text;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatUIUtils;
import fr.poulpogaz.isekai.editor.ui.ButtonFactory;
import fr.poulpogaz.isekai.editor.utils.icons.AbstractIcon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.JFileChooser.FILES_ONLY;

public class JPathTextField extends JIsekaiTextField {

    public static final String OK_COLOR_CHANGED = "Ok color changed";
    public static final String ERROR_COLOR_CHANGED = "Error color changed";

    private final JFileChooser chooser;
    private LockableDocumentListener listener;

    private Color okForeground;
    private Color errorForeground;

    public JPathTextField() {
        initBrowseButton();
        initListener();

        putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Unknown");

        okForeground = getForeground();
        errorForeground = UIManager.getColor("PathTextField.errorColor");

        chooser = new JFileChooser();
        chooser.setSelectedFile(FileSystemView.getFileSystemView().getHomeDirectory());
        setText(chooser.getSelectedFile().getAbsolutePath());
    }

    protected void initBrowseButton() {
        Icon icon = new FlatFileOpenIcon();

        JButton button = ButtonFactory.createIconButton(icon);
        button.addActionListener(this::openDialog);

        setTrailingComponent(button);
    }

    protected void initListener() {
        listener = new LockableDocumentListener() {
            @Override
            public void insertUpdateImpl(DocumentEvent e) {
                setFontColor();
            }

            @Override
            public void removeUpdateImpl(DocumentEvent e) {
                setFontColor();
            }
        };

        getDocument().addDocumentListener(listener);
    }

    protected void openDialog(ActionEvent e) {
        String text = getText();
        File file = new File(text);

        if (file.exists() && file.isDirectory()) {
            chooser.setCurrentDirectory(file);
        }

        int value = chooser.showDialog(SwingUtilities.getWindowAncestor(this), "Choose");

        if (value == JFileChooser.APPROVE_OPTION) {
            setPath(chooser.getSelectedFile());
        }
    }

    protected void setFontColor() {
        String text = getText();

        File file = new File(text);

        if (accept(file)) {
            setForeground(okForeground);

            File current = chooser.getSelectedFile().getAbsoluteFile();

            if (!current.equals(file.getAbsoluteFile())) {
                lock(); // do not rewrite text
                setPath(file);
            }
        } else {
            setForeground(errorForeground);
        }
    }

    public boolean accept(File file) {
        if (!file.exists()) {
            return false;
        }

        boolean result = switch (getFileSelectionMode()) {
            case DIRECTORIES_ONLY -> file.isDirectory();
            case FILES_ONLY -> file.isFile();
            default -> true;
        };

        if (result && file.isFile()) {
            return chooser.accept(file);
        }

        return result;
    }

    public File getPath() {
        return chooser.getSelectedFile();
    }

    public void setPath(File path) {
        if (path != null) {
            chooser.setSelectedFile(path);
            listener.lock();
            setText(path.getAbsolutePath());
            setFontColor();
        }
    }

    public Color getOkForeground() {
        return okForeground;
    }

    public void setOkForeground(Color okForeground) {
        if (this.okForeground != okForeground && okForeground != null) {
            Color old = this.okForeground;

            this.okForeground = okForeground;
            setFontColor();

            firePropertyChange(OK_COLOR_CHANGED, old, okForeground);
        }
    }

    public Color getErrorForeground() {
        return errorForeground;
    }

    public void setErrorForeground(Color errorForeground) {
        if (this.errorForeground != errorForeground && errorForeground != null) {
            Color old = this.errorForeground;

            this.errorForeground = errorForeground;
            setFontColor();

            firePropertyChange(ERROR_COLOR_CHANGED, old, errorForeground);
        }
    }

    public int getFileSelectionMode() {
        return chooser.getFileSelectionMode();
    }

    public void setFileSelectionMode(int mode) {
        chooser.setFileSelectionMode(mode);
    }

    public void setFileFilter(FileFilter filter) {
        chooser.setFileFilter(filter);
    }

    public FileFilter getFileFilter() {
        return chooser.getFileFilter();
    }

    public void addChoosableFileFilter(FileFilter filter) {
        chooser.addChoosableFileFilter(filter);
    }

    public void removeChoosableFileFilter(FileFilter filter) {
        chooser.removeChoosableFileFilter(filter);
    }

    public JFileChooser getChooser() {
        return chooser;
    }

    /**
     * @see com.formdev.flatlaf.icons.FlatTreeOpenIcon
     */
    private static class FlatFileOpenIcon extends AbstractIcon {

        FlatFileOpenIcon() {
            super(16, 16);
        }

        @Override
        protected void paintIcon(Component c, Graphics2D g) {
            g.fill(FlatUIUtils.createPath(1,2, 6,2, 8,4, 14,4, 14,6, 3.5,6, 1,11));
            g.fill(FlatUIUtils.createPath(4,7, 16,7, 13,13, 1,13));
        }
    }
}