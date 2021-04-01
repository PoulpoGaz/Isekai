#ifndef __key_h__
#define __key_h__

#include <tice.h>

#define key_2nd    0
#define key_Del    1

#define key_Enter  2
#define key_Clear  3

#define key_Down   4
#define key_Left   5
#define key_Right  6
#define key_Up     7

void scan();

bool key_pressed(uint8_t key);
bool key_released(uint8_t key);

#endif
