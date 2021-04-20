package fr.poulpogaz.isekai.editor.ui.check;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.checker.Solver;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.concurrent.AppExecutor;
import fr.poulpogaz.isekai.editor.utils.concurrent.ExecutorWithException;
import fr.poulpogaz.isekai.editor.utils.concurrent.NamedThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CheckLevelDialog extends JDialog {

    private static final ExecutorWithException executor = (ExecutorWithException) ExecutorWithException.newFixedThreadPool(1, new NamedThreadFactory("Solver"));

    static {
        executor.setKeepAliveTime(1, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
    }

    private static final CheckLevelDialog INSTANCE = new CheckLevelDialog();

    public static void showDialog() {
        INSTANCE.setVisible(true);
    }

    private JPanel content;

    private CheckLevelDialog() {
        super(IsekaiEditor.getInstance(), "Checking level", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();

        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        content = new JPanel();
        content.setLayout(new BorderLayout());

        IsekaiEditor editor = IsekaiEditor.getInstance();

        Solver solver = new Solver(editor.getPack().getLevel(0));
        Analyser analyser = new Analyser(solver);
        add(analyser, BorderLayout.CENTER);

        executor.submit(() -> {
            System.out.println(solver.check());

            analyser.notifyEnd();
        });
    }
}