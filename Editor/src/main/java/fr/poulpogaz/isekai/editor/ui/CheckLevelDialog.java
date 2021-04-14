package fr.poulpogaz.isekai.editor.ui;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.checker.Solver;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CheckLevelDialog extends JDialog {

    private static final CheckLevelDialog INSTANCE = new CheckLevelDialog();

    public static void showDialog() {
        INSTANCE.setVisible(true);
    }

    private Pack pack;

    private JPanel content;
    private JPanel buttons;
    private JRadioButton checkAllLevel;
    private JButton check;

    private JProgressBar bar;
    private JTextArea results;

    private CheckLevelDialog() {
        super(IsekaiEditor.getInstance(), "Check level", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        initLayout();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        content = new JPanel();
        content.setLayout(new VerticalLayout(5, 5));

        buttons = new JPanel();
        buttons.setLayout(new HorizontalLayout(5, 5, 15, 15));

        checkAllLevel = new JRadioButton("Check all level");
        check = new JButton("Check");

        check.addActionListener(this::check);

        results = new JTextArea();
        results.setLineWrap(true);

        bar = new JProgressBar();
    }

    private void initLayout() {
        content.removeAll();
        buttons.removeAll();

        buttons.add(checkAllLevel);
        buttons.add(check);

        content.add(buttons);

        setContentPane(content);
    }

    private void check(ActionEvent e) {
        content.add(bar);
        content.add(results);

        pack = IsekaiEditor.getInstance().getPack();

        Solver solver = new Solver(pack.getLevel(0));
        System.out.println(solver.check());
    }
}