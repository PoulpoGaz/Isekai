#ifndef __screen_h__
#define __screen_h__

#include <stdint.h>

#define DRAW 0
#define EXIT 1

typedef struct screen_t {
	void (*draw)(struct screen_t *);

	/*
		Return draw or exit or the next screen_t index
	*/
	uint8_t (*update)(struct screen_t *);
	void (*show)(struct screen_t *);
	void (*hide)(struct screen_t *);
} screen_t;

void add_screen(screen_t *screen);
void run();


void draw(screen_t *self);
uint8_t update(screen_t *self);
void show(screen_t *self);
void hide(screen_t *self);

#endif