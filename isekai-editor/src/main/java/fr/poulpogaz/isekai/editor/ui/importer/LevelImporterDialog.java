package fr.poulpogaz.isekai.editor.ui.importer;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.SIPack;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class LevelImporterDialog extends JDialog {

    public static List<Level> showDialog() {
        INSTANCE.setupForImport();
        INSTANCE.setVisible(true);

        return INSTANCE.getLevels();
    }

    private static final LevelImporterDialog INSTANCE = new LevelImporterDialog();

    private List<Level> levels;
    private JPanel content;

    private LevelImporterPanel panel;
    private LevelImporter importer;

    private JProgressBar bar;

    private List<Integer> fails;

    private LevelImporterDialog() {
        super(IsekaiEditor.getInstance(), "Import", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (importer != null) {
                    importer.cancel(true);
                }
            }
        });

        levels = new ArrayList<>();
    }

    protected void initComponents() {
        content = new JPanel();

        setContentPane(content);
    }

    protected void setupForImport() {
        levels = null;
        importer = null;

        if (SIPack.arePacksLoaded()) {
            showPacks();
        } else if (SIPack.isError()) {
            showError();
        } else {
            loadPacks();
        }

        pack();
        setLocationRelativeTo(getParent());
    }

    protected void loadPacks() {
        content.removeAll();
        content.setLayout(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel loading = new JLabel("Loading packs");
        loading.setFont(loading.getFont().deriveFont(20f));

        content.add(loading);

        new PackLoader().execute();
    }

    protected void showError() {
        content.removeAll();
        content.setLayout(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea area = new JTextArea();
        area.setBackground(content.getBackground());
        area.setLineWrap(true);
        area.setEditable(false);

        Exception exception = SIPack.getException();

        area.setText("Failed to fetch packs\nError: " + exception);

        JButton showDetailedError = new JButton("Show detailed error");
        showDetailedError.addActionListener((l) -> {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            exception.printStackTrace(pw);

            area.setText("Failed to fetch packs\nError: " + sw.getBuffer().toString());
        });

        JPanel bottom = new JPanel(new GridBagLayout());
        bottom.add(showDetailedError);

        content.add(area, BorderLayout.CENTER);
        content.add(bottom, BorderLayout.SOUTH);
    }

    protected void showPacks() {
        content.removeAll();
        content.setLayout(new VerticalLayout());

        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        if (panel == null) {
            panel = new LevelImporterPanel(this);
        }

        panel.setEnabled(true);

        content.add(panel, constraint);
    }

    protected void importLevels(SIPack pack, String range) {
        if (importer == null) {
            if (bar == null) {
                bar = new JProgressBar();
            }

            fails = new ArrayList<>();

            content.add(bar);

            importer = new LevelImporter(pack, range, this);
            bar.setValue(0);
            bar.setMaximum(importer.getNumberOfLevels());

            revalidate();
            repaint();

            importer.execute();
        }
    }

    protected void increase() {
        bar.setValue(bar.getValue() + 1);
    }

    protected void fail(int index) {
        fails.add(index);
        increase();
    }

    protected void dispose(List<Level> levels) {
        this.levels = levels;

        if (fails.size() > 0) {
            StringBuilder text = new StringBuilder();
            text.append("Failed to import ").append(fails.size()).append(" levels.\n");

            asRange(text, fails);

            JOptionPane.showMessageDialog(this, text.toString(), "Failed to import levels", JOptionPane.ERROR_MESSAGE);
        }

        dispose();
        fails = null;
    }

    protected void asRange(StringBuilder text, List<Integer> levels) {
        int begin = -1;
        int last = levels.get(0);

        boolean write = false;
        for (int i = 1; i < levels.size(); i++) {
            int level = levels.get(i);

            if (last + 1 == level) { // two consecutive levels
                if (begin == -1) {
                    begin = last;
                }
                write = false;
            } else {
                if (begin == -1) {
                    text.append(last);
                } else {
                    text.append(begin).append("-").append(last);
                    begin = -1;
                }
                if (i != levels.size() - 1) {
                    text.append(", ");
                }

                write = true;
            }

            last = level;
        }

        if (!write) {
            if (begin == -1) {
                text.append(last);
            } else {
                text.append(begin).append("-").append(last);
            }
        }
    }

    public List<Level> getLevels() {
        return levels;
    }

    private class PackLoader extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() {
            SIPack.loadPacks();

            return null;
        }

        @Override
        protected void done() {
            setupForImport();
        }
    }
}