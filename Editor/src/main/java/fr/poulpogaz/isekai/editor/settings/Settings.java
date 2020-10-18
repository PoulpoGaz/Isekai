package fr.poulpogaz.isekai.editor.settings;

import fr.poulpogaz.isekai.editor.Main;
import fr.poulpogaz.isekai.editor.utils.Cache;
import fr.poulpogaz.json.JsonException;
import fr.poulpogaz.json.tree.JsonObject;
import fr.poulpogaz.json.tree.JsonTreeReader;
import fr.poulpogaz.json.tree.JsonTreeWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Settings {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static final Path SETTING_PATH;
    private static SettingGroup settings = new SettingGroup("Settings");

    public static void initSettings() {
        SettingGroup ti83_84 = new SettingGroup("TI-83/84");
        PathSetting setting = new PathSetting("convimg path", null);
        setting.getComponent().setFileSelectionMode(JFileChooser.FILES_ONLY);

        ti83_84.add(new PathSetting("convimg path", null));

        settings.add(ti83_84);
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