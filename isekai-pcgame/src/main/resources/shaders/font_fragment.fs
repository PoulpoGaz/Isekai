#version 330

uniform sampler2D sampler;

in vec2 texture_coords;
in vec4 out_color;

out vec4 frag_color;

void main() {
    frag_color = out_color * texture(sampler, texture_coords);
}