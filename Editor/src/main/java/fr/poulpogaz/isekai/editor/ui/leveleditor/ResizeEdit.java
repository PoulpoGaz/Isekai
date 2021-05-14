package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class ResizeEdit extends AbstractUndoableEdit {

    private static final Logger LOGGER = LogManager.getLogger(ResizeEdit.class);

    private final LevelEditorModel model;
    private final Level level;
    private final int oldWidth;
    private final int oldHeight;
    private final int width;
    private final int height;

    public ResizeEdit(Level level, int oldWidth, int oldHeight, int width, int height) {
        this.model = IsekaiEditor.getInstance().getEditorModel();
        this.level = level;
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.width = width;
        this.height = height;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();

        LOGGER.info("Undo ResizeEdit. Resizing level from ({};{}) to ({};{}]", width, height, oldWidth, oldHeight);

        if (model.getSelectedLevel() != level) {
            model.setSelectedLevel(level);
        }

        level.resize(oldWidth, oldHeight);
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();

        LOGGER.info("Redo ResizeEdit. Resizing level from ({};{}) to ({};{}]", oldWidth, oldHeight, width, height);

        if (model.getSelectedLevel() != level) {
            model.setSelectedLevel(level);
        }

        level.resize(width, height);
    }
}