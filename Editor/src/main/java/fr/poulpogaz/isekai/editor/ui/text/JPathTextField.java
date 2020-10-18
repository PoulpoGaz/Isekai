package fr.poulpogaz.isekai.editor.ui.text;

import com.formdev.flatlaf.ui.FlatUIUtils;
import fr.poulpogaz.isekai.editor.ui.ButtonFactory;
import fr.poulpogaz.isekai.editor.utils.icons.AbstractIcon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class JPathTextField extends JIsekaiTextField {

    public static final String DIRECTORY_CHANGE = "Directory change";
    public static final String OK_COLOR_CHANGE = "Ok color change";
    public static final String ERROR_COLOR_CHANGE = "Error color change";

    private LockableDocumentListener listener;

    private File directory;
    private Color okForeground;
    private Color errorForeground;

    public JPathTextField() {
        initBrowseButton();
        initListener();

        okForeground = getForeground();
        errorForeground = UIManager.getColor("PathTextField.errorColor");

        directory = FileSystemView.getFileSystemView().getHomeDirectory();
        setText(directory.getAbsolutePath());
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
                update();
            }

            @Override
            public void removeUpdateImpl(DocumentEvent e) {
                update();
            }
        };

        getDocument().addDocumentListener(listener);
    }

    protected void openDialog(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(directory);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int value = chooser.showDialog(SwingUtilities.getWindowAncestor(this), "Choose");

        if (value == JFileChooser.APPROVE_OPTION) {
            setDirectory(chooser.getSelectedFile());
        }
    }

    protected void update() {
        String text = getText();

        File file = new File(text);

        if (file.isDirectory()) {
            setForeground(okForeground);

            if (!this.directory.getAbsolutePath().equals(file.getAbsolutePath())) {
                lock(); // do not fire a document listener
                setDirectory(file);
            }
        } else {
            setForeground(errorForeground);
        }
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        if (!this.directory.getAbsolutePath().equals(directory.getAbsolutePath()) && directory.isDirectory()) {

            File old = this.directory;
            this.directory = directory;

            listener.lock(); // Do not fire update function
            setText(directory.getAbsolutePath());

            firePropertyChange(DIRECTORY_CHANGE, old, directory);
        }
    }

    public Color getOkForeground() {
        return okForeground;
    }

    public void setOkForeground(Color okForeground) {
        if (this.okForeground != okForeground && okForeground != null) {
            Color old = this.okForeground;

            this.okForeground = okForeground;
            update();

            firePropertyChange(OK_COLOR_CHANGE, old, okForeground);
        }
    }

    public Color getErrorForeground() {
        return errorForeground;
    }

    public void setErrorForeground(Color errorForeground) {
        if (this.errorForeground != errorForeground && errorForeground != null) {
            Color old = this.errorForeground;

            this.errorForeground = errorForeground;
            update();

            firePropertyChange(ERROR_COLOR_CHANGE, old, errorForeground);
        }
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