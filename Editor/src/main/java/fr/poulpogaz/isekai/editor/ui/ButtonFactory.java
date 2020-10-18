package fr.poulpogaz.isekai.editor.ui;

import fr.poulpogaz.isekai.editor.utils.GraphicsUtils;
import fr.poulpogaz.isekai.editor.utils.icons.AbstractIcon;

import javax.swing.*;
import java.awt.*;

public class ButtonFactory {

    public static JButton createIconButton(Icon baseIcon) {
        if (baseIcon instanceof AbstractIcon) {
            AbstractIcon icon = (AbstractIcon) baseIcon;

            Color pressedColor  = GraphicsUtils.brighter(icon.getIconColor(), 0.9f);
            Color rolloverColor = GraphicsUtils.brighter(icon.getIconColor(), 0.8f);

            return createIconButton(baseIcon, icon.derive(pressedColor), icon.derive(rolloverColor));
        } else {
            return createIconButton(baseIcon, null, null);
        }
    }

    public static JButton createIconButton(Icon icon, Icon pressedIcon, Icon rolloverIcon) {
        JButton button = createTransparentButton();
        button.setIcon(icon);

        if (pressedIcon != null) {
            button.setPressedIcon(pressedIcon);
        }
        if (rolloverIcon != null) {
            button.setRolloverIcon(rolloverIcon);
        }

        return button;
    }

    public static JButton createTransparentButton() {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBorder(null);

        return button;
    }
}