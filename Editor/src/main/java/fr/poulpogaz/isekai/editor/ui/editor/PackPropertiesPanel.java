package fr.poulpogaz.isekai.editor.ui.editor;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class PackPropertiesPanel extends JPanel {

    private final Pack pack = IsekaiEditor.getPack();

    public PackPropertiesPanel() {
        setLayout(new VerticalLayout(6));
        setBorder(BorderFactory.createTitledBorder("Pack properties"));

        initComponents();
    }

    private void initComponents() {
        JTextField packNameField = new JTextField(pack.getPackName());
        packNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pack name");
        addDocumentListener(packNameField, pack::setPackName);

        JTextField authorNameField = new JTextField(pack.getAuthor());
        authorNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Author");
        addDocumentListener(packNameField, pack::setAuthor);

        JTextField versionField = new JTextField(pack.getVersion());
        versionField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Version");
        addDocumentListener(versionField, pack::setVersion);

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
}