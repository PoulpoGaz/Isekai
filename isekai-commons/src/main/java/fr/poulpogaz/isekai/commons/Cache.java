package fr.poulpogaz.isekai.commons;

import java.io.*;
import java.nio.file.*;

public class Cache {

    public static Path ROOT = Path.of(System.getProperty("java.io.tmpdir") + "/Java");

    public static void setRoot() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            Cache.setRoot(System.getenv("APPDATA"), "/isekai");
        } else {
            Cache.setRoot(System.getProperty("user.home"), "/.config/isekai");
        }
    }

    public static void setRoot(Path root) {
        ROOT = root;
    }

    public static void setRoot(File root) {
        ROOT = root.toPath();
    }

    public static void setRoot(String root) {
        ROOT = Path.of(root);
    }

    public static void setRoot(String root, String... more) {
        ROOT = Path.of(root, more);
    }

    public static Path of(String path) {
        return resolve(Path.of(path));
    }

    public static Path of(String path, String more) {
        return resolve(Path.of(path, more));
    }

    public static Path resolve(Path path) {
        return ROOT.resolve(path);
    }
}