#ifndef __define_h__
#define __define_h__

#include <stdint.h>
#include <graphx.h>

#define TILE_WIDTH 16
#define TILE_HEIGHT 16

typedef struct pack_info_t {
	char app_var[8];
	char *name;
	char *author;
	char *version;
	uint8_t n_levels;
	uint8_t current_level;
} pack_info_t;

enum direction_t {
	UP,
	DOWN,
	LEFT,
	RIGHT
};

typedef struct game_t {
	gfx_tilemap_t tilemap;

	uint8_t player_x;
	uint8_t player_y;
	enum direction_t player_dir;

	uint8_t level_width;
	uint8_t level_height;
	uint8_t nb_targets;
	uint8_t nb_crates_on_target;

	uint16_t scroll_x;
	uint16_t scroll_y;

	uint16_t moves;
	uint16_t pushs;
} game_t;

enum state_t {
	MAIN_MENU,
	PACK_INFO,
	IN_GAME,
	STATS,
	EXIT
};

extern enum state_t state;
extern pack_info_t *current_pack;
extern game_t game;

#define MAX_DRAW_WIDTH 20
#define MAX_DRAW_HEIGHT 15

#define FLOOR 0
#define WALL 1
#define CRATE 2
#define CRATE_ON_TARGET 3
#define TARGET 4

#endif
