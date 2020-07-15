package fr.poulpogaz.isekai.editor.ui.icons;

import fr.poulpogaz.isekai.editor.utils.icons.AbstractIcon;

import javax.swing.*;
import java.awt.*;

public class SplitVerticallyIcon extends AbstractIcon {

    public static final String IDENTIFIER = "SplitVerticallyIcon";

    public SplitVerticallyIcon() {
        super(16, 16);
    }

    @Override
    protected void paintIcon(Component c, Graphics2D g2) {
        g2.drawRect(1, 3, 4, 9);
        g2.drawRect(10, 3, 4, 9);
        g2.fillRect(7, 1, 2, 14);
    }
}