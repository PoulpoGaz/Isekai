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
#include "game.h"

enum state_t state = MAIN_MENU;
pack_info_t *current_pack = NULL;
game_t game;

void run();

void main(void) {
	dbg_ClearConsole();

	srand(rtc_Time());
	generate_stars();

	load_packs_info();
	init_game();

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
			case PACK_INFO:
				show_pack_info();
				break;
            case IN_GAME:
				run_game();
                break;
            case STATS:
                break;
			default:
				return;
        }
    }
}
