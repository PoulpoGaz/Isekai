#include <stdlib.h>
#include <graphx.h>
#include <tice.h>
#include <math.h>
#include <debug.h>

#include "utils.h"
#include "gfx/gfx.h"
#include "cos_sin_table.h"

#define DEG           16
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
    uint8_t theta;
    uint16_t radius;
    uint8_t speed;
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
    for (uint8_t i = 0; i < N_STARS; i++) {
        star_t *star = &stars[i];

        star->theta = randInt(0, 127);
        star->radius = randInt(INNER_RADIUS, OUTER_RADIUS);
        star->speed = randInt(1, 3);
        star->size = randInt(1, 2);
    }
}

void draw_menu_background(bool draw_title, bool draw_chicken, uint8_t offset) {
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
    gfx_SetColor(WHITE);

    for (uint8_t i = 0; i < N_STARS; i++) {
        star_t *star = &stars[i];

        star->theta += star->speed;

        if (star->theta > 127) {
            star->theta -= 127;
        }

        int16_t x = (int16_t) (fast_cos(255 - star->theta) * star->radius) + CENTER_X;
        int16_t y = (int16_t) (fast_sin(255 - star->theta) * star->radius) + CENTER_Y;

        gfx_FillRectangle(x, y, star->size, star->size);
    }

    // Draw chickens
    if (draw_chicken) {
    	float x = EARTH_RADIUS + 12;
    	float y = 0;

    	offset = offset % DEG;

    	if (offset > 0) {
    		rotate(&x, &y, offset);
    	}

        gfx_rletsprite_t *rlet_sprite = NULL;
    	for (int8_t i = -1; i <= 180 / DEG; i++) {
    		uint16_t y_draw = (uint16_t) (y + 224);

    		if (y_draw < 240) {
    	  		uint16_t x_draw = (uint16_t) (x + 144);

    			gfx_RotateScaleSprite(chicken_left_walk_tiles[offset / 5], scale_rotate_sprite, 191 - (i + 1) * DEG - offset, 128);

                rlet_sprite = gfx_ConvertMallocRLETSprite(scale_rotate_sprite);
                gfx_RLETSprite(rlet_sprite, x_draw, y_draw);
                free(rlet_sprite);
			}

    		rotate(&x, &y, DEG);
    	}
    }

    // Draw title
    if(draw_title) {
        print_string_centered("ISEKAI", 50);
    }
}

void rotate(float *x, float *y, uint8_t theta) {
	float cos_ = fast_cos(255 - theta);
	float sin_ = fast_sin(255 - theta);

    float tempX = *x;
	float tempY = *y;

	*x = cos_ * tempX - sin_ * tempY;
	*y = sin_ * tempX + cos_ * tempY;
}
