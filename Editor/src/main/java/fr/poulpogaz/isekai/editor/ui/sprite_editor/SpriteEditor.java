package fr.poulpogaz.isekai.editor.ui.sprite_editor;

import fr.poulpogaz.isekai.editor.ui.colorpicker.ColorPicker;

import javax.swing.*;

public class SpriteEditor extends JPanel {

    public SpriteEditor() {
        add(new ColorPicker());
    }
}