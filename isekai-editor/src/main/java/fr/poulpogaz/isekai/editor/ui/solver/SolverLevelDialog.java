package fr.poulpogaz.isekai.editor.ui.solver;

import fr.poulpogaz.isekai.commons.concurrent.ExecutorWithException;
import fr.poulpogaz.isekai.commons.concurrent.NamedThreadFactory;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.LevelModel;
import fr.poulpogaz.isekai.editor.pack.solver.BFSSolver;
import fr.poulpogaz.isekai.editor.pack.solver.DFSSolver;
import fr.poulpogaz.isekai.editor.pack.solver.ISolver;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

public class SolverLevelDialog extends JDialog {

    private static final Logger LOGGER = LogManager.getLogger(SolverLevelDialog.class);

    private static final ExecutorWithException executor;

    static {
        executor = (ExecutorWithException) ExecutorWithException.newFixedThreadPool(1, new NamedThreadFactory("Solver"));
        executor.setKeepAliveTime(1, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
    }

    private static final SolverLevelDialog INSTANCE = new SolverLevelDialog();

    public static void showDialog(LevelModel level) {
        INSTANCE.setLevel(level);
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
    private LevelModel level;

    private SolverLevelDialog() {
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
        setLocationRelativeTo(IsekaiEditor.getInstance());
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

        setContentPane(content);
    }

    private void setupChooseLayout() {
        content.removeAll();
        choosePanel.setLayout(new HorizontalLayout(5, 5));
        choosePanel.add(choose);
        choosePanel.add(solvers);

        content.setLayout(new VerticalLayout(5, 5));
        content.add(choosePanel);
        content.add(run);
    }

    private void setupAnalyserLayout() {
        content.removeAll();
        content.setLayout(new BorderLayout());
        content.add(analyser, BorderLayout.CENTER);

        solver = createSolver(level);
        if (solver == null) {
            JOptionPane.showMessageDialog(this, "You didn't select a solver");
        }

        analyser.setSolver(solver);

        executor.submit(() -> {
            LOGGER.info("Sokoban has a solution ? {}", solver.check());

            analyser.notifyEnd();
        });

        repaint();
    }

    private void run(ActionEvent e) {
        setupAnalyserLayout();
    }

    private ISolver createSolver(LevelModel level) {
        Object obj = solvers.getSelectedItem();

        if (obj == null) {
            return null;
        }

        return switch ((String) obj) {
            case BFS -> new BFSSolver(level);
            case DFS -> new DFSSolver(level);
            default -> null;
        };
    }

    private void setLevel(LevelModel level) {
        this.level = level;
    }
}