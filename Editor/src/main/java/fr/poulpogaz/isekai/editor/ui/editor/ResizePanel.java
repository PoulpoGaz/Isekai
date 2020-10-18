package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.JLabeledComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import static fr.poulpogaz.isekai.editor.pack.Level.*;

public class ResizePanel extends JPanel implements LevelListener {

    private final Pack pack = IsekaiEditor.getInstance().getPack();

    private Level level;

    private JSpinner widthSpinner;
    private JSpinner heightSpinner;

    public ResizePanel() {
        level = pack.getLevel(0);

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
        int width = (int) widthSpinner.getValue();
        int height = (int) heightSpinner.getValue();

        level.resize(width, height);

        fireResizeListener(level, width, height);
    }

    private void fireResizeListener(Level level, int width, int height) {
        for (ResizeListener listener : listenerList.getListeners(ResizeListener.class)) {
            listener.levelResized(level, width, height);
        }
    }

    public void addResizeListener(ResizeListener listener) {
        listenerList.add(ResizeListener.class, listener);
    }

    public void removeResizeListener(ResizeListener listener) {
        listenerList.remove(ResizeListener.class, listener);
    }

    @Override
    public void levelInserted(Level insertedLevel, int index) {
        // does nothing because when the LevelPanel class adds a level, it changes the selected level
    }

    @Override
    public void levelDeleted(Level deletedLevel, int index) {
        int size = pack.getLevels().size() - 1;

        level = pack.getLevel(Math.min(index, size));
    }

    @Override
    public void levelMoved(int from, int to) {
        // does nothing because when the LevelPanel class moves a level, it changes the selected level
    }

    @Override
    public void selectedLevelChanged(Level newLevel, int index) {
        this.level = newLevel;

    }
}