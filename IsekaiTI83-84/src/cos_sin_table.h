#ifndef __cos_sin_table_h__
#define __cos_sin_table_h__

extern const float cosinus[256];
extern const float sinuss[256];

#define fast_sin(x) sinuss[x]
#define fast_cos(x) cosinus[x]

#endif
