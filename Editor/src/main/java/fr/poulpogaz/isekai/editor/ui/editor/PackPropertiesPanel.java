package fr.poulpogaz.isekai.editor.ui.editor;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.mvc.PackController;
import fr.poulpogaz.isekai.editor.mvc.PackView;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.beans.PropertyChangeEvent;
import java.util.function.Consumer;

public class PackPropertiesPanel extends JPanel implements PackView {

    private final PackController controller;

    public PackPropertiesPanel(PackController controller) {
        this.controller = controller;
        controller.addView(this);

        setLayout(new VerticalLayout(6));
        setBorder(BorderFactory.createTitledBorder("Pack properties"));

        initComponents();
    }

    private void initComponents() {
        JTextField packNameField = new JTextField(controller.getName());
        packNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pack name");
        addDocumentListener(packNameField, controller::setName);

        JTextField authorNameField = new JTextField(controller.getAuthor());
        authorNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Author");
        addDocumentListener(packNameField, controller::setAuthor);

        JTextField versionField = new JTextField(controller.getVersion());
        versionField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Version");
        addDocumentListener(versionField, controller::setVersion);

        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        add(packNameField, constraint);
        add(authorNameField, constraint);
        add(versionField, constraint);
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

    @Override
    public void modelPropertyChange(PropertyChangeEvent event) {

    }
}