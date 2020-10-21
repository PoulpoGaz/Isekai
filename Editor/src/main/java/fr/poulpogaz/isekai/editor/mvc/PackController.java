package fr.poulpogaz.isekai.editor.mvc;

import fr.poulpogaz.isekai.editor.pack.Pack;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class PackController implements PropertyChangeListener {

    private Pack pack;
    private List<PackView> views;

    public PackController(Pack pack) {
        this.pack = pack;

        views = new ArrayList<>();
    }

    public void addView(PackView view) {
        views.add(view);
    }

    public void removeView(PackView view) {
        views.remove(view);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        for (PackView view : views) {
            view.modelPropertyChange(evt);
        }
    }

    // simple, just redirect
    public String getName() {
        return pack.getName();
    }

    public void setName(String name) {
        pack.setName(name);
    }

    public String getAuthor() {
        return pack.getAuthor();
    }

    public void setAuthor(String author) {
        pack.setAuthor(author);
    }

    public String getVersion() {
        return pack.getVersion();
    }

    public void setVersion(String version) {
        pack.setVersion(version);
    }
}