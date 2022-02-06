#ifndef __move_handler_h__
#define __move_handler_h__

#include "defines.h"

void move(int8_t dir_x, int8_t dir_y);
bool can_undo();
void undo();
void reset_undo();

#endif
