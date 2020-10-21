package fr.poulpogaz.isekai.editor.pack.io;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.process.CommandExecutor;
import fr.poulpogaz.isekai.editor.settings.PathSetting;
import fr.poulpogaz.isekai.editor.settings.Settings;
import fr.poulpogaz.isekai.editor.utils.Vector2i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class TIPackIO {

    private static final byte[] PACK_MARKER = new byte[] {'I', 'S', 'K', 'V', '0'};

    private static final Logger LOGGER = LogManager.getLogger(TIPackIO.class);

    private static final PathSetting CONVBIN_PATH = (PathSetting) Settings.find(Settings.CONV_BIN)[0];
    private static final PathSetting CONVIMG_PATH = (PathSetting) Settings.find(Settings.CONV_IMG)[0];

    private static final Path TEMP = Path.of(System.getProperty("java.io.tmpdir"));

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
     */
    public static void serialize(Pack pack, Path out) throws TIPackIOException {
        if (!Files.isDirectory(out)) {
            throw new TIPackIOException("Output path isn't a directory");
        }

        LOGGER.info("Exporting pack to {}", out);

        Path convbinPath = CONVBIN_PATH.getValue().toPath();
        Path convimgPath = CONVIMG_PATH.getValue().toPath();

        if (!Files.exists(convbinPath)) {
            throw new TIPackIOException("convbin not specified");
        }
        if (!Files.exists(convimgPath)) {
            throw new TIPackIOException("convimg not specified");
        }

        try {
            LOGGER.info("Writing temp data");
            Path path = Files.createTempFile(TEMP, "isekai_", ".bin");
            OutputStream os = Files.newOutputStream(path);

            os.write(PACK_MARKER);
            writePackInfo(pack, os);

            writeLevels(pack.getLevels(), os);
            os.close();

            String p = pack.getName();
            String packName = p.substring(0, Math.min(p.length(), 7)) + "0";

            LOGGER.info("Exporting to 8xv");
            CommandExecutor executor = new CommandExecutor(
                    convbinPath.toAbsolutePath().toString(),
                    "--iformat", "bin", "--oformat", "8xv",
                    "--input", path.toAbsolutePath().toString(),
                    "--output", out.resolve(packName + ".8xv").toAbsolutePath().toString(),
                    "--name", packName,
                    "--archive");

            executor.get();
        } catch (IOException e) {
            LOGGER.warn("Failed to export pack", e);
        }
    }

    private static void writePackInfo(Pack pack, OutputStream os) throws IOException {
        byte[] name = pack.getName().getBytes();
        os.write(name, 0, Math.min(name.length, 31));
        os.write('\0'); // string end

        byte[] author = pack.getAuthor().getBytes();
        os.write(author, 0, Math.min(author.length, 31));
        os.write('\0');

        byte[] version = pack.getVersion().getBytes();
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

    // RLE
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
        baos.write((byte) 255);

        return baos.toByteArray();
    }
}