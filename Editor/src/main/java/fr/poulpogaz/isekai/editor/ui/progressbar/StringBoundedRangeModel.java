package fr.poulpogaz.isekai.editor.ui.progressbar;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.util.function.Function;

public class StringBoundedRangeModel extends DefaultBoundedRangeModel {

    private ChangeListener listener;

    private final JProgressBar bar;
    private Function<JProgressBar, String> converter;

    public StringBoundedRangeModel(JProgressBar bar) {
        this.bar = bar;

        installListener();
    }

    public StringBoundedRangeModel(JProgressBar bar, int value, int extent, int min, int max) {
        super(value, extent, min, max);
        this.bar = bar;

        installListener();
    }

    protected void installListener() {
        listener = (listener) -> updateString();

        bar.addChangeListener(listener);

        converter = (bar) -> String.valueOf(bar.getValue());
    }

    public void uninstallListener() {
        bar.removeChangeListener(listener);
    }

    protected void updateString() {
        if(converter != null) {
            bar.setString(getText());
        }
    }

    public String getText() {
        return converter.apply(bar);
    }

    public void setConverter(Function<JProgressBar, String> converter) {
        this.converter = converter;
        fireStateChanged();
        updateString();
    }

    public Function<JProgressBar, String> getConverter() {
        return converter;
    }
}