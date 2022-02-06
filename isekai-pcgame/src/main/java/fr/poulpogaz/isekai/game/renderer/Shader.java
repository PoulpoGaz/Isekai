package fr.poulpogaz.isekai.game.renderer;

import fr.poulpogaz.isekai.game.renderer.utils.Disposable;
import fr.poulpogaz.isekai.game.renderer.utils.Resource;
import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader implements Disposable {

    public static Shader newShader(String path) throws IOException {
        return newShader("shaders/%s.vs".formatted(path), "shaders/%s.fs".formatted(path));
    }

    public static Shader newShader(String vertexPath, String fragmentPath) throws IOException {
        String vertex = Resource.readString(vertexPath);
        String frag = Resource.readString(fragmentPath);

        try {
            return new Shader(vertex, frag);
        } catch (IOException e) {

            throw new IOException(e.getMessage() + "\nIn: " + vertexPath + "  |  " + fragmentPath);
        }
    }

    protected final HashMap<String, Integer> uniforms;

    protected int program;
    protected int vertex;
    protected int fragment;

    public Shader(String vertexData, String fragmentData) throws IOException {
        createShader(vertexData, fragmentData);

        uniforms = new HashMap<>();
    }

    protected void createShader(String vertexData, String fragmentData) throws IOException {
        vertex = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertex, vertexData);
        glCompileShader(vertex);

        if (glGetShaderi(vertex, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new IOException("Vertex shader compile error\n" + glGetShaderInfoLog(vertex));
        }

        fragment = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragment, fragmentData);
        glCompileShader(fragment);

        if (glGetShaderi(fragment, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new IOException("Fragment shader compile error\n" + glGetShaderInfoLog(fragment));
        }

        program = glCreateProgram();
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
    }

    public void bindAttribute(int location, String name) {
        glBindAttribLocation(program, location, name);
    }

    public void link() throws IOException {
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new IOException("Shader program link error\n" + glGetProgramInfoLog(program));
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
            throw new IOException("Shader program validate error\n" + glGetProgramInfoLog(program));
        }
    }

    public void createUniform(String name) throws IOException {
        int location = glGetUniformLocation(program, name);

        if (location < 0) {
            throw new IOException("could not find uniform: " + name);
        }

        uniforms.put(name, location);
    }

    // Point light createUniform methods
    public void createPointLightsUniform(String name, int length) throws IOException {
        for (int i = 0; i < length; i++) {
            createPointLightUniform(name + "[" + i + "]");
        }
    }

    public void createPointLightUniform(String name) throws IOException {
        createUniform(name + ".color");
        createUniform(name + ".position");
        createUniform(name + ".intensity");
        createUniform(name + ".att.constant");
        createUniform(name + ".att.linear");
        createUniform(name + ".att.exponent");
    }

    // Sun light createUniform methods
    public void createSunLightUniform(String name) throws IOException {
        createUniform(name + ".color");
        createUniform(name + ".direction");
        createUniform(name + ".intensity");
    }

    // Spot light createUniform methods
    public void createSpotLightsUniform(String name, int length) throws IOException {
        for (int i = 0; i < length; i++) {
            createSpotLightUniform(name + "[" + i + "]");
        }
    }

    public void createSpotLightUniform(String name) throws IOException {
        createUniform(name + ".point.color");
        createUniform(name + ".point.position");
        createUniform(name + ".point.intensity");
        createUniform(name + ".point.att.constant");
        createUniform(name + ".point.att.linear");
        createUniform(name + ".point.att.exponent");

        createUniform(name + ".direction");
        createUniform(name + ".cutoff");
    }

    public void createMaterialUniform(String name) throws IOException {
        createUniform(name + ".ambient");
        createUniform(name + ".diffuse");
        createUniform(name + ".specular");
        createUniform(name + ".has_texture");
        createUniform(name + ".has_normal_map");
        createUniform(name + ".reflectance");
    }

    public void setUniform(String name, Vector2fc vec) {
        glUniform2f(uniforms.get(name), vec.x(), vec.y());
    }

    public void setUniform(String name, Vector3fc vec) {
        glUniform3f(uniforms.get(name), vec.x(), vec.y(), vec.z());
    }

    public void setUniform(String name, Vector4fc vec) {
        glUniform4f(uniforms.get(name), vec.x(), vec.y(), vec.z(), vec.w());
    }

    public void setUniform(String name, Matrix4fc mat) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniforms.get(name), false,
                               mat.get(stack.mallocFloat(16)));
        }
    }

    public void setUniformi(String name, int value) {
        glUniform1i(uniforms.get(name), value);
    }

    public void setUniformf(String name, float value) {
        glUniform1f(uniforms.get(name), value);
    }

    public void bind() {
        glUseProgram(program);
    }

    public static void unbind() {
        glUseProgram(0);
    }

    public void dispose() {
        glDetachShader(program, vertex);
        glDetachShader(program, fragment);
        glDeleteShader(vertex);
        glDeleteShader(fragment);
        glDeleteProgram(program);
    }
}