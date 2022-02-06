package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.List;

public class ImportEdit extends AbstractUndoableEdit {

    private static final Logger LOGGER = LogManager.getLogger(ImportEdit.class);

    private final Pack pack;
    private final List<Level> levels;

    private boolean showMessage = true;

    public ImportEdit(Pack pack, List<Level> levels) {
        this.pack = pack;
        this.levels = levels;
    }

    @Override
    public void undo() throws CannotUndoException {
        if (showMessage) {
            IsekaiEditor editor = IsekaiEditor.getInstance();

            int result = JOptionPane.showConfirmDialog(editor, "Do you want to undo the import of %d levels?".formatted(levels.size()), "Undo import", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.NO_OPTION) {
                return;
            }

            showMessage = false;
        }

        super.undo();

        LOGGER.info("Undo ImportEdit. {} levels removed", levels.size());
        pack.removeAll(levels);
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();

        LOGGER.info("Redo ImportEdit. {} levels added", levels.size());
        pack.addAll(levels);
    }
}