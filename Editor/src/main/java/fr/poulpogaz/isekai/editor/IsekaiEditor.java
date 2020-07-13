package fr.poulpogaz.isekai.editor;

import com.formdev.flatlaf.icons.FlatTreeClosedIcon;
import com.formdev.flatlaf.icons.FlatTreeLeafIcon;
import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import fr.poulpogaz.isekai.editor.ui.EditorMenuBar;
import fr.poulpogaz.isekai.editor.ui.blenderpane.BasicBlenderPanel;
import fr.poulpogaz.isekai.editor.ui.blenderpane.BlenderArea;

import javax.swing.*;
import java.awt.*;

public class IsekaiEditor extends JFrame {

    public IsekaiEditor() {
        super("Editor");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();

        setJMenuBar(new EditorMenuBar());

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        BlenderArea container = new BlenderArea();

        BasicBlenderPanel panel1 = new BasicBlenderPanel();
        panel1.setIcon(new FlatTreeOpenIcon());
        panel1.setText("First");
        panel1.setLayout(new GridBagLayout());
        panel1.add(new JButton("Hello world"));

        BasicBlenderPanel panel2 = new BasicBlenderPanel();
        panel2.setIcon(new FlatTreeLeafIcon());
        panel2.setText("Second");
        panel2.setLayout(new GridBagLayout());
        panel2.add(new JTextField("Hello world"));

        BasicBlenderPanel panel3 = new BasicBlenderPanel();
        panel3.setIcon(new FlatTreeClosedIcon());
        panel3.setText("THird");
        panel3.setLayout(new GridBagLayout());

        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);
        panel3.add(bar);

        JMenuBar menuBar = panel3.getMenuBar();
        JMenu menu = new JMenu("Progress");

        JMenuItem zero = new JMenuItem("0%");
        zero.addActionListener((e) -> {
            bar.setValue(0);
            bar.setIndeterminate(false);
        });

        JMenuItem twenty_five = new JMenuItem("25%");
        twenty_five.addActionListener((e) -> {
            bar.setValue(25);
            bar.setIndeterminate(false);
        });

        JMenuItem fifty = new JMenuItem("50%");
        fifty.addActionListener((e) -> {
            bar.setValue(50);
            bar.setIndeterminate(false);
        });

        JMenuItem seventy_five = new JMenuItem("75%");
        seventy_five.addActionListener((e) -> {
            bar.setValue(75);
            bar.setIndeterminate(false);
        });

        JMenuItem hundred = new JMenuItem("100%");
        hundred.addActionListener((e) -> {
            bar.setValue(100);
            bar.setIndeterminate(false);
        });

        JMenuItem indeterminate = new JMenuItem("Indeterminate");
        indeterminate.addActionListener((e) -> bar.setIndeterminate(true));

        menu.add(zero);
        menu.add(twenty_five);
        menu.add(fifty);
        menu.add(seventy_five);
        menu.add(hundred);
        menu.addSeparator();
        menu.add(indeterminate);
        menuBar.add(menu);

        container.addPanel(panel1);
        container.addPanel(panel2);
        container.addPanel(panel3);

        setContentPane(container);
    }
}