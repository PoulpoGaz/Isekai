package fr.poulpogaz.isekai.editor.ui.importer;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.utils.concurrent.ExecutorWithException;

import javax.swing.*;
import java.awt.*;

public class LevelImporter extends JDialog {

    private static final ExecutorWithException executor = (ExecutorWithException) ExecutorWithException.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void showDialog(Pack pack) {
        INSTANCE.setPack(pack);
        INSTANCE.setupForImport();
        INSTANCE.setVisible(true);
    }

    private static final LevelImporter INSTANCE = new LevelImporter();

    private Pack pack;

    private LevelImporter() {
        super(IsekaiEditor.getInstance(), "Import", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
    }

    protected void initComponents() {

    }

    protected void setupForImport() {
        removeAll();

        if (SIPack.arePacksLoaded()) {
            executor.submit(SIPack::loadPacks);

            setLayout(new GridBagLayout());
            JLabel loading = new JLabel("Loading packs");
            loading.setFont(loading.getFont().deriveFont(20f));

            add(loading);
        }

        pack();
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }
}