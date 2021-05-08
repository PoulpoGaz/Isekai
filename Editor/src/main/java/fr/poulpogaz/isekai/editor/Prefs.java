package fr.poulpogaz.isekai.editor;

import java.util.prefs.Preferences;

public class Prefs {

    public static final String THEME = "Theme";
    public static final String ROOT = "/isekai-editor";

    private static Preferences prefs;

    public static void init() {
        prefs = Preferences.userRoot().node(ROOT);
    }
}