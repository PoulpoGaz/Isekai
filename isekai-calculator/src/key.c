#include "key.h"

#include <keypadc.h>
#include <debug.h>

#define LAST 0
#define CURRENT 1

const uint24_t mappings[NUM_KEYS] = {
	kb_Key2nd,
	kb_KeyDel,

	kb_KeyEnter,
	kb_KeyClear,
	kb_KeyMode,
	kb_KeyStat,

	kb_KeyDown,
	kb_KeyLeft,
	kb_KeyRight,
	kb_KeyUp,

	kb_Key2,
	kb_Key4,
	kb_Key5,
	kb_Key6,
	kb_Key8,

	kb_KeyGraph
};

bool keys[NUM_KEYS][2] = { {false} };
uint8_t ticks[NUM_KEYS] = {0};

void scan() {
	uint8_t i;

	kb_Scan();

	for (i = 0; i < NUM_KEYS; i++) {
		keys[i][LAST] = keys[i][CURRENT];

		keys[i][CURRENT] = kb_IsDown(mappings[i]);

		if (keys[i][CURRENT]) {
			ticks[i]++;
		} else {
			ticks[i] = 0;
		}
	}
}

bool key_pressed(uint8_t key) {
	return keys[key][LAST] && keys[key][CURRENT];
}

bool key_released(uint8_t key) {
	return keys[key][LAST] && !keys[key][CURRENT];
}

bool key_pressed_long(uint8_t key, uint8_t nTick) {
	return key_pressed(key) && ticks[key] % nTick == 0;
}
