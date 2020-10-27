package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.LevelsOrganisationListener;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.layout.SplitLayout;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Objects;

public class LevelPanel extends JPanel implements LevelsOrganisationListener {

    private final Pack pack;
    private final MapEditorModel editor;

    private JComboBox<Integer> levelsComboBox;

    public LevelPanel(Pack pack, MapEditorModel editor) {
        this.pack = Objects.requireNonNull(pack);
        pack.addLevelsOrganisationListener(this);

        this.editor = Objects.requireNonNull(editor);
        editor.addPropertyChangeListener(MapEditorModel.SELECTED_LEVEL_PROPERTY, this::switchLevel);

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

            editor.setSelectedLevel(pack, index);
        });

        for (int i = 0; i < pack.getNumberOfLevels(); i++) {
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
        int index = levelsComboBox.getSelectedIndex() + 1;

        Level level = new Level();

        pack.addLevel(level, index);
        editor.setSelectedLevel(level);
    }

    private void deleteLevel(ActionEvent event) {
        if (pack.getNumberOfLevels() > 1) {
            int index = levelsComboBox.getSelectedIndex();

            pack.removeLevel(index);
            editor.setSelectedLevel(pack, Math.max(index - 1, 0));
        }
    }

    private void moveDown(ActionEvent event) {
        int curr = editor.getSelectedLevelIndex();
        int index = curr + 1;

        if (index < pack.getNumberOfLevels() + 1) {
            pack.swapLevels(curr, index);
        }
    }

    private void moveUp(ActionEvent event) {
        int curr = editor.getSelectedLevelIndex();
        int index = curr - 1;

        if (index >= 0) {
            pack.swapLevels(curr, index);
        }
    }
    
    @Override
    public void levelInserted(int index) {
        levelsComboBox.addItem(pack.getNumberOfLevels());
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
    public void newLevels() {
        int packSize = pack.getNumberOfLevels();
        int comboSize = levelsComboBox.getItemCount();

        if (packSize > comboSize) {
            for (int i = comboSize; i < packSize; i++) {
                levelsComboBox.addItem(i);
            }

        } else {
            for (int i = comboSize - 1; i >= pack.getNumberOfLevels(); i--) {
                levelsComboBox.removeItemAt(i);
            }
        }

        levelsComboBox.setSelectedIndex(0);
    }

    private void switchLevel(PropertyChangeEvent evt) {
        levelsComboBox.setSelectedIndex(editor.getSelectedLevelIndex());
    }
}