package fr.poulpogaz.isekai.editor.ui.area;

import javax.swing.*;
import java.util.Vector;

public class DefaultBRegionModel extends DefaultComboBoxModel<BRegionView> implements BRegionModel {

    public DefaultBRegionModel() {
    }

    public DefaultBRegionModel(BRegionView[] items) {
        super(items);
    }

    public DefaultBRegionModel(Vector<BRegionView> v) {
        super(v);
    }

    @Override
    public BRegionModel copy() {
        DefaultBRegionModel model = new DefaultBRegionModel();

        for (int i = 0; i < getSize(); i++) {
            model.addElement(getElementAt(i).copyWithDataLink());
        }

        int index = getIndexOf(getSelectedItem());

        model.setSelectedItem(model.getElementAt(index));

        return model;
    }
}