package fr.poulpogaz.isekai.editor.pack.io;

import fr.poulpogaz.isekai.editor.pack.Level;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.image.*;
import fr.poulpogaz.isekai.editor.process.CommandExecutor;
import fr.poulpogaz.isekai.editor.settings.PathSetting;
import fr.poulpogaz.isekai.editor.settings.Settings;
import fr.poulpogaz.isekai.editor.utils.Vector2i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TIPackIO {

    private static final byte[] PACK_MARKER = new byte[] {'I', 'S', 'K', 'V', '0'};

    private static final Logger LOGGER = LogManager.getLogger(TIPackIO.class);

    private static final PathSetting CONVBIN_PATH = (PathSetting) Settings.find(Settings.CONV_BIN)[0];
    private static final PathSetting CONVIMG_PATH = (PathSetting) Settings.find(Settings.CONV_IMG)[0];

    private static final Path TEMP = Path.of(System.getProperty("java.io.tmpdir"));

    private static final int MAGENTA = 0xFFFF00FF;

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

        if (!pack.isTIPack()) {
            throw new TIPackIOException("This pack is not a valid TI pack");
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

        String p = pack.getName();
        String tiFileName = p.substring(0, Math.min(p.length(), 7));

        try {
            LOGGER.info("Writing temp data");
            writePack(pack, tiFileName + "0", out, convbinPath);
            writeSprites(pack, tiFileName + "1", out, convimgPath);
        } catch (TIPackIOException e) {
            throw new TIPackIOException(e); // redirect
        } catch (IOException e) {
            LOGGER.warn("Failed to export pack", e);
        }

        LOGGER.info("Pack exported!");
    }

    private static void writePack(Pack pack, String tiFileName, Path out, Path convbinPath) throws TIPackIOException, IOException {
        Path path = Files.createTempFile(TEMP, "isekai_", ".bin");
        OutputStream os = Files.newOutputStream(path);

        os.write(PACK_MARKER);
        writePackInfo(pack, os);

        writeLevels(pack.getLevels(), os);
        os.close();

        LOGGER.info("Exporting to 8xv");
        CommandExecutor executor = new CommandExecutor(
                convbinPath.toAbsolutePath().toString(),
                "--iformat", "bin", "--oformat", "8xv",
                "--input", path.toAbsolutePath().toString(),
                "--output", out.resolve(tiFileName + ".8xv").toAbsolutePath().toString(),
                "--name", tiFileName,
                "--archive");

        executor.get();

        if (executor.getError().length != 0) {
            throw new TIPackIOException(String.join("\n", executor.getError()));
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

    // RLE compress algorithm
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

    private static void writeSprites(Pack pack, String tiFileName, Path out, Path convimgPath) throws TIPackIOException, IOException {
        Path path = Files.createTempFile(TEMP, "isekai_", ".yaml");
        BufferedWriter bw = Files.newBufferedWriter(path);

        Group spriteGroup = createSpriteGroup(pack, tiFileName);
        spriteGroup.setDirectory(out);

        bw.write("outputs:\n");
        spriteGroup.write(bw);

        bw.write("palettes:\n");
        for (Palette palette : spriteGroup.getPalettes()) {
            palette.write(bw);
        }

        bw.write("converts:\n");
        for (Convert convert : spriteGroup.getConverts()) {
            convert.write(bw);
        }

        bw.close();

        LOGGER.info("Exporting images to 8xv");
        CommandExecutor executor = new CommandExecutor(
                convimgPath.toAbsolutePath().toString(),
                "-i", path.toAbsolutePath().toString());

        executor.get();

        if (executor.getError().length != 0) {
            throw new TIPackIOException(String.join("\n", executor.getError()));
        }
    }

    private static Group createSpriteGroup(Pack pack, String tiFileName) throws IOException {
        Group group = new Group(tiFileName);

        Path out = Files.createTempFile(TEMP, "sprites", ".png");

        TIImage allSprites = createSpriteTileset(pack, out);

        Palette palette = new Palette("sprites", allSprites.getTransparentColor());
        group.addPalette(palette);

        Convert convert = new Convert("sprites", palette);
        convert.addImage(allSprites);

        group.addConvert(convert);

        return group;
    }

    private static TIImage createSpriteTileset(Pack pack, Path output) throws IOException {
        int width = PackSprites.SIZE * 16;
        int height = 16;

        boolean hasTransparency = false;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int x = 0;
        for (String name : PackSprites.SPRITES) {
            AbstractSprite sprite = pack.getSprite(name);

            if (sprite instanceof BasicSprite) {
                hasTransparency = paint(0, 0, ((BasicSprite) sprite).getImage(), x, 0, image) || hasTransparency;
                x += 16;
            } else if (sprite instanceof SubSprite) {
                SubSprite s = (SubSprite) sprite;

                hasTransparency = paint(s.getX(), s.getY(), s.getParent(), x, 0, image);
                x += 16;
            }
        }

        ImageIO.write(image, "png", output.toFile());

        return new TIImage(output.toString(), image, hasTransparency ? Color.MAGENTA : null);
    }

    private static boolean paint(int srcX, int srcY, PackImage src, int xDraw, int yDraw, BufferedImage dest) {
        boolean hasTransparency = false;

        DataBufferInt buffer = (DataBufferInt) dest.getRaster().getDataBuffer();

        int destWidth = dest.getWidth();

        for (int y = 0; y < 16; y++) {
            int yOffset = (y + yDraw) * destWidth;

            for (int x = 0; x < 16; x++) {
                int color = src.getRGBA(x + srcX, y + srcY);
                int alpha = (color >> 24) & 0xFF;

                if (alpha > 127) {
                    buffer.setElem(yOffset + x + xDraw, 0xFF00_0000 | color);
                } else {
                    buffer.setElem(yOffset + x + xDraw, MAGENTA);
                    hasTransparency = true;
                }
            }
        }

        return hasTransparency;
    }

    private static class Group {

        private String name;
        private List<Palette> palettes;
        private List<Convert> converts;

        private Path directory;

        public Group(String name) {
            this.name = name;

            palettes = new ArrayList<>();
            converts = new ArrayList<>();
        }

        public void write(BufferedWriter bw) throws IOException {
            bw.write("  - type: appvar\n");
            bw.write("    name: %s\n".formatted(name));
            bw.write("    prepend-palette-sizes: true\n");
            bw.write("    lut-entries: true\n");
            bw.write("    archived: true\n");

            if (directory != null) {
                bw.write("    directory: %s\n".formatted(directory.toAbsolutePath()));
            }

            bw.write("    palettes:\n");

            for (Palette palette : palettes) {
                bw.write("      - %s\n".formatted(palette.getName()));
            }

            bw.write("    converts:\n");
            for (Convert convert : converts) {
                bw.write("      - %s\n".formatted(convert.getName()));
            }
        }

        public Path getDirectory() {
            return directory;
        }

        public void setDirectory(Path directory) {
            this.directory = directory;
        }

        public void addConvert(Convert convert) {
            converts.add(convert);
        }

        public void addPalette(Palette palette) {
            palettes.add(palette);
        }

        public List<Palette> getPalettes() {
            return palettes;
        }

        public List<Convert> getConverts() {
            return converts;
        }
    }

    private static class Convert {

        private final String name;
        private final List<INamedImage> images;
        private final Palette palette;

        public Convert(String name, Palette palette) {
            this.name = name;
            this.images = new ArrayList<>();
            this.palette = palette;
        }

        public void write(BufferedWriter bw) throws IOException {
            bw.write("  - name: %s\n".formatted(name));
            bw.write("    palette: %s\n".formatted(palette.getName()));

            if (palette.hasTransparentColor()) {
                bw.write("    transparent-color-index: 0\n");
            }

            bw.write("    images:\n");

            for (INamedImage image : images) {
                bw.write("      - %s\n".formatted(image.getName()));
            }
        }

        public void addImage(INamedImage image) {
            images.add(image);
        }

        public String getName() {
            return name;
        }

        public List<INamedImage> getImages() {
            return images;
        }

        public Palette getPalette() {
            return palette;
        }
    }

    private static class Palette {

        private final String name;
        private final Color transparentColor;

        public Palette(String name, Color transparentColor) {
            this.name = name;
            this.transparentColor = transparentColor;
        }

        public void write(BufferedWriter bw) throws IOException {
            bw.write("  - name: %s\n".formatted(name));
            bw.write("    images: automatic\n");

            if (hasTransparentColor()) {
                int r = transparentColor.getRed();
                int g = transparentColor.getGreen();
                int b = transparentColor.getBlue();

                bw.write("    fixed-entries:\n");
                bw.write("      - color: {index: 2, r: %d, g: %d, b: %d}\n".formatted(r, g, b));
            }
        }

        public boolean hasTransparentColor() {
            return transparentColor != null;
        }

        public String getName() {
            return name;
        }

        public Color getTransparentColor() {
            return transparentColor;
        }
    }

    private static class TIImage implements INamedImage {

        private final String name;
        private final BufferedImage image;
        private final Color transparentColor;

        public TIImage(String name, BufferedImage image, Color transparentColor) {
            this.name = name;
            this.image = image;
            this.transparentColor = transparentColor;
        }

        @Override
        public String getName() {
            return name;
        }

        public BufferedImage getImage() {
            return image;
        }

        public Color getTransparentColor() {
            return transparentColor;
        }
    }
}