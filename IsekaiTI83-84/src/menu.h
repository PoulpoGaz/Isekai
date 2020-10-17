#ifndef __menu_h__
#define __menu_h__

#include "screen.h";

typedef struct menu_t {
	screen_t parent;
	char **menus;
	uint8_t *redirection;
	uint8_t length;

	uint8_t cursor_pos;
} menu_t;

menu_t new_menu(uint8_t length, char **menus, uint8_t *redirection);

#endif