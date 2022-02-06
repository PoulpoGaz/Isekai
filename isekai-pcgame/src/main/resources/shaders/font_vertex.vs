#version 330

layout (location = 0) in vec2 vertex;
layout (location = 1) in vec2 translation; // x, y in window coordinates
layout (location = 2) in vec3 glyph_info;  // x, y, width int pixel. eg: 50, 50, 20
layout (location = 3) in vec4 color;

uniform mat4 projection;
uniform int texture_size;  // size of the texture

uniform int font_height;
uniform float height_ratio; // height / font_height
uniform float height; // desired height

out vec2 texture_coords;
out vec4 out_color;

void main() {
    // compute texture coordinates, values between 0 and texture_size
    texture_coords = vec2(glyph_info.x + vertex.x * glyph_info.z, glyph_info.y + vertex.y * font_height);
    // map texture coordinates between 0 and 1
    texture_coords /= texture_size;

    out_color = color;

    vec2 pos = translation;
    pos.x += vertex.x * glyph_info.z * height_ratio;
    pos.y += vertex.y * height;

    gl_Position = projection * vec4(pos, 1, 1);
}