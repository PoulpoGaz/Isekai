package fr.poulpogaz.isekai.editor.ui.area;

import java.awt.*;
import java.util.EventObject;

public class BAreaRegionDeletedEvent extends EventObject {

    private final BRegion deletedRegion;
    private final Component remainingComponent;

    public BAreaRegionDeletedEvent(Object source, BRegion deletedRegion, Component remainingComponent) {
        super(source);
        this.deletedRegion = deletedRegion;
        this.remainingComponent = remainingComponent;
    }

    public BRegion getDeletedRegion() {
        return deletedRegion;
    }

    public Component getRemainingComponent() {
        return remainingComponent;
    }
}