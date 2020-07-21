package fr.poulpogaz.isekai.editor.ui.area;

import fr.poulpogaz.isekai.editor.ui.icons.CloseIcon;
import fr.poulpogaz.isekai.editor.ui.icons.SplitHorizontallyIcon;
import fr.poulpogaz.isekai.editor.ui.icons.SplitVerticallyIcon;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import static com.formdev.flatlaf.FlatClientProperties.BUTTON_TYPE;
import static com.formdev.flatlaf.FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;

public class BAreaRegion extends BRegion {

    private BAreaModel areaModel;

    protected JButton horizontalSplitButton;
    protected JButton verticalSplitButton;
    protected JButton closeButton;

    protected JMenuItem horizontalSplitMenu;
    protected JMenuItem verticalSplitMenu;
    protected JMenuItem closeMenu;
    protected JMenuItem hideOrShowMenu;

    protected boolean areButtonsVisible = true;
    protected boolean isCloseButtonVisible = false;

    public BAreaRegion(BRegionView[] views, BAreaModel areaModel) {
        super(views);
        this.areaModel = areaModel;
    }

    public BAreaRegion(BRegionModel model, BAreaModel areaModel, boolean areButtonsVisible, boolean isCloseButtonVisible) {
        super(model);
        this.areaModel = areaModel;
        setButtonsVisible(areButtonsVisible);
        setCloseButtonVisible(isCloseButtonVisible);
    }

    @Override
    public BAreaRegion copy() {
        return new BAreaRegion(model.copy(), areaModel, areButtonsVisible, isCloseButtonVisible);
    }

    void setCloseButtonVisible(boolean visible) {
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
        areaModel.delete(this);
    }

    protected void split(int orientation) {
        areaModel.split(this, orientation);
    }
}