package fr.poulpogaz.isekai.editor.ui.check;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.checker.BFSSolver;
import fr.poulpogaz.isekai.editor.pack.checker.DFSSolver;
import fr.poulpogaz.isekai.editor.pack.checker.ISolver;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.concurrent.ExecutorWithException;
import fr.poulpogaz.isekai.editor.utils.concurrent.NamedThreadFactory;
import net.miginfocom.layout.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CheckLevelDialog extends JDialog {

    private static final ExecutorWithException executor = (ExecutorWithException) ExecutorWithException.newFixedThreadPool(1, new NamedThreadFactory("Solver"));

    static {
        executor.setKeepAliveTime(1, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
    }

    private static final CheckLevelDialog INSTANCE = new CheckLevelDialog();

    public static void showDialog() {
        INSTANCE.setupChooseLayout();
        INSTANCE.setVisible(true);
    }

    private static final String BFS = "BFS";
    private static final String DFS = "DFS";

    private JPanel content;

    private JPanel choosePanel;
    private JLabel choose;
    private JComboBox<String> solvers;
    private JButton run;
    private Analyser analyser;

    private ISolver solver;

    private CheckLevelDialog() {
        super(IsekaiEditor.getInstance(), "Checking level", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        setupChooseLayout();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (solver != null) {
                    solver.cancel();
                }

                solver = null;
                analyser.setSolver(null);
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        content = new JPanel();
        choosePanel = new JPanel();

        choose = new JLabel("Choose a solver");

        solvers = new JComboBox<>();
        solvers.addItem(BFS);
        solvers.addItem(DFS);
        solvers.setSelectedItem(BFS);

        run = new JButton("Run");
        run.addActionListener(this::run);

        analyser = new Analyser();
    }

    private void setupChooseLayout() {
        choosePanel.setLayout(new HorizontalLayout(5, 5));
        choosePanel.add(choose);
        choosePanel.add(solvers);

        content.setLayout(new VerticalLayout(5, 5));
        content.add(choosePanel);
        content.add(run);

        setContentPane(content);
    }

    private void setupAnalyserLayout() {
        content.removeAll();
        content.setLayout(new BorderLayout());
        content.add(analyser, BorderLayout.CENTER);

        Pack pack = IsekaiEditor.getInstance().getPack();

        solver = createSolver(pack.getLevel(0));
        if (solver == null) {
            JOptionPane.showMessageDialog(this, "You didn't select a solver");
        }

        analyser.setSolver(solver);

        executor.submit(() -> {
            System.out.println(solver.check());

            analyser.notifyEnd();
        });
    }

    private void run(ActionEvent e) {
        setupAnalyserLayout();
    }

    private ISolver createSolver(Level level) {
        Object obj =  solvers.getSelectedItem();

        if (obj == null) {
            return null;
        }

        return switch ((String) obj) {
            case BFS -> new BFSSolver(level);
            case DFS -> new DFSSolver(level);
            default -> null;
        };
    }
}