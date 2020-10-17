#include "editor.h"

uint8_t menuPos = 0;
uint8_t numPack = 0;

void renderEditor() {
	gfx_FillScreen(0x00);
	gfx_SetColor(0x09);
	gfx_VertLine_NoClip(0, 220, 320);
	
}

void updateEditor() {
	if(key(key_Del)) {
		savePack();
		gameState = EDITOR_MENU;
	}
}

void renderMenuEditor() {
	char *varName;
	uint8_t i = 0;
	pack_t p;
	uint8_t *searchPos = NULL;
	ti_var_t slot;

	gfx_FillScreen(0x00);
	gfx_PrintStringXY("Name", 20, 5);
	gfx_PrintStringXY("Size", 120, 5);
	gfx_PrintStringXY("Author", 150, 5);

	while((varName = ti_Detect(&searchPos, &search_string))) {
        ti_CloseAll();
        if(slot = ti_Open((char*)varName, "r")){
            ti_Read(&p, 26, 1, slot);
            ti_SetArchiveStatus(true, slot);
        }
        ti_CloseAll();

		if(p.locked) {
			gfx_SetTextFGColor(0x14);
		} else {
			gfx_SetTextFGColor(0x00);
		}

		gfx_PrintStringXY(p.description, 20, 15 + i*16);
		gfx_SetTextXY(120, 15 + i * 16);
		gfx_PrintUInt(p.num_levels ,2);
		gfx_PrintStringXY(p.author, 150, 15 + i * 16);

		if(i == menuPos) {
			gfx_TransparentSprite_NoClip(chicken_right, 2, 5 + i*16);
		}

		i++;
    }

    numPack = i;

    if(i == 0) {
    	gfx_PrintStringXY("No pack found... Press 2nd to create a pack", 5, 5);
    }

	gfx_SwapDraw();
}

void updateMenuEditor() {
	if(key(key_Del)) {
		gameState = GAME_MENU;
	} else if(key(key_2nd)) {
		if(numPack != 0) {
			gameState = EDITOR;
		} else {
			//Create pack
		}
	}
}