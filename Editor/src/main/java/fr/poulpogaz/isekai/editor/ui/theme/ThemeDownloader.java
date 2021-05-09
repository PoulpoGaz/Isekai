package fr.poulpogaz.isekai.editor.ui.theme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ThemeDownloader {

    private static final Logger LOGGER = LogManager.getLogger(ThemeDownloader.class);

    public static void main(String[] args) throws IOException {
        ThemeManager.loadThemes();

        for (IntelliJIDEATheme theme : ThemeManager.getThemes()) {
            LOGGER.info("Downloading theme: {} at {} ", theme.name(), theme.getDownloadUrl());

            download(theme.getDownloadUrl(), "src/main/resources/themes/" + theme.fileName());
        }
    }

    private static void download(String from, String to) throws IOException {
        Path dest = Path.of(to);

        if (!Files.exists(dest.getParent())) {
            Files.createDirectories(dest.getParent());
        }

        URL url = new URL(from);

        URLConnection connection = url.openConnection();
        Files.copy(connection.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
    }
}