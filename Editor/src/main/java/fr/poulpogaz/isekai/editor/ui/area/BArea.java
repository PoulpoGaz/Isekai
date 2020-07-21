package fr.poulpogaz.isekai.editor.ui.area;

import javax.swing.*;
import java.awt.*;

public class BArea extends JPanel implements BAreaListener {

    public static final String MODEL_PROPERTY = "ModelProperty";

    private BAreaModel model;

    private Component root;

    public BArea() {
        setLayout(new BorderLayout());
        setModel(new DefaultBAreaModel());
    }

    public BArea(BAreaModel model) {
        setLayout(new BorderLayout());
        setModel(model == null ? new DefaultBAreaModel() : model);
    }

    public void addView(BRegionView region) {
        model.addView(region);
    }

    public void removeView(BRegionView region) {
        model.removeView(region);
    }

    public void insertView(BRegionView region, int index) {
        model.insertView(region, index);
    }

    public void removeViewAt(int index) {
        model.removeViewAt(index);
    }

    public BRegion split(BRegion region, int orientation) {
        return model.split(region, orientation);
    }

    public void delete(BRegion region) {
        model.delete(region);
    }

    public BRegion getRootRegionIfAvailable() {
        return model.getRootRegionIfAvailable();
    }

    public BRegion[] getBRegions() {
        return model.getBRegions();
    }

    @Override
    public void regionSplit(BAreaRegionSplitEvent event) {
        if (model.getRootComponent() != root) {
            setRoot(model.getRootComponent());
        }

        EventQueue.invokeLater(() -> {
            revalidate();

            ((BSplitPane) event.getBaseRegion().getParent()).setDividerLocation(0.5);

            repaint();
        });
    }

    @Override
    public void regionDeleted(BAreaRegionDeletedEvent event) {
        if (model.getRootComponent() != root) {
            setRoot(model.getRootComponent());
        }

        revalidate();
        repaint();
    }

    protected void setRoot(Component root) {
        this.root = root;

        removeAll();

        add(root, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public BAreaModel getModel() {
        return model;
    }

    public void setModel(BAreaModel model) {
        if (this.model != model && model != null) {
            BAreaModel old = this.model;

            if (old != null) {
                old.removeAreaSplitListener(this);
            }

            this.model = model;

            root = model.getRootComponent();
            setRoot(root);

            model.addAreaSplitListener(this);

            firePropertyChange(MODEL_PROPERTY, old, model);
        }
    }
}