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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Settings {

    public static final String CONV_BIN = "convbin path";
    public static final String CONV_IMG = "convimg path";

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static final Path SETTING_PATH;
    private static SettingGroup settings = new SettingGroup("Settings");

    public static void initSettings() {
        SettingGroup ti83_84 = new SettingGroup("TI-83/84");
        PathSetting convimgPath = new PathSetting(CONV_IMG, new File(""));
        convimgPath.getComponent().setFileSelectionMode(JFileChooser.FILES_ONLY);

        PathSetting convbinPath = new PathSetting(CONV_BIN, new File(""));
        convbinPath.getComponent().setFileSelectionMode(JFileChooser.FILES_ONLY);

        ti83_84.add(convimgPath);
        ti83_84.add(convbinPath);

        settings.add(ti83_84);
        settings.init();
    }

    public static void read() {
        if (!Files.exists(SETTING_PATH)) {
            return;
        }

        LOGGER.info("Reading settings");

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
        LOGGER.info("Writing settings");

        JsonObject root = new JsonObject();
        settings.write(root);

        try {
            BufferedWriter bw = Files.newBufferedWriter(SETTING_PATH);
            JsonTreeWriter.write(root, bw);
        } catch (IOException | JsonException e) {
            LOGGER.warn("Failed to save settings", e);
        }
    }

    public static SettingObject[] find(String name) {
        return find(name, settings).toArray(new SettingObject[0]);
    }

    private static List<SettingObject> find(String name, SettingGroup group) {
        ArrayList<SettingObject> objects = new ArrayList<>();

        for (SettingObject object : group.getChilds()) {
            if (object.getName().equals(name)) {
                objects.add(object);
            }

            if (object.isGroup()) {
                objects.addAll(find(name, (SettingGroup) object));
            }
        }

        return objects;
    }

    public static SettingGroup getSettings() {
        return settings;
    }

    static {
        SETTING_PATH = Cache.of("settings.json");
    }
}