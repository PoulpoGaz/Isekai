package fr.poulpogaz.isekai.editor;

import java.util.prefs.Preferences;

public class Prefs {

    public static final String THEME = "Theme";

    public static final String WIDTH = "Width";
    public static final String HEIGHT = "Height";
    public static final String WINDOW_X = "WindowX";
    public static final String WINDOW_Y = "WindowY";
    public static final String MAXIMIZED = "Maximized";

    public static final String ROOT = "/isekai-editor";

    private static Preferences prefs;

    public static void init() {
        prefs = Preferences.userRoot().node(ROOT);
    }

    public static Preferences getPrefs() {
        return prefs;
    }

    public static void setTheme(String theme) {
        prefs.put(THEME, theme);
    }

    public static String getTheme() {
        return prefs.get(THEME, null);
    }

    public static void setWidth(int width) {
        prefs.putInt(WIDTH, width);
    }

    public static int getWidth() {
        return prefs.getInt(WIDTH, IsekaiEditor.UNKNOWN_DIMENSION);
    }

    public static void setHeight(int height) {
        prefs.putInt(HEIGHT, height);
    }

    public static int getHeight() {
        return prefs.getInt(HEIGHT, IsekaiEditor.UNKNOWN_DIMENSION);
    }

    public static void setWindowX(int x) {
        prefs.putInt(WINDOW_X, x);
    }

    public static int getWindowX() {
        return prefs.getInt(WINDOW_X, IsekaiEditor.UNKNOWN_DIMENSION);
    }

    public static void setWindowY(int y) {
        prefs.putInt(WINDOW_Y, y);
    }

    public static int getWindowY() {
        return prefs.getInt(WINDOW_Y, IsekaiEditor.UNKNOWN_DIMENSION);
    }

    public static void setMaximized(boolean maximized) {
        prefs.putBoolean(MAXIMIZED, maximized);
    }

    public static boolean isMaximized() {
        return prefs.getBoolean(MAXIMIZED, false);
    }
}