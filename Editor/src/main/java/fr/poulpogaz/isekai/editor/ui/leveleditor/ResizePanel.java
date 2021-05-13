package fr.poulpogaz.isekai.editor.ui.leveleditor;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.LevelSizeListener;
import fr.poulpogaz.isekai.editor.ui.JLabeledComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeEvent;

import static fr.poulpogaz.isekai.editor.pack.Level.*;

public class ResizePanel extends JPanel {

    private final LevelEditorModel editor;
    private Level level;

    private final LevelSizeListener levelSizeListener;

    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private boolean canResize = true;

    public ResizePanel(LevelEditorModel editor) {
        this.editor = editor;

        levelSizeListener = this::levelResized;
        level = editor.getSelectedLevel();

        editor.addPropertyChangeListener(LevelEditorModel.SELECTED_MAP_PROPERTY, this::switchLevel);
        level.addSizeListener(levelSizeListener);

        setBorder(BorderFactory.createTitledBorder("Resize"));
        initComponents();
    }

    private void initComponents() {
        widthSpinner = new JSpinner(new SpinnerNumberModel(level.getWidth(), MINIMUM_MAP_WIDTH, MAXIMUM_MAP_WIDTH, 1));
        heightSpinner = new JSpinner(new SpinnerNumberModel(level.getHeight(), MINIMUM_MAP_HEIGHT, MAXIMUM_MAP_HEIGHT, 1));

        widthSpinner.addChangeListener(this::resize);
        heightSpinner.addChangeListener(this::resize);

        add(new JLabeledComponent("Width:", widthSpinner));
        add(new JLabeledComponent("Height:", heightSpinner));
    }

    private void resize(ChangeEvent e) {
        if (canResize) {
            int width = (int) widthSpinner.getValue();
            int height = (int) heightSpinner.getValue();

            level.resize(width, height);
        }
    }

    private void switchLevel(PropertyChangeEvent evt) {
        level.removeSizeListener(levelSizeListener);

        this.level = editor.getSelectedLevel();

        level.addSizeListener(levelSizeListener);

        canResize = false;
        widthSpinner.setValue(level.getWidth());
        heightSpinner.setValue(level.getHeight());
        canResize = true;
    }

    private void levelResized(Level level, int w, int h) {
        widthSpinner.setValue(w);
        heightSpinner.setValue(h);
    }
}