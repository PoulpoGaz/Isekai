package fr.poulpogaz.isekai.commons.pack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2i;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class PackIO {

    private static final byte[] PACK_MARKER = new byte[] {(byte) 0xFE, (byte) 0xDC, (byte) 0xBA};

    private static final Logger LOGGER = LogManager.getLogger(PackIO.class);

    /**
     * Pack format
     * header bytes              3 bytes
     * pack name                 (n <= 32 bytes)
     * author                    (n <= 32 bytes)
     * sprite theme              8 bytes
     * playable                  1 byte
     * number of levels          2 byte
     * offsets to each levels    number of levels * 2 bytes
     *  level
     *  player x                 1 byte
     *  player y                 1 byte
     *  width                    1 byte
     *  height                   1 byte
     *  compressed               1 byte
     *  data                     n byte compressed or not with RLE
     *  OxFF                     marks end
     */
    public static void serialize(Pack pack, Path out) throws IOException {
        if (!Files.isDirectory(out)) {
            throw new PackIOException("Path isn't a directory: " + out);
        }

        LOGGER.info("Exporting pack to {}", out);
        writePack(pack, out.resolve(pack.getFileName() + ".8xv"));

        LOGGER.info("Pack exported!");
    }

    private static void writePack(Pack pack, Path out) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos.write(PACK_MARKER);
        writePackInfo(pack, baos);

        // sprite theme
        for (int i = 0; i < 8; i++) {
            baos.write(0);
        }

        baos.write(pack.isPlayable() ? 1 : 0);

        writeLevels(pack.getLevels(), baos);
        baos.close();

        byte[] data = Converter.convert(baos.toByteArray(), pack.getFileName());
        Files.write(out, data, StandardOpenOption.CREATE);
    }

    private static void writePackInfo(Pack pack, OutputStream os) throws IOException {
        byte[] name = pack.getPackName().getBytes(StandardCharsets.ISO_8859_1);
        os.write(name);
        os.write('\0'); // string end

        byte[] author = pack.getAuthor().getBytes(StandardCharsets.ISO_8859_1);
        os.write(author);
        os.write('\0');
    }

    private static void writeLevels(List<Level> levels, OutputStream os) throws IOException {
        os.write(levels.size() & 0xFF);
        os.write((levels.size() >> 8) & 0xFF);

        byte[][] levelsData = new byte[levels.size()][];

        short offset = 0;
        for (int i = 0; i < levels.size(); i++) {
            levelsData[i] = toByte(levels.get(i));

            // write offset
            os.write(offset & 0xFF);
            os.write((offset >> 8) & 0xFF);

            offset += levelsData[i].length;
        }

        for (byte[] levelData : levelsData) {
            os.write(levelData);
        }
    }

    private static byte[] toByte(Level level) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Vector2i player = level.getPlayerPos();
        baos.write(player.x);
        baos.write(player.y);

        baos.write(level.getWidth());
        baos.write(level.getHeight());

        byte[] levelData = convert(level.getTiles(), level.getWidth(), level.getHeight());
        byte[] compressed = compress(levelData);

        if (compressed.length >= levelData.length) {
            baos.write(0); // not compressed
            baos.write(levelData);
        } else {
            baos.write(1); // compressed
            baos.write(compressed);
        }

        return baos.toByteArray();
    }

    private static byte[] convert(Tile[][] tiles, int width, int height) {
        byte[] out = new byte[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                out[y * width + x] = (byte) tiles[y][x].ordinal();
            }
        }

        return out;
    }

    // Run Length Encoding
    private static byte[] compress(byte[] in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte last = in[0];
        int n = 0;
        for (int i = 1; i < in.length; i++) {
            if (last == in[i]) {
                n++;

                if (n == 254) {
                    baos.write((byte) n);
                    baos.write(last);
                    n = 0;
                }

            } else {
                baos.write((byte) n);
                baos.write(last);
                n = 0;
            }

            last = in[i];
        }

        if (n > 0) {
            baos.write((byte) n);
            baos.write(last);
        }

        baos.write(0xFF);

        return baos.toByteArray();
    }

    public static Pack deserialize(Path in) throws PackIOException {
        if (Files.isDirectory(in)) {
            throw new PackIOException("Input path isn't a file");
        }

        LOGGER.info("Importing pack from {}", in);

        byte[] data;
        byte[] fileName;
        try {
            byte[] fileBytes = Files.readAllBytes(in);

            data = Converter.extract(fileBytes);
            fileName = Converter.extractFileName(fileBytes);
        } catch (Exception e) {
            LOGGER.warn("Failed to import pack", e);

            throw new PackIOException(e);
        }

        Pack pack = new Pack();
        pack.setFileName(new String(fileName));

        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        try {
            bais.readNBytes(PACK_MARKER.length);

            readPackInfo(pack, bais);
            readLevels(pack, bais);

        }  catch (PackIOException e) {
            throw e;
        } catch (IOException ignored) {} // can't happen

        return pack;
    }

    private static void readPackInfo(Pack pack, InputStream is) throws IOException {
        pack.setPackName(readString(is));
        pack.setAuthor(readString(is));

        is.readNBytes(8); // sprite theme
        is.readNBytes(1); // valid
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder builder = new StringBuilder();

        while (true) {
            int next = is.read();

            if (next == -1) {
                throw new PackIOException("EOF");
            }

            if (next == '\0') {
                return builder.toString();
            }

            builder.append((char) next);
        }
    }

    private static void readLevels(Pack pack, InputStream is) throws IOException {
        int nLevels = (is.read() & 0xFF) | ((is.read() << 8) & 0xFF00);

        is.readNBytes(nLevels * 2); // skip offsets, they are useless here

        for (int i = 0; i < nLevels; i++) {
            Level level = new Level();

            Vector2i pos = level.getPlayerPos();
            pos.x = is.read();
            pos.y = is.read();

            level.resize(is.read(), is.read());

            boolean compressed = is.read() == 1;

            if (compressed) {
                fillLevelCompressed(level, is);
            } else {
                fillLevel(level, is);
            }

            if (i == 0) {
                pack.setLevel(level, 0);
            } else {
                pack.addLevel(level);
            }
        }
    }

    private static void fillLevel(Level level, InputStream is) throws IOException {
        int width = level.getWidth();
        int height = level.getHeight();

        Tile[] values = Tile.values();
        Tile[][] data = level.getTiles();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = is.read();

                if (i == -1) {
                    throw new PackIOException("EOF");
                }

                data[y][x] = values[i];
            }
        }
    }

    private static void fillLevelCompressed(Level level, InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // uncompress
        int count = -1;
        int i;
        while ((i = is.read()) != 0xFF) {
            if (i == -1) {
                throw new PackIOException("EOF");
            }

            if (count > 0) {
                for (int j = 0; j < count; j++) {
                    baos.write(i);
                }

                count = -1;
            } else {
                count = i + 1;
            }
        }

        // setup data
        byte[] data = baos.toByteArray();

        int width = level.getWidth();
        int height = level.getHeight();

        Tile[] values = Tile.values();
        Tile[][] levelData = level.getTiles();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                i = y * width + x;

                if (data[i] >= values.length) {
                    continue;
                }

                levelData[y][x] = values[data[i]];
            }
        }
    }
}