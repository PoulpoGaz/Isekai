#include <stdlib.h>
#include <graphx.h>
#include <tice.h>
#include <math.h>
#include <debug.h>

#include "utils.h"
#include "gfx/gfx.h"

#define DEG       20
#define THETA     to_rad(DEG)
#define COS_THETA cos(THETA)
#define SIN_THETA sin(THETA)

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

void draw_menu_background(bool draw_title, bool draw_chicken, uint8_t offset) {
	int8_t i;
	uint8_t max;

    gfx_SetTextFGColor(WHITE);

    gfx_FillScreen(BACKGROUND_1);

    gfx_SetColor(BACKGROUND_2);
    gfx_FillCircle(160, 240, 170);

    gfx_SetColor(BACKGROUND_3);
    gfx_FillCircle(160, 240, 140);

    gfx_SetColor(BACKGROUND_4);
    gfx_FillCircle(160, 240, 110);

    gfx_SetColor(BACKGROUND_5);
    gfx_FillCircle(160, 240, 90);

    gfx_SetColor(BACKGROUND_6);
    gfx_FillCircle(160, 240, 70);

    gfx_SetColor(BACKGROUND_7);
    gfx_FillCircle(160, 240, 50);

    gfx_SetColor(WHITE);
    for(i = 0, max = randInt(70, 90); i < max; i++) {
        uint8_t l = randInt(1, 2);

		uint16_t x = randInt(0, 320);
        uint8_t y = randInt(0, 100);

        gfx_FillRectangle(x, y, l, l);
    }

    if (draw_chicken) {
    	float x = -122;
    	float y = 0;
    	gfx_sprite_t *sprite = NULL;

    	uint16_t y_draw;
    	uint16_t x_draw;
    	uint8_t angular_255;

    	offset = offset % DEG;

    	if (offset > 0) {
    		rotate(&x, &y, -to_rad(offset));
    	}

    	sprite = gfx_MallocSprite(32, 32);

    	for (i = -1; i <= 180 / DEG; i++) {
    		y_draw = (uint16_t) y + 224;

    		if (y_draw < 240) {
    	  		x_draw = (uint16_t) x + 144;

    	  		// 63 (perform 90° clockwise rotation)
    	  		angular_255 = 63 + from_deg_to_255_angular(DEG * (i + 1) - offset);

    			gfx_RotateScaleSprite(chicken_left_walk_tiles[offset / 5], sprite, angular_255, 128);

				gfx_TransparentSprite(sprite, x_draw, y_draw);
			}

    		rotate_pre_compute(&x, &y, COS_THETA, SIN_THETA);
    	}

    	free(sprite);
    }

    if(draw_title) {
        print_string_centered("ISEKAI", 50);
    }
}

void rotate(float *x, float *y, float theta) {
	float cos_ = cos(theta);
	float sin_ = sin(theta);

	rotate_pre_compute(x, y, cos_, sin_);
}

void rotate_pre_compute(float *x, float *y, float cos_val, float sin_val) {
	float tempX = *x;
	float tempY = *y;

	*x = cos_val * tempX - sin_val * tempY;
	*y = sin_val * tempX + cos_val * tempY;
}
