package fr.poulpogaz.isekai.editor.utils.icons;

import fr.poulpogaz.isekai.editor.utils.Cache;
import fr.poulpogaz.isekai.editor.utils.StringUtil;
import fr.poulpogaz.isekai.editor.utils.concurrent.AppExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public abstract class AbstractLoader {

    private static final Logger LOGGER = LogManager.getLogger(AbstractLoader.class);

    protected final int UNKNOWN = -1;

    protected int width;
    protected int height;
    protected ImagePostProcess[] processes;

    public AbstractLoader() {
    }

    public BufferedImage load(File file) throws IOException {
        return load(new BufferedInputStream(new FileInputStream(file)));
    }

    public BufferedImage load(Path path) throws IOException {
        return load(new BufferedInputStream(Files.newInputStream(path)));
    }

    public abstract BufferedImage load(InputStream stream) throws IOException;

    protected Path getCachePath(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            ByteBuffer buffer = ByteBuffer.allocate(12);

            if (width != UNKNOWN) {
                buffer.putInt(width);
            }

            if (height != UNKNOWN) {
                buffer.putInt(height);
            }

            if (processes != null) {
                buffer.putInt(Arrays.hashCode(processes));
            }

            digest.update(bytes);

            digest.update(buffer);
            return Cache.of("cache/" + StringUtil.toHexString(digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
            return null;
        }
    }

    protected BufferedImage readCache(Path path) {
        if (path == null || !Files.exists(path)) {
            return null;
        }

        LOGGER.trace("Reading from cache");

        try {
            BufferedImage image = ImageIO.read(path.toFile());
            LOGGER.trace("Finished reading");

            return image;
        } catch (IOException e) {
            LOGGER.warn("Failed to read image at {}", path);

            delete(path);
        }

        return null;
    }

    protected void writeCache(Path path, BufferedImage in) {
        AppExecutor.getExecutor().submit(() -> {
            if (path == null) {
                return;
            }

            if (!Files.exists(path.getParent())) {
                try {
                    Files.createDirectories(path.getParent());
                } catch (IOException e) {
                    LOGGER.warn("Failed to create parent directories", e);
                    return;
                }
            }

            LOGGER.trace("Writing image");

            try {
                ImageIO.write(in, "png", path.toFile());
            } catch (IOException e) {
                LOGGER.warn("Error while writing image", e);

                delete(path);
            }

            LOGGER.trace("Finished writing");
        });
    }

    protected void reset() {
        width = UNKNOWN;
        height = UNKNOWN;
        processes = null;
    }

    protected static void delete(Path path) {
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException ignored) {
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImagePostProcess[] getProcesses() {
        return processes;
    }

    public void setProcesses(ImagePostProcess[] processes) {
        this.processes = processes;
    }
}