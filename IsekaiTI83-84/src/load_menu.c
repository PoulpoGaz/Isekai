#include "load_menu.h"

void draw_load_menu(screen_t *this) {

}

uint8_t update_load_menu(screen_t *this) {
	return EXIT;
}

void show_load_menu(screen_t *this) {

}

void hide_load_menu(screen_t *this) {

}

load_menu_t new_load_menu() {
	load_menu_t menu;

	menu.parent.draw = draw_load_menu;
	menu.parent.update = update_load_menu;
	menu.parent.show = show_load_menu;
	menu.parent.hide = hide_load_menu;

	return menu;
}