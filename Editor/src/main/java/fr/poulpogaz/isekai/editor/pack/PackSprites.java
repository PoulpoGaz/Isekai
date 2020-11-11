package fr.poulpogaz.isekai.editor.pack;

public final class PackSprites {

    // TILES
    public static final String FLOOR = "floor";
    public static final String WALL = "wall";
    public static final String TARGET = "target";
    public static final String CRATE = "crate";
    public static final String CRATE_ON_TARGET = "crate_on_target";

    // PLAYER
    public static final String PLAYER_DOWN_STATIC = "down_static";
    public static final String PLAYER_UP_STATIC = "up_static";
    public static final String PLAYER_LEFT_STATIC = "left_static";
    public static final String PLAYER_RIGHT_STATIC = "right_static";

    public static final String PLAYER_DOWN_WALK = "down_walk";
    public static final String PLAYER_UP_WALK = "up_walk";
    public static final String PLAYER_LEFT_WALK = "left_walk";
    public static final String PLAYER_RIGHT_WALK = "right_walk";

    public static final String PLAYER_DEFAULT_STATIC = PLAYER_DOWN_STATIC;

    public static final String[] SPRITES = new String[] {
            FLOOR, WALL, TARGET, CRATE, CRATE_ON_TARGET,
            PLAYER_DOWN_STATIC, PLAYER_UP_STATIC, PLAYER_LEFT_STATIC, PLAYER_RIGHT_STATIC,
            PLAYER_DOWN_WALK, PLAYER_UP_WALK, PLAYER_LEFT_WALK, PLAYER_RIGHT_WALK
    };

    public static final int SIZE = SPRITES.length;
}