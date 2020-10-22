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

    private JTextField nameField;
    private JTextField authorField;
    private JTextField versionField;

    public PackPropertiesPanel(PackController controller) {
        this.controller = controller;
        controller.addView(this);

        setLayout(new VerticalLayout(6));
        setBorder(BorderFactory.createTitledBorder("Pack properties"));

        initComponents();
    }

    private void initComponents() {
        nameField = new JTextField(controller.getName());
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pack name");
        addDocumentListener(nameField, controller::setName);

        authorField = new JTextField(controller.getAuthor());
        authorField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Author");
        addDocumentListener(authorField, controller::setAuthor);

        versionField = new JTextField(controller.getVersion());
        versionField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Version");
        addDocumentListener(versionField, controller::setVersion);

        VerticalConstraint constraint = new VerticalConstraint();
        constraint.fillXAxis = true;

        add(nameField, constraint);
        add(authorField, constraint);
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
        switch (event.getPropertyName()) {
            case Pack.NAME_PROPERTY -> setText((String) event.getNewValue(), nameField);
            case Pack.AUTHOR_PROPERTY -> setText((String) event.getNewValue(), authorField);
            case Pack.VERSION_PROPERTY -> setText((String) event.getNewValue(), versionField);
        }
    }

    private void setText(String text, JTextField field) {
        if (!field.getText().equals(text)) {
            field.setText(text);
        }
    }
}