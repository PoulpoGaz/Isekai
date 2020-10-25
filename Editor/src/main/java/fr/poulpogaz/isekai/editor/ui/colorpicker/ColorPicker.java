package fr.poulpogaz.isekai.editor.ui.colorpicker;

import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;

import javax.swing.*;

public class ColorPicker extends JPanel {

    private AlphaSlider alphaSlider;
    private HueSlider hueSlider;

    private JTextField red;
    private JTextField green;
    private JTextField blue;
    private JTextField alpha;

    public ColorPicker() {
        setLayout(new VerticalLayout());

        add(new AlphaSlider(0, false, false));
        add(new AlphaSlider(0, false, true));
        add(new AlphaSlider(0, true, false));
        add(new AlphaSlider(0, true, true));

        add(new HueSlider(0, false, false));
        add(new HueSlider(0, false, true));
        add(new HueSlider(0, true, false));
        add(new HueSlider(0, true, true));
    }

}