package fr.poulpogaz.isekai.editor.utils.icons;

import java.awt.*;

public class DelegateIcon extends AbstractIcon {

    private final AbstractIcon icon;

    public DelegateIcon(AbstractIcon icon) {
        this.icon = icon;
    }

    public DelegateIcon(Color color, AbstractIcon icon) {
        super(color);
        this.icon = icon;
    }

    public DelegateIcon(int size, Color color, AbstractIcon icon) {
        super(size, color);
        this.icon = icon;
    }

    public DelegateIcon(int width, int height, AbstractIcon icon) {
        super(width, height);
        this.icon = icon;
    }

    public DelegateIcon(int width, int height, Color color, AbstractIcon icon) {
        super(width, height, color);
        this.icon = icon;
    }

    @Override
    protected void paintIcon(Component c, Graphics2D g2) {
        g2.scale((double) width / icon.getIconWidth(), (double) height / icon.getIconHeight());

        icon.paintIcon(c, g2);
    }
}