package fr.poulpogaz.isekai.editor.ui.colorpicker;

import java.awt.*;

public interface IPipette {

    void show();

    void dispose();

    Color pick();

    Color getSelectedColor();
}
