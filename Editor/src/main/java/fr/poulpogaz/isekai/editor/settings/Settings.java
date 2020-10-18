package fr.poulpogaz.isekai.editor.settings;

import fr.poulpogaz.isekai.editor.Main;
import fr.poulpogaz.isekai.editor.utils.Cache;
import fr.poulpogaz.json.JsonException;
import fr.poulpogaz.json.tree.JsonObject;
import fr.poulpogaz.json.tree.JsonTreeReader;
import fr.poulpogaz.json.tree.JsonTreeWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Settings {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static final Path SETTING_PATH;
    private static SettingGroup settings = new SettingGroup("settings");

    public static void initSettings() {
        PathSetting setting = new PathSetting("convimg path", null);

        settings.add(setting);
        settings.init();
    }

    public static void read() {
        if (!Files.exists(SETTING_PATH)) {
            return;
        }

        JsonObject object;
        try {
            BufferedReader br = Files.newBufferedReader(SETTING_PATH);
            object = (JsonObject) JsonTreeReader.read(br);
        } catch (IOException | JsonException e) {
            LOGGER.warn("Failed to read settings", e);
            return;
        }

        settings.read(object);
    }

    public static void write() {
        JsonObject root = new JsonObject();

        settings.write(root);

        try {
            BufferedWriter bw = Files.newBufferedWriter(SETTING_PATH);
            JsonTreeWriter.write(root, bw);
        } catch (IOException | JsonException e) {
            LOGGER.warn("Failed to save settings", e);
        }
    }

    public static SettingGroup getSettings() {
        return settings;
    }

    static {
        SETTING_PATH = Cache.of("settings.json");
    }
}