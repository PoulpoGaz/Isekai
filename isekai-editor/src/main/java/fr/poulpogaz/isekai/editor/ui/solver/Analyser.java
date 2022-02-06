package fr.poulpogaz.isekai.editor.ui.solver;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.editor.pack.solver.ISolver;
import fr.poulpogaz.isekai.editor.pack.solver.State;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.concurrent.Alarm;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

public class Analyser extends JPanel {

    private ISolver solver;
    private Alarm alarm;

    private JLabel status;
    private StateView stateView;

    private JFormattedTextField searchState;
    private JLabel numberOfState;

    public Analyser() {
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
            List<State> states = solver.visited();
            Object value = searchState.getValue();

            if (value == null) {
                return;
            }
            int v = (int) (long) value; // needed cast to long, because value is a Long

            if (v >= 0 && v < states.size()) {
                stateView.setState(states.get(v));
            }
        });

        JPanel left = new JPanel();
        left.setLayout(new VerticalLayout(5, 5));
        left.add(numberOfState);
        left.add(searchState);

        add(left, BorderLayout.EAST);
        add(status, BorderLayout.NORTH);
    }

    protected void update() {
        List<State> states = solver.visited();

        if (states.size() == 0) {
            return;
        }

        numberOfState.setText("Number of states: " + states.size());

        if (stateView.getState() == null) {
            stateView.setState(states.get(0));
        }
    }

    public void setSolver(ISolver solver) {
        if (solver != null) {
            this.solver = solver;

            stateView = new StateView(solver, this);
            add(stateView, BorderLayout.CENTER);

            alarm = new Alarm();
            alarm.schedule(this::update, 500, 500);
        } else if (this.solver != null) {
            if (alarm != null) {
                alarm.shutdown();
                alarm = null;
            }
            removeAll();
            stateView = null;
            this.solver = null;

            // for garbage collect
        }
    }

    public void notifyEnd() {
        EventQueue.invokeLater(() -> {
            if (solver != null) {
                if (solver.check()) {
                    status.setText("This sokoban has a solution!");
                } else {
                    status.setText("This sokoban has no solution.");
                }

                update();
            }
        });

        if (alarm != null) {
            alarm.shutdown();
            alarm = null;
        }
    }

    public State getStateAt(int index) {
        return solver.visited().get(index);
    }
}