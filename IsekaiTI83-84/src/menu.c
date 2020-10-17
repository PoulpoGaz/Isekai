#include "menu.h"

#include <tice.h>
#include <stdint.h>

#include <graphx.h>
#include <stdarg.h>
#include "utils.h"
#include "key.h"
#include "screen.h"

void draw_menu(screen_t *self) {
    menu_t *menu = (menu_t *) self;
	uint8_t i;
    uint8_t y = 140;
    static uint8_t offset = 0;

    //gfx_ZeroScreen();

    draw_menu_background(true, true, offset);

    for (i = 0; i < menu->length; i++) {
        print_string_centered(menu->menus[i], y);

        if (i == menu->cursor_pos) {
            gfx_SetColor(WHITE);
            gfx_FillRectangle(95, y + 2, 8, 2);
        }

        y += 20;
    }

    offset++;

    gfx_SwapDraw();
}

uint8_t update_menu(screen_t *self) {
    menu_t *menu = (menu_t *) self;
    uint8_t *next_state = NULL;
    uint8_t temp;

    uint8_t wait = 10;

    while (next_state == NULL) {
        scan();

        if (key_released(key_Down)) {
            menu->cursor_pos++;

            if (menu->cursor_pos >= menu->length) {
                menu->cursor_pos = 0;
            }

            temp = DRAW; // DRAW is a define
            next_state = &temp;
        }

        if (key_released(key_Up)) {
            menu->cursor_pos--; // uint8_t (0 - 255 range)

            if (menu->cursor_pos >= menu->length) {
                menu->cursor_pos = menu->length - 1;
            }

            temp = DRAW; // DRAW is a define
            next_state = &temp;
        }

        if (key_released(key_Enter)) {
            next_state = &menu->redirection[menu->cursor_pos];
        }

        if (wait > 0) {
            wait--;
        } else {
            temp = DRAW;
            next_state = &temp;
        }
    }

    return *next_state;
}

void show_menu(screen_t *self) {
    menu_t *menu = (menu_t *) self;

    menu->cursor_pos = 0;
}

menu_t new_menu(uint8_t length, char **menus, uint8_t *redirection) {
    menu_t menu;

    menu.parent.draw = draw_menu;
    menu.parent.update = update_menu;
    menu.parent.show = show_menu;
    menu.parent.hide = NULL;

    menu.menus = menus;
    menu.redirection = redirection;
    menu.length = length;

    return menu;
}