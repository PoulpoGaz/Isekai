package fr.poulpogaz.isekai.editor.ui.progressbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IskProgressBar extends JProgressBar {

    private ActionListener actionListener;

    private ButtonModel buttonModel;

    public IskProgressBar() {
        this(HORIZONTAL);
    }

    public IskProgressBar(int orient) {
        this(orient, 0, 100);
    }

    public IskProgressBar(int min, int max) {
        this(HORIZONTAL, min, max);
    }

    public IskProgressBar(int orient, int min, int max) {
        super(orient, min, max);

        init();
    }

    public IskProgressBar(BoundedRangeModel newModel) {
        super(newModel);

        init();
    }

    protected void init() {
        setButtonModel(new DefaultButtonModel());
        addMouseListener(new InputListener());
    }

    @Override
    public void removeNotify() {
        super.removeNotify();

        if(getModel() instanceof StringBoundedRangeModel) {
            ((StringBoundedRangeModel) getModel()).uninstallListener();
        }
    }

    public void addActionListener(ActionListener actionListener) {
        buttonModel.addActionListener(actionListener);
        this.actionListener = AWTEventMulticaster.add(actionListener, actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        buttonModel.removeActionListener(actionListener);
        this.actionListener = AWTEventMulticaster.remove(actionListener, actionListener);
    }

    public ButtonModel getButtonModel() {
        return buttonModel;
    }

    public void setButtonModel(ButtonModel buttonModel) {
        if(buttonModel != null) {
            ButtonModel old = getButtonModel();

            if(old != null) {
                old.removeActionListener(actionListener);
            }

            this.buttonModel = buttonModel;

            buttonModel.setEnabled(old == null || old.isEnabled());
            buttonModel.addActionListener(actionListener);
            firePropertyChange("model", old, buttonModel);
        }
    }

    public boolean isSelected() {
        return buttonModel.isSelected();
    }

    public void setSelected(boolean selected) {
        buttonModel.setSelected(selected);
    }

    public boolean isArmed() {
        return buttonModel.isArmed();
    }

    public void setArmed(boolean armed) {
        buttonModel.setArmed(armed);
    }

    public boolean isPressed() {
        return buttonModel.isPressed();
    }

    public void setPressed(boolean pressed) {
        buttonModel.setPressed(pressed);
    }

    public boolean isRollover() {
        return buttonModel.isRollover();
    }

    public void setRollover(boolean rollover) {
        buttonModel.setRollover(rollover);
    }


    private static class InputListener implements FocusListener, MouseListener {

        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            IskProgressBar bar = (IskProgressBar) e.getSource();

            ButtonModel model = bar.getButtonModel();
            model.setArmed(false);
            model.setPressed(false);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            IskProgressBar bar = (IskProgressBar) e.getSource();

            if(SwingUtilities.isLeftMouseButton(e)) {
                if(bar.contains(e.getX(), e.getY())) {
                    ButtonModel model = bar.getButtonModel();

                    if (model.isEnabled()) {
                        if (!model.isArmed()) {
                            model.setArmed(true);
                        }
                        model.setPressed(true);
                    }
                    if (bar.isEnabled() && !bar.hasFocus() && bar.isRequestFocusEnabled()) {
                        bar.requestFocus();
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            IskProgressBar bar = (IskProgressBar) e.getSource();

            if(SwingUtilities.isLeftMouseButton(e)) {
                ButtonModel model = bar.getButtonModel();
                model.setPressed(false);
                model.setArmed(false);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            IskProgressBar bar = (IskProgressBar) e.getSource();

            ButtonModel model = bar.getButtonModel();

            if(!SwingUtilities.isLeftMouseButton(e)) {
                model.setRollover(true);
            }
            if(model.isPressed()) {
                model.setArmed(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            IskProgressBar bar = (IskProgressBar) e.getSource();

            ButtonModel model = bar.getButtonModel();

            model.setRollover(false);
            model.setArmed(false);
        }
    }
}