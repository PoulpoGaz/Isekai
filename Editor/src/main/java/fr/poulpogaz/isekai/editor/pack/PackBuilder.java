package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.pack.image.AnimatedSprite;
import fr.poulpogaz.isekai.editor.pack.image.SubSprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PackBuilder {

    public static Pack createDefaultPack() {
        Pack pack = new Pack();
        pack.setPackName("Default");
        pack.setAuthor("Unknown");
        pack.setVersion("1.0");

        pack.setTileWidth(16);
        pack.setTileHeight(16);
        pack.setGameWidth(320);
        pack.setGameHeight(240);

        BufferedImage tileset;
        BufferedImage player;
        try {
            player = ImageIO.read(PackBuilder.class.getResourceAsStream("/pack/player.png"));
            tileset = ImageIO.read(PackBuilder.class.getResourceAsStream("/pack/tileset.png"));

            pack.putImage("player", player);
            pack.putImage("tileset", tileset);
        } catch (IOException e) {
            throw new InternalError(e);
        }

        pack.putSprite(new SubSprite(Pack.CRATE_ON_TARGET_SPRITE, tileset, 0, 0, 16, 16));
        pack.putSprite(new SubSprite(Pack.WALL_SPRITE, tileset, 16, 0, 16, 16));
        pack.putSprite(new SubSprite(Pack.CRATE_SPRITE, tileset, 32, 0, 16, 16));
        pack.putSprite(new SubSprite(Pack.FLOOR_SPRITE, tileset, 48, 0, 16, 16));

        AnimatedSprite target = new AnimatedSprite("target", 250);

        for (int i = 0; i < 4; i++) {
            target.addFrame(new SubSprite(String.format("target_%d", i), tileset, 16 * i, 16, 16, 16));
        }

        pack.putSprite(target);

        return pack;
    }
}