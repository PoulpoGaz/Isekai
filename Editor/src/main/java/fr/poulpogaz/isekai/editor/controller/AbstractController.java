package fr.poulpogaz.isekai.editor.controller;

import java.beans.PropertyChangeListener;

public class AbstractController<T extends Model> {

    private final Model model;

    public AbstractController(Model model) {
        this.model = model;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        model.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        model.addPropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        model.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        model.removePropertyChangeListener(property, listener);
    }
}