#include <tice.h>
#include <stdlib.h>

#include <debug.h>
#include <graphx.h>
#include <keypadc.h>

#include "gfx/gfx.h"
#include "screen.h"

#include "menu.h"
#include "load_menu.h"

#include "utils.h"

#define MAIN_MENU 2
#define GAME 3
#define SETTINGS 4

void main(void) {
	menu_t main_menu;
	menu_t settings;
    load_menu_t load_menu;

	dbg_ClearConsole();
	dbg_sprintf(dbgout, "Initializing\n");

    load_menu = new_load_menu();
	main_menu = new_menu(3, string_array_of(3, "Play", "Settings", "Exit"), uin8_t_array_of(3, GAME, SETTINGS, EXIT));
	settings = new_menu(1, string_array_of(1, "Return"), uin8_t_array_of(1, MAIN_MENU));

 	gfx_Begin(gfx_8bpp);
    gfx_SetPalette(palette, sizeof_palette, 0);
    gfx_SetTextFGColor(0x01);
    gfx_SetTransparentColor(TRANSPARENT_COLOR);
    gfx_SetDrawBuffer();

    add_screen((screen_t *) &main_menu);  
    add_screen((screen_t *) &load_menu);
    add_screen((screen_t *) &settings);

    dbg_sprintf(dbgout, "Running\n");
    run();
 	
 	dbg_sprintf(dbgout, "Exiting\n");
 	gfx_End();
    prgm_CleanUp();
}