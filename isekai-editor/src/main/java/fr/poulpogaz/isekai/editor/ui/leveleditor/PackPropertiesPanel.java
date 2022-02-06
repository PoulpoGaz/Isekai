package fr.poulpogaz.isekai.editor.ui.leveleditor;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.editor.pack.Pack;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.Objects;
import java.util.function.Consumer;

public class PackPropertiesPanel extends JPanel {

    private final Pack pack;

    private JTextField fileNameField;
    private JTextField packNameField;
    private JTextField authorField;

    public PackPropertiesPanel(Pack pack) {
        this.pack = Objects.requireNonNull(pack);
        pack.addPropertyChangeListener(this::propertyChange);

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Pack properties"));

        initComponents();
    }

    private void initComponents() {
        fileNameField = new JTextField();
        fileNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "File name");
        fileNameField.setDocument(new LimitedDocument(Pack.MAX_FILE_NAME_SIZE));
        fileNameField.setText(pack.getFileName());
        addDocumentListener(fileNameField, pack::setFileName);

        packNameField = new JTextField();
        packNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pack name");
        packNameField.setDocument(new LimitedDocument(Pack.MAX_PACK_NAME_SIZE));
        packNameField.setText(pack.getPackName());
        addDocumentListener(packNameField, pack::setPackName);

        authorField = new JTextField();
        authorField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Author");
        authorField.setDocument(new LimitedDocument(Pack.MAX_AUTHOR_SIZE));
        authorField.setText(pack.getAuthor());
        addDocumentListener(authorField, pack::setAuthor);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(3, 3, 3, 3);
        add(new JLabel("File name:"), constraints);

        constraints.gridy = 1;
        add(new JLabel("Pack name:"), constraints);

        constraints.gridy = 2;
        add(new JLabel("Author:"), constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;

        add(authorField, constraints);

        constraints.gridy = 1;
        add(packNameField, constraints);

        constraints.gridy = 0;
        add(fileNameField, constraints);
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
            case Pack.FILE_NAME_PROPERTY -> setText(pack.getFileName(), fileNameField);
            case Pack.PACK_NAME_PROPERTY -> setText(pack.getPackName(), packNameField);
            case Pack.AUTHOR_PROPERTY -> setText(pack.getAuthor(), authorField);
        }
    }

    private void setText(String text, JTextField field) {
        if (!field.getText().equals(text)) {
            field.setText(text);
        }
    }

    private static class LimitedDocument extends PlainDocument {

        private int limit;

        public LimitedDocument(int limit) {
            this.limit = limit;
        }


        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offs, str, a);
            }
        }
    }
}