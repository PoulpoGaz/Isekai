package fr.poulpogaz.isekai.editor.ui.importer;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LevelImporterPanel extends JPanel {

    private final LevelImporterDialog dialog;

    private JComboBox<Object> packs;
    private JTextField range;

    private JTable packInfo;

    private JButton importLevels;

    public LevelImporterPanel(LevelImporterDialog dialog) {
        this.dialog = dialog;

        setLayout(new GridBagLayout());
        initComponents();
        setupLayout();
    }

    protected void initComponents() {
        packs = new JComboBox<>();
        packs.setRenderer(new PackCellRenderer());
        packs.setModel(new PackComboModel());

        String author = null;
        for (SIPack pack : SIPack.getPacks()) {
            if (!pack.author().equals(author)) {
                packs.addItem(pack.author());
                author = pack.author();
            }

            packs.addItem(pack);
        }

        packs.setSelectedItem(SIPack.getPacks().get(0));
        packs.addItemListener(e -> updateTable());

        packInfo = new JTable(new DefaultTableModel(new Object[] {"Pack name", "Author", "Number of levels"}, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        packInfo.setFocusable(false);
        packInfo.setRowSelectionAllowed(false);
        updateTable();

        range = new JTextField();
        range.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                check();
            }

            protected void check() {
                if (isValidRange((SIPack) packs.getSelectedItem(), range.getText())) {
                    range.putClientProperty(FlatClientProperties.OUTLINE, null);
                } else {
                    range.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
                }
            }
        });

        importLevels = new JButton("Import");
        importLevels.addActionListener((e) -> {
            SIPack pack = (SIPack) packs.getSelectedItem();

            if (isValidRange(pack, range.getText())) {
                dialog.importLevels(pack, range.getText());

                setEnabled(false);
            } else {
                Window parent = SwingUtilities.getWindowAncestor(this);

                JOptionPane.showMessageDialog(parent, "The range is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    protected void setupLayout() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(3, 3, 3, 3);

        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridx = 0;
        constraints.gridy = 0;

        add(new JLabel("Choose a pack:"), constraints);

        constraints.gridy = 3;
        add(new JLabel("Range (ex: 4-7 or 3,5): "), constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        add(range, constraints);

        constraints.gridy = 0;
        add(packs, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(3, 3, 0, 3);
        add(packInfo.getTableHeader(), constraints);

        constraints.gridy = 2;
        constraints.insets = new Insets(0, 3, 3, 3);
        add(packInfo, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.insets = new Insets(3, 3, 3, 3);

        add(importLevels, constraints);
    }

    protected void updateTable() {
        DefaultTableModel model = (DefaultTableModel) packInfo.getModel();

        SIPack selection = (SIPack) packs.getSelectedItem();
        if (selection == null) {
            throw new NullPointerException();
        }

        model.setValueAt(selection.name(), 0, 0);
        model.setValueAt(selection.author(), 0, 1);
        model.setValueAt(selection.nLevels(), 0, 2);
    }

    protected boolean isValidRange(SIPack pack, String str) {
        if (str.isEmpty()) {
            return false;
        }

        String[] ranges = str.split(",");

        int nColon = (int) str.chars().filter((c) -> c == ',').count();

        if (nColon + 1 != ranges.length) { // the number of ranges is the number of colon plus one
            return false;
        }

        for (String range : ranges) {
            if (range.indexOf('-') != range.lastIndexOf('-')) { // multiple hyphen
                return false;
            }

            String[] limits = range.split("-");

            if (limits.length == 1) {
                int val = asInt(limits[0]);

                if (val < 1 || val > pack.nLevels()) {
                    return false;
                }

            } else if (limits.length == 2) {
                int min = asInt(limits[0]);
                int max = asInt(limits[1]);

                if (min < 1 || min > pack.nLevels() || max < 1 || max > pack.nLevels() || max < min) {
                    return false;
                }
            }
        }

        return true;
    }

    protected int asInt(String val) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        packs.setEnabled(enabled);
        range.setEditable(enabled);
        importLevels.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    private static class PackCellRenderer extends DefaultListCellRenderer  {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof SIPack pack) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                String text = "%s (%d)".formatted(pack.name(), pack.nLevels());

                if (index != -1) {
                    text = "  ↪ " + text;
                }

                setText(text);
            } else if (value instanceof String) {
                super.getListCellRendererComponent(list, value, index, false, false);

                setText((String) value);
                setFont(getFont().deriveFont(Font.BOLD));
            }

            return this;
        }
    }

    private static class PackComboModel extends DefaultComboBoxModel<Object> {

        @Override
        public void setSelectedItem(Object anObject) {
            if (anObject instanceof SIPack) {
                super.setSelectedItem(anObject);
            }
        }
    }
}