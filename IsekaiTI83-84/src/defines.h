#ifndef __define_h__
#define __define_h__

#include <graphx.h>

#define floor 0
#define wall 1
#define target 2
#define crate 3
#define crate_on_target 4

#define player_down_static 5
#define player_up_static 6
#define player_left_static 7
#define player_right_static 8

#define player_down_walk 9
#define player_up_walk 10
#define player_left_walk 11
#define player_right_walk 12

#define n_sprites 13

typedef struct pack_t {
	char app_var[8];
	char *name;
	char *author;
	char *version;
	uint8_t n_levels;

	gfx_sprite_t *sprites[n_sprites];
} pack_t;

#endif