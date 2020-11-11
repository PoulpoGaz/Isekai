#include "main_menu.h"

#include <graphx.h>

#include "utils.h"
#include "loader.h"
#include "key.h"

void draw_main_menu(screen_t *this) {
	static uint8_t offset = 0;
    uint8_t i;

    draw_menu_background(true, true, offset);

    gfx_SetTextFGColor(WHITE);
    for (i = 0; i < num_packs; i++) {
        pack_t pack = packs[i];
        uint8_t y = i * 10 + 100;

        gfx_PrintStringXY(pack.name, 20, y);
        gfx_PrintStringXY(pack.author, 100, y);
        gfx_PrintStringXY(pack.version, 200, y);
    }

    offset++;
}

uint8_t update_main_menu(screen_t *this) {
    uint8_t *next_state = NULL;

    uint8_t wait = 10;

    while (next_state == NULL) {
        scan();

        if (key_released(key_Enter)) {
            uint8_t temp = DRAW;
            next_state = &temp;
        } else if (key_released(key_2nd)) {
            uint8_t temp = EXIT;
            next_state = &temp;
        }

        if (wait > 0) {
            wait--;
        } else {
            uint8_t temp = DRAW;
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
