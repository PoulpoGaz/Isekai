package fr.poulpogaz.isekai.editor.ui.blenderpane;

import javax.swing.*;

public interface BlenderAreaModel extends ComboBoxModel<BlenderPanel> {

    BlenderAreaModel shallowCopy();
}