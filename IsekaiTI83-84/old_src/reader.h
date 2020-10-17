#ifndef __reader__
#define __reader__

#include "defines.h"

#define loadSize(file) ti_GetSize(file)

void findPack();
void readPack();
void savePack();
uint16_t getPackSize();

void saveProgress();
void loadProgress();

#endif