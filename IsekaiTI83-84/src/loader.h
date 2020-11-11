#ifndef __loader_h__
#define __loader_h__

#include <stdint.h>

#include "defines.h"

extern pack_t packs[256];
extern uint8_t num_packs;

void load_packs();

void loadGFX(pack_t pack);

void free_packs();

void save();
void load_save();

#endif