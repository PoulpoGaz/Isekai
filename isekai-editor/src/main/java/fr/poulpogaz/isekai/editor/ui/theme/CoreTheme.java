package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatLaf;

public record CoreTheme(String name, boolean dark,
                        Class<? extends FlatLaf> class_) implements Theme {

    @Override
    public FlatLaf createLaf() throws Exception {
        return class_.getConstructor().newInstance();
    }

    @Override
    public boolean isIntellijTheme() {
        return false;
    }

    @Override
    public boolean isCoreTheme() {
        return true;
    }
}