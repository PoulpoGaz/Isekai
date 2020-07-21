package fr.poulpogaz.isekai.editor.ui.area;

import com.formdev.flatlaf.ui.FlatSplitPaneUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class BSplitPaneUI extends FlatSplitPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new BSplitPaneUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();

        Integer temp = (Integer) UIManager.get("BSplitPane.dividerSize");
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

            setBackground(UIManager.getColor("BSplitPane.dividerBackground"));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}