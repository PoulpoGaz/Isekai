package fr.poulpogaz.isekai.editor.ui.blenderpane;

import com.formdev.flatlaf.ui.FlatSplitPaneUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class BlenderSplitAreaUI extends FlatSplitPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new BlenderSplitAreaUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();

        Integer temp = (Integer) UIManager.get("BlenderSplitArea.dividerSize");
        LookAndFeel.installProperty(splitPane, "dividerSize", temp == null ? 4 : temp);

        divider.setDividerSize(splitPane.getDividerSize());
        dividerSize = divider.getDividerSize();
    }

    @Override
    public BasicSplitPaneDivider createDefaultDivider() {
        return new BlenderSplitPaneDivider(this);
    }

    protected class BlenderSplitPaneDivider extends FlatSplitPaneDivider {

        protected BlenderSplitPaneDivider(BasicSplitPaneUI ui) {
            super(ui);

            updateBackground();
        }

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            super.propertyChange(e);

            if (e.getSource() == splitPane) {
                if (e.getNewValue() == JSplitPane.ORIENTATION_PROPERTY) {
                    updateBackground();
                }
            }
        }

        protected void updateBackground() {
            if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                setBackground(UIManager.getColor("BlenderSplitArea.dividerBackground"));
            } else {
                setBackground(null);
            }

            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}