package fr.poulpogaz.isekai.editor.ui.text;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.util.Objects;

public class JIsekaiTextField extends JTextField {

    public static final String TRAILING_CHANGED_PROPERTY = "TrailingChanged";
    public static final String LEADING_CHANGED_PROPERTY = "LeadingChanged";

    private transient boolean lock;

    private Component trailingComponent;
    private Component leadingComponent;

    public JIsekaiTextField() {
        setLayout(new IsekaiTextFieldLayout());
    }

    public JIsekaiTextField(String text) {
        super(text);
        setLayout(new IsekaiTextFieldLayout());
    }

    public JIsekaiTextField(int columns) {
        super(columns);
        setLayout(new IsekaiTextFieldLayout());
    }

    public JIsekaiTextField(String text, int columns) {
        super(text, columns);
        setLayout(new IsekaiTextFieldLayout());
    }

    public JIsekaiTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        setLayout(new IsekaiTextFieldLayout());
    }

    public Component getTrailingComponent() {
        return trailingComponent;
    }

    public void setTrailingComponent(Component trailingComponent) {
        if (this.trailingComponent != trailingComponent) {
            Component old  = this.trailingComponent;

            this.trailingComponent = trailingComponent;
            add(trailingComponent, IsekaiTextFieldLayout.TRAIL);
            getUI().setTrailingComponent(trailingComponent);

            firePropertyChange(TRAILING_CHANGED_PROPERTY, old, trailingComponent);
        }
    }

    public Component getLeadingComponent() {
        return leadingComponent;
    }

    public void setLeadingComponent(Component leadingComponent) {
        if (this.leadingComponent != leadingComponent) {
            Component old = this.leadingComponent;

            this.leadingComponent = leadingComponent;
            add(leadingComponent, IsekaiTextFieldLayout.LEAD);
            getUI().setLeadingComponent(leadingComponent);

            firePropertyChange(LEADING_CHANGED_PROPERTY, old, trailingComponent);
        }
    }

    @Override
    public void setText(String t) {
        try {
            if (!isLock()) {
                super.setText(t);
            }
        } finally {
            unlock();
        }
    }

    @Override
    public IsekaiTextFieldUI getUI() {
        return (IsekaiTextFieldUI) super.getUI();
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    protected synchronized void lock() {
        lock = true;
    }

    protected synchronized void unlock() {
        lock = false;
    }

    protected synchronized boolean isLock() {
        return lock;
    }
}