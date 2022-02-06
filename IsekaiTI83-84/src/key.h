#ifndef __key_h__
#define __key_h__

#include <tice.h>

#define key_2nd    0
#define key_Del    1

#define key_Enter  2
#define key_Clear  3
#define key_Mode   4
#define key_Stat   5

#define key_Down   6
#define key_Left   7
#define key_Right  8
#define key_Up     9

#define key_2     10
#define key_4     11
#define key_5     12
#define key_6     13
#define key_8     14

#define key_Graph 15

#define NUM_KEYS  16


void scan();

bool key_pressed(uint8_t key);
bool key_released(uint8_t key);
bool key_pressed_long(uint8_t key, uint8_t nTick);

#endif
