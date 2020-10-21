package fr.poulpogaz.isekai.editor.settings;

import fr.poulpogaz.isekai.editor.ui.text.JPathTextField;
import fr.poulpogaz.json.tree.JsonElement;
import fr.poulpogaz.json.tree.JsonObject;
import fr.poulpogaz.json.tree.JsonValue;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.Objects;

public class PathSetting extends SettingElement<File, JPathTextField> {

    private boolean fire = true;

    public PathSetting(String name, File defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public JPathTextField createComponent() {
        JPathTextField component = new JPathTextField();
        component.setPath(defaultValue);
        component.getChooser().addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, this::onChange);

        return component;
    }

    public void onChange(PropertyChangeEvent event) {
        if (fire) {
            fireListener(VALUE_CHANGED);
        }

        value = getComponent().getPath();
    }

    @Override
    public void write(JsonObject object) {
        if (Objects.equals(value, defaultValue)) {
            return;
        }

        object.put(name, value.getAbsolutePath());
    }

    @Override
    public void read(JsonObject object) {
        JsonElement element = object.get(name);

        if (element != null && element.isValue()) {
            JsonValue jsonValue = (JsonValue) element;

            File f = new File(jsonValue.getAsString());

            if (getComponent().accept(f)) {
                setValue(f);
            }
        }
    }

    @Override
    public void setValue(File v) {
        synchronized (PathSetting.this) {
            fire = false;

            value = v;
            getComponent().setPath(v);

            fire = true;
        }
    }
}