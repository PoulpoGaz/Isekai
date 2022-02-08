package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.commons.Log4j2Init;
import fr.poulpogaz.isekai.commons.Utils;
import fr.poulpogaz.isekai.commons.concurrent.ExecutorWithException;
import fr.poulpogaz.isekai.commons.pack.Level;
import fr.poulpogaz.isekai.commons.pack.Pack;
import fr.poulpogaz.isekai.commons.pack.PackIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AllPackDownloader {

    private static final String LOGGER_LOGGER = "LOGGER";

    private static final Path LEVELS = Path.of("../levels/");
    private static final Path CACHE = Path.of("../levels/cache");

    private static final Logger LOGGER = LogManager.getLogger(AllPackDownloader.class);

    public static void main(String[] args) {
        Log4j2Init.initPath("all-pack-downloader", LEVELS);
        delete8XV();

        SIPack.loadPacks();

        ExecutorWithException executor = (ExecutorWithException) ExecutorWithException.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executor.setKeepAliveTime(10, TimeUnit.MILLISECONDS);
        executor.allowCoreThreadTimeOut(true);

        Map<SIPack, List<Future<Result>>> levels = new LinkedHashMap<>();
        List<Future<Result>> allLevels = Collections.synchronizedList(new ArrayList<>());

        for (final SIPack pack : SIPack.getPacks()) {
            List<Future<Result>> l = new ArrayList<>();

            for (int i = 1; i <= pack.nLevels(); i++) {
                final int i2 = i;

                Future<Result> future = executor.submit(() -> getLevel(pack, i2));

                l.add(future);
                allLevels.add(future);
            }

            levels.put(pack, l);
        }

        final int max = allLevels.size();
        LOGGER.info("Total number of levels={}", max);

        long last = max;
        long time = System.currentTimeMillis();
        while (!allLevels.isEmpty()) {
            allLevels.removeIf(Future::isDone);

            long time2 = System.currentTimeMillis();

            if (time2 - time > 1000) {
                double progress = ((double) max - allLevels.size()) / max * 100d;

                progress = BigDecimal.valueOf(progress).setScale(2, RoundingMode.HALF_UP).doubleValue();

                long diff = last - allLevels.size();

                LOGGER.info("Progress: {}%, Speed: {} import/s", progress, diff);

                time = time2;
                last = allLevels.size();
            }
        }

        LOGGER.info("Download finished. Exporting...");
        for (Map.Entry<SIPack, List<Future<Result>>> entry : levels.entrySet()) {
            write(entry.getKey(), entry.getValue());
        }
    }

    private static Result getLevel(SIPack pack, int index) {
        Path cache = CACHE.resolve(pack.name() + "_(" + pack.id() + ")_" + index);

        String link = pack.getLinkFor(index);
        Document doc = null;

        // try read cache
        if (Files.exists(cache)) {
            doc = getFromCache(cache, link);
        }

        // else get file from website
        if (doc == null) {
            try {
                LOGGER.info("Downloading level from {}", link);

                URL url = new URL(link);

                InputStream is = new BufferedInputStream(url.openStream());
                OutputStream os = new BufferedOutputStream(Files.newOutputStream(cache));

                is.transferTo(os);
                os.close();
                is.close();
            } catch (Exception e) {
                LOGGER.warn("Failed to import level {}", index, e);

                return null;
            }

            doc = getFromCache(cache, link);
        }

        if (doc == null) {
            return new Result(pack, null, index);
        }

        Level level = pack.importLevel(doc, index);

        return new Result(pack, level, index);
    }

    private static Document getFromCache(Path cache, String link) {
        LOGGER.info("Reading cache for {}", link);

        InputStream is;
        try {
            is = Files.newInputStream(cache);

            return Jsoup.parse(is, null, link);
        } catch (IOException e) {
            LOGGER.warn("Failed to read cache", e);
            return null;
        }
    }

    private static Result get(Future<Result> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.warn("Unexpected exception", e);

            return null;
        }
    }

    private static void write(SIPack pack, List<Future<Result>> results) {
        List<Level> levels =
                results.stream()
                .map(AllPackDownloader::get)
                .filter((r) -> {
                    if (r == null) {
                        return false;
                    }

                    if (r.level == null) {
                        LOGGER.info("Failed to get level {} of pack {}", r.index, r.pack);

                        return false;
                    }
                    return true;
                })
                .map((result) -> result.level)
                .collect(Collectors.toList());

        Pack p = new Pack();
        p.addAll(levels);
        p.removeLevel(0);

        String fileName = getFileName(pack.name());

        LOGGER.info("{} => {}", pack, fileName);

        p.setFileName(fileName);
        p.setPackName(pack.name());
        p.setAuthor(pack.author());

        try {
            PackIO.serialize(p, LEVELS);
        } catch (IOException e) {
            LOGGER.warn("Failed to save pack {}", pack, e);
        }
    }

    private static String getFileName(String str) {
        StringBuilder builder = new StringBuilder();

        for (char c : str.toCharArray()) {
            if ((c >= '0' & c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                builder.append(c);

                if (builder.length() >= PackModel.MAX_FILE_NAME_SIZE) {
                    break;
                }
            }
        }

        String text = builder.toString();

        String fileName = text;
        int i = 0;
        while (Files.exists(LEVELS.resolve(fileName + ".8xv")) || text.length() > 8) {
            if (text.length() >= 8) {
                int limit = 7;

                if (i >= 10) {
                    limit--;
                }

                fileName = text.substring(0, limit) + i;
            } else {
                fileName = text + i;
            }

            i++;
        }

        return fileName;
    }

    public static void delete8XV() {
        try {
            if (Files.exists(LEVELS)) {
                Files.walk(LEVELS).forEach((p) -> {
                    if (Files.isDirectory(p)) {
                        return;
                    }

                    if (Utils.checkExtension(p, "8xv")) {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Files.createDirectory(LEVELS);
                Files.createDirectory(CACHE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static record Result(SIPack pack, Level level, int index) {

    }
}