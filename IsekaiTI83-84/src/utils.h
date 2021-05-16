#ifndef __utils_h__
#define __utils_h__

#include <stdint.h>
#include <stdbool.h>
#include <graphx.h>

#define BLACK              0
#define WHITE              1

#define TRANSPARENT_COLOR  2
#define MAGENTA            2

// For utils#draw_menu_background(bool)

#define BACKGROUND_1       3
#define BACKGROUND_2       4
#define BACKGROUND_3       5
#define BACKGROUND_4       6
#define BACKGROUND_5       7
#define BACKGROUND_6       8
#define BACKGROUND_7       9

#define RED               10

#define GAME_BACKGROUND   11

#define max(a, b) a > b ? a : b
#define min(a, b) a > b ? b : a

void print_string_centered_between(const char *str, uint8_t y, uint24_t min_x, uint24_t width);
void print_string_centered(const char *str, uint8_t y);

uint24_t get_x_centered_between(const char *str, uint24_t min_x, uint24_t width);
#define get_x_centered(str) get_x_centered_between(str, 0, LCD_WIDTH)

void generate_stars();
void draw_menu_background(bool draw_title, bool draw_chicken);

void rotate(float *x, float *y, uint8_t theta);

#define draw_triangle(x1, y1, x2, y2, x3, y3) gfx_Line(x1, y1, x2, y2); \
                                              gfx_Line(x2, y2, x3, y3); \
                                              gfx_Line(x3, y3, x1, y1)

#endif
