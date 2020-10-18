package fr.poulpogaz.isekai.editor.settings;

import fr.poulpogaz.json.tree.JsonObject;

import java.util.Objects;

public abstract class SettingObject {

    SettingObject parent = null;

    protected final String name;

    public SettingObject(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public abstract void init();

    public abstract void write(JsonObject parent);

    public abstract void read(JsonObject parent);

    public String getName() {
        return name;
    }

    public SettingObject getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public abstract boolean isGroup();

    public abstract boolean isElement();
}