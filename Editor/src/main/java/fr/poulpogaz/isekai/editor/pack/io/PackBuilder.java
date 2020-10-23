package fr.poulpogaz.isekai.editor.pack.io;

import com.sun.jdi.InternalException;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.AbstractSprite;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;
import fr.poulpogaz.isekai.editor.utils.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class PackBuilder {

    private static final Logger LOGGER = LogManager.getLogger(PackBuilder.class);

    public static Pack loadDefaultPack() {
        Path path = Cache.of("default.skb");

        try {
            if (!Files.exists(path)) {
                extract("/pack/default.skb", path);
            } else {
                byte[] out = getBytes(path);
                byte[] in = PackBuilder.class.getResourceAsStream("/pack/default.skb").readAllBytes();

                if (!Arrays.equals(out, in)) {
                    extract("/pack/default.skb", path);
                }
            }

            return PackIO.deserialize(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void extract(String from, Path to) throws IOException {
        InputStream stream = PackBuilder.class.getResourceAsStream(from);

        if (stream == null) {
            throw new InternalException("The default pack is missing");
        }

        Cache.copy(stream, to, StandardCopyOption.REPLACE_EXISTING);

        stream.close();
    }

    private static byte[] getBytes(Path path) throws IOException {
        InputStream stream = Files.newInputStream(path);

        byte[] bytes =  stream.readAllBytes();

        stream.close();

        return bytes;
    }

    public static Pack emptyPack() {
        Pack pack = new Pack();

        pack.putImage("tileset", get("/pack/tileset.png"));

        pack.putSprite(PackSprites.FLOOR, new SubSprite(pack, "tileset", 0, 0, 16, 16));
        pack.putSprite(PackSprites.WALL, new SubSprite(pack, "tileset", 16, 0, 16, 16));
        pack.putSprite(PackSprites.CRATE, new SubSprite(pack, "tileset", 32, 0, 16, 16));
        pack.putSprite(PackSprites.CRATE_ON_TARGET, new SubSprite(pack, "tileset", 48, 0, 16, 16));
        pack.putSprite(PackSprites.TARGET, new SubSprite(pack, "tileset", 64, 0, 16, 16));

        AbstractSprite left = new SubSprite(pack, "tileset", 0, 16, 16, 16);
        AbstractSprite right = new SubSprite(pack, "tileset", 16, 16, 16, 16);
        AbstractSprite up = new SubSprite(pack, "tileset", 32, 16, 16, 16);
        AbstractSprite down = new SubSprite(pack, "tileset", 48, 16, 16, 16);

        pack.putSprite(PackSprites.LEFT_STATIC, left);
        pack.putSprite(PackSprites.RIGHT_STATIC, right);
        pack.putSprite(PackSprites.DOWN_STATIC, down);
        pack.putSprite(PackSprites.UP_STATIC, up);

        pack.putSprite(PackSprites.LEFT_WALK, left);
        pack.putSprite(PackSprites.RIGHT_WALK, right);
        pack.putSprite(PackSprites.DOWN_WALK, down);
        pack.putSprite(PackSprites.UP_WALK, up);

        return pack;
    }

    private static BufferedImage get(String path) {
        InputStream stream = PackBuilder.class.getResourceAsStream(path);

        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            LOGGER.error("Failed to read resource", e);
            return null;
        }
    }
}