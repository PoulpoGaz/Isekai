package fr.poulpogaz.isekai.editor.settings;

import fr.poulpogaz.json.tree.JsonObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class SettingElement<T, C extends Component> extends SettingObject {

    public static final String INIT = "init";
    public static final String VALUE_CHANGED = "value_changed";

    private final HashMap<String, List<Consumer<SettingElement<T, C>>>> listeners = new HashMap<>();

    protected final T defaultValue;

    protected T value;
    private C component;

    public SettingElement(String name, T defaultValue) {
        super(name);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public void init() {
        fireListener(INIT);
    }

    public abstract C createComponent();

    public void on(String event, Consumer<SettingElement<T, C>> listener) {
        List<Consumer<SettingElement<T, C>>> listeners = this.listeners.computeIfAbsent(event, k -> new ArrayList<>());

        listeners.add(listener);
    }

    protected void fireListener(String category) {
        List<Consumer<SettingElement<T, C>>> listeners = this.listeners.get(category);

        if (listeners != null) {
            for (Consumer<SettingElement<T, C>> listener : listeners) {
                listener.accept(this);
            }
        }
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (this.value != value) {
            this.value = value;

            fireListener(VALUE_CHANGED);
        }
    }

    public C getComponent() {
        if (component == null) {
            component = createComponent();
        }

        return component;
    }

    public void setComponent(C component) {
        this.component = component;
    }

    protected boolean isComponentNull() {
        return component == null;
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    @Override
    public boolean isElement() {
        return true;
    }
}