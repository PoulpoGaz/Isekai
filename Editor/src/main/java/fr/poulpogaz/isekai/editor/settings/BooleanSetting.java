package fr.poulpogaz.isekai.editor.settings;

import fr.poulpogaz.json.tree.JsonElement;
import fr.poulpogaz.json.tree.JsonObject;
import fr.poulpogaz.json.tree.JsonValue;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Objects;

public class BooleanSetting extends SettingElement<Boolean, JCheckBox> {

    private boolean fire = true;

    public BooleanSetting(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public JCheckBox createComponent() {
        JCheckBox component = new JCheckBox();
        component.setSelected(value);
        component.addItemListener(this::onChange);

        return component;
    }

    public void onChange(ItemEvent event) {
        if (fire) {
            fireListener(VALUE_CHANGED);
        }

        value = getComponent().isSelected();
    }

    @Override
    public void write(JsonObject object) {
        if (Objects.equals(value, defaultValue)) {
            return;
        }

        object.put(name, value);
    }

    @Override
    public void read(JsonObject object) {
        JsonElement element = object.get(name);

        if (element != null && element.isValue()) {
            JsonValue jsonValue = (JsonValue) element;

            setValue(jsonValue.getAsBoolean());
        }
    }

    @Override
    public void setValue(Boolean v) {
        synchronized (BooleanSetting.this) {
            fire = false;

            getComponent().setSelected(v);
            value = v;
        }
    }
}