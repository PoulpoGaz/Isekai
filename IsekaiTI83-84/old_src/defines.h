#ifndef __defines__
#define __defines__

#undef NDEBUG

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>
#include <tice.h>

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <graphx.h>
#include <fileioc.h>
#include <keypadc.h>
#include <debug.h>

#include "gfx/sprites_gfx.h"

#include "keyHandler.h"
#include "common.h"
#include "reader.h"
#include "main.h"
#include "editor.h"

extern char search_string;

enum state {
	MENU,
	GAME_MENU,
	EDITOR_MENU,
	EDITOR,
	STATS,
	GAME,
	EXIT
};

typedef struct {
	uint8_t x;
	uint8_t y;
	gfx_sprite_t *sprite;
	uint24_t pushs;
	uint24_t moves;
} chicken_t;

typedef struct {
	gfx_sprite_t *sprite;
	uint8_t value;
} case_t;

typedef struct {
	uint8_t **level;
	uint8_t width;
	uint8_t height;
} level_t;

typedef struct {
	char *author;
	char *description;
	uint8_t num_levels;
	bool locked;
	uint8_t password[8];
	level_t *levels;
} pack_t;

extern pack_t pack;

extern chicken_t chicken;
extern uint24_t Moves[50];
extern uint24_t Pushs[50];

extern enum state gameState;

extern uint8_t curentLevel;
extern uint8_t nbObj;
extern uint8_t nbCaisseOk;
extern uint8_t maxLvl;

extern case_t **level;

#endif