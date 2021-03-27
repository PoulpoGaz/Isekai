#include <tice.h>
#include <stdlib.h>

#include <debug.h>
#include <graphx.h>
#include <keypadc.h>

#include "gfx/gfx.h"
#include "screen.h"

#include "menu.h"
#include "main_menu.h"

#include "loader.h"
#include "utils.h"

#define MAIN_MENU 2
#define GAME 3

// MAIN
void main(void) {
	screen_t main_menu;

	dbg_ClearConsole();
	dbg_sprintf(dbgout, "Initializing\n");

    main_menu = new_main_menu();

 	gfx_Begin(gfx_8bpp);
    gfx_SetPalette(palette, sizeof_palette, 0);
    gfx_SetTextFGColor(0x00);
    gfx_SetDrawBuffer();

    add_screen(&main_menu);  

    dbg_sprintf(dbgout, "Running\n");
    run();
 	
 	dbg_sprintf(dbgout, "Exiting\n");
 	gfx_End();
    prgm_CleanUp();
}