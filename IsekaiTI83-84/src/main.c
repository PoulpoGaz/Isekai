#include <tice.h>
#include <stdlib.h>

#include <debug.h>
#include <graphx.h>
#include <keypadc.h>

#include "gfx/gfx.h"
#include "screen.h"

#include "menu.h"
#include "loader.h"

#include "utils.h"
#include "key.h"

#define MAIN_MENU 2
#define GAME 3

// MAIN MENU

void draw_main_menu(screen_t *this) {
    uint8_t i;

    gfx_ZeroScreen();

    gfx_SetColor(1);
    for (i = 0; i < num_packs; i++) {
        pack_t pack = packs[i];

        print_string_centered(pack.name, i * 10);
        print_string_centered(pack.author, (i + 1) * 10);
        print_string_centered(pack.version, (i + 2) * 10);
        print_string_centered(pack.app_var, (i + 3) * 10);
    }

    gfx_SwapDraw();
}

uint8_t update_main_menu(screen_t *this) {
    uint8_t *next_state = NULL;
    uint8_t temp;

    while (next_state == NULL) {
        scan();

        if (key_released(key_Enter)) {
            uint8_t temp = DRAW;
            next_state = &temp;
        } else if (key_released(key_2nd)) {
            uint8_t temp = EXIT;
            next_state = &temp;
        }
    }

    return *next_state;
}

screen_t new_main_menu() {
    screen_t screen;

    screen.draw = draw_main_menu;
    screen.update = update_main_menu;
    screen.show = NULL;
    screen.hide = NULL;

    return screen;
}

// MAIN

void main(void) {
	screen_t main_menu;

	dbg_ClearConsole();
	dbg_sprintf(dbgout, "Initializing\n");

    load_packs();
    load_save();

    main_menu = new_main_menu();

 	gfx_Begin(gfx_8bpp);
    gfx_SetPalette(palette, sizeof_palette, 0);
    gfx_SetTextFGColor(0x01);
    gfx_SetTransparentColor(TRANSPARENT_COLOR);
    gfx_SetDrawBuffer();

    add_screen(&main_menu);  

    dbg_sprintf(dbgout, "Running\n");
    run();
 	
    free_packs();

 	dbg_sprintf(dbgout, "Exiting\n");
 	gfx_End();
    prgm_CleanUp();
}