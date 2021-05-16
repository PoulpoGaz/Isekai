#include "game.h"
#include "defines.h"
#include "gfx/gfx.h"
#include "key.h"
#include "utils.h"
#include "loader.h"
#include "transition.h"
#include "menus.h"

#include <graphx.h>
#include <debug.h>
#include <stdint.h>

#define is_outside_map(x, y)    x < 0 || y < 0 || x >= game.level_width || y >= game.level_height
#define is_outside_map_index(i) i < 0 || i > game.level_width * game.level_height
#define OUTSIDE 0xFF

/*
	95 = nhtr + nnhtr + nop = 28 + 28 + 55
	nhtr = number of horizontal runs
	nnhtr = number of non horizontal runs
	nop = number of opaque pixels
*/
gfx_UninitedRLETSprite(chicken_right, 95);

void change_level();
void init_level();
void center_camera();
void move_camera(int8_t dir_x, int8_t dir_y);
void draw();
bool update();
void move(int8_t dir_x, int8_t dir_y);
uint8_t get_tile_at(int8_t x, int8_t y); // OUTISDE if outside
uint8_t get_tile_at_index(uint16_t i);
uint16_t compute_scroll(int16_t value, uint8_t length, uint8_t max_length);

void init_game() {
	gfx_tilemap_t *tilemap = &game.tilemap;

	tilemap->map = NULL;
	tilemap->tiles = tileset_tiles;
	tilemap->tile_width = 16;
	tilemap->tile_height = 16;
	tilemap->type_width = gfx_tile_16_pixel;
	tilemap->type_height = gfx_tile_16_pixel;
	tilemap->x_loc = 0;
	tilemap->y_loc = 0;

	gfx_sprite_t *sprite = gfx_MallocSprite(16, 16);
	gfx_sprite_t *chicken_left_sprite = gfx_MallocSprite(16, 16);

	gfx_ConvertFromRLETSprite(chicken_left, chicken_left_sprite);
	gfx_FlipSpriteY(chicken_left_sprite, sprite);
	gfx_ConvertToRLETSprite(sprite, chicken_right);

	free(sprite);
	free(chicken_left_sprite);
}

void change_level(void (*draw_in)(), void (*draw_out)()) {
	if (draw_in != NULL) {
		transition_in(draw_in);
	} else {
		gfx_FillScreen(BLACK);
	}

	print_string_centered("Loading...", 124);
	gfx_SwapDraw();

	load_level_data(*current_pack, current_pack->current_level);
	init_level();

	if (draw_out != NULL) {
		transition_out(draw_out);
	}
}

void run_game() {
	change_level(NULL, &draw);

	do {
		draw();
		gfx_SwapDraw();
	} while (update());

    free(game.tilemap.map);
	state = MAIN_MENU;
}

void init_level() {
    gfx_tilemap_t *tilemap = &game.tilemap;

	tilemap->draw_width = min(MAX_DRAW_WIDTH, game.level_width);
	tilemap->draw_height = min(MAX_DRAW_HEIGHT, game.level_height);

	center_camera();

	game.nb_targets = 0;
	game.nb_crates_on_target = 0;

	uint16_t size = game.level_width * game.level_height;
	for (uint16_t i = 0; i < size; i++) {
		uint8_t j = tilemap->map[i];

		if (j == TARGET) {
			game.nb_targets++;
		} else if (j == CRATE_ON_TARGET) {
			game.nb_crates_on_target++;
			game.nb_targets++;
		}
	}

	game.player_dir = DOWN;
	game.moves = 0;
	game.pushs = 0;
}

void center_camera() {
	gfx_tilemap_t *tilemap = &game.tilemap;

	if (game.level_width > MAX_DRAW_WIDTH) {
		int16_t x = game.player_x * 16 - MAX_DRAW_WIDTH * 8; // * 16 / 2 = * 8

		game.scroll_x = compute_scroll(x, game.level_width, MAX_DRAW_WIDTH);
		tilemap->x_loc = 0;
	} else {
		tilemap->x_loc = (LCD_WIDTH - game.level_width * 16) / 2;
	}

	if (game.level_height > MAX_DRAW_HEIGHT) {
		int16_t y = game.player_y * 16 - (MAX_DRAW_HEIGHT + 1) * 8; // * 16 / 2 = * 8

		game.scroll_y = compute_scroll(y, game.level_height, MAX_DRAW_HEIGHT);
		tilemap->y_loc = 0;
	} else {
		tilemap->y_loc = (LCD_HEIGHT - game.level_height * 16) / 2;
	}
}

void move_camera(int8_t dir_x, int8_t dir_y) {
	if (dir_x != 0 && game.level_width > MAX_DRAW_WIDTH) {
		int16_t x = game.scroll_x + dir_x; // * 16 / 2 = * 8

		game.scroll_x = compute_scroll(x, game.level_width, MAX_DRAW_WIDTH);
	}

	if (dir_y != 0 && game.level_height > MAX_DRAW_HEIGHT) {
		int16_t y = game.scroll_y + dir_y; // * 16 / 2 = * 8

		game.scroll_y = compute_scroll(y, game.level_height, MAX_DRAW_HEIGHT);
	}
}

uint16_t compute_scroll(int16_t value, uint8_t length, uint8_t max_length) {
	if (value < 0) {
		return 0;
	} else if (value + max_length * 16 > length * 16) {
		return (length - max_length) * 16;
	} else {
		return (uint16_t) value;
	}
}

void draw() {
	gfx_FillScreen(BACKGROUND_7);

	gfx_Tilemap(&game.tilemap, game.scroll_x, game.scroll_y);

	int x = game.player_x * 16 + game.tilemap.x_loc - game.scroll_x;
	int y = game.player_y * 16 + game.tilemap.y_loc - game.scroll_y;

	switch (game.player_dir) {
		case UP:
			gfx_RLETSprite(chicken_up, x, y);
			break;
		case DOWN:
			gfx_RLETSprite(chicken_down, x, y);
			break;
		case LEFT:
			gfx_RLETSprite(chicken_left, x, y);
			break;
		case RIGHT:
			gfx_RLETSprite(chicken_right, x, y);
			break;
	}

	gfx_PrintStringXY("Moves:", 1, 1);
	gfx_SetTextXY(49, 1);
	gfx_PrintUInt(game.moves, 5);

	gfx_PrintStringXY("Pushs:", 92, 1);
	gfx_SetTextXY(140, 1);
	gfx_PrintUInt(game.pushs, 5);
}

bool update() {
	while (true) {
		scan();

		if (key_released(key_Del)) {
			return false;
		}

		bool repaint = false;

		if (key_pressed(key_2)) {
			move_camera(0, 16);

			repaint = true;
		}
		if (key_pressed(key_4)) {
			move_camera(-16, 0);

			repaint = true;
		}
		if (key_pressed(key_6)) {
			move_camera(16, 0);

			repaint = true;
		}
		if (key_pressed(key_8)) {
			move_camera(0, -16);

			repaint = true;
		}
		if (key_released(key_5)) {
			center_camera();

			repaint = true;
		}

		// Move player
		if (key_released(key_Up)) {
			move(0, -1);
			center_camera();

			game.player_dir = UP;
			repaint = true;
		}
		if (key_released(key_Down)) {
			move(0, 1);
			center_camera();

			game.player_dir = DOWN;
			repaint = true;
		}
		if (key_released(key_Left)) {
			move(-1, 0);
			center_camera();

			game.player_dir = LEFT;
			repaint = true;
		}
		if (key_released(key_Right)) {
			move(1, 0);
			center_camera();

			game.player_dir = RIGHT;
			repaint = true;
		}
		if (key_released(key_2nd)) {
			change_level(NULL, NULL);
			repaint = true;
		}

		if (repaint) {
			if (game.nb_targets == game.nb_crates_on_target) { // win check
				uint16_t level = current_pack->current_level;
				current_pack->pushs[level] = game.pushs;
				current_pack->moves[level] = game.moves;

				if (level + 1 >= current_pack->n_levels) {
					draw_menu_background(false, true);
					print_string_centered("You win!", 124);
					gfx_SwapDraw();

					while (!os_GetCSC());

					state = MAIN_MENU;

					return false;
				} else {
					current_pack->current_level++;

					if (current_pack->current_level > current_pack->max_level_reached) {
						current_pack->max_level_reached = current_pack->current_level;
					}

					change_level(&draw, &draw);
				}
			}

			return true;
		}
	}
}

void move(int8_t dir_x, int8_t dir_y) {
	int16_t new_x = game.player_x + dir_x;
	int16_t new_y = game.player_y + dir_y;

	int16_t x2 = new_x + dir_x;
	int16_t y2 = new_y + dir_y;

	uint16_t i = new_y * game.level_width + new_x;
	uint16_t i2 = y2 * game.level_width + x2;

	uint8_t dest_tile = OUTSIDE;

	switch (get_tile_at_index(i)) {
		case FLOOR:
		case TARGET:
			game.player_x = new_x;
			game.player_y = new_y;
			game.moves++;
			break;
		case CRATE_ON_TARGET:
			dest_tile = get_tile_at_index(i2);

			if (dest_tile == FLOOR) {
				game.player_x = new_x;
				game.player_y = new_y;

				game.tilemap.map[i] = TARGET;
				game.tilemap.map[i2] = CRATE;

				game.nb_crates_on_target--;
				game.pushs++;
				game.moves++;
			} else if (dest_tile == TARGET) {
				game.player_x = new_x;
				game.player_y = new_y;

				game.tilemap.map[i] = TARGET;
				game.tilemap.map[i2] = CRATE_ON_TARGET;
				game.pushs++;
				game.moves++;
			}
			break;
		case CRATE:
			dest_tile = get_tile_at_index(i2);

			if (dest_tile == FLOOR) {
				game.player_x = new_x;
				game.player_y = new_y;

				game.tilemap.map[i] = FLOOR;
				game.tilemap.map[i2] = CRATE;
				game.pushs++;
				game.moves++;
			} else if (dest_tile == TARGET) {
				game.player_x = new_x;
				game.player_y = new_y;

				game.tilemap.map[i] = FLOOR;
				game.tilemap.map[i2] = CRATE_ON_TARGET;

				game.nb_crates_on_target++;
				game.pushs++;
				game.moves++;
			}
			break;
		default: // include WALL
			break;
	}
}

uint8_t get_tile_at_index(uint16_t i) {
	if (is_outside_map_index(i)) {
		return OUTSIDE;
	}

	return game.tilemap.map[i];
}


uint8_t get_tile_at(int8_t x, int8_t y) {
	return get_tile_at_index(y * game.level_width + x);
}
