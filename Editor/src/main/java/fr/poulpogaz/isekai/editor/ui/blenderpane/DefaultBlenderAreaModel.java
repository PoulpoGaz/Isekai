package fr.poulpogaz.isekai.editor.ui.blenderpane;

import javax.swing.*;
import java.util.Vector;

public class DefaultBlenderAreaModel extends DefaultComboBoxModel<BlenderPanel> implements MutableBlenderAreaModel {

    public DefaultBlenderAreaModel() {
    }

    public DefaultBlenderAreaModel(BlenderPanel[] items) {
        super(items);
    }

    public DefaultBlenderAreaModel(Vector<BlenderPanel> v) {
        super(v);
    }

    @Override
    public BlenderAreaModel shallowCopy() {
        DefaultBlenderAreaModel model = new DefaultBlenderAreaModel();

        for (int i = 0; i < getSize(); i++) {
            model.addElement(getElementAt(i).shallowCopy());
        }

        int index = getIndexOf(getSelectedItem());

        model.setSelectedItem(model.getElementAt(index));

        return model;
    }
}