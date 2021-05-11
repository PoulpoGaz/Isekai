package fr.poulpogaz.isekai.editor.ui.about;

import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.Main;
import fr.poulpogaz.isekai.editor.ui.Icons;
import fr.poulpogaz.isekai.editor.ui.JLabelLink;
import fr.poulpogaz.isekai.editor.ui.JLabeledComponent;
import fr.poulpogaz.isekai.editor.ui.NoSelectionCaret;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.VerticalLayout;
import fr.poulpogaz.isekai.editor.utils.GraphicsUtils;
import fr.poulpogaz.isekai.editor.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class AboutPanel extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger(AboutPanel.class);

    private static AboutPanel panel = null;

    public static void showDialog() {
        IsekaiEditor parent = IsekaiEditor.getInstance();

        JDialog dialog = new JDialog(parent, "About", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);

        if (panel == null) {
            panel = new AboutPanel();
        }

        dialog.setContentPane(panel);
        dialog.pack();

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public AboutPanel() {
        initComponents();
    }

    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 15));
        center.setLayout(new VerticalLayout());

        VerticalConstraint constraint = new VerticalConstraint();
        constraint.xAlignment = 0;
        constraint.fillXAxis = true;

        center.add(createPrgmInfoPanel(), constraint);

        constraint.fillXAxis = false;

        center.add(Box.createVerticalStrut(25));
        center.add(new JLabel("JVM: " + getJVMName()), constraint);
        center.add(new JLabel("JVM version: " + getJVMVersion()), constraint);
        center.add(Box.createVerticalStrut(25));

        center.add(new JLabel("Special thanks"), constraint);

        JTextPane specialThanks = new JTextPane();
        //specialThanks.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        specialThanks.setEditable(false);
        specialThanks.setCaret(new NoSelectionCaret(specialThanks));
        specialThanks.setContentType("text/html");
        specialThanks.setText(getSpecialThanksText());

        center.add(specialThanks, constraint);

        add(new BackgroundPanel(), BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    protected JPanel createPrgmInfoPanel() {
        JPanel prgmInfo = new JPanel();
        prgmInfo.setLayout(new GridBagLayout());

        JLabel app = new JLabel("Isekai Editor  " + Main.VERSION);
        app.setFont(app.getFont().deriveFont(Font.BOLD));

        JLabelLink author = new JLabelLink("PoulpoGaz");
        author.addActionListener(e -> Utils.browse("https://www.github.com/PoulpoGaz"));
        author.setFont(author.getFont().deriveFont(Font.BOLD));

        JLabelLink license = new JLabelLink("MIT");
        license.addActionListener(e -> Utils.browse("https://github.com/PoulpoGaz/Isekai/blob/master/LICENSE"));

        JLabelLink github = new JLabelLink("Github");
        github.setIcon(Icons.get("icons/github.svg"));
        github.addActionListener(e -> Utils.browse("https://github.com/PoulpoGaz/Isekai/"));

        JLabel myIcon = new JLabel();
        createIconFetcher(myIcon);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;

        prgmInfo.add(app, constraints);

        constraints.gridy = 1;
        prgmInfo.add(author, constraints);

        constraints.gridy = 2;
        prgmInfo.add(new JLabeledComponent("License: ", license), constraints);

        constraints.gridy = 3;
        prgmInfo.add(github, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridheight = 4;

        prgmInfo.add(myIcon, constraints);

        return prgmInfo;
    }

    protected void createIconFetcher(JLabel target) {
        SwingWorker<BufferedImage, Void> worker = new SwingWorker<>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {
                LOGGER.info("Fetching icon at https://github.com/PoulpoGaz.png?size=64");
                URL url = new URL("https://github.com/PoulpoGaz.png?size=64");

                InputStream is = url.openStream();

                BufferedImage img = ImageIO.read(is);

                return GraphicsUtils.makeRoundedCorner(img, Integer.MAX_VALUE);
            }

            @Override
            protected void done() {
                try {
                    if (isCancelled()) {
                        return;
                    }

                    BufferedImage img = get();

                    if (img != null) {
                        LOGGER.info("Icon fetched!");
                        target.setIcon(new ImageIcon(img));
                    } else {
                        LOGGER.info("Failed to fetch icon");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.warn("Failed to fetch icon", e);
                }
            }
        };
        worker.execute();
    }

    protected String getSpecialThanksText() {
        Color color = UIManager.getColor("Text.disabled");

        return """
                <html>
                Roms30<br>
                B1rdem1c<br>
                GR4Y<br>
                Doomination <span style="color:%s%s%s">aka</span> Dévoreur de mondes
                </html>
                """.formatted(
                        Integer.toHexString(color.getRed()),
                        Integer.toHexString(color.getGreen()),
                        Integer.toHexString(color.getBlue()));
    }

    private String getJVMName() {
        return System.getProperty("java.vm.name");
    }

    private String getJVMVersion() {
        return System.getProperty("java.vm.version");
    }
}