package fr.poulpogaz.isekai.editor.ui;

import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

public class NoSelectionCaret extends DefaultCaret {

    public NoSelectionCaret(JTextComponent textComponent) {
        setBlinkRate(textComponent.getCaret().getBlinkRate());
        textComponent.setHighlighter(null);
    }

    @Override
    public int getMark() {
        return getDot();
    }
}