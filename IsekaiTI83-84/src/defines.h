#ifndef __define_h__
#define __define_h__

#include <graphx.h>

typedef struct pack_t {
	char app_var[8];
	char *name;
	char *author;
	char *version;
	uint8_t n_levels;
} pack_t;

enum state_t {
	MAIN_MENU,
	PACK_LIST,
	IN_GAME,
	STATS,
	EXIT
};

extern enum state_t state;

#endif
