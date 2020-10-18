package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.layout.SplitLayout;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelPanel extends JPanel {

    private final Pack pack = IsekaiEditor.getInstance().getPack();

    private JComboBox<Integer> levelsComboBox;

    public LevelPanel() {
        setLayout(new VerticalLayout(6));
        setBorder(BorderFactory.createTitledBorder("Level order"));

        initComponents();
    }

    private void initComponents() {
        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        JButton insertLevel = createButton("/icons/add.svg", "Insert level", this::insertLevel);
        JButton deleteLevel = createButton("/icons/delete.svg", "Delete level", this::deleteLevel);

        JButton moveUp = createButton("/icons/move_up.svg", "Move up", this::moveUp);
        JButton moveDown = createButton("/icons/move_down.svg", "Move down", this::moveDown);

        levelsComboBox = new JComboBox<>();
        levelsComboBox.addItemListener((e) -> {
            int index = levelsComboBox.getSelectedIndex();

            fireSelectedLevelChange(pack.getLevel(index), index);
        });

        for (int i = 0; i < pack.getLevels().size(); i++) {
            levelsComboBox.addItem(i + 1);
        }

        add(split(insertLevel, deleteLevel), constraint);
        add(split(moveUp, moveDown), constraint);
        add(levelsComboBox, constraint);
    }

    private JButton createButton(String resource, String text, ActionListener listener) {
        JButton button = new JButton();
        button.addActionListener(listener);
        button.setText(text);
        button.setIcon(IconLoader.loadSVGIcon(resource));

        return button;
    }

    private JPanel split(JButton left, JButton right) {
        JPanel panel = new JPanel();
        panel.setLayout(new SplitLayout(1, 0.5f));

        panel.add(left);
        panel.add(right);

        return panel;
    }

    private void insertLevel(ActionEvent e) {
        Level level = new Level();

        int index = levelsComboBox.getSelectedIndex() + 1;

        pack.addLevel(level, index);

        levelsComboBox.addItem(levelsComboBox.getItemCount() + 1);
        levelsComboBox.setSelectedIndex(index);

        fireLevelInserted(level, index);
    }

    private void deleteLevel(ActionEvent e) {
        int size = levelsComboBox.getItemCount();

        if (size > 1) {
            int index = levelsComboBox.getSelectedIndex();

            Level removedLevel = pack.removeLevel(index);
            levelsComboBox.removeItemAt(size - 1);

            fireLevelRemoved(removedLevel, index);
        }
    }

    private void moveUp(ActionEvent e) {
        swap(levelsComboBox.getSelectedIndex(), -1);
    }

    private void moveDown(ActionEvent e) {
        swap(levelsComboBox.getSelectedIndex(), 1);
    }

    private void swap(int index, int direction) {
        int to = index + direction;

        if (to < 0 || to > levelsComboBox.getItemCount() - 1) {
            return;
        }

        // swap
        Level current = pack.getLevel(index);
        Level down = pack.getLevel(to);

        pack.setLevel(down, index);
        pack.setLevel(current, to);

        levelsComboBox.setSelectedIndex(to);

        fireLevelMoved(index, to);
    }

    public void addLevelListener(LevelListener listener) {
        listenerList.add(LevelListener.class, listener);
    }

    public void removeLevelListener(LevelListener listener) {
        listenerList.remove(LevelListener.class, listener);
    }

    private void fireLevelInserted(Level insertedLevel, int index) {
        for (LevelListener listener : listenerList.getListeners(LevelListener.class)) {
            listener.levelInserted(insertedLevel, index);
        }
    }

    private void fireLevelRemoved(Level deletedLevel, int index) {
        for (LevelListener listener : listenerList.getListeners(LevelListener.class)) {
            listener.levelDeleted(deletedLevel, index);
        }
    }

    private void fireLevelMoved(int from, int to) {
        for (LevelListener listener : listenerList.getListeners(LevelListener.class)) {
            listener.levelMoved(from, to);
        }
    }

    private void fireSelectedLevelChange(Level newLevel, int index) {
        for (LevelListener listener : listenerList.getListeners(LevelListener.class)) {
            listener.selectedLevelChanged(newLevel, index);
        }
    }
}