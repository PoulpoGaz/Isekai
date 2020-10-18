package fr.poulpogaz.isekai.editor.settings;

import fr.poulpogaz.json.tree.JsonElement;
import fr.poulpogaz.json.tree.JsonObject;
import fr.poulpogaz.json.tree.JsonValue;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.util.Objects;

public class IntegerSetting extends SettingElement<Integer, JSpinner> {

    private boolean fire = true;

    private Integer min;
    private Integer max;
    private Integer step;

    public IntegerSetting(String name, Integer defaultValue) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
    }

    public IntegerSetting(String name, Integer defaultValue, Integer min, Integer max) {
        this(name, defaultValue, min, max, 1);
    }

    public IntegerSetting(String name, Integer defaultValue, Integer min, Integer max, Integer step) {
        super(name, defaultValue);

        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public JSpinner createComponent() {
        JSpinner component = new JSpinner();
        component.addChangeListener(this::onChange);
        setSpinnerModel(component);

        return component;
    }

    public void onChange(ChangeEvent event) {
        if (fire) {
            fireListener(VALUE_CHANGED);
        }

        value = (Integer) getComponent().getValue();
    }

    @Override
    public void write(JsonObject parent) {
        if (Objects.equals(value, defaultValue)) {
            return;
        }

        parent.put(name, value);
    }

    @Override
    public void read(JsonObject parent) {
        JsonElement element = parent.get(name);

        if (element != null && element.isValue()) {
            JsonValue jsonValue = (JsonValue) element;

            setValue(jsonValue.getAsInt());
        }
    }

    @Override
    public void setValue(Integer v) {
        synchronized (this) {
            fire = false;
            value = v;
            getComponent().setValue(v);
            fire = true;
        }
    }

    private void setSpinnerModel(JSpinner component) {
        component.setModel(new SpinnerNumberModel(value, min, max, step));
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        Objects.requireNonNull(min, "Min cannot be null");

        this.min = Math.min(min, max);
        this.max = Math.max(min, max);

        setSpinnerModel(getComponent());
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        Objects.requireNonNull(max, "Max cannot be null");

        this.max = Math.max(min, max);
        this.min = Math.min(min, max);

        setSpinnerModel(getComponent());
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = Objects.requireNonNull(step, "Step cannot be null");

        setSpinnerModel(getComponent());
    }
}