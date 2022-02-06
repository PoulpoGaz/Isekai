#version 330

layout (location = 0) in vec3 vertex;
layout (location = 1) in vec2 texture;
layout (location = 2) in vec3 normal;

out vec3 vertex_pos;
out vec2 texture_pos;
out vec3 vertex_normal;
out mat4 model_mat;

uniform mat4 projection;
uniform mat4 model;
uniform mat4 view;

void main() {
    mat4 viewModel = view * model;

    vec4 mvPos = (viewModel * vec4(vertex, 1));

    vertex_pos = mvPos.xyz;
    texture_pos = texture;
    vertex_normal = normalize(viewModel * vec4(normal, 0.0)).xyz;

    model_mat = viewModel; // for normal

    gl_Position = projection * mvPos;
}