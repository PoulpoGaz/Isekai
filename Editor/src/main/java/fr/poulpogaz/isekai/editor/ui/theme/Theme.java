package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatLaf;
import fr.poulpogaz.isekai.editor.utils.LazyValue;

public abstract class Theme {

    private final LazyValue<? extends FlatLaf> laf;

    public Theme() {
        laf = createLaf();
    }

    protected abstract LazyValue<? extends FlatLaf> createLaf();

    public abstract boolean isIntellijTheme();

    public abstract boolean isCoreTheme();

    public abstract String getName();

    public abstract boolean isDark();

    public FlatLaf getLaf() {
        return laf.get();
    }
}