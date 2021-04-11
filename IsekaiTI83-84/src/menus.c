#include <stdint.h>
#include <graphx.h>
#include <debug.h>

#include "menus.h"
#include "utils.h"

#include "key.h"
#include "defines.h"
#include "loader.h"

#define MAX_PACK 7

void draw_main_menu(uint8_t offset);
bool update_main_menu();
void show_danger_zone();

uint8_t selected = 0;
uint8_t scroll = 0;
uint8_t offset = 0;

void show_main_menu() {
	uint8_t wait = 0;
	offset = 0;

	selected = 0;

	while (true) {
		if (wait == 0) {
			draw_main_menu(offset);
			gfx_SwapDraw();

			wait = 10;
			offset++;
		}

		scan();
		if (update_main_menu()) {
			return;
		}

		wait--;
	}
}

void draw_main_menu(uint8_t offset) {
	draw_menu_background(true, true, offset);

	if (num_packs == 0) {
		print_string_centered("No pack found ;(", 100);
	} else {
		print_string_centered("Choose a pack", 70);

		uint8_t max = min(MAX_PACK, num_packs);

		gfx_SetColor(WHITE);

		uint8_t y = 100;
		for (uint8_t i = 0; i < max; i++) {
			pack_info_t pack = packs[i + scroll];

			gfx_PrintStringXY(pack.name, 50, y);
			gfx_PrintStringXY(pack.author, 100, y);
			gfx_PrintStringXY(pack.version, 200, y);

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
}

bool update_main_menu() {
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

	if (key_released(key_Del)) {
		state = EXIT;

		return true;
	}

	if (key_released(key_2nd)) {
		current_pack = &packs[scroll + selected];
        state = IN_GAME;

		return true;
	}


	if (key_released(key_Mode)) {
		current_pack = &packs[scroll + selected];
        state = PACK_INFO;

		return true;
	}

	return false;
}


void show_pack_info() {
	uint8_t wait = 0;
	offset = 0;

	selected = 0;

	while (true) {
		if (wait == 0) {
			draw_menu_background(false, true, offset);

			print_string_centered(current_pack->name, 50);
			print_string_centered("By", 70);
			print_string_centered(current_pack->author, 80);
			print_string_centered("Version:", 100);
			print_string_centered(current_pack->version, 110);

			print_string_centered("Press [Del] to return", 180);
			print_string_centered("Press [2nd] to reset progression", 190);

			gfx_SwapDraw();

			wait = 10;
			offset++;
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
			if (selected == 0) {
				// delete progression
			}

			break;
		}
		if (key_released(key_Del)) {
			break;
		}
	}
}
