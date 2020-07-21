package fr.poulpogaz.isekai.editor.ui.area;

import javax.swing.*;
import java.awt.*;

/**
 * An object that contains all type of regions and
 * the structure of the {@code BArea}.
 */
public interface BAreaModel extends ListModel<BRegionView> {

    void addView(BRegionView region);

    void removeView(BRegionView region);

    void insertView(BRegionView region, int index);

    void removeViewAt(int index);


    void addAreaSplitListener(BAreaListener listener);

    void removeAreaSplitListener(BAreaListener listener);

    BRegion split(BRegion region, int orientation);

    void delete(BRegion region);

    Component getRootComponent();

    BRegion getRootRegionIfAvailable();

    BRegion[] getBRegions();
}