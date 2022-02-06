#include "common.h"

uint8_t page = 0;
uint8_t yPos = 140;

void renderStats() {
    uint8_t i, f=page*15, g=0;

    gfx_SetTextFGColor(0x00);
    renderBackground(false);
    for(i=0; i<15;i++) {
        g=i+f;
        if(g==50) break;
        gfx_PrintStringXY("Level: ",0,i*15);
        gfx_SetTextXY(50,i*15);
        gfx_PrintUInt(g+1, 2);
        gfx_PrintStringXY("Moves: ", 80,i*15);
        if(Moves[g]!=0) {
            gfx_SetTextXY(140,i*15);
            gfx_PrintUInt(Moves[g], 5);
        }
        gfx_PrintStringXY("Pushs: ", 190, i*15);
        if(Pushs[g]!=0) {
            gfx_SetTextXY(250, i*15);
            gfx_PrintUInt(Pushs[g],5);
        }
    }
    print_string_centered("[<>]-move [Del]-back",230);
    gfx_SwapDraw();
}

void end() {
	renderBackground(true);
    print_string_centered("Thank you!", 120);
    gfx_SwapDraw();
    while(!os_GetCSC());
}

void renderBackground(bool title) {
    uint8_t a, yy,l;
    uint16_t xx;
    gfx_SetTextFGColor(0x00);

    gfx_FillScreen(0xAF);

    gfx_SetColor(0x03);
    gfx_FillCircle(160,240,170);

    gfx_SetColor(0x11); 
    gfx_FillCircle(160,240,140);

    gfx_SetColor(0x3F); 
    gfx_FillCircle(160,240,110);

    gfx_SetColor(0x06); 
    gfx_FillCircle(160,240,90);

    gfx_SetColor(0x32); 
    gfx_FillCircle(160,240,70);

    gfx_SetColor(0x28); 
    gfx_FillCircle(160,240,50);

    gfx_SetColor(0x0E);
    for(a=0; a<randInt(70,90); a++) {
        xx=randInt(0,320); 
        yy=randInt(0,100); 
        l=randInt(1,2);
        gfx_FillRectangle(xx,yy,l,l);
    }

    if(title) {
        gfx_ScaledTransparentSprite_NoClip(chicken_down,140,102,2,2);
        print_string_centered("THE CHICKEN ADVENTURE I", 50);
    }
}

void renderGameMenu() {
    gfx_SetTextFGColor(0x00);
    renderBackground(true);
    gfx_SetColor(0x00); 
    gfx_FillRectangle(95,yPos+2,8,2);
    print_string_centered("Continue", 140);
    gfx_PrintStringXY("<      >", 210,140);
    gfx_SetTextXY((gfx_lcdWidth/2)+gfx_GetStringWidth("Continue")-10,140);
    gfx_PrintUInt(curentLevel+1, 2);
    print_string_centered("New game",160);
    print_string_centered("Stats",180);
    print_string_centered("Pack", 200);
    print_string_centered("Back",220);

    gfx_PrintStringXY("PoulpoCorp (C) -- v2.0 - Pre release 3", 0, 230);
    gfx_SwapDraw();
}

void updateGameMenu() {
	if(key(key_down)) {
		if(yPos == 220) {
			yPos = 140;
		} else {
			yPos+=20;
		}
	} else if(key(key_up)) {
		if(yPos == 140) {
			yPos = 220;
		} else {
			yPos-=20;
		}
	} else if(key(key_2nd)) {
		if(yPos == 140) {
			gameState = GAME;
        	startGame();
		} else if(yPos == 160) {
    		newGame();
		} else if(yPos == 180) {
			gameState = STATS;
		} else if(yPos == 200) {

		} else if(yPos == 220) {
            gameState = MENU;
        }
	} else if(key(key_Del)) {
		gameState = MENU;
	} else if(keyPressed(key_left) && yPos == 140) {
		if(curentLevel != 0) {
			curentLevel--;
		}
	} else if(keyPressed(key_right) && yPos == 140) {
		if(curentLevel != maxLvl) {
			curentLevel++;
		}
	}
}

void updateStats() {
	if(key(key_left)) {
		if(page == 0) {
			page = 3;
		} else {
			page--;
		}
	} else if(key(key_right)) {
		if(page == 3) {
			page = 0;
		} else {
			page++;
		}
	} else if(key(key_Del)) {
		gameState = GAME_MENU;
	}
}

void renderMenu() {
    gfx_SetTextFGColor(0x00);
    renderBackground(true);
    gfx_SetColor(0x00); 
    gfx_FillRectangle(95,yPos+2,8,2);
    print_string_centered("Play", 140);
    print_string_centered("Editor",160);
    print_string_centered("Skin",180);
    print_string_centered("Achivements", 200);
    print_string_centered("Quit", 220);

    gfx_PrintStringXY("PoulpoCorp (C) -- v2.0 - Pre release 3", 0, 230);
    gfx_SwapDraw();
}

void updateMenu() {
    if(key(key_down)) {
        if(yPos == 220) {
            yPos = 140;
        } else {
            yPos+=20;
        }
    } else if(key(key_up)) {
        if(yPos == 140) {
            yPos = 220;
        } else {
            yPos-=20;
        }
    } else if(key(key_2nd)) {
        if(yPos == 140) {
            gameState = GAME_MENU; 
        } else if(yPos == 160) {
            gameState = EDITOR_MENU;
        } else if(yPos == 180) {
            //gameState = STATS;
        } else if(yPos == 200) {
            //gameState = MENU;
        } else if(yPos == 220) {
            gameState = EXIT;
        }
    } else if(key(key_Del)) {
        gameState = EXIT;
    }
}

void print_string_centered(const char *str, uint16_t y) {
    gfx_PrintStringXY(str, (gfx_lcdWidth-gfx_GetStringWidth(str)) / 2, y);
}