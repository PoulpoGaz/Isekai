package fr.poulpogaz.isekai.editor.ui.area;

import javax.swing.*;

public interface BRegionModel extends MutableComboBoxModel<BRegionView> {

    BRegionModel copy();
}