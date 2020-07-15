package fr.poulpogaz.isekai.editor.ui.blenderpane;

import fr.poulpogaz.isekai.editor.ui.icons.CloseIcon;
import fr.poulpogaz.isekai.editor.ui.icons.SplitHorizontallyIcon;
import fr.poulpogaz.isekai.editor.ui.icons.SplitVerticallyIcon;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;
import fr.poulpogaz.isekai.editor.utils.icons.ImagePostProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static com.formdev.flatlaf.FlatClientProperties.BUTTON_TYPE;
import static com.formdev.flatlaf.FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON;

public class BlenderSplitArea extends JSplitPane {

    private static final String uiClassID = "BlenderSplitAreaUI";

    protected static final boolean LEFT = false;

    protected static final boolean RIGHT = true;

    protected boolean isRoot;
    protected boolean hasChildren;


    public BlenderSplitArea(BlenderArea area) {
        isRoot = true;
        hasChildren = false;

        init(new BlenderAreaImpl(area.getModel().shallowCopy()));
    }

    protected BlenderSplitArea(BlenderAreaImpl area) {
        isRoot = false;
        hasChildren = false;

        init(area);
    }

    protected void init(BlenderAreaImpl area) {
        if (!isRoot) {
            area.setCloseButtonVisible(true);
        }

        setLeftComponent(area);
        setRightComponent(null);

        setDividerSize(0);
    }

    protected void split(int orientation) {
        setOrientation(orientation);
        setDividerLocation(0.5);

        BlenderAreaImpl left = getChild();
        BlenderAreaImpl right = (BlenderAreaImpl) left.shallowCopy();

        setLeftComponent(new BlenderSplitArea(left));
        setRightComponent(new BlenderSplitArea(right));

        setDividerSize(UIManager.getInt("BlenderSplitArea.dividerSize"));

        hasChildren = true;

        revalidate();
        repaint();
    }

    protected void close() {
        BlenderSplitArea parent = (BlenderSplitArea) getParent();

        boolean leftOrRight = parent.getLeftComponent() == this ? RIGHT : LEFT;

        parent.close(leftOrRight);
    }

    protected void close(boolean leftOrRight) {
        if (leftOrRight == LEFT) {
            close((BlenderSplitArea) getLeftComponent());
        } else {
            close((BlenderSplitArea) getRightComponent());
        }

        revalidate();
        repaint();
    }

    protected void close(BlenderSplitArea splitArea) {
        if (splitArea.hasChildren()) {
            setDividerLocation(splitArea.getDividerLocation());
            setOrientation(splitArea.getOrientation());

            setLeftComponent(splitArea.getLeftComponent());
            setRightComponent(splitArea.getRightComponent());

        } else {
            BlenderAreaImpl area = splitArea.getChild();

            if (isRoot()) {
                area.setCloseButtonVisible(false);
            }

            setLeftComponent(area);
            setRightComponent(null);

            setDividerSize(0);

            hasChildren = false;
        }
    }

    protected BlenderAreaImpl getChild() {
        Component left = getLeftComponent();

        if (left != null) {
            return (BlenderAreaImpl) left;
        } else {
            return (BlenderAreaImpl) getRightComponent();
        }
    }

    @Override
    public void updateUI() {
        setUI((BlenderSplitAreaUI) UIManager.getUI(this));
    }

    @Override
    public BlenderSplitAreaUI getUI() {
        return (BlenderSplitAreaUI) ui;
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean hasChildren() {
        return hasChildren;
    }

    protected static class BlenderAreaImpl extends BlenderArea {

        protected JButton horizontalSplitButton;
        protected JButton verticalSplitButton;
        protected JButton closeButton;

        protected JMenuItem horizontalSplitMenu;
        protected JMenuItem verticalSplitMenu;
        protected JMenuItem closeMenu;
        protected JMenuItem hideOrShowMenu;

        protected boolean areButtonsVisible = true;
        protected boolean isCloseButtonVisible = false;

        public BlenderAreaImpl(BlenderAreaModel model) {
            super(model);
        }

        @Override
        public BlenderArea shallowCopy() {
            return new BlenderAreaImpl(model.shallowCopy());
        }

        protected void setCloseButtonVisible(boolean visible) {
            isCloseButtonVisible = visible;

            if (areButtonsVisible) {
                closeButton.setVisible(visible);
            }

            closeMenu.setVisible(visible);

            revalidate();
            repaint();
        }

        @Override
        protected JMenuBar createMenuBar() {
            createHorizontalSplitButton();
            createVerticalSplitButton();
            createCloseButton();

            JMenuBar menuBar = super.createMenuBar();

            menuBar.add(horizontalSplitButton);
            menuBar.add(verticalSplitButton);
            menuBar.add(closeButton);

            return menuBar;
        }

        protected void createHideOrShowMenu() {
            hideOrShowMenu = new JMenuItem();
            hideOrShowMenu.setText("Hide bar buttons");

            hideOrShowMenu.addActionListener((e) -> {
                setButtonsVisible(!areButtonsVisible);

                if (areButtonsVisible) {
                    hideOrShowMenu.setText("Hide bar buttons");
                } else {
                    hideOrShowMenu.setText("Show bat buttons");
                }
            });
        }

        protected void setButtonsVisible(boolean visible) {
            areButtonsVisible = visible;

            horizontalSplitButton.setVisible(visible);
            verticalSplitButton.setVisible(visible);

            if (isCloseButtonVisible) {
                closeButton.setVisible(visible);
            }
        }

        @Override
        protected JPopupMenu createMenuBarPopupMenu() {
            JPopupMenu menu = super.createMenuBarPopupMenu();

            createHideOrShowMenu();

            menu.addSeparator();

            menu.add(hideOrShowMenu);

            menu.add(horizontalSplitMenu);
            menu.add(verticalSplitMenu);
            menu.add(closeMenu);

            return menu;
        }

        protected void createCloseButton() {
            closeButton = new JButton();

            closeButton.setToolTipText("Close");
            closeButton.setIcon(IconLoader.load(CloseIcon.IDENTIFIER, CloseIcon.class));
            closeButton.putClientProperty(BUTTON_TYPE, BUTTON_TYPE_TOOLBAR_BUTTON);

            closeButton.addActionListener((e) -> close());

            closeButton.setVisible(false);

            closeMenu = new JMenuItem("Close");
            closeMenu.setIcon(IconLoader.load(CloseIcon.IDENTIFIER, CloseIcon.class));
            closeMenu.addActionListener((e) -> close());
            closeMenu.setVisible(false);
        }

        protected void createHorizontalSplitButton() {
            horizontalSplitButton = new JButton();

            horizontalSplitButton.setToolTipText("Vertical split");
            horizontalSplitButton.setIcon(IconLoader.load(SplitVerticallyIcon.IDENTIFIER, SplitVerticallyIcon.class));
            horizontalSplitButton.putClientProperty(BUTTON_TYPE, BUTTON_TYPE_TOOLBAR_BUTTON);

            horizontalSplitButton.addActionListener((e) -> split(HORIZONTAL_SPLIT));

            horizontalSplitMenu = new JMenuItem("Vertical split");
            horizontalSplitMenu.setIcon(IconLoader.load(SplitVerticallyIcon.IDENTIFIER, SplitVerticallyIcon.class));
            horizontalSplitMenu.addActionListener((e) -> split(HORIZONTAL_SPLIT));
        }

        protected void createVerticalSplitButton() {
            verticalSplitButton = new JButton();

            verticalSplitButton.setToolTipText("Horizontal split");
            verticalSplitButton.setIcon(IconLoader.load(SplitHorizontallyIcon.IDENTIFIER, SplitHorizontallyIcon.class));
            verticalSplitButton.putClientProperty(BUTTON_TYPE, BUTTON_TYPE_TOOLBAR_BUTTON);

            verticalSplitButton.addActionListener((e) -> split(VERTICAL_SPLIT));

            verticalSplitMenu = new JMenuItem("Horizontal split");
            verticalSplitMenu.setIcon(IconLoader.load(SplitHorizontallyIcon.IDENTIFIER, SplitHorizontallyIcon.class));
            verticalSplitMenu.addActionListener((e) -> split(VERTICAL_SPLIT));
        }

        protected void close() {
            ((BlenderSplitArea) getParent()).close();
        }

        protected void split(int orientation) {
            ((BlenderSplitArea) getParent()).split(orientation);
        }
    }
}