package fr.poulpogaz.isekai.editor.ui.area;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class DefaultBAreaModel extends AbstractListModel<BRegionView> implements BAreaModel {

    private Vector<BRegionView> objects;

    private Component root;
    private ArrayList<BRegion> regions;

    public DefaultBAreaModel() {
        objects = new Vector<>();

        regions = new ArrayList<>();

        root = new BAreaRegion(new BRegionView[0], this);

        regions.add((BAreaRegion) root);
    }

    @Override
    public void addView(BRegionView region) {
        objects.addElement(region);

        fireIntervalAdded(this, objects.size() - 1, objects.size() - 1);
    }

    @Override
    public void removeView(BRegionView region) {
        int index = objects.indexOf(region);

        if (index != -1) {
            removeViewAt(index);
        }
    }

    @Override
    public void insertView(BRegionView region, int index) {
        objects.insertElementAt(region, index);

        fireIntervalAdded(region, index, index);
    }

    @Override
    public void removeViewAt(int index) {
        objects.remove(index);

        fireIntervalRemoved(this, index, index);
    }

    @Override
    protected void fireIntervalAdded(Object source, int index0, int index1) {
        for (BRegion region : getBRegions()) {
            for (int i = index0; i <= index1; i++) {
                region.addView(getElementAt(i));
            }
        }

        super.fireIntervalAdded(source, index0, index1);
    }

    @Override
    protected void fireIntervalRemoved(Object source, int index0, int index1) {
        for (BRegion region : getBRegions()) {
            for (int i = index0; i <= index1; i++) {
                region.removeView(getElementAt(i));
            }
        }

        super.fireIntervalRemoved(source, index0, index1);
    }

    public void removeAllElements() {
        if (objects.size() > 0) {

            int firstIndex = 0;
            int lastIndex = objects.size() - 1;

            objects.removeAllElements();
            fireIntervalRemoved(this, firstIndex, lastIndex);
        }
    }

    public void addAll(Collection<? extends BRegionView> c) {
        if (c.isEmpty()) {
            return;
        }

        int startIndex = getSize();

        objects.addAll(c);
        fireIntervalAdded(this, startIndex, getSize() - 1);
    }

    public void addAll(int index, Collection<? extends BRegionView> c) {
        if (index < 0 || index > getSize()) {
            throw new ArrayIndexOutOfBoundsException("index out of range: " + index);
        }

        if (c.isEmpty()) {
            return;
        }

        objects.addAll(index, c);
        fireIntervalAdded(this, index, index + c.size() - 1);
    }

    public int indexOf(BRegionView region) {
        return objects.indexOf(region);
    }

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public BRegionView getElementAt(int index) {
        return objects.get(index);
    }
    @Override
    public void addAreaSplitListener(BAreaListener listener) {
        listenerList.add(BAreaListener.class, listener);
    }

    @Override
    public void removeAreaSplitListener(BAreaListener listener) {
        listenerList.remove(BAreaListener.class, listener);
    }

    public BAreaListener[] getAreaSplitListeners() {
        return listenerList.getListeners(BAreaListener.class);
    }

    protected void fireRegionSplit(Object source, BRegion baseRegion, BRegion createdRegion) {
        Object[] listeners = listenerList.getListenerList();
        BAreaRegionSplitEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == BAreaListener.class) {

                if (e == null) {
                    e = new BAreaRegionSplitEvent(source, baseRegion, createdRegion);
                }

                ((BAreaListener) listeners[i+1]).regionSplit(e);
            }
        }
    }

    protected void fireRegionRemoved(Object source, BRegion deletedRegion, Component remainingComponent) {
        Object[] listeners = listenerList.getListenerList();
        BAreaRegionDeletedEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == BAreaListener.class) {

                if (e == null) {
                    e = new BAreaRegionDeletedEvent(source, deletedRegion, remainingComponent);
                }

                ((BAreaListener) listeners[i+1]).regionDeleted(e);
            }
        }
    }

    @Override
    public BRegion split(BRegion region, int orientation) {
        if (!regions.contains(region)) {
            return null;
        }

        BAreaRegion areaRegion = (BAreaRegion) region;
        areaRegion.setCloseButtonVisible(true);

        BAreaRegion newRegion = areaRegion.copy();
        BSplitPane pane = new BSplitPane(orientation);

        if (region == root) {
            root = pane;
        } else {
            BSplitPane parent = (BSplitPane) region.getParent();

            int dividerLocation = parent.getDividerLocation();

            boolean atLeft = parent.getLeftComponent() == region;

            if (atLeft) {
                parent.setLeftComponent(pane);
            } else {
                parent.setRightComponent(pane);
            }

            parent.setDividerLocation(dividerLocation);
        }

        pane.setLeftComponent(region);
        pane.setRightComponent(newRegion);

        regions.add(newRegion);

        fireRegionSplit(this, region, newRegion);

        return newRegion;
    }

    @Override
    public void delete(BRegion region) {
        if (!regions.contains(region)) {
            return;
        }

        BSplitPane parent = (BSplitPane) region.getParent();

        boolean isLeftComponent = parent.getLeftComponent() == region;
        Component siblingComponent = (isLeftComponent ? parent.getRightComponent() : parent.getLeftComponent());

        if (parent == root) {
            if (siblingComponent instanceof BAreaRegion) {
                ((BAreaRegion) siblingComponent).setCloseButtonVisible(false);
            }

            root = siblingComponent;
        } else {
            BSplitPane superParent = (BSplitPane) parent.getParent();

            int dividerLocation = superParent.getDividerLocation();
            boolean atLeft = superParent.getParent() == parent;

            if (atLeft) {
                superParent.setLeftComponent(siblingComponent);
            } else {
                superParent.setRightComponent(siblingComponent);
            }

            superParent.setDividerLocation(dividerLocation);
        }

        regions.remove(region);

        fireRegionRemoved(this, region, siblingComponent);
    }

    @Override
    public Component getRootComponent() {
        return root;
    }

    @Override
    public BRegion getRootRegionIfAvailable() {
        return (BRegion) root;
    }

    @Override
    public BRegion[] getBRegions() {
        return regions.toArray(new BRegion[0]);
    }
}