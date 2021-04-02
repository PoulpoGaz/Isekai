#include <tice.h>
#include <stdlib.h>
#include <stdint.h>

#include <debug.h>
#include <graphx.h>
#include <keypadc.h>

#include "gfx/gfx.h"

#include "loader.h"
#include "utils.h"
#include "menus.h"

enum state_t state = PACK_LIST;

void run();

void main(void) {
	dbg_ClearConsole();

	load_packs_info();

 	gfx_Begin(gfx_8bpp);
    gfx_SetPalette(palette, sizeof_palette, 0);
    gfx_SetTextFGColor(WHITE);
    gfx_SetTransparentColor(MAGENTA);
    gfx_SetDrawBuffer();

    run();

	free_packs_info();

 	gfx_End();
    prgm_CleanUp();
}

void run() {
    while (state != EXIT) {
        switch (state) {
            case MAIN_MENU:
                show_main_menu();
                break;
            case PACK_LIST:
				show_pack_list_menu();
                break;
            case IN_GAME:
                break;
            case STATS:
                break;
			default:
				return;
        }
    }
}
