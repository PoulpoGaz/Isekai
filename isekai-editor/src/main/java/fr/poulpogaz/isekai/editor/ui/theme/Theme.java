package fr.poulpogaz.isekai.editor.ui.theme;

import javax.swing.*;

public interface Theme {

    LookAndFeel createLaf() throws Exception;

    boolean isIntellijTheme();

    boolean isCoreTheme();

    String name();

    boolean dark();
}