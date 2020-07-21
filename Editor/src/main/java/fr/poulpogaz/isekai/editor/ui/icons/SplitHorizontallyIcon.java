package fr.poulpogaz.isekai.editor.ui.icons;

import fr.poulpogaz.isekai.editor.utils.icons.AbstractIcon;

import java.awt.*;

public class SplitHorizontallyIcon extends AbstractIcon {

    public static final String IDENTIFIER = "SplitHorizontallyIcon";

    public SplitHorizontallyIcon() {
        super(16, 16);
    }

    @Override
    protected void paintIcon(Component c, Graphics2D g2) {
        g2.drawRect(3, 1, 9, 4);
        g2.drawRect(3, 10, 9, 4);
        g2.fillRect(1, 7, 14, 2);
    }
}