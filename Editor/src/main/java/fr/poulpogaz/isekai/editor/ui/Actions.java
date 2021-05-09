package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.*;
import fr.poulpogaz.isekai.editor.ui.importer.LevelImporterDialog;
import fr.poulpogaz.isekai.editor.ui.theme.ThemePanel;
import fr.poulpogaz.isekai.editor.utils.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static fr.poulpogaz.isekai.editor.ui.Dialogs.showError;
import static fr.poulpogaz.isekai.editor.ui.Dialogs.showFileChooser;
import static java.awt.event.KeyEvent.*;

public class Actions {

    private static final FileNameExtensionFilter _8XV = new FileNameExtensionFilter("8xv", "8xv");

    public static Action newAction(String name, Icon icon, KeyStroke keyStroke, ActionListener listener) {
        Action action = new AbstractAction(name, icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.actionPerformed(e);
            }
        };

        action.putValue(Action.ACCELERATOR_KEY, keyStroke);

        return action;
    }

    public static KeyStroke ctrlKey(int c) {
        return KeyStroke.getKeyStroke(c, InputEvent.CTRL_DOWN_MASK);
    }

    public static KeyStroke shiftKey(int c) {
        return KeyStroke.getKeyStroke(c, InputEvent.SHIFT_DOWN_MASK);
    }

    public static KeyStroke ctrlShiftKey(int c) {
        return KeyStroke.getKeyStroke(c, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
    }

    public static final Action NEW = newAction("New", Icons.get("icons/new.svg"), ctrlKey(VK_N), e -> {
        IsekaiEditor editor = IsekaiEditor.getInstance();
        Pack pack = editor.getPack();

        if (pack == null) {
            editor.setPack(new Pack());
        } else {
            savePackDialog(editor, () -> editor.setPack(new Pack()));
        }
    });

    public static final Action OPEN = newAction("Open", new FlatTreeOpenIcon(), ctrlKey(VK_O), e -> {
        open(IsekaiEditor.getInstance());
    });

    public static final Action SAVE = newAction("Save", Icons.get("icons/save.svg"), ctrlKey(VK_S), e -> {
        IsekaiEditor editor = IsekaiEditor.getInstance();
        Pack pack = editor.getPack();

        if (pack != null) {
            save(editor, pack, false, null);
        }
    });

    public static final Action SAVE_AS = newAction("Save as", null, ctrlShiftKey(VK_S), e -> {
        IsekaiEditor editor = IsekaiEditor.getInstance();
        Pack pack = editor.getPack();

        if (pack != null) {
            save(editor, pack, true, null);
        }
    });

    public static final Action IMPORT_SOKO_INFO = newAction("From sokoban.info", null, ctrlKey(VK_I), e -> {
        List<Level> levels = LevelImporterDialog.showDialog();

        if (levels != null && levels.size() > 0) {
            addLevelsToPack(IsekaiEditor.getInstance(), levels);
        }
    });

    public static final Action IMPORT_LOCAL = newAction("From your computer", null, shiftKey(VK_I), e -> {
        IsekaiEditor editor = IsekaiEditor.getInstance();

        Path path = showFileChooser(editor, JFileChooser.FILES_ONLY, new Filter(), true);

        if (path != null) {
            List<Level> levels = SOKReader.read(path);

            if (levels != null && levels.size() > 0) {
                addLevelsToPack(IsekaiEditor.getInstance(), levels);
            } else {
                JOptionPane.showMessageDialog(editor, "Can't read levels from this file: " + path, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    public static final Action QUIT = newAction("Quit", null, ctrlKey(VK_Q), e -> {
        IsekaiEditor editor = IsekaiEditor.getInstance();
        Pack pack = editor.getPack();

        if (pack != null) {
            savePackDialog(editor, editor::dispose);
        } else {
            editor.dispose();
        }
    });

    public static final Action THEME = newAction("Theme", Icons.get("icons/theme.svg"), ctrlKey(VK_T), e -> {
        ThemePanel.showDialog();
    });

    public static void open(IsekaiEditor editor) {
        Path result = showFileChooser(editor, JFileChooser.FILES_ONLY, _8XV);

        if (result != null) {
            try {
                Pack pack = TIPackIO.deserialize(result);

                if (editor.getPack() == null) {
                    editor.setPack(pack);
                } else {
                    savePackDialog(editor, () -> editor.setPack(pack));
                }
            } catch (TIPackIOException e) {
                showError(editor, "Can't open file.", e);
            }
        }
    }

    public static void save(IsekaiEditor editor, Pack pack, boolean saveAs, Runnable callback) {
        if (isPackValid(editor, pack)) {
            Path out = pack.getSaveLocation();

            if (out == null || saveAs) {
                out = showFileChooser(editor, JFileChooser.DIRECTORIES_ONLY, null);

                if (out == null) {
                    return;
                }
            }

            try {
                TIPackIO.serialize(pack, out);

                pack.setSaveLocation(out);
            } catch (IOException e) {
                e.printStackTrace();
                showError(editor, "Failed to save the pack.", e);

                return;
            }

            if (callback != null) {
                callback.run();
            }
        }
    }

    private static void savePackDialog(IsekaiEditor editor, Runnable callback) {
        int result = JOptionPane.showConfirmDialog(editor, "Save current pack?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);

        if (result == JFileChooser.APPROVE_OPTION) {
            save(editor, editor.getPack(), false, callback);
        } else if (result == JFileChooser.CANCEL_OPTION && callback != null) { // = no
            callback.run();
        }
    }

    private static boolean isPackValid(IsekaiEditor editor, Pack pack) {
        if (pack.getName() == null || pack.getName().isEmpty()) {
            showError(editor, "The pack doesn't have a name");
            return false;
        }

        if (pack.getAuthor() == null || pack.getAuthor().isEmpty()) {
            showError(editor, "The pack doesn't have an author");
            return false;
        }

        if (pack.getNumberOfLevels() >= 65_536) {
            showError(editor, "Too many levels!");
            return false;
        }

        if (pack.getVersion() == null || pack.getVersion().isEmpty()) {
            pack.setVersion("1.0");
        }

        return true;
    }

    private static void addLevelsToPack(IsekaiEditor editor, List<Level> levels) {
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