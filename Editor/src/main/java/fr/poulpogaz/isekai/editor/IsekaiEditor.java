package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.ui.Actions;
import fr.poulpogaz.isekai.editor.ui.NoPackLoadedPanel;
import fr.poulpogaz.isekai.editor.ui.layout.HCOrientation;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import fr.poulpogaz.isekai.editor.ui.leveleditor.LevelEditor;
import fr.poulpogaz.isekai.editor.ui.leveleditor.LevelEditorModel;
import fr.poulpogaz.isekai.editor.ui.progressbar.JMemoryBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class IsekaiEditor extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(IsekaiEditor.class);

    public static final int UNKNOWN_DIMENSION = -1;

    private static final IsekaiEditor INSTANCE = new IsekaiEditor();

    private final UndoManager undoManager = new UndoManager();

    private Pack pack;

    private JPanel content;

    private final NoPackLoadedPanel noPackLoadedPanel = new NoPackLoadedPanel();
    private JPanel bottomPanel;
    private JMemoryBar memoryBar;
    private LevelEditor editor;

    private IsekaiEditor() {
        super("Isekai Editor");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();

        setIconImages(createImages());
        setJMenuBar(createMenuBar());

        int width = Prefs.getWidth();
        int height = Prefs.getHeight();
        int x = Prefs.getWindowX();
        int y = Prefs.getWindowY();

        if (width > 0 && height > 0 && x >= 0 && y >= 0) {
            setSize(new Dimension(width, height));
            setLocation(x, y);
        } else {
            pack();
            setLocationRelativeTo(null);
        }

        if (Prefs.isMaximized()) {
            setExtendedState(MAXIMIZED_BOTH);
        }

        addWindowListener(createWindowListener());
    }

    private void initComponents() {
        memoryBar = new JMemoryBar();

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new HorizontalLayout());

        HorizontalConstraint constraint = new HorizontalConstraint();
        constraint.orientation = HCOrientation.RIGHT;

        bottomPanel.add(memoryBar, constraint);

        content = new JPanel();
        content.setLayout(new BorderLayout());

        content.add(noPackLoadedPanel, BorderLayout.CENTER);
        content.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(content);
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.add(Actions.NEW);
        file.add(Actions.OPEN);
        file.add(Actions.SAVE);
        file.add(Actions.SAVE_AS);
        file.add(Actions.CLOSE_PROJECT);

        JMenu importMenu = new JMenu("Import");
        importMenu.add(Actions.IMPORT_SOKO_INFO);
        importMenu.add(Actions.IMPORT_LOCAL);

        file.add(importMenu);
        file.addSeparator();
        file.add(Actions.THEME);
        file.addSeparator();
        file.add(Actions.QUIT);

        JMenu edit = new JMenu("Edit");
        edit.add(Actions.UNDO).setEnabled(false);
        edit.add(Actions.REDO).setEnabled(false);
        edit.add(Actions.CLEAR_UNDO_HISTORY).setEnabled(false);

        JMenu help = new JMenu("Help");
        help.add(Actions.LICENSE);
        help.add(Actions.ABOUT);

        bar.add(file);
        bar.add(edit);
        bar.add(help);

        return bar;
    }

    private WindowListener createWindowListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (pack != null && pack.isModified()) {
                    Actions.savePackDialog(IsekaiEditor.this, () -> saveAndExit());
                } else {
                    saveAndExit();
                }
            }
        };
    }

    public void saveAndExit() {
        LOGGER.info("Saving...");
        Prefs.setMaximized(getExtendedState() == Frame.MAXIMIZED_BOTH);
        Prefs.setWidth(getWidth());
        Prefs.setHeight(getHeight());

        Point location = getLocationOnScreen();

        Prefs.setWindowX(location.x);
        Prefs.setWindowY(location.y);

        LOGGER.info("Disposing Isekai Editor");
        dispose();
    }

    public void setPack(Pack pack) {
        clearEdits();
        Actions.UNDO.setEnabled(false);
        Actions.REDO.setEnabled(false);
        Actions.CLEAR_UNDO_HISTORY.setEnabled(false);

        this.pack = pack;

        if (editor != null) {
            content.remove(editor);
            editor = null;
        }

        if (pack == null) {
            content.add(noPackLoadedPanel, BorderLayout.CENTER);
        } else {
            content.remove(noPackLoadedPanel);

            editor = new LevelEditor(pack);
            content.add(editor, BorderLayout.CENTER);
        }

        if (getExtendedState() != MAXIMIZED_BOTH) {
            resizeWindow();
        }

        revalidate();
        repaint();
    }

    private void resizeWindow() {
        Dimension pref = getPreferredSize();
        Dimension screen = getScreenDimension();

        if (pref.width > screen.width || pref.height > screen.height) {
            setExtendedState(MAXIMIZED_BOTH);
        } else {
            Dimension current = getSize();

            boolean width = current.width >= pref.width;
            boolean height = current.height >= pref.height;

            if (!width && !height) {
                Point point = getLocationOnScreen();

                int x = point.x + (current.width - pref.width) / 2;
                int y = point.y + (current.height - pref.height) / 2;

                pack();
                setLocation(x, y);
            } else if (width && !height) {
                Point point = getLocationOnScreen();

                int y = point.y + (current.height - pref.height) / 2;

                setSize(new Dimension(current.width, pref.height));
                setLocation(point.x, y);
            } else if (!width) {
                Point point = getLocationOnScreen();

                int x = point.x + (current.width - pref.width) / 2;

                setSize(new Dimension(pref.width, current.height));
                setLocation(x, point.y);
            }
        }
    }

    private Dimension getScreenDimension() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screen = toolkit.getScreenSize();
        Insets insets = toolkit.getScreenInsets(gc);

        screen.width = screen.width - insets.left - insets.right;
        screen.height = screen.height - insets.top - insets.bottom;

        return screen;
    }

    private List<Image> createImages() {
        BufferedImage player = PackSprites.getPlayer();

        ArrayList<Image> images = new ArrayList<>();
        images.add(player);

        for (int size = 16; size <= 48; size += 16) {
            images.add(player.getScaledInstance(size, size, Image.SCALE_FAST));
        }

        return images;
    }

    public void addEdit(UndoableEdit edit) {
        undoManager.addEdit(edit);

        Actions.UNDO.setEnabled(true);
        Actions.REDO.setEnabled(false);
        Actions.CLEAR_UNDO_HISTORY.setEnabled(true);
    }

    public void undo() {
        undoManager.undo();
    }

    public void redo() {
        undoManager.redo();
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }

    public void clearEdits() {
        undoManager.discardAllEdits();

        Actions.REDO.setEnabled(false);
        Actions.UNDO.setEnabled(false);
        Actions.CLEAR_UNDO_HISTORY.setEnabled(false);
    }

    public Pack getPack() {
        return pack;
    }

    public LevelEditor getLevelEditor() {
        return editor;
    }

    public LevelEditorModel getEditorModel() {
        return editor.getEditorModel();
    }

    public static IsekaiEditor getInstance() {
        return INSTANCE;
    }
}