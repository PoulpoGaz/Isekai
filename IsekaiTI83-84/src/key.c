#include "key.h"

#include <keypadc.h>

#define LAST 0
#define CURRENT 1

const uint24_t mappings[8] = {
	kb_Key2nd,
	kb_KeyDel,

	kb_KeyEnter,
	kb_KeyClear,

	kb_KeyDown,
	kb_KeyLeft,
	kb_KeyRight,
	kb_KeyUp
};

bool keys[8][2] = { {false} };

void scan() {
	uint8_t i;

	kb_Scan();

	for (i = 0; i < 8; i++) {
		keys[i][LAST] = keys[i][CURRENT];

		keys[i][CURRENT] = kb_IsDown(mappings[i]);
	}
}

bool key_pressed(uint8_t key) {
	return keys[key][LAST] && keys[key][CURRENT];
}

bool key_released(uint8_t key) {
	return keys[key][LAST] && !keys[key][CURRENT];
}