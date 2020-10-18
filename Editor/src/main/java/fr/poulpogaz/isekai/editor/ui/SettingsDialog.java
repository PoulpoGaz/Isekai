package fr.poulpogaz.isekai.editor.ui;

import fr.poulpogaz.isekai.editor.settings.SettingElement;
import fr.poulpogaz.isekai.editor.settings.SettingGroup;
import fr.poulpogaz.isekai.editor.settings.SettingObject;
import fr.poulpogaz.isekai.editor.settings.Settings;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

public class SettingsDialog extends JDialog {

    private static final SettingGroup settings = Settings.getSettings();

    private final HashMap<TreePath, JPanel> cache = new HashMap<>();

    private JSplitPane container;
    private JTree tree;

    public SettingsDialog(Frame parent) {
        super(parent, "Settings", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);

        initComponents();
        pack();
        setMinimumSize(getSize());

        setLocationRelativeTo(null);
    }

    private void initComponents() {
        container = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tree = new JTree();
        tree.setMinimumSize(new Dimension(50, 50));
        tree.setModel(createTreeModel());
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener((e) -> setLeftPanel(e.getNewLeadSelectionPath()));
        tree.setCellRenderer(new CellRenderer());
        tree.setSelectionPath(tree.getPathForRow(0));

        container.setLeftComponent(tree);

        add(container);
    }

    private TreeModel createTreeModel() {
        MutableTreeNode root = createTreeNode(settings);

        return new DefaultTreeModel(root);
    }

    private MutableTreeNode createTreeNode(SettingObject object) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(object);

        if (object.isGroup()) {
            SettingGroup group = (SettingGroup) object;

            for (SettingObject child : group.getChilds()) {
                if (child.isElement()) {
                    continue;
                }

                node.add(createTreeNode(child));
            }
        }

        return node;
    }

    private void setLeftPanel(TreePath path) {
        if (cache.containsKey(path)) {
            container.setRightComponent(cache.get(path));
            return;
        }

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

        SettingGroup group = (SettingGroup) node.getUserObject();
        JPanel panel = createSettingPanel(group, node);

        cache.put(path, panel);
        container.setRightComponent(panel);
    }

    private JPanel createSettingPanel(SettingGroup group, DefaultMutableTreeNode node) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new VerticalLayout());

        VerticalConstraint constraint = new VerticalConstraint();
        constraint.xAlignment = 0;
        constraint.fillXAxis = true;

        JLabel title = new JLabel(group.getName());
        title.setFont(title.getFont().deriveFont(18f));

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));

        int i = 0;
        for (SettingObject object : group.getChilds()) {
            if (object.isElement()) {
                JPanel elementPanel = new JPanel();
                elementPanel.setLayout(new HorizontalLayout());

                HorizontalConstraint horizontalConstraint = new HorizontalConstraint();
                horizontalConstraint.endComponent = true;

                elementPanel.add(new JLabel(object.getName()));
                elementPanel.add(Box.createHorizontalStrut(10));
                elementPanel.add(((SettingElement<?, ?>) object).getComponent(), horizontalConstraint);

                panel.add(elementPanel, constraint);
            } else {
                JLabelLink link = new JLabelLink(object.getName());

                final int tempI = i;
                link.addActionListener((e) -> {
                    DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(tempI);

                    TreePath path = new TreePath(n.getPath());
                    tree.setSelectionPath(path);
                });

                panel.add(link, constraint);
            }

            i++;
        }

        return panel;
    }

    private static class CellRenderer implements TreeCellRenderer {

        private final DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            SettingObject object = (SettingObject) node.getUserObject();

            renderer.setText(object.getName());

            return renderer;
        }
    }
}