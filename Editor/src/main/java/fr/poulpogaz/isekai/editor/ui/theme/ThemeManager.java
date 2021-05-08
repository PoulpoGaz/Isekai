package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import fr.poulpogaz.json.JsonException;
import fr.poulpogaz.json.tree.JsonElement;
import fr.poulpogaz.json.tree.JsonObject;
import fr.poulpogaz.json.tree.JsonTreeReader;
import fr.poulpogaz.json.tree.value.JsonBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static boolean loaded = false;

    private ThemeManager() {}

    public static void loadThemes() {
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
    }

    public static List<CoreTheme> getCoreThemes() {
        return Collections.unmodifiableList(coreThemes);
    }

    public static List<IntelliJIDEATheme> getThemes() {
        return Collections.unmodifiableList(themes);
    }
}