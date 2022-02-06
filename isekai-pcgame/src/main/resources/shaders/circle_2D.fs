#version 330

in vec2 texture;
out vec4 color;

vec3 hsv2rgb(vec3 c) {
    if (c.y == 0) {
        return vec3(c.y);
    } else {
        float h = (c.x - int(c.x)) * 6.0;
        int i = int(h);
        float f = h - i;
        float p = c.z * (1.0f - c.y);
        float q = c.z * (1.0f - c.y * f);
        float t = c.z * (1.0f - (c.y * (1.0f - f)));

        switch (i) {
            case 0:
                return vec3(c.z, t, p);
            case 1:
                return vec3(q, c.z, p);
            case 2:
                return vec3(p, c.z, t);
            case 3:
                return vec3(p, q, c.z);
            case 4:
                return vec3(t, p, c.z);
            case 5:
                return vec3(c.z, p, q);
            default:
                return vec3(0);
        }
    }
}

void main() {
    vec2 v = texture - vec2(0.5);

    float dis = dot(v, v);

    if (dis > 0.25) {
        discard;
    } else {
        v = normalize(v);

        float dot = dot(v, normalize(vec2(1, 0)));
        float angle = acos(dot);

        if (v.y > 0) {
            angle = 6.283 - angle;
        }

        color = vec4(hsv2rgb(vec3(angle / 6.283, sqrt(dis) * 2, 1)), 1);
    }
}