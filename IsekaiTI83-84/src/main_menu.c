#include <stdint.h>
#include <graphx.h>

#include "main_menu.h"
#include "utils.h"

#include "key.h"
#include "defines.h"

uint8_t selected = 0;
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
