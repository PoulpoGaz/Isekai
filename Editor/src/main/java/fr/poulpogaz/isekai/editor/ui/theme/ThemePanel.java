package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.ui.Dialogs;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class ThemePanel extends JPanel {

    public static void showDialog() {
        IsekaiEditor parent = IsekaiEditor.getInstance();

        JDialog dialog = new JDialog(parent, "Themes", false);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        dialog.setContentPane(new ThemePanel());
        dialog.pack();

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private JPanel top;

    private JList<Theme> themes;
    private JScrollPane pane;

    public ThemePanel() {
        setLayout(new BorderLayout());

        ThemeManager.loadThemes();

        initComponents();
    }

    protected void initComponents() {
        // center
        themes = new JList<>(new DefaultListModel<>());
        themes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel<Theme> model = (DefaultListModel<Theme>) themes.getModel();

        for (Theme theme : ThemeManager.getCoreThemes()) {
            model.addElement(theme);
        }

        for (Theme theme : ThemeManager.getThemes()) {
            model.addElement(theme);
        }

        themes.addListSelectionListener(this::changeLaf);

        pane = new JScrollPane();
        pane.setViewportView(themes);

        // top
        top = new JPanel();
        top.setLayout(new HorizontalLayout());

        HorizontalConstraint constraint = new HorizontalConstraint();
        constraint.endComponent = true;

        top.add(new JLabel("Themes:"));


        // add components to panel
        add(top, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
    }

    protected void changeLaf(ListSelectionEvent event) {
        Theme theme = themes.getSelectedValue();

        FlatLaf laf = theme.getLaf();

        if (laf == null) {
            Dialogs.showError(SwingUtilities.getWindowAncestor(this), "Failed to change theme to " + theme.getName());
        } else {
            FlatAnimatedLafChange.showSnapshot();

            try {
                UIManager.setLookAndFeel(laf);
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();

                Dialogs.showError(SwingUtilities.getWindowAncestor(this), "Failed to change them to " + theme.getName());
            }

            FlatLaf.updateUI();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        }
    }
}