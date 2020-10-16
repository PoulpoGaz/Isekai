package fr.poulpogaz.isekai.editor.ui.editor;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Player;
import fr.poulpogaz.isekai.editor.pack.image.SpriteIcon;
import fr.poulpogaz.isekai.editor.tools.PlayerTool;
import fr.poulpogaz.isekai.editor.tools.ToolHelper;

import javax.swing.*;
import java.awt.*;

public class PlayerPosPanel extends JPanel {

    private final Pack pack = IsekaiEditor.getPack();
    private final ToolHelper toolHelper;

    public PlayerPosPanel(EditorPanel panel) {
        toolHelper = panel.getToolHelper();

        setBorder(BorderFactory.createTitledBorder("Set player position"));
        setLayout(new GridBagLayout());

        initComponents();
    }

    private void initComponents() {
        JButton button = new JButton();
        button.setIcon(new SpriteIcon(pack.getSprite(Player.DOWN_STATIC), pack.getTileWidth() * 2, pack.getTileHeight() * 2));
        button.addActionListener((a) -> toolHelper.setTool(PlayerTool.getInstance()));

        add(button);
    }
}