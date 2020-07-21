package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.icons.FlatTreeLeafIcon;
import fr.poulpogaz.isekai.editor.ui.area.BRegionView;

import javax.swing.*;
import java.awt.*;

public class TextFieldPanel extends BRegionView {

    private JTextField field;

    public TextFieldPanel() {
        this(new JTextField());
    }

    private TextFieldPanel(JTextField field) {
        this.field = field;
        setLayout(new GridBagLayout());
        add(field);
    }

    @Override
    public JMenuBar getMenuBar() {
        return null;
    }

    @Override
    public Icon getIcon() {
        return new FlatTreeLeafIcon();
    }

    @Override
    public String getText() {
        return "TextField";
    }

    @Override
    public BRegionView copyWithDataLink() {
        JTextField copy = new JTextField();
        copy.setDocument(field.getDocument());

        return new TextFieldPanel(copy);
    }
}