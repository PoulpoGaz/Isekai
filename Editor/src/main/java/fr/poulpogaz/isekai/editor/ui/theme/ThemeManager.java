package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import fr.poulpogaz.isekai.editor.Prefs;
import fr.poulpogaz.isekai.editor.ui.Dialogs;
import fr.poulpogaz.json.JsonException;
import fr.poulpogaz.json.tree.JsonElement;
import fr.poulpogaz.json.tree.JsonObject;
import fr.poulpogaz.json.tree.JsonTreeReader;
import fr.poulpogaz.json.tree.value.JsonBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ThemeManager {

    private static final Logger LOGGER = LogManager.getLogger(ThemeManager.class);

    private static final List<CoreTheme> coreThemes = new ArrayList<>();
    private static final List<IntelliJIDEATheme> themes = new ArrayList<>();

    private static volatile boolean loaded = false;

    private ThemeManager() {}

    public static void loadThemes() {
        synchronized (ThemeManager.class) {
            if (loaded) {
                return;
            }

            try {
                InputStream is = ThemeDownloader.class.getResourceAsStream("/themes/themes.json");

                JsonObject root = (JsonObject) JsonTreeReader.read(is);

                for (Map.Entry<String, JsonElement> set : root.entrySet()) {
                    JsonObject object = (JsonObject) set.getValue();

                    String fileName = set.getKey();
                    String name = object.getAsJsonString("name").getAsString();
                    String license = object.getAsJsonString("license").getAsString();
                    String sourceCodeUrl = object.getAsJsonString("sourceCodeUrl").getAsString();
                    String sourceCodePath = object.getAsJsonString("sourceCodePath").getAsString();

                    boolean dark = false;
                    JsonElement element = object.get("dark");
                    if (element != null) {
                        dark = ((JsonBoolean) element).getAsBoolean();
                    }

                    themes.add(new IntelliJIDEATheme(fileName,
                            name,
                            dark,
                            license,
                            sourceCodeUrl,
                            sourceCodePath));
                }
            } catch (IOException | JsonException e) {
                LOGGER.warn("Failed to load themes", e);
            }

            coreThemes.add(new CoreTheme("Flat Darcula Laf", true, FlatDarculaLaf.class));
            coreThemes.add(new CoreTheme("Flat Dark Laf", true, FlatDarkLaf.class));
            coreThemes.add(new CoreTheme("Flat Light Laf", true, FlatLightLaf.class));
            coreThemes.add(new CoreTheme("Flat IntelliJ Laf", true, FlatIntelliJLaf.class));

            loaded = true;
        };
    }

    public static void setTheme(boolean updateUI) {
        loadThemes();
        String themeName = Prefs.getPrefs().get(Prefs.THEME, null);
        Theme theme = null;

        if (themeName == null) {
            theme = coreThemes.get(0);
        } else {
            for (Theme t : getAllThemes()) {
                if (t.name().equals(themeName)) {
                    theme = t;
                    break;
                }
            }

            if (theme == null) {
                theme = coreThemes.get(0);
            }
        }

        if (!setTheme(theme, updateUI, null, true)) {
            setTheme(coreThemes.get(0), updateUI, null, false);
        }
    }

    public static boolean setTheme(Theme theme, boolean updateUI, Component parent, boolean showError) {
        try {
            LookAndFeel laf = theme.createLaf();

            if (updateUI) {
                FlatAnimatedLafChange.showSnapshot();
            }

            try {
                UIManager.setLookAndFeel(laf);
            } catch (UnsupportedLookAndFeelException e) {
                LOGGER.warn("Failed to change theme to {}", theme.name());
                showError(parent, e, theme, showError);

                return false;
            }

            Prefs.getPrefs().put(Prefs.THEME, theme.name());

            if (updateUI) {
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            }

            return true;
        } catch (Exception e) {
            LOGGER.warn("Failed to change theme to {}", theme.name());
            showError(parent, e, theme, showError);

            return false;
        }
    }

    private static void showError(Component parent, Exception e, Theme theme, boolean showError) {
        if (showError) {
            if (parent != null) {
                parent = SwingUtilities.getWindowAncestor(parent);
            }

            Dialogs.showError(parent, "Failed to change theme to " + theme.name(), e);
        }
    }

    public static List<Theme> getAllThemes() {
        loadThemes();

        List<Theme> themes = new ArrayList<>();
        themes.addAll(coreThemes);
        themes.addAll(ThemeManager.themes);

        return themes;
    }

    public static List<CoreTheme> getCoreThemes() {
        loadThemes();

        return Collections.unmodifiableList(coreThemes);
    }

    public static List<IntelliJIDEATheme> getThemes() {
        loadThemes();

        return Collections.unmodifiableList(themes);
    }
}