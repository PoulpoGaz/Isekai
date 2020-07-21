package fr.poulpogaz.isekai.editor.ui.area;

import java.util.EventListener;

public interface BAreaListener extends EventListener {

    void regionSplit(BAreaRegionSplitEvent event);

    void regionDeleted(BAreaRegionDeletedEvent event);
}
