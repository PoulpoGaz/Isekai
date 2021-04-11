#include "transition.h"

#include <stdint.h>
#include <tice.h>
#include <graphx.h>
#include <debug.h>
#include "utils.h"

#define NUMBER_OF_TRANSITIONS 3

// ray transition
#define SQUARE_SIZE 16
#define MAX_NUM_RAYS 15

// horiz line transition
#define HORIZ_LINE_SIZE 8
#define N_LINES 30

void circle_transition_in(void (*draw)());
void ray_transition_in(void (*draw)());
void horiz_line_in(void (*draw)());

void circle_transition_out(void (*draw)());
void ray_transition_out(void (*draw)());
void horiz_line_out(void (*draw)());

void (*transitions_in[])(void (*draw)()) = {circle_transition_in, ray_transition_in, horiz_line_in};
void (*transitions_out[])(void (*draw)()) = {circle_transition_out, ray_transition_out, horiz_line_out};

uint8_t current_transition = 0;

void transition_in(void (*draw)()) {
    current_transition = randInt(0, NUMBER_OF_TRANSITIONS - 1);

    (*transitions_in[current_transition])(draw);
}

void transition_out(void (*draw)()) {
    (*transitions_out[current_transition])(draw);
}

// CIRCLE TRANSITION
void circle_transition_in(void (*draw)()) {
    (*draw)();
    gfx_SwapDraw();

    gfx_SetColor(BLACK);

    for (uint8_t radius = 10; radius <= 200; radius += 5) {
        gfx_BlitScreen();
        gfx_FillCircle(160, 120, radius);
        gfx_SwapDraw();
    }
}

void circle_transition_out(void (*draw)()) {
    gfx_SetColor(BLACK);

    for (uint8_t radius = 200; radius > 0; radius -= 5) {
        (*draw)();
        gfx_FillCircle(160, 120, radius);
        gfx_SwapDraw();
    }
}

// RAY TRANSITION
void ray_transition_in(void (*draw)()) {
    (*draw)();
    gfx_SwapDraw();

    gfx_SetColor(BLACK);

    const uint8_t width_add = 8;

    uint16_t width = width_add;
    while (true) {
        gfx_BlitScreen();

        uint16_t w = width;
        for (uint8_t i = 0; i < MAX_NUM_RAYS; i++) {
            if (w <= LCD_WIDTH) {
                gfx_FillRectangle(min(w, LCD_WIDTH) - width_add, i * SQUARE_SIZE, width_add, SQUARE_SIZE);
            }

            if (w <= SQUARE_SIZE) {
                break;
            }

            w -= SQUARE_SIZE;
        }

        gfx_SwapDraw();

        if (w >= LCD_WIDTH) { // exit
            return;
        }

        width += width_add;
    }
}

void ray_transition_out(void (*draw)()) {
    static uint16_t widths[MAX_NUM_RAYS];

    widths[0] = LCD_WIDTH;
    for (uint8_t i = 1; i < MAX_NUM_RAYS; i++) {
        widths[i] = widths[i - 1] + SQUARE_SIZE;
    }

    gfx_SetColor(BLACK);

    while (widths[MAX_NUM_RAYS - 1] != 0) {
        (*draw)();

        for (uint8_t i = 0; i < MAX_NUM_RAYS; i++) {
            uint8_t y = i * SQUARE_SIZE;

            uint16_t w = min(LCD_WIDTH, widths[i]);

            if (w == 0) {
                continue;
            }

            gfx_FillRectangle(LCD_WIDTH - w, y, w, SQUARE_SIZE);

            widths[i] -= 8;
        }

        gfx_SwapDraw();
    }
}

// HOZI LINE
void horiz_line_in(void (*draw)()) {
    (*draw)();
    gfx_SwapDraw();

    gfx_SetColor(BLACK);
    for (uint16_t w = 0; w < LCD_WIDTH; w += 5) {
        gfx_BlitScreen();

        for (uint8_t i = 0; i < N_LINES; i++) {
            uint8_t y = i * HORIZ_LINE_SIZE;

            if (i % 2 == 0) {
                gfx_FillRectangle(0, y, w, HORIZ_LINE_SIZE);
            } else {
                gfx_FillRectangle(LCD_WIDTH - w, y, w, HORIZ_LINE_SIZE);
            }
        }

        gfx_SwapDraw();
    }
}

void horiz_line_out(void (*draw)()) {
    gfx_SetColor(BLACK);
    for (uint16_t w = LCD_WIDTH; w > 0; w -= 5) {
        draw();

        for (uint8_t i = 0; i < N_LINES; i++) {
            uint8_t y = i * HORIZ_LINE_SIZE;

            if (i % 2 == 0) {
                gfx_FillRectangle(0, y, w, HORIZ_LINE_SIZE);
            } else {
                gfx_FillRectangle(LCD_WIDTH - w, y, w, HORIZ_LINE_SIZE);
            }
        }

        gfx_SwapDraw();
    }
}
