#include "reader.h"

void findPack() {

}

void readPack() {
	ti_var_t file;
	uint8_t i, j, k;
	char str;

	ti_CloseAll();
    file = ti_Open("CHKNPCK", "r");
    if(file) {
        ti_Read(&pack, 15000, 1, file);
    }
    ti_CloseAll();
}

void savePack()  {
	ti_var_t file;
	uint8_t i, j, k;

    ti_CloseAll();
    file = ti_Open("CHKNPCK", "w");
    if(file) {
        ti_PutC(search_string, file);
        ti_Write(&pack, getPackSize(), 1, file);
    }

    ti_SetArchiveStatus(true, file);
    ti_CloseAll();
}

uint16_t getPackSize() {
    uint16_t size = sizeof(pack);
    uint16_t i;

    for(i = 0; i < pack.num_levels; i++) {
        size += pack.levels[i].width * pack.levels[i].height;
    }

    return size;
}

void loadProgress() {
    ti_var_t file;

    ti_CloseAll();
    file = ti_Open("CHKNSV", "r");
    if(file) {
        ti_Read(&maxLvl, sizeof(uint8_t), sizeof(maxLvl)/sizeof(uint8_t), file);
        ti_Read(&Moves, sizeof(uint24_t), sizeof(Moves)/sizeof(uint24_t), file);
        ti_Read(&Pushs, sizeof(uint24_t), sizeof(Moves)/sizeof(uint24_t), file);
    }
    ti_CloseAll();
}

void saveProgress() {
    ti_var_t file;

    ti_CloseAll();
    file = ti_Open("CHKNSV", "w");
    if(file){
        ti_Write(&maxLvl, sizeof(uint8_t), sizeof(maxLvl)/sizeof(uint8_t), file);
        ti_Write(&Moves, sizeof(uint24_t), sizeof(Moves)/sizeof(uint24_t), file);
        ti_Write(&Pushs, sizeof(uint24_t), sizeof(Moves)/sizeof(uint24_t), file);
    }
    ti_SetArchiveStatus(true, file);
    ti_CloseAll();
}