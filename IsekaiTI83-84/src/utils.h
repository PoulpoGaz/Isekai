#ifndef __utils_h__
#define __utils_h__

#include <stdint.h>
#include <stdbool.h>
#include <graphx.h>

#define BLACK             0
#define WHITE             1

#define TRANSPARENT_COLOR 2
#define MAGENTA           2

// For utils#draw_menu_background(bool)

#define BACKGROUND_1      3
#define BACKGROUND_2      4
#define BACKGROUND_3      5
#define BACKGROUND_4      6
#define BACKGROUND_5      7
#define BACKGROUND_6      8
#define BACKGROUND_7      9

#define to_rad(x) (x) * M_PI / 180.0
#define from_deg_to_255_angular(x) (uint8_t) ((x) / 360.0 * 255) 

void print_string_centered_between(const char *str, uint8_t y, uint24_t min_x, uint24_t width);
void print_string_centered(const char *str, uint8_t y);

uint24_t get_x_centered_between(const char *str, uint24_t min_x, uint24_t width);
#define get_x_centered(str) get_x_centered_between(str, 0, LCD_WIDTH)

void draw_menu_background(bool draw_title, bool draw_chicken, uint8_t offset);

void rotate(float *x, float *y, float theta);
void rotate_pre_compute(float *x, float *y, float cos_val, float sin_val);

#endif