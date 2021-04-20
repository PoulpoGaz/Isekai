package fr.poulpogaz.isekai.editor.ui.check;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.editor.pack.checker.Solver;
import fr.poulpogaz.isekai.editor.pack.checker.State;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.concurrent.Alarm;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.apache.xpath.operations.Number;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class Analyser extends JPanel {

    private static final int MAX_SIZE = 100;

    private final Solver solver;
    private final Alarm alarm;

    private JLabel status;
    private StateView stateView;

    private JFormattedTextField searchState;
    private JLabel numberOfState;

    public Analyser(Solver solver) {
        this.solver = solver;
        this.alarm = new Alarm();
        alarm.schedule(this::update, 500, 500);

        setLayout(new BorderLayout());
        initComponents();
    }

    protected void initComponents() {
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        status = new JLabel("Checking...");

        numberOfState = new JLabel("Number of states: 0");
        searchState = new JFormattedTextField(NumberFormat.getIntegerInstance());
        searchState.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search state by number");
        searchState.addPropertyChangeListener("value", (e) -> {
            ListOrderedSet<State> states = solver.getVisited();
            Object value = searchState.getValue();

            if (value == null) {
                return;
            }
            int v = (int) (long) value; // needed cast to long, because value is a Long

            if (v >= 0 && v < states.size()) {
                stateView.setState(states.get(v));
            }
        });

        stateView = new StateView(solver, this);

        JPanel left = new JPanel();
        left.setLayout(new VerticalLayout(5, 5));
        left.add(numberOfState);
        left.add(searchState);

        add(left, BorderLayout.EAST);
        add(stateView, BorderLayout.CENTER);
        add(status, BorderLayout.NORTH);
    }

    protected void update() {
        ListOrderedSet<State> states = solver.getVisited();

        if (states.size() == 0) {
            return;
        }

        numberOfState.setText("Number of states: " + states.size());

        if (stateView.getState() == null) {
            stateView.setState(states.get(0));
        }
    }

    public void notifyEnd() {
        EventQueue.invokeLater(() -> {
            if (solver.check()) {
                status.setText("This sokoban has a solution!");
            } else {
                status.setText("This sokoban has no solution.");
            }

            update();
        });

        alarm.shutdown();
    }

    public State getStateAt(int index) {
        return solver.getVisited().get(index);
    }
}