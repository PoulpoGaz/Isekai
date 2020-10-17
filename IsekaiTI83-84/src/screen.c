#include "screen.h"
#include <stdlib.h>
#include <debug.h>

// "private"
void swap(screen_t **current, screen_t *new);

uint8_t length = 0;
screen_t **screens; // Array of pointer of screen_t

void add_screen(screen_t *screen) {
	if (length == 0) {
		screens = (screen_t **) malloc(sizeof(screen_t *));
	} else {
		screens = (screen_t **) realloc(screens, sizeof(screen_t *) * length);
	}

	screens[length] = screen;

	length++;
}

void run() {
	if (length > 0) {
		uint8_t next_state = 0;
		screen_t *current = NULL;

		current = screens[0];

		show(current);
		draw(current);
		while (next_state != EXIT) {
			next_state = update(current);

			switch (next_state) {
				case DRAW:
					draw(current);
					break;
				case EXIT:
					continue;
				default:
					if (next_state - 2 >= length) {
						dbg_sprintf(dbgout, "[WARNING] Attempting to access at an unknown state (%i). The maximum is %i", next_state - 2, length);
						break;
					}
					swap(&current, screens[next_state - 2]);
					break;
			}
		}


		free(screens);
	}
}

void swap(screen_t **current, screen_t *new) {
	dbg_sprintf(dbgout, "Swapping (%p to %p)\n", &current, new);

	hide(*current);

	*current = new;

	show(*current);
	draw(*current);
}


void draw(screen_t *self) {
	if (self->draw != NULL) {
		self->draw(self);
	}
}

uint8_t update(screen_t *self) {
	if (self->update != NULL) {
		return self->update(self);
	} else {
		dbg_sprintf(dbgout, "Update function pointer is null. Exiting...");

		return EXIT;
	}
}

void show(screen_t *self) {
	dbg_sprintf(dbgout, "Showing (%p)\n", self);

	if (self->show != NULL) {
		self->show(self);
	}
}

void hide(screen_t *self) {
	dbg_sprintf(dbgout, "Hiding (%p)\n", self);

	if (self->hide != NULL) {
		self->hide(self);
	} 
}