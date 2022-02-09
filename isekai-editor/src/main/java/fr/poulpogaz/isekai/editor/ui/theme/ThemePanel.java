package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.commons.Utils;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.Prefs;
import fr.poulpogaz.isekai.editor.ui.Dialogs;
import fr.poulpogaz.isekai.editor.ui.Icons;
import fr.poulpogaz.isekai.editor.ui.WrapBorder;
import fr.poulpogaz.isekai.editor.ui.layout.HCOrientation;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ThemePanel extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger(ThemePanel.class);

    public static void showDialog() {
        IsekaiEditor parent = IsekaiEditor.getInstance();

        JDialog dialog = new JDialog(parent, "Themes", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        dialog.setContentPane(new ThemePanel());
        dialog.pack();

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private final List<Theme> themesList;

    private JPanel top;
    private JComboBox<String> filter;
    private JButton github;

    private JList<Theme> themes;
    private JScrollPane pane;

    private boolean isAdjusting = false;

    public ThemePanel() {
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());

        ThemeManager.loadThemes();
        themesList = createThemesList();

        initComponents();
    }

    protected List<Theme> createThemesList() {
        Comparator<Theme> comparator = (a, b) -> a.name().compareToIgnoreCase(b.name());

        ArrayList<Theme> toAdd = new ArrayList<>(ThemeManager.getCoreThemes());
        toAdd.sort(comparator);

        ArrayList<Theme> themes = new ArrayList<>(toAdd);

        toAdd.clear();
        toAdd.addAll(ThemeManager.getThemes());
        toAdd.sort(comparator);

        themes.addAll(toAdd);

        return themes;
    }

    protected void initComponents() {
        // center
        themes = new JList<>(new DefaultListModel<>());
        themes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        themes.setCellRenderer(new CellRenderer());
        addThemesToList();

        selectTheme();
        themes.addListSelectionListener(this::changeLaf);

        pane = new JScrollPane();
        pane.setViewportView(themes);
        pane.setBorder(new WrapBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), pane.getBorder()));

        // top
        github = new JButton(Icons.get("icons/github.svg"));
        github.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        github.addActionListener(this::browse);
        setGithubIconSelected();

        filter = new JComboBox<>();
        filter.addItem("All");
        filter.addItem("Light");
        filter.addItem("Dark");
        filter.addActionListener((e) -> addThemesToList());

        top = new JPanel();
        top.setLayout(new HorizontalLayout());

        HorizontalConstraint constraint = new HorizontalConstraint();
        constraint.endComponent = true;

        top.add(new JLabel("Themes:"), constraint);

        constraint.endComponent = false;
        constraint.orientation = HCOrientation.RIGHT;

        top.add(filter, constraint);
        top.add(github, constraint);

        // add components to panel
        add(top, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        themes.requestFocus();
        themes.ensureIndexIsVisible(themes.getSelectedIndex());
    }

    protected void addThemesToList() {
        isAdjusting = true;

        Theme oldSelected = themes.getSelectedValue();

        DefaultListModel<Theme> model = (DefaultListModel<Theme>) themes.getModel();
        model.removeAllElements();

        String filter = this.filter == null ? "All" : (String) this.filter.getSelectedItem();

        if (filter == null) {
            return;
        }

        if (filter.equals("Light")) {
            for (Theme theme : themesList) {
                if (!theme.dark()) {
                    model.addElement(theme);
                }
            }
        } else if (filter.equals("Dark")) {
            for (Theme theme : themesList) {
                if (theme.dark()) {
                    model.addElement(theme);
                }
            }
        } else {
            model.addAll(themesList);
        }

        if (oldSelected != null) {
            if (model.contains(oldSelected)) {
                themes.setSelectedValue(oldSelected, true); // don't change theme
            } else {
                isAdjusting = false;
                themes.setSelectedValue(model.get(0), true); // change theme
            }
        }

        isAdjusting = false;
    }

    protected void changeLaf(ListSelectionEvent event) {
        if (event.getValueIsAdjusting() || isAdjusting) {
            return;
        }

        setGithubIconSelected();

        EventQueue.invokeLater(() -> {
            Theme theme = themes.getSelectedValue();
            ThemeManager.setTheme(theme, true, this, true);
        });
    }

    protected void selectTheme() {
        String themeName = Prefs.getTheme();
        if (themeName == null) {
            return;
        }

        ListModel<Theme> model = themes.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            Theme theme = model.getElementAt(i);

            if (theme.name().equals(themeName)) {
                boolean old = this.isAdjusting;

                isAdjusting = true;
                themes.setSelectedValue(theme, true);
                isAdjusting = old;

                break;
            }
        }
    }

    protected void setGithubIconSelected() {
        Theme theme = themes.getSelectedValue();

        github.setEnabled(theme.isIntellijTheme());
    }

    protected void browse(ActionEvent evt) {
        IntelliJIDEATheme theme = (IntelliJIDEATheme) themes.getSelectedValue();

        if (!Utils.browse(theme.sourceCodeUrl())) {
            Dialogs.showError(this, "Failed to open your browser");
        }
    }

    private static class CellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            Theme theme = (Theme) value;
            setText(theme.name());

            String tooltip = "Name: %s\nDark: %s".formatted(theme.name(), theme.dark());

            if (theme instanceof IntelliJIDEATheme iTheme) {
                tooltip += "\nLicense: %s\nSource code: %s".formatted(iTheme.license(), iTheme.sourceCodeUrl());
            }

            setToolTipText(tooltip);

            return this;
        }
    }
}