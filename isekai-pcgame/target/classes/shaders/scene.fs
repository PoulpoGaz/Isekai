#version 330

// Phong Shading
// https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter10/chapter10.html

const int MAX_POINT_LIGHTS = 5;
const int MAX_SPOT_LIGHTS = 5;

// input variables
in vec3 vertex_pos;
in vec2 texture_pos;
in vec3 vertex_normal;
in mat4 model_mat;

// structures
struct Attenuation {
    float constant;
    float linear;
    float exponent;
};

// lights
struct PointLight {
    vec3 color;
    vec3 position;
    float intensity;
    Attenuation att;
};

struct SunLight {
    vec3 color;
    vec3 direction;
    float intensity;
};

struct SpotLight {
    PointLight point;
    vec3 direction;
    float cutoff;
};

struct Material {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int has_texture;
    int has_normal_map;
    float reflectance;
};

// uniforms
uniform sampler2D sampler;
uniform sampler2D normal_sampler;

uniform vec3 ambient_light;
uniform float specular_power;
uniform Material material;

uniform PointLight points[MAX_POINT_LIGHTS];
uniform SpotLight spots[MAX_SPOT_LIGHTS];
uniform SunLight sun;

// out
out vec4 frag_color;

// global variables
vec4 ambient;
vec4 diffuse;
vec4 specular;
vec3 camera_dir;
vec3 normal;

vec4 calculate_light_color(float intensity, vec4 color, vec3 to_light_direction) {
    // Diffuse
    float diffuse_factor = max(dot(normal, to_light_direction), 0);
    vec4 diffuse_color = diffuse * color * diffuse_factor * intensity;

    // Specular
    vec3 from_light_direction = -to_light_direction;
    vec3 reflected_light = normalize(reflect(from_light_direction, normal));
    float specular_factor = max(dot(camera_dir, reflected_light), 0);
    specular_factor = pow(specular_factor, specular_power);
    vec4 specular_color = specular * color * material.reflectance * specular_factor * intensity;

    return diffuse_color + specular_color;
}

vec4 calculate_point_light(PointLight light) {
    vec3 light_direction = light.position - vertex_pos;

    vec4 color = calculate_light_color(light.intensity, vec4(light.color, 1), normalize(light_direction));

    // Attenutation
    Attenuation att = light.att;

    float distance = length(light_direction);
    float attenuationInv = att.constant + att.linear * distance + att.exponent * distance * distance;

    return color / attenuationInv;
}

vec4 calculate_sun_light(SunLight light) {
    return calculate_light_color(light.intensity, vec4(light.color, 1), normalize(light.direction));
}

vec4 calculate_spot_light(SpotLight light) {
    PointLight point = light.point;

    vec3 from_light_direction = normalize(vertex_pos - point.position);
    float scalar = dot(from_light_direction, normalize(light.direction));

    if (scalar >= light.cutoff) {
        vec4 color = calculate_point_light(point);

        float v = 1 - (1 - scalar) / (1 - light.cutoff);

        return v * color;
    }

    return vec4(0, 0, 0, 0);
}

vec3 calculate_normal() {
    vec3 normal = vertex_normal;

    if (material.has_normal_map == 1) {
        normal = texture(normal_sampler, texture_pos).rgb;
        normal = normalize(normal * 2 - 1);
        normal = normalize(model_mat * vec4(normal, 0.0)).xyz;
    }

    return normal;
}

void main() {
    if (material.has_texture == 1) {
        ambient = texture2D(sampler, texture_pos);
        diffuse = ambient;
        specular = ambient;
    } else {
        ambient = material.ambient;
        diffuse = material.diffuse;
        specular = material.specular;
    }

    normal = calculate_normal();
    camera_dir = normalize(-vertex_pos);

    vec4 color = vec4(0, 0, 0, 0);
    if (sun.intensity > 0) {
        color = calculate_sun_light(sun);
    }

    for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
        PointLight point = points[i];

        if (point.intensity > 0) {
            color += calculate_point_light(point);
        }
    }

    for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
        SpotLight spot = spots[i];

        if (spot.point.intensity > 0) {
            color += calculate_spot_light(spot);
        }
    }

    frag_color = ambient * vec4(ambient_light, 1) + color;
}