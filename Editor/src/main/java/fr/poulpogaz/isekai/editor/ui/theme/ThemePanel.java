package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.Prefs;
import fr.poulpogaz.isekai.editor.ui.Dialogs;
import fr.poulpogaz.isekai.editor.ui.WrapBorder;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class ThemePanel extends JPanel {

    public static void showDialog() {
        IsekaiEditor parent = IsekaiEditor.getInstance();

        JDialog dialog = new JDialog(parent, "Themes", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        dialog.setContentPane(new ThemePanel());
        dialog.pack();

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private JPanel top;

    private JList<Theme> themes;
    private JScrollPane pane;

    private boolean isAdjusting = false;

    public ThemePanel() {
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());

        ThemeManager.loadThemes();

        initComponents();
    }

    protected void initComponents() {
        // center
        DefaultListModel<Theme> model = new DefaultListModel<>();

        for (Theme theme : ThemeManager.getCoreThemes()) {
            model.addElement(theme);
        }

        for (Theme theme : ThemeManager.getThemes()) {
            model.addElement(theme);
        }

        themes = new JList<>(model);
        themes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        themes.setCellRenderer(new CellRenderer());

        selectTheme();
        themes.addListSelectionListener(this::changeLaf);

        pane = new JScrollPane();
        pane.setViewportView(themes);
        pane.setBorder(new WrapBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), pane.getBorder()));

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
        if (event.getValueIsAdjusting() || isAdjusting) {
            return;
        }

        EventQueue.invokeLater(() -> {
            Theme theme = themes.getSelectedValue();
            ThemeManager.setTheme(theme, true, this, true);
        });
    }

    protected void selectTheme() {
        String themeName = Prefs.getPrefs().get(Prefs.THEME, null);
        if (themeName == null) {
            return;
        }

        ListModel<Theme> model = themes.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            Theme theme = model.getElementAt(i);

            if (theme.name().equals(themeName)) {
                isAdjusting = true;
                themes.setSelectedValue(theme, true);
                isAdjusting = false;

                break;
            }
        }
    }

    private static class CellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            Theme theme = (Theme) value;
            setText(theme.name());

            return this;
        }
    }
}