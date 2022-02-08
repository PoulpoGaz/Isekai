package fr.poulpogaz.isekai.editor.ui.theme;

import fr.poulpogaz.isekai.commons.Cache;
import fr.poulpogaz.isekai.commons.Log4j2Init;
import fr.poulpogaz.isekai.commons.concurrent.ExecutorWithException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ThemeDownloader {

    private static final Logger LOGGER = LogManager.getLogger(ThemeDownloader.class);

    public static void main(String[] args) throws InterruptedException {
        Cache.setRoot();
        Log4j2Init.init("theme-downloader");
        ThemeManager.loadThemes();

        ExecutorService executor = ExecutorWithException.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        long time = System.currentTimeMillis();
        for (IntelliJIDEATheme theme : ThemeManager.getThemes()) {
            executor.submit(() -> {
                LOGGER.info("Downloading theme: {} at {} ", theme.name(), theme.getDownloadUrl());

                try {
                    download(theme.getDownloadUrl(), "isekai-editor/src/main/resources/themes/" + theme.fileName());
                } catch (IOException e) {
                    LOGGER.warn(e);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long time2 = System.currentTimeMillis();

        LOGGER.info("Download finished in {}s", (time2 - time) / 1000f);
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