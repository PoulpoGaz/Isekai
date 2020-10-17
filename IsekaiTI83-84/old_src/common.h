#ifndef __common__
#define __common__

#include "defines.h"

void renderMenu();
void end();
void renderBackground(bool title);
void renderStats();
void print_string_centered(const char *str, uint16_t y);
void renderGameMenu();

void updateStats();
void updateMenu();
void updateGameMenu();

extern uint8_t yPos;
extern uint8_t page;

#endif