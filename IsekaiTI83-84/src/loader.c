#include "loader.h"

#include <fileioc.h>
#include <string.h>
#include <debug.h>

static const char search_string[] = {'I', 'S', 'K', 'V', '0'};

pack_t packs[256];
uint8_t num_packs = 0;

uint8_t *get_pack_pointer(ti_var_t slot) {
    uint8_t *ptr = (uint8_t*) ti_GetDataPtr(slot) + 5;

    return ptr;
}

char* read_string(uint8_t** data, char **out) {
	char *string = (char *) *data;
	
	uint8_t length = strlen(string);

	*out = malloc(length);
	memcpy(*out, string, length);
	*data += length + 1;

	return *out;
}

void load_packs() {
	char *var_name;
	uint8_t *search_pos = NULL;

	num_packs = 0;

	ti_CloseAll();
	while((var_name = ti_Detect(&search_pos, search_string)) != NULL) {
		pack_t *pack = &packs[num_packs];

		ti_var_t slot = ti_Open(var_name, "r"); 
		uint8_t *data = get_pack_pointer(slot);

		strcpy(pack->app_var, var_name);
		read_string(&data, &pack->name);
		read_string(&data, &pack->author);
		read_string(&data, &pack->version);

		pack->n_levels = *((uint8_t *) data);
		pack++;

		dbg_sprintf(dbgout, "New pack\n-name:%s\n-author:%s\n-version:%s\n-levels:%i\n", pack->name, pack->author, pack->version, pack->n_levels);

		num_packs++;
	}

	dbg_sprintf(dbgout, "%i pack(s) found\n", num_packs);

	ti_CloseAll();
}

void free_packs() {
	uint8_t i;

	for (i = 0; i < num_packs; i++) {
		pack_t *pack = &packs[i];
	
		free(packs->name);
		free(packs->author);
		free(packs->version);
	}
}

void save() {

}

void load_save() {

}