package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.ui.JLabeledComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import static fr.poulpogaz.isekai.editor.pack.Level.*;

public class ResizePanel extends JPanel {

    private JSpinner widthSpinner;
    private JSpinner heightSpinner;

    public ResizePanel() {
        initComponents();
    }

    private void initComponents() {
        widthSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_MAP_WIDTH, MINIMUM_MAP_WIDTH, MAXIMUM_MAP_WIDTH, 1));
        heightSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_MAP_HEIGHT, MINIMUM_MAP_HEIGHT, MAXIMUM_MAP_HEIGHT, 1));

        //widthSpinner.addChangeListener(this::fireResizeListener);
        //heightSpinner.addChangeListener(this::fireResizeListener);

        add(new JLabeledComponent("Width:", widthSpinner));
        add(new JLabeledComponent("Height:", heightSpinner));
    }

   /* private void fireResizeListener(ChangeEvent e) {
        ResizeListener[] listeners = listenerList.getListeners(ResizeListener.class);

        int nWidth = (int) widthSpinner.getValue();
        int nHeight = (int) heightSpinner.getValue();

        for (ResizeListener listener : listeners) {
            listener.sizeChanged(nWidth, nHeight);
        }
    }

    public void addResizeListener(ResizeListener listener) {
        listenerList.add(ResizeListener.class, listener);
    }

    public void removeResizeListener(ResizeListener listener) {
        listenerList.remove(ResizeListener.class, listener);
    }*/
}