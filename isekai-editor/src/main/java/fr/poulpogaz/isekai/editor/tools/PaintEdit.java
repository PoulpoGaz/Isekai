package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.LevelModel;
import fr.poulpogaz.isekai.editor.ui.leveleditor.LevelEditorModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PaintEdit extends AbstractUndoableEdit {

    private static final Logger LOGGER = LogManager.getLogger(PaintEdit.class);

    private final LevelEditorModel model;
    private final LevelModel level;
    private final Tile old;
    private final Tile newTile;
    private final List<Integer> affectedTiles;

    public PaintEdit(LevelModel level, Tile old, Tile newTile, List<Integer> affectedTiles) {
        this.model = IsekaiEditor.getInstance().getEditorModel();
        this.level = level;
        this.old = old;
        this.newTile = newTile;
        this.affectedTiles = Collections.unmodifiableList(affectedTiles);
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();

        LOGGER.info("Undo PaintEdit. Replacing {} by {}. Number of tiles affected: {}", newTile, old, affectedTiles.size());

        if (model.getSelectedLevel() != level) {
            model.setSelectedLevel(level);
        }

        level.setModifyingMap(true);
        affectedTiles.forEach((i) -> {
            level.set(old, i % level.getWidth(), i / level.getHeight());
        });
        level.setModifyingMap(false);
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();

        LOGGER.info("Redo PaintEdit. Replacing {} by {}. Number of tiles affected: {}", newTile, old, affectedTiles.size());

        if (model.getSelectedLevel() != level) {
            model.setSelectedLevel(level);
        }

        level.setModifyingMap(true);
        affectedTiles.forEach((i) -> {
            level.set(newTile, i % level.getWidth(), i / level.getHeight());
        });
        level.setModifyingMap(false);
    }

    public static class Builder {

        private final LevelModel level;
        private final Tile old;
        private final Tile newTile;
        private final List<Integer> affectedTiles;

        public Builder(LevelModel level, Tile old, Tile newTile) {
            this.level = level;
            this.old = old;
            this.newTile = newTile;
            affectedTiles = new ArrayList<>();
        }

        public void add(int pos) {
            affectedTiles.add(pos);
        }

        public void addAll(Collection<Integer> pos) {
            affectedTiles.addAll(pos);
        }

        public PaintEdit build() {
            if (affectedTiles.isEmpty()) {
                return null;
            }

            return new PaintEdit(level, old, newTile, affectedTiles);
        }
    }
}