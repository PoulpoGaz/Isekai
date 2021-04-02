#include <stdint.h>
#include <graphx.h>

#include "menus.h"
#include "utils.h"

#include "key.h"
#include "defines.h"
#include "loader.h"

#define MAX_PACK 7

// common variable for all menus
// in main menu, value is between 0 and 3
// in pack list menu, value is between 0 and MAX_PACK (= 7)
uint8_t selected = 0;

/*******************
 **** MAIN MENU ****
 ******************/
const char *menus[] = {"Nouvelle partie", "Continuer", "Stats", "Quitter"};
const char *selected_menus[] = {"> Nouvelle partie <", "> Continuer <", "> Stats <", "> Quitter <"};

void draw_main_menu(uint8_t offset) {
	draw_menu_background(true, true, offset);

	uint8_t y = 140;
	for (uint8_t i = 0; i < 4; i++) {
		if (selected == i) {
			print_string_centered(selected_menus[i], y);
		} else {
			print_string_centered(menus[i], y);
		}

		y += 20;
	}
}

bool update_main_menu() {
	if (key_released(key_Down)) {
		if (selected == 3) {
			selected = 0;
		} else {
			selected++;
		}

	} else if (key_released(key_Up)) {
		if (selected == 0) {
			selected = 3;
		} else {
			selected--;
		}
	}

	if (key_released(key_2nd)) {
		switch (selected) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				state = EXIT;
				break;
		}

		return true;
	}

	return false;
}

void show_main_menu() {
	uint8_t wait = 0;
	uint8_t offset = 0;

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

/*******************
 **** PACK LIST ****
 ******************/
 uint8_t scroll = 0;

void draw_pack_list_menu(uint8_t offset) {
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
	gfx_PrintStringXY("[Del] Back/Quit", 15, 190);
	gfx_PrintStringXY("[Arrow] Move", 15, 200);
	gfx_PrintStringXY("[2nd] Restart level/Enter", 15, 210);
}

bool update_pack_list_menu() {
	if (key_released(key_Up) && selected + scroll != 0) {
		if (selected == 0) {
			scroll--;
		} else {
			selected--;
		}
	}

	if (key_released(key_Down) && selected + scroll < MAX_PACK) {
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

	return false;
}

void show_pack_list_menu() {
	uint8_t wait = 0;
	uint8_t offset = 0;

	selected = 0;

	while (true) {
		if (wait == 0) {
			draw_pack_list_menu(offset);
			gfx_SwapDraw();

			wait = 10;
			offset++;
		}

		scan();
		if (update_pack_list_menu()) {
			return;
		}

		wait--;
	}
}
