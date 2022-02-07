package fr.poulpogaz.isekai.editor.ui.about;

import fr.poulpogaz.isekai.commons.Utils;
import fr.poulpogaz.isekai.editor.IsekaiEditor;
import fr.poulpogaz.isekai.editor.ui.JLabelLink;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public record License(String name, String link, String version, String license, String licenseLink) {

    private static JPanel licensePanel = null;

    public static void showDialog() {
        IsekaiEditor parent = IsekaiEditor.getInstance();

        JDialog dialog = new JDialog(parent, "Open source license", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        if (licensePanel == null) {
            licensePanel = createContent();
        }

        dialog.setContentPane(licensePanel);
        dialog.pack();

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private static JPanel createContent() {
        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        content.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(2, 2, 2, 2);

        content.add(new JLabel("Software"), constraints);

        constraints.gridx = 1;
        content.add(new JLabel("Version"), constraints);

        constraints.gridx = 2;
        content.add(new JLabel("License"), constraints);

        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;

        for (License license : LICENSES) {
            constraints.gridx = 0;
            content.add(createLink(license.name(), license.link()), constraints);

            constraints.gridx = 1;
            content.add(new JLabel(license.version()), constraints);

            constraints.gridx = 2;
            content.add(createLink(license.license(), license.licenseLink()), constraints);

            constraints.gridy++;
        }

        return content;
    }

    public static JLabelLink createLink(String text, String link) {
        JLabelLink l = new JLabelLink(text);
        l.addActionListener((e) -> {
            Utils.browse(link);
        });

        return l;
    }

    public static final List<License> LICENSES;

    static {
        LICENSES = List.of(
                new License("FlatLaf", "https://www.formdev.com/flatlaf/", "1.1.2", "Apache-2.0", "https://github.com/JFormDesigner/FlatLaf/blob/main/LICENSE"),
                new License("FlatLaf Extras", "https://www.formdev.com/flatlaf/", "1.1.2", "Apache-2.0", "https://github.com/JFormDesigner/FlatLaf/blob/main/LICENSE"),
                new License("json", "https://github.com/PoulpoGaz/json", "1.0.1", "MIT", "https://github.com/PoulpoGaz/json/blob/master/LICENSE"),
                new License("Commons Collections 4", "https://commons.apache.org/proper/commons-collections/", "4.4", "Apache-2.0", "https://www.apache.org/licenses/LICENSE-2.0.html"),
                new License("Log4j 2", "https://logging.apache.org/log4j/2.x/", "2.14.1", "Apache-2.0", "https://www.apache.org/licenses/LICENSE-2.0.html"),
                new License("Jsoup", "https://jsoup.org/", "1.13.1", "MIT", "https://github.com/jhy/jsoup/blob/master/LICENSE"));
    }
}