package fr.poulpogaz.isekai.editor.ui.leveleditor;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.beans.PropertyChangeEvent;
import java.util.Objects;
import java.util.function.Consumer;

public class PackPropertiesPanel extends JPanel {

    private final Pack pack;

    private JTextField nameField;
    private JTextField authorField;
    private JTextField versionField;

    public PackPropertiesPanel(Pack pack) {
        this.pack = Objects.requireNonNull(pack);
        pack.addPropertyChangeListener(this::propertyChange);

        setLayout(new VerticalLayout(6));
        setBorder(BorderFactory.createTitledBorder("Pack properties"));

        initComponents();
    }

    private void initComponents() {
        nameField = new JTextField(pack.getName());
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pack name");
        addDocumentListener(nameField, pack::setName);

        authorField = new JTextField(pack.getAuthor());
        authorField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Author");
        addDocumentListener(authorField, pack::setAuthor);

        versionField = new JTextField(pack.getVersion());
        versionField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Version");
        addDocumentListener(versionField, pack::setVersion);

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

    private void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case Pack.NAME_PROPERTY -> setText(pack.getName(), nameField);
            case Pack.AUTHOR_PROPERTY -> setText(pack.getAuthor(), authorField);
            case Pack.VERSION_PROPERTY -> setText(pack.getVersion(), versionField);
        }
    }

    private void setText(String text, JTextField field) {
        if (!field.getText().equals(text)) {
            field.setText(text);
        }
    }
}