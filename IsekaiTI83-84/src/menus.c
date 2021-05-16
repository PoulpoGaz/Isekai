#include <stdint.h>
#include <graphx.h>
#include <debug.h>

#include "menus.h"
#include "utils.h"

#include "key.h"
#include "defines.h"
#include "loader.h"

#define MAX_PACK 7

void draw_main_menu();
bool update_main_menu();
void show_danger_zone();

void draw_stats_menu();
bool update_stats_menu();

/*************
 * MAIN MENU *
 *************/

uint8_t selected = 0;
uint8_t scroll = 0;

void show_main_menu() {
	uint8_t wait = 0;

	while (true) {
		if (wait == 0) {
			draw_main_menu();
			gfx_SwapDraw();

			wait = 10;
		}

		scan();
		if (update_main_menu()) {
			return;
		}

		wait--;
	}
}

void draw_main_menu() {
	draw_menu_background(true, true);

	if (num_packs == 0) {
		print_string_centered("No pack found ;(", 100);
	} else {
		print_string_centered("Choose a pack", 70);

		uint8_t max = min(MAX_PACK, num_packs);

		gfx_SetColor(WHITE);

		uint8_t y = 100;
		for (uint8_t i = 0; i < max; i++) {
			pack_info_t *pack = &packs[i + scroll];

			gfx_PrintStringXY(pack->name, 50, y);

			gfx_SetTextXY(298, y);
			gfx_PrintUInt(pack->current_level + 1, 2);

			if (i == selected) {
				gfx_FillRectangle(35, y + 2, 10, 4);
			}

			y += 10;
		}
	}

	gfx_PrintStringXY("Controls", 10, 180);
	gfx_PrintStringXY("[Del]", 15, 190);
	gfx_PrintStringXY("[Arrow]", 15, 200);
	gfx_PrintStringXY("[2nd]", 15, 210);
	gfx_PrintStringXY("[Mode]", 15, 220);
	gfx_PrintStringXY("[Stats]", 15, 230);

	gfx_PrintStringXY("Back/Quit", 80, 190);
	gfx_PrintStringXY("Move", 80, 200);
	gfx_PrintStringXY("Restart level/Enter", 80, 210);
    gfx_PrintStringXY("Pack info", 80, 220);
    gfx_PrintStringXY("Pack stats", 80, 230);

	gfx_PrintStringXY("1.0", 300, 220);
	gfx_PrintStringXY("By PoulpoGaz", 230, 230);
}

bool update_main_menu() {
	if (num_packs > 0) {
		if (key_released(key_Up) && selected + scroll != 0) {
			if (selected == 0) {
				scroll--;
			} else {
				selected--;
			}
		}

		if (key_released(key_Down) && selected + scroll + 1 < num_packs) {
			if (selected == MAX_PACK - 1) {
				scroll++;
			} else {
				selected++;
			}
		}

		current_pack = &packs[scroll + selected];

		if (key_released(key_Right) && current_pack->current_level + 1 <= current_pack->max_level_reached) {
			current_pack->current_level++;
		}

		if (key_released(key_Left) && current_pack->current_level > 0) {
			current_pack->current_level--;
		}

		if (key_released(key_2nd)) {
	        state = IN_GAME;

			return true;
		}

		if (key_released(key_Mode)) {
	        state = PACK_INFO;

			return true;
		}

		if (key_released(key_Stat)) {
			state = STATS;

			return true;
		}
	}

	if (key_released(key_Del)) {
		state = EXIT;

		return true;
	}

	return false;
}

/*************
 * PACK INFO *
 *************/
void show_pack_info() {
	uint8_t wait = 0;

	while (true) {
		if (wait == 0) {
			draw_menu_background(false, true);

			print_string_centered(current_pack->name, 50);
			print_string_centered("By", 70);
			print_string_centered(current_pack->author, 80);
			print_string_centered("Progression:", 100);

			gfx_PrintStringXY("/", 156, 110);
			gfx_SetTextXY(116, 110);
			gfx_PrintUInt(current_pack->max_level_reached + 1, 5);

			gfx_SetTextXY(164, 110);
			gfx_PrintUInt(current_pack->n_levels, 5);

			print_string_centered("Press [Del] to return", 180);
			print_string_centered("Press [2nd] to reset progression", 190);

			gfx_SwapDraw();

			wait = 10;
		}

		scan();
		if (key_released(key_Del)) {
			state = MAIN_MENU;
			return;
		}

		if (key_released(key_2nd)) {
			show_danger_zone();
		}

		wait--;
	}
}

/***************
 * DANGER ZONE * sub menu of pack info
 ***************/

void show_danger_zone() {
	uint8_t selected = 1;

DRAW:
	gfx_FillScreen(BLACK);

	print_string_centered("Danger zone", 50);

	gfx_SetColor(RED);
	draw_triangle(160, 70, 210, 140, 110, 140);

	gfx_SetColor(WHITE);
	gfx_FillRectangle(157, 85, 6, 40);
	gfx_FillRectangle(157, 130, 6, 6);

	print_string_centered("Are you sure you want", 160);
	print_string_centered("to delete your progression?", 170);

	gfx_PrintStringXY("Yes", 93, 190); // width = 24
	gfx_PrintStringXY("No no no", 188, 190); // width = 54

	if (selected == 0) {
		gfx_Rectangle(90, 187, 28, 13);
	} else {
		gfx_Rectangle(185, 187, 58, 13);
	}

	gfx_SwapDraw();

	while (true) {
		scan();

		if (key_released(key_Left)) {
			selected = 0;
			goto DRAW;
		}
		if (key_released(key_Right)) {
			selected = 1;
			goto DRAW;
		}
		if (key_released(key_2nd)) {
			if (selected == 0) { // delete progression
				current_pack->current_level = 0;
				current_pack->max_level_reached = 0;

				for (uint8_t i = 0; i < current_pack->n_levels; i++) {
					current_pack->moves[i] = 0;
					current_pack->pushs[i] = 0;
				}
			}

			break;
		}
		if (key_released(key_Del)) {
			break;
		}
	}
}

/*********
 * STATS *
 *********/
uint8_t page;

void show_stats_menu() {
	uint8_t wait = 0;

	page = 0;

	while (true) {
		if (wait == 0) {
			draw_stats_menu();
			gfx_SwapDraw();

			wait = 10;
		}

		scan();
		if (update_stats_menu()) {
			return;
		}

		wait--;
	}
}

void draw_stats_menu() {
	gfx_ZeroScreen();
	draw_menu_background(false, true);

	gfx_PrintStringXY("Statistics of", 10, 10);
	gfx_PrintStringXY(current_pack->name, 108, 10);

	gfx_PrintStringXY("Level", 15, 24);
	gfx_PrintStringXY("Moves", 88, 24);
	gfx_PrintStringXY("Pushs", 224, 24);

	uint16_t start = page * 20;
	uint16_t max = min(start + 20, current_pack->n_levels);

	uint8_t y = 34;
	for (uint16_t i = start; i < max; i++) {
		gfx_SetTextXY(16, y);
		gfx_PrintUInt(i + 1, 2);

		uint16_t moves = current_pack->moves[i];
		if (moves > 0) {
			gfx_SetTextXY(88, y);
			gfx_PrintUInt(moves, 5);

			gfx_SetTextXY(224, y);
			gfx_PrintUInt(current_pack->pushs[i], 5);
		}

		y += 10;
	}
}

bool update_stats_menu() {
	if (key_released(key_Del)) {
		state = MAIN_MENU;

		return true;
	}

	if (key_released(key_Left) && page != 0) {
		page--;
	}

	if (key_released(key_Right) && (page + 1) * 20  < current_pack->n_levels) {
		page++;
	}

	return false;
}
