#include "move_handler.h"
#include "defines.h"
#include <debug.h>

#define is_outside_map(x, y)    x < 0 || y < 0 || x >= game.level_width || y >= game.level_height
#define is_outside_map_index(i) i < 0 || i > game.level_width * game.level_height
#define OUTSIDE 0xFF

int8_t last_dir_x = 0;
int8_t last_dir_y = 0;

uint8_t get_tile_at_index(uint16_t i) {
	if (is_outside_map_index(i)) {
		return OUTSIDE;
	}

	return game.tilemap.map[i];
}

uint8_t get_tile_at(int8_t x, int8_t y) {
	return get_tile_at_index(y * game.level_width + x);
}

void move(int8_t dir_x, int8_t dir_y) {
	int16_t new_x = game.player_x + dir_x;
	int16_t new_y = game.player_y + dir_y;

	int16_t x2 = new_x + dir_x;
	int16_t y2 = new_y + dir_y;

	uint16_t i = new_y * game.level_width + new_x;
	uint16_t i2 = y2 * game.level_width + x2;

	uint8_t dest_tile = OUTSIDE;

	bool hasMoved = false;
	switch (get_tile_at_index(i)) {
		case FLOOR:
		case TARGET:
			game.player_x = new_x;
			game.player_y = new_y;
			game.moves++;

			hasMoved = true;
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

			hasMoved = true;
		} else if (dest_tile == TARGET) {
			game.player_x = new_x;
			game.player_y = new_y;
			
			game.tilemap.map[i] = TARGET;
			game.tilemap.map[i2] = CRATE_ON_TARGET;
			game.pushs++;
			game.moves++;

			hasMoved = true;
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

			hasMoved = true;
		} else if (dest_tile == TARGET) {
			game.player_x = new_x;
			game.player_y = new_y;
			
			game.tilemap.map[i] = FLOOR;
			game.tilemap.map[i2] = CRATE_ON_TARGET;
			
			game.nb_crates_on_target++;
			game.pushs++;
			game.moves++;

			hasMoved = true;
		}
		break;
	default: // include WALL
		break;
	}

	if (hasMoved) {
		last_dir_x = dir_x;
		last_dir_y = dir_y;

		dbg_sprintf(dbgout, "helo, %i; %i\n", last_dir_x, last_dir_y);
	}
}

 bool can_undo() {
     return last_dir_x != 0 || last_dir_y != 0;
}

void undo() {
	dbg_sprintf(dbgout, "%i; %i\n", last_dir_x, last_dir_y);

	if (!can_undo()) {
		dbg_sprintf(dbgout, "can't undo!\n");
		return;
	}
	dbg_sprintf(dbgout, "undo!\n");


    uint8_t old_x = game.player_x - last_dir_x;
	uint8_t old_y = game.player_y - last_dir_y;

	uint8_t forward_x = game.player_x + last_dir_x;
	uint8_t forward_y = game.player_y + last_dir_y;

	uint16_t current_i = game.player_y * game.level_width + game.player_x;
	uint16_t forward_i = forward_y * game.level_width + forward_x;

	dbg_sprintf(dbgout, "%i, %i, %i, %i, %i, %i\n", old_x, old_y, forward_x, forward_y, current_i, forward_i);

	uint8_t current_tile = OUTSIDE;
	switch (get_tile_at_index(forward_i)) {
		case FLOOR:
		case TARGET:
			game.player_x = old_x;
			game.player_y = old_y;
			game.moves--;
		break;
	case CRATE_ON_TARGET:
		current_tile = get_tile_at_index(current_i);

		if (current_tile == FLOOR) {
			game.player_x = old_x;
			game.player_y = old_y;
			
			game.tilemap.map[forward_i] = TARGET;
			game.tilemap.map[current_i] = CRATE;
			
			game.nb_crates_on_target--;
			game.pushs--;
			game.moves--;
		} else if (current_tile == TARGET) {
			game.player_x = old_x;
			game.player_y = old_y;
			
			game.tilemap.map[forward_i] = TARGET;
			game.tilemap.map[current_i] = CRATE_ON_TARGET;
			game.pushs--;
			game.moves--;
		}
		break;
	case CRATE:
		current_tile = get_tile_at_index(current_tile);
		
		if (current_tile == FLOOR) {
			game.player_x = old_x;
			game.player_y = old_y;
			
			game.tilemap.map[forward_i] = FLOOR;
			game.tilemap.map[current_i] = CRATE;
			game.pushs--;
			game.moves--;
		} else if (current_tile == TARGET) {
			game.player_x = old_x;
			game.player_y = old_y;
			
			game.tilemap.map[forward_i] = FLOOR;
			game.tilemap.map[current_i] = CRATE_ON_TARGET;
			
			game.nb_crates_on_target++;
			game.pushs--;
			game.moves--;
		}
		break;
	default: // include WALL
		break;
	}

	reset_undo();
}

void reset_undo() {
    last_dir_x = 0;
    last_dir_y = 0;
}
