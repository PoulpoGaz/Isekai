package fr.poulpogaz.isekai.editor.ui.area;

import java.util.EventObject;

public class BAreaRegionSplitEvent extends EventObject {

    private final BRegion baseRegion;
    private final BRegion createdRegion;

    public BAreaRegionSplitEvent(Object source, BRegion baseRegion, BRegion createdRegion) {
        super(source);
        this.baseRegion = baseRegion;
        this.createdRegion = createdRegion;
    }

    public BRegion getBaseRegion() {
        return baseRegion;
    }

    public BRegion getCreatedRegion() {
        return createdRegion;
    }
}