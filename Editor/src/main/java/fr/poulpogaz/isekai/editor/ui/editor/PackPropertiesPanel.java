package fr.poulpogaz.isekai.editor.ui.editor;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class PackPropertiesPanel extends JPanel {

    private Pack pack;

    public PackPropertiesPanel(Pack pack) {
        this.pack = pack;

        setLayout(new VerticalLayout(6));
        setBorder(BorderFactory.createTitledBorder("Pack properties"));

        initComponents();
    }

    private void initComponents() {
        JTextField packNameField = new JTextField();
        packNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pack name");
        addDocumentListener(packNameField, (newValue) -> pack.setPackName(newValue));

        JTextField authorNameField = new JTextField();
        authorNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Author");
        addDocumentListener(packNameField, (newValue) -> pack.setAuthor(newValue));

        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        add(packNameField, constraint);
        add(authorNameField, constraint);
    }

    private void addDocumentListener(JTextField field, Consumer<String> action) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                action.accept(field.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                action.accept(field.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                action.accept(field.getText());
            }
        });
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Pack getPack() {
        return pack;
    }
}