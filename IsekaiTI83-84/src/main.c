#include <tice.h>
#include <stdlib.h>
#include <stdint.h>

#include <debug.h>
#include <graphx.h>
#include <keypadc.h>

#include "gfx/gfx.h"

#include "loader.h"
#include "utils.h"
#include "main_menu.h"

enum state_t state = MAIN_MENU;

void run();

void main(void) {
	dbg_ClearConsole();

 	gfx_Begin(gfx_8bpp);
    gfx_SetPalette(palette, sizeof_palette, 0);
    gfx_SetTextFGColor(WHITE);
    gfx_SetTransparentColor(MAGENTA);
    gfx_SetDrawBuffer();

    run();

 	gfx_End();
    prgm_CleanUp();
}

// doesn't call scan();
void run() {
    while (state != EXIT) {
        switch (state) {
            case MAIN_MENU:
                show_main_menu();
                break;
            case PACK_LIST:
                break;
            case IN_GAME:
                break;
            case STATS:
                break;
            case EXIT:
                return;
        }
    }
}
