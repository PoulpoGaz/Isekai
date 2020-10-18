package fr.poulpogaz.isekai.editor.settings;

import fr.poulpogaz.json.tree.JsonElement;
import fr.poulpogaz.json.tree.JsonObject;

import java.util.Hashtable;

public class SettingGroup extends SettingObject {

    private Hashtable<String, SettingObject> settings;

    public SettingGroup(String name) {
        super(name);
        settings = new Hashtable<>();
    }

    @Override
    public void init() {
        for (SettingObject object : settings.values()) {
            object.init();
        }
    }

    @Override
    public void write(JsonObject parent) {
        JsonObject group = new JsonObject();

        for (SettingObject object : settings.values()) {
            object.write(group);
        }

        parent.put(name, group);
    }

    @Override
    public void read(JsonObject parent) {
        JsonElement element = parent.get(name);

        if (element != null && element.isObject()) {
            JsonObject jsonObject = (JsonObject) element;

            for (SettingObject object : settings.values()) {
                object.read(jsonObject);
            }
        }
    }

    public void add(SettingObject object) {
        settings.put(object.getName(), object);

        object.parent = this;
    }

    public void remove(SettingObject object) {
        settings.remove(object.getName(), object);

        object.parent = null;
    }

    public SettingObject get(String name) {
        return settings.get(name);
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    @Override
    public boolean isElement() {
        return false;
    }
}