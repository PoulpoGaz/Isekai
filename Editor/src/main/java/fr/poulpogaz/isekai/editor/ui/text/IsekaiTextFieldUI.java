package fr.poulpogaz.isekai.editor.ui.text;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatBorder;
import com.formdev.flatlaf.ui.FlatCaret;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.ui.MigLayoutVisualPadding;
import fr.poulpogaz.isekai.editor.utils.Math2;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

import static com.formdev.flatlaf.util.UIScale.scale;

/**
 * @see com.formdev.flatlaf.ui.FlatTextFieldUI
 */
public class IsekaiTextFieldUI extends BasicTextFieldUI {

    private static final boolean MINIMUM = false;
    private static final boolean PREFERRED = true;

    private static final int LEFT_TO_RIGHT = 0;
    private static final int RIGHT_TO_LEFT = 1;
    private static final int DO_NOT_MIND = 2;

    private int focusWidth;
    private int minimumWidth;
    private Color placeholderForeground;

    private MouseListener mouseListener;
    private CursorHoverListener trailCursorListener;
    private CursorHoverListener leadCursorListener;

    private Component trailingComponent;
    private Component leadingComponent;

    private FocusListener focusListener;

    private Color hoverBackgroundColor;
    private Color focusBackgroundColor;

    private boolean hasFocus = false;

    public static ComponentUI createUI(JComponent c) {
        return new IsekaiTextFieldUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();

        String prefix = getPropertyPrefix();
        hoverBackgroundColor = UIManager.getColor(prefix + ".hoverBackgroundColor");
        focusBackgroundColor = UIManager.getColor(prefix + ".focusBackgroundColor");

        focusWidth = UIManager.getInt("Component.focusWidth");
        minimumWidth = UIManager.getInt("Component.minimumWidth");
        placeholderForeground = UIManager.getColor( prefix + ".placeholderForeground");

        MigLayoutVisualPadding.install(getComponent());
    }

    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();

        placeholderForeground = null;

        MigLayoutVisualPadding.uninstall(getComponent());
    }

    @Override
    protected void installListeners() {
        super.installListeners();

        JTextComponent component = getComponent();

        mouseListener = new MouseListener(component);
        focusListener = new FocusListener(component);

        component.addMouseListener(mouseListener);
        component.addFocusListener(focusListener);

        if (component instanceof JIsekaiTextField) {
            JIsekaiTextField field = (JIsekaiTextField) component;

            setLeadingComponent(field.getLeadingComponent());
            setTrailingComponent(field.getTrailingComponent());
        }
    }

    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();

        getComponent().removeMouseListener(mouseListener);
        getComponent().removeFocusListener(focusListener);

        setLeadingComponent(null);
        setTrailingComponent(null);
    }

    @Override
    protected Caret createCaret() {
        return new FlatCaret(UIManager.getString("TextComponent.selectAllOnFocusPolicy"));
    }

    @Override
    protected void propertyChange(PropertyChangeEvent e) {
        super.propertyChange(e);

        if(FlatClientProperties.PLACEHOLDER_TEXT.equals(e.getPropertyName())) {
            getComponent().repaint();
        }
    }

    @Override
    protected void paintSafely(Graphics g) {
        super.paintSafely(g);

        if (getComponent() instanceof JIsekaiTextField) {
            paintPlaceholder(g, (JIsekaiTextField) getComponent());
        }
    }

    @Override
    protected void paintBackground(Graphics g) {
        JTextComponent c = getComponent();

        // do not paint background if:
        //   - not opaque and
        //   - border is not a flat border and
        //   - opaque was explicitly set (to false)
        // (same behaviour as in AquaTextFieldUI)
        if(!c.isOpaque() && !(c.getBorder() instanceof FlatBorder) && FlatUIUtils.hasOpaqueBeenExplicitlySet(c)) {
            return;
        }

        // fill background if opaque to avoid garbage if user sets opaque to true
        if(c.isOpaque() && focusWidth > 0) {
            FlatUIUtils.paintParentBackground(g, c);
        }

        // paint background
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            FlatUIUtils.setRenderingHints( g2 );

            float fFocusWidth = (c.getBorder() instanceof FlatBorder) ? scale( (float) focusWidth ) : 0;

            g2.setColor(getBackground(c));
            FlatUIUtils.paintComponentBackground(g2, 0, 0, c.getWidth(), c.getHeight(), fFocusWidth, 0);
        } finally {
            g2.dispose();
        }
    }

    protected void paintPlaceholder(Graphics g, JIsekaiTextField c) {
        // check whether text component is empty
        if (c.getDocument().getLength() > 0) {
            return;
        }

        // get placeholder text
        String placeholder = c.getPlaceholder();
        if(placeholder == null) {
            return;
        }

        // compute placeholder location
        Insets insets = c.getInsets();
        FontMetrics fm = c.getFontMetrics(c.getFont());

        Dimension leadDim = getLeadingDim(getOrientation(c), true);

        int x = insets.left + leadDim.width;
        int y = insets.top + fm.getAscent() + ((c.getHeight() - insets.top - insets.bottom - fm.getHeight()) / 2);

        // paint placeholder
        g.setColor(placeholderForeground);
        FlatUIUtils.drawString(c, g, placeholder, x, y);
    }

    @Override
    protected Rectangle getVisibleEditorRect() {
        Rectangle alloc = super.getVisibleEditorRect();

        if (alloc != null) {
            int leftToRight = getOrientation(getComponent());

            Dimension trailDim = getTrailingDim(leftToRight, true);
            Dimension leadDim = getLeadingDim(leftToRight, true);

            alloc.x     = alloc.x + leadDim.width;
            alloc.width = alloc.width - leadDim.width - trailDim.width;
        }

        return alloc;
    }

    private Dimension getTrailingDim(int orientation, boolean prefOrMin) {
        Component comp;

        if (orientation == DO_NOT_MIND || orientation == LEFT_TO_RIGHT) {
            comp = trailingComponent;
        } else {
            comp = leadingComponent;
        }

        return comp == null ? new Dimension() :
                prefOrMin ? comp.getPreferredSize() : comp.getMinimumSize();
    }

    private Dimension getLeadingDim(int orientation, boolean prefOrMin) {
        Component comp;

        if (orientation == DO_NOT_MIND || orientation == LEFT_TO_RIGHT) {
            comp = leadingComponent;
        } else {
            comp = trailingComponent;
        }

        return comp == null ? new Dimension() :
                prefOrMin ? comp.getPreferredSize() : comp.getMinimumSize();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return getSize(c, PREFERRED);
    }

    @Override
    public Dimension getMinimumSize(JComponent c) {
        return getSize(c, MINIMUM);
    }

    protected Dimension getSize(JComponent c, boolean prefOrMin) {
        Dimension dim = super.getPreferredSize(c);
        Insets insets = c.getInsets();

        if (c instanceof JIsekaiTextField) {
            Dimension trailDim = getTrailingDim(DO_NOT_MIND, prefOrMin);
            Dimension leadDim = getLeadingDim(DO_NOT_MIND, prefOrMin);

            dim.width += trailDim.width + leadDim.width;

            int height = dim.height - insets.top - insets.bottom;

            height = Math2.max(height, trailDim.height, leadDim.height);

            dim.height = height + insets.top + insets.bottom;
        }

        return applyMinimumWidth(dim, c);
    }

    private Dimension applyMinimumWidth(Dimension size, JComponent c) {
        // do not apply minimum width if JTextField.columns is set
        if(c instanceof JTextField && ((JTextField)c).getColumns() > 0) {
            return size;
        }

        Container parent = c.getParent();
        if(parent instanceof JComboBox || parent instanceof JSpinner || (parent != null && parent.getParent() instanceof JSpinner)) {
            return size;
        }

        Dimension trailDim = getTrailingDim(DO_NOT_MIND, MINIMUM);
        Dimension leadDim = getLeadingDim(DO_NOT_MIND, MINIMUM);

        int minimumWidth = FlatUIUtils.minimumWidth(getComponent(), this.minimumWidth) + trailDim.width + leadDim.width;
        int focusWidth = (c.getBorder() instanceof FlatBorder) ? this.focusWidth : 0;

        size.width = Math.max(size.width, scale(minimumWidth + (focusWidth * 2)));

        return size;
    }

    private int getOrientation(Component c) {
        boolean leftToRight = c.getComponentOrientation().isLeftToRight();

        return leftToRight ? LEFT_TO_RIGHT : RIGHT_TO_LEFT;
    }

    private Color getBackground(Component c) {
        if(hasFocus) {
            return focusBackgroundColor;
        } else if(mouseListener.isHover()) {
            return hoverBackgroundColor;
        } else {
            return c.getBackground();
        }
    }

    public void setTrailingComponent(Component trailingComponent) {
        if (this.trailingComponent != trailingComponent && getComponent() instanceof JIsekaiTextField) {

            if (this.trailingComponent != null) {
                this.trailingComponent.removeMouseListener(trailCursorListener);
            }

            this.trailingComponent = trailingComponent;

            trailCursorListener = new CursorHoverListener(trailingComponent);
            trailingComponent.addMouseListener(trailCursorListener);
        }
    }

    public void setLeadingComponent(Component leadingComponent) {
        if (this.leadingComponent != leadingComponent && getComponent() instanceof JIsekaiTextField) {

            if (this.leadingComponent != null) {
                this.leadingComponent.removeMouseListener(leadCursorListener);
            }

            this.leadingComponent = leadingComponent;

            leadCursorListener = new CursorHoverListener(leadingComponent);
            leadingComponent.addMouseListener(leadCursorListener);
        }
    }

    private static class MouseListener extends MouseAdapter {

        private final Component component;
        private boolean isHover;

        MouseListener(Component component) {
            this.component = component;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isHover = false;
            component.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            isHover = true;
            component.repaint();
        }

        public boolean isHover() {
            return isHover;
        }
    }

    private class CursorHoverListener extends MouseAdapter {

        private final Cursor textCursor = new Cursor(Cursor.TEXT_CURSOR);
        private final Component component;

        public CursorHoverListener(Component component) {
            this.component = component;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            component.setCursor((getComponent().isEditable()) ? textCursor : null);
        }
    }

    private class FocusListener implements java.awt.event.FocusListener {

        private final Component component;

        FocusListener(Component component) {
            this.component = component;
        }

        @Override
        public void focusGained(FocusEvent e) {
            hasFocus = true;
            component.repaint();
        }

        @Override
        public void focusLost(FocusEvent e) {
            hasFocus = false;
            component.repaint();
        }
    }
}