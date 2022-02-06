#ifndef __loader_h__
#define __loader_h__

#include <stdint.h>

#include "defines.h"

#define END 0xFF

extern pack_info_t packs[256];
extern uint8_t num_packs;

void load_packs_info();
void free_packs_info();

void load_level_data(pack_info_t pack, uint16_t level);

void save();
void load_save();

#endif
