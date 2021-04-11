#include "loader.h"

#include <fileioc.h>
#include <string.h>
#include <debug.h>

uint8_t *get_pack_pointer(ti_var_t slot);
uint8_t read_string(uint8_t* data, char **out);
void decompress(uint8_t *data, uint8_t *out);
void init_level();

static const char search_string[] = {0xFE, 0xDC, 0xBA, 0x00};

pack_info_t packs[256];
uint8_t num_packs = 0;

uint8_t *get_pack_pointer(ti_var_t slot) {
    uint8_t *ptr = (uint8_t*) ti_GetDataPtr(slot) + 3;

    return ptr;
}

uint8_t read_string(uint8_t* data, char **out) {
	char *string = (char *) data;

	uint8_t length = strlen(string);

	*out = malloc(length);
	memcpy(*out, string, length);

	return length + 1;
}

void load_packs_info() {
	char *var_name;
	void *search_pos = NULL;

	num_packs = 0;

	ti_CloseAll();
	while((var_name = ti_Detect(&search_pos, search_string)) != NULL) {
		pack_info_t *pack = &packs[num_packs];

		ti_var_t slot = ti_Open(var_name, "r");
		uint8_t *data = get_pack_pointer(slot);

		strcpy(pack->app_var, var_name);
		data += read_string(data, &pack->name);
		data += read_string(data, &pack->author);
		data += read_string(data, &pack->version);

		pack->n_levels = *data;

		dbg_sprintf(dbgout, "New pack\n-name:%s\n-author:%s\n-version:%s\n-levels:%i\n", pack->name, pack->author, pack->version, pack->n_levels);

		num_packs++;
	}

	dbg_sprintf(dbgout, "%i pack(s) found\n", num_packs);

	ti_CloseAll();
}

void free_packs_info() {
	uint8_t i;

	for (i = 0; i < num_packs; i++) {
		pack_info_t *pack = &packs[i];

		free(pack->name);
		free(pack->author);
		free(pack->version);
	}
}

void load_level_data(pack_info_t pack, uint8_t level) {
    ti_var_t slot;

    ti_CloseAll();
    if ((slot = ti_Open(pack.app_var, "r"))) {
        uint8_t *data = get_pack_pointer(slot);

        data += strlen(pack.name) + 1;    // skip pack name
        data += strlen(pack.author) + 1;  // skip pack author
        data += strlen(pack.version) + 1; // skip pack version
        data += 1;                        // skip number of levels

        uint16_t *offsets = (uint16_t *) data;
        uint16_t level_data_offset = offsets[level];

        data += pack.n_levels * 2 + level_data_offset; // skip offsets and others level data

        game.player_x = *data;
        data++;
        game.player_y = *data;
        data++;
        game.level_width = *data;
        data++;
        game.level_height = *data;
        data++;

        // dbg_sprintf(dbgout, "Loading level: x: %i, y: %i, w: %i, h: %i\n", game.player_x, game.player_y, game.level_width, game.level_height);

        uint16_t size = game.level_width * game.level_height;

        gfx_tilemap_t *tilemap = &game.tilemap;
        tilemap->map = malloc(game.level_width * game.level_height);

        if (tilemap->map == NULL) {
            exit(0);
        }

        bool compressed = (bool) *data;
        data++;
        if (compressed) {
            decompress(data, tilemap->map);
        } else {
            memcpy(tilemap->map, data, size);
        }

        tilemap->width = game.level_width;
        tilemap->height = game.level_height;
    } else {
        exit(0);
    }
}

void decompress(uint8_t *in, uint8_t *out) {
    uint8_t i = 0;
    uint8_t count = 0;

    while (true) {
        i = *in;

        if (i == END) {
            break;
        }

        in++;

        if (count == 0) {
            count = i + 1;
        } else {
            memset(out, i, count);
            out += count;
            count = 0;
        }
    }
}

void save() {

}

void load_save() {

}
