package fr.poulpogaz.isekai.editor.ui.importer;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.utils.concurrent.ExecutorWithException;
import fr.poulpogaz.isekai.editor.utils.concurrent.NamedThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

public class LevelImporter extends JDialog {

    private static final ExecutorWithException executor;

    static {
        int nproc = Runtime.getRuntime().availableProcessors();

        executor = (ExecutorWithException) ExecutorWithException.newFixedThreadPool(nproc, new NamedThreadFactory("Importer"));
        executor.setKeepAliveTime(1, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
    }

    public static void showDialog(Pack pack) {
        INSTANCE.setPack(pack);
        INSTANCE.setupForImport();
        INSTANCE.setVisible(true);
    }

    private static final LevelImporter INSTANCE = new LevelImporter();

    private Pack pack;
    private JPanel content;
    private JComboBox<Object> packs;

    private LevelImporter() {
        super(IsekaiEditor.getInstance(), "Import", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
    }

    protected void initComponents() {
        content = new JPanel();

        setContentPane(content);
    }

    protected void setupForImport() {
        if (SIPack.arePacksLoaded()) {
            content.removeAll();
            content.setLayout(new GridBagLayout());

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

            content.add(packs);
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

        executor.submit(() -> {
            SIPack.loadPacks();

            setupForImport();
        });
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

    public void setPack(Pack pack) {
        this.pack = pack;
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