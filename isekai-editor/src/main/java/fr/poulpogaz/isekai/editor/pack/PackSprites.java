package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.commons.pack.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class PackSprites {

    private static BufferedImage TILESET;

    private static BufferedImage FLOOR;
    private static BufferedImage WALL;
    private static BufferedImage TARGET;
    private static BufferedImage CRATE;
    private static BufferedImage CRATE_ON_TARGET;

    private static BufferedImage PLAYER;

    public static void initialize() throws Exception {
        TILESET = ImageIO.read(Objects.requireNonNull(PackSprites.class.getResourceAsStream("/tileset.png")));

        FLOOR           = TILESET.getSubimage(0, 0, 16, 16);
        WALL            = TILESET.getSubimage(16, 0, 16, 16);
        TARGET          = TILESET.getSubimage(64, 0, 16, 16);
        CRATE           = TILESET.getSubimage(32, 0, 16, 16);
        CRATE_ON_TARGET = TILESET.getSubimage(48, 0, 16, 16);
        PLAYER          = TILESET.getSubimage(32, 16, 16, 16);
    }

    public static BufferedImage img(Tile tile) {
        return switch (tile) {
            case WALL -> WALL;
            case CRATE -> CRATE;
            case FLOOR -> FLOOR;
            case TARGET -> TARGET;
            case CRATE_ON_TARGET -> CRATE_ON_TARGET;
        };
    }

    public static BufferedImage getTileset() {
        return TILESET;
    }

    public static BufferedImage getFloor() {
        return FLOOR;
    }

    public static BufferedImage getWall() {
        return WALL;
    }

    public static BufferedImage getTarget() {
        return TARGET;
    }

    public static BufferedImage getCrate() {
        return CRATE;
    }

    public static BufferedImage getCrateOnTarget() {
        return CRATE_ON_TARGET;
    }

    public static BufferedImage getPlayer() {
        return PLAYER;
    }
}