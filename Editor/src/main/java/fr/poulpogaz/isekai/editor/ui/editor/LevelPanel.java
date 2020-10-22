package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.controller.LevelsOrganisationListener;
import fr.poulpogaz.isekai.editor.controller.PackController;
import fr.poulpogaz.isekai.editor.ui.layout.SplitLayout;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LevelPanel extends JPanel implements LevelsOrganisationListener, PropertyChangeListener {

    private final PackController controller;

    private JComboBox<Integer> levelsComboBox;

    public LevelPanel(PackController controller) {
        this.controller = controller;
        controller.addLevelsOrganisationListener(this);
        controller.addSelectedLevelListener(this);

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

            controller.setSelectedLevel(index);
        });

        for (int i = 0; i < controller.getNumberOfLevels(); i++) {
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

        controller.addLevel(index);
    }

    private void deleteLevel(ActionEvent event) {
        if (controller.getNumberOfLevels() > 1) {
            int index = levelsComboBox.getSelectedIndex();

            controller.removeLevel(index);
        }
    }

    private void moveDown(ActionEvent event) {
        int index = levelsComboBox.getSelectedIndex() + 1;

        if (index < controller.getNumberOfLevels() + 1) {
            controller.swapLevels(index);
        }
    }

    private void moveUp(ActionEvent event) {
        int index = levelsComboBox.getSelectedIndex() - 1;

        if (index >= 0) {
            controller.swapLevels(index);
        }
    }
    
    @Override
    public void levelInserted(int index) {
        levelsComboBox.addItem(controller.getNumberOfLevels());
        levelsComboBox.setSelectedIndex(index);
    }

    @Override
    public void levelRemoved(int index) {
        levelsComboBox.removeItemAt(controller.getNumberOfLevels());

        if (index >= controller.getNumberOfLevels()) {
            index--;
        }

        levelsComboBox.setSelectedIndex(index);
    }

    @Override
    public void levelChanged(int index) {

    }

    @Override
    public void levelsSwapped(int index1, int index2) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PackController.SELECTED_LEVEL_PROPERTY)) {
            levelsComboBox.setSelectedIndex((int) evt.getNewValue());
        }
    }
}