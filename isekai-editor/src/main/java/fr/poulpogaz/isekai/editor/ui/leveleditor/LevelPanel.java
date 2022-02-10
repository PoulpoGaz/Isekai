package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.LevelModel;
import fr.poulpogaz.isekai.editor.pack.LevelsOrganisationListener;
import fr.poulpogaz.isekai.editor.pack.PackModel;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.GraphicsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.beans.PropertyChangeEvent;
import java.util.Objects;

public class LevelPanel extends JPanel implements LevelsOrganisationListener {

    private static final Logger LOGGER = LogManager.getLogger(LevelPanel.class);

    private final LevelEditorModel editor;
    private final PackModel pack;

    private JComboBox<Integer> levelsComboBox;

    public LevelPanel(PackModel pack, LevelEditorModel editor) {
        this.pack = Objects.requireNonNull(pack);
        pack.addLevelsOrganisationListener(this);

        this.editor = Objects.requireNonNull(editor);
        editor.addPropertyChangeListener(LevelEditorModel.SELECTED_MAP_PROPERTY, this::switchLevel);

        setLayout(new VerticalLayout(6));
        setBorder(BorderFactory.createTitledBorder("Level order"));

        initComponents();
    }

    private void initComponents() {
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        JButton insertLevel = GraphicsUtils.createButton("icons/add.svg", "Insert level", (e) -> {
            insertLevel(new LevelModel(), levelsComboBox.getSelectedIndex() + 1, true);
        });
        JButton deleteLevel = GraphicsUtils.createButton("icons/delete.svg", "Delete level", (e) -> {
            deleteLevel(levelsComboBox.getSelectedIndex(), true);
        });

        JButton moveUp = GraphicsUtils.createButton("icons/move_up.svg", "Move up", (e) -> {
            moveUp(editor.getSelectedLevelIndex(), true);
        });
        JButton moveDown = GraphicsUtils.createButton("icons/move_down.svg", "Move down", (e) -> {
            moveDown(editor.getSelectedLevelIndex(), true);
        });

        levelsComboBox = new JComboBox<>();
        levelsComboBox.addItemListener((e) -> {
            int index = levelsComboBox.getSelectedIndex();

            editor.setSelectedLevel(index);
        });

        for (int i = 0; i < pack.getNumberOfLevels(); i++) {
            levelsComboBox.addItem(i + 1);
        }

        add(GraphicsUtils.split(insertLevel, deleteLevel), constraint);
        add(GraphicsUtils.split(moveUp, moveDown), constraint);
        add(levelsComboBox, constraint);
    }

    private void insertLevel(LevelModel level, int index, boolean addEdit) {
        pack.addLevel(level, index);
        editor.setSelectedLevel(level);

        if (addEdit) {
            IsekaiEditor.getInstance().addEdit(new InsertEdit(index));
        }
    }

    private void deleteLevel(int index, boolean addEdit) {
        if (pack.getNumberOfLevels() > 1 && index < pack.getNumberOfLevels()) {
            LevelModel old = pack.removeLevel(index);
            editor.setSelectedLevel(Math.max(index - 1, 0));

            if (addEdit) {
                IsekaiEditor.getInstance().addEdit(new DeleteEdit(index, old));
            }
        }
    }

    private void moveDown(int current, boolean addEdit) {
        int index = current + 1;

        if (index < pack.getNumberOfLevels()) {
            pack.swapLevels(current, index);

            if (addEdit) {
                IsekaiEditor.getInstance().addEdit(new MoveDownEdit(index));
            }
        }
    }

    private void moveUp(int current, boolean addEdit) {
        int index = current - 1;

        if (index >= 0) {
            pack.swapLevels(current, index);

            if (addEdit) {
                IsekaiEditor.getInstance().addEdit(new MoveUpEdit(index));
            }
        }
    }
    
    @Override
    public void levelInserted(int start, int end) {
        for (int i = levelsComboBox.getItemCount() + 1; i <= pack.getNumberOfLevels(); i++) {
            levelsComboBox.addItem(i);
        }
    }

    @Override
    public void levelRemoved(int index) {
        levelsComboBox.removeItemAt(pack.getNumberOfLevels());
    }

    @Override
    public void levelChanged(int index) {

    }

    @Override
    public void levelsSwapped(int index1, int index2) {
        levelsComboBox.setSelectedIndex(editor.getSelectedLevelIndex());
    }

    @Override
    public void organisationChanged() {
        int packSize = pack.getNumberOfLevels();
        int comboSize = levelsComboBox.getItemCount();

        if (packSize > comboSize) {
            for (int i = comboSize; i < packSize; i++) {
                levelsComboBox.addItem(i);
            }

        } else {
            editor.setSelectedLevel(packSize - 1);

            for (int i = comboSize - 1; i >= pack.getNumberOfLevels(); i--) {
                levelsComboBox.removeItemAt(i);
            }
        }

        levelsComboBox.setSelectedIndex(0);
    }

    private void switchLevel(PropertyChangeEvent evt) {
        levelsComboBox.setSelectedIndex(editor.getSelectedLevelIndex());
    }

    private class InsertEdit extends AbstractUndoableEdit {

        private final int index;

        public InsertEdit(int index) {
            this.index = index;
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();

            LOGGER.info("Redo InsertEdit. Creating level at index {}", index);
            insertLevel(new LevelModel(), index, false);
        }

        @Override
        public void undo() throws CannotUndoException {
            super.undo();

            LOGGER.info("Undo InsertEdit. Removing level at index {}", index);
            deleteLevel(index, false);
        }
    }

    private class DeleteEdit extends AbstractUndoableEdit {

        private final int index;
        private final LevelModel old;

        public DeleteEdit(int index, LevelModel old) {
            this.index = index;
            this.old = old;
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();

            LOGGER.info("Redo DeleteEdit. Removing level at index {}", index);
            deleteLevel(index, false);
        }

        @Override
        public void undo() throws CannotUndoException {
            super.undo();

            LOGGER.info("Undo DeleteEdit. Creating level at index {}", index);
            insertLevel(old, index, false);
        }
    }

    private class MoveUpEdit extends AbstractUndoableEdit {

        private final int index;

        public MoveUpEdit(int index) {
            this.index = index;
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();

            LOGGER.info("Redo MoveUpEdit. Move up level at index {}", index);
            moveUp(index, false);
        }

        @Override
        public void undo() throws CannotUndoException {
            super.undo();

            LOGGER.info("Undo MoveUpEdit. Move down level at index {}", index);
            moveDown(index, false);
        }
    }

    private class MoveDownEdit extends AbstractUndoableEdit {

        private final int index;

        public MoveDownEdit(int index) {
            this.index = index;
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();

            LOGGER.info("Redo MoveDownEdit. Move up level at index {}", index);
            moveDown(index, false);
        }

        @Override
        public void undo() throws CannotUndoException {
            super.undo();

            LOGGER.info("Undo MoveDownEdit. Move down level at index {}", index);
            moveUp(index, false);
        }
    }
}