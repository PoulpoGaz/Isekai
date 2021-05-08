package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatLaf;
import fr.poulpogaz.isekai.editor.utils.LazyValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

public class CoreTheme extends Theme {

    private static final Logger LOGGER = LogManager.getLogger(CoreTheme.class);

    private final Class<? extends FlatLaf> class_;
    private final String name;
    private final boolean dark;

    public CoreTheme(String name, boolean dark, Class<? extends FlatLaf> class_) {
        this.name = name;
        this.dark = dark;
        this.class_ = class_;
    }

    @Override
    protected LazyValue<? extends FlatLaf> createLaf() {
        return new LazyValue<>() {
            @Override
            protected FlatLaf create() {
                try {
                    FlatLaf laf = class_.getConstructor().newInstance();

                    LOGGER.info("Successfully instantiate core theme: {}", class_);

                    return laf;
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    LOGGER.warn("Failed to instantiate core theme: {}", class_);

                    return null;
                }
            }
        };
    }

    @Override
    public boolean isIntellijTheme() {
        return false;
    }

    @Override
    public boolean isCoreTheme() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isDark() {
        return dark;
    }
}