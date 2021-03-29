package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.utils.Vector2i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

public class TIPackIO {

    private static final byte[] PACK_MARKER = new byte[] {'I', 'S', 'K', 'V', '0'};

    private static final Logger LOGGER = LogManager.getLogger(TIPackIO.class);

    /**
     * Pack format
     * header bytes              5 bytes
     * pack name                 (n <= 32 bytes)
     * author                    (n <= 32 bytes)
     * version                   (n <= 8 bytes)
     * number of levels          1 byte
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
    public static void serialize(Pack pack, Path out) throws TIPackIOException {
        if (!Files.isDirectory(out)) {
            throw new TIPackIOException("Output path isn't a directory");
        }

        LOGGER.info("Exporting pack to {}", out);

        String packName = pack.getName();
        String varName = packName.substring(0, Math.min(packName.length(), 8));

        try {
            writePack(pack, varName, out.resolve(varName + ".8xv"));
        } catch (IOException e) {
            LOGGER.warn("Failed to export pack", e);
        }

        LOGGER.info("Pack exported!");
    }

    private static void writePack(Pack pack, String varName, Path out) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos.write(PACK_MARKER);
        writePackInfo(pack, baos);

        writeLevels(pack.getLevels(), baos);
        baos.close();

        byte[] data = Converter.convert(baos.toByteArray(), varName);
        Files.write(out, data, StandardOpenOption.CREATE);
    }

    private static void writePackInfo(Pack pack, OutputStream os) throws IOException {
        byte[] name = pack.getName().getBytes(StandardCharsets.ISO_8859_1);
        os.write(name, 0, Math.min(name.length, 31));
        os.write('\0'); // string end

        byte[] author = pack.getAuthor().getBytes(StandardCharsets.ISO_8859_1);
        os.write(author, 0, Math.min(author.length, 31));
        os.write('\0');

        byte[] version = pack.getVersion().getBytes(StandardCharsets.ISO_8859_1);
        os.write(version, 0, Math.min(version.length, 7));
        os.write('\0');
    }

    private static void writeLevels(ArrayList<Level> levels, OutputStream os) throws IOException {
        os.write(levels.size());

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

    public static Pack deserialize(Path in) throws TIPackIOException {
        if (Files.isDirectory(in)) {
            throw new TIPackIOException("Input path isn't a file");
        }

        LOGGER.info("Importing pack from {}", in);

        byte[] data;
        try {
            data = Converter.extract(Files.readAllBytes(in));
        } catch (IOException e) {
            LOGGER.warn("Failed to import pack", e);

            return null;
        }

        Pack pack = new Pack();

        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        try {
            bais.readNBytes(PACK_MARKER.length);

            readPackInfo(pack, bais);
            readLevels(pack, bais);

        }  catch (TIPackIOException e) {
            throw e;
        } catch (IOException ignored) {} // can't happen

        return pack;
    }

    private static void readPackInfo(Pack pack, InputStream is) throws IOException {
        pack.setName(readString(is));
        pack.setAuthor(readString(is));
        pack.setVersion(readString(is));
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder builder = new StringBuilder();

        while (true) {
            int next = is.read();

            if (next == -1) {
                throw new TIPackIOException("EOF");
            }

            if (next == '\0') {
                return builder.toString();
            }

            builder.append((char) next);
        }
    }

    private static void readLevels(Pack pack, InputStream is) throws IOException {
        int nLevels = is.read();

        is.readNBytes(nLevels * 2); // skip offsets, they are useless here

        for (int i = 0; i < nLevels; i++) {
            Level level = new Level();

            Vector2i pos = level.getPlayerPos();
            pos.setX(is.read());
            pos.setY(is.read());

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
                    throw new TIPackIOException("EOF");
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
                throw new TIPackIOException("EOF");
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