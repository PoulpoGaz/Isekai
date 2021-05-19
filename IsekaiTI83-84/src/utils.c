#include <stdlib.h>
#include <graphx.h>
#include <tice.h>
#include <math.h>
#include <debug.h>

#include "utils.h"
#include "gfx/gfx.h"

#define EARTH_RADIUS 110

#define N_STARS       64
#define CENTER_X     170
#define CENTER_Y     240
#define INNER_RADIUS 170
#define OUTER_RADIUS 260

/*
    Stars are placed between two circles of center (160; 240) and radius 170 and 260
*/
typedef struct star_t {
    float x;
    float y;
    float vx;
    float vy;
    uint8_t size;
} star_t;

star_t stars[N_STARS];

gfx_UninitedSprite(scale_rotate_sprite, 32, 32);  // => gfx_sprite_t *scale_rotate_sprite = ...

void print_string_centered_between(const char *str, uint8_t y, uint24_t min_x, uint24_t width) {
    uint8_t x = get_x_centered_between(str, min_x, width);

    gfx_PrintStringXY(str, x, y);
}

void print_string_centered(const char *str, uint8_t y) {
	gfx_PrintStringXY(str, get_x_centered(str), y);
}

uint24_t get_x_centered_between(const char *str, uint24_t min_x, uint24_t width) {
     return (width - gfx_GetStringWidth(str)) / 2 + min_x;
}


// MENU BACKGROUND
void generate_stars() {
    int8_t dir_x = randInt(0, 1) == 0 ? -1 : 1;
    int8_t dir_y = randInt(0, 1) == 0 ? -1 : 1;

    float star_vx = (float) rand() / RAND_MAX * dir_x;
    float star_vy = (float) rand() / RAND_MAX * dir_y;

    for (uint8_t i = 0; i < N_STARS; i++) {
        uint16_t x = randInt(0, LCD_WIDTH);
        uint8_t y = randInt(0, LCD_HEIGHT);

        star_t *star = &stars[i];
        star->x = x;
        star->y = y;
        star->vx = star_vx * randInt(1, 4);
        star->vy = star_vy * randInt(1, 4);
        star->size = randInt(1, 2);
    }
}

void draw_menu_background(bool draw_title, bool draw_chicken) {
    static uint8_t offset = 0;

    // Draw background
    gfx_FillScreen(BACKGROUND_1);

    gfx_SetColor(BACKGROUND_2);
    gfx_FillCircle(160, 240, INNER_RADIUS);

    gfx_SetColor(BACKGROUND_3);
    gfx_FillCircle(160, 240, 140);

    gfx_SetColor(BACKGROUND_4);
    gfx_FillCircle(160, 240, EARTH_RADIUS);

    gfx_SetColor(BACKGROUND_5);
    gfx_FillCircle(160, 240, 90);

    gfx_SetColor(BACKGROUND_6);
    gfx_FillCircle(160, 240, 70);

    gfx_SetColor(BACKGROUND_7);
    gfx_FillCircle(160, 240, 50);

    // Draw stars
    for (uint8_t i = 0; i < N_STARS; i++) {
        star_t *star = &stars[i];

        star->x += star->vx;
        star->y += star->vy;

        if (star->x < 0) {
            star->x = LCD_WIDTH;
        }
        if (star->y < 0) {
            star->y = LCD_HEIGHT;
        }
        if (star->x > LCD_WIDTH) {
            star->x = 0;
        }
        if (star->y > LCD_HEIGHT) {
            star->y = 0;
        }

        int16_t diff_x = (int16_t) (star->x) - LCD_WIDTH / 2;
        int16_t diff_y = (int16_t) (star->y) - LCD_HEIGHT;

        uint16_t radius = diff_x * diff_x + diff_y * diff_y;

        if (radius < EARTH_RADIUS * EARTH_RADIUS) {
            continue;
        } else if (radius < 140 * 140) {
            gfx_SetColor(STAR_COLOR_3);
        } else if (radius < INNER_RADIUS * INNER_RADIUS) {
            gfx_SetColor(STAR_COLOR_2);
        } else {
            gfx_SetColor(WHITE);
        }

        gfx_FillRectangle((uint16_t) star->x, (uint8_t) star->y, star->size, star->size);

    }
    // Draw chickens
    if (draw_chicken) {
    	offset = offset % 16;
        uint8_t frame = offset / 4;

        gfx_ScaledTransparentSprite_NoClip(chicken_down_walk_tiles[frame], 144, 104, 2, 2);

        offset++;
    }

    // Draw title
    if(draw_title) {
        print_string_centered("ISEKAI", 50);
    }
}
