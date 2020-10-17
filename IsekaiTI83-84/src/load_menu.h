#ifndef __load_menu_h__
#define __load_menu_h__

#include "screen.h"


typedef struct load_menu_t {
	screen_t parent;
//	pack_t *packs;
} load_menu_t;

load_menu_t new_load_menu();

#endif