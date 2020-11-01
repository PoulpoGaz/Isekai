package fr.poulpogaz.isekai.editor.pack.io;

import com.sun.jdi.InternalException;
import fr.poulpogaz.isekai.editor.pack.Pack;
import fr.poulpogaz.isekai.editor.pack.PackSprites;
import fr.poulpogaz.isekai.editor.pack.image.PackImage;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;
import fr.poulpogaz.isekai.editor.utils.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
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

        PackImage tileset = get("/pack/", "tileset.png");

        pack.addSprite(new SubSprite(PackSprites.FLOOR, tileset, 0, 0, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.WALL, tileset, 16, 0, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.CRATE, tileset, 32, 0, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.CRATE_ON_TARGET, tileset, 48, 0, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.TARGET, tileset, 64, 0, 16, 16));

        pack.addSprite(new SubSprite(PackSprites.PLAYER_LEFT_STATIC, tileset, 0, 16, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.PLAYER_RIGHT_STATIC, tileset, 16, 16, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.PLAYER_DOWN_STATIC, tileset, 32, 16, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.PLAYER_UP_STATIC, tileset, 48, 16, 16, 16));

        pack.addSprite(new SubSprite(PackSprites.PLAYER_LEFT_WALK, tileset, 0, 16, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.PLAYER_RIGHT_WALK, tileset, 16, 16, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.PLAYER_DOWN_WALK, tileset, 32, 16, 16, 16));
        pack.addSprite(new SubSprite(PackSprites.PLAYER_UP_WALK, tileset, 48, 16, 16, 16));

        return pack;
    }

    private static PackImage get(String path, String name) {
        InputStream stream = PackBuilder.class.getResourceAsStream(path + name);

        try {
            return new PackImage(name, ImageIO.read(stream));
        } catch (IOException e) {
            LOGGER.error("Failed to read resource", e);
            return null;
        }
    }
}