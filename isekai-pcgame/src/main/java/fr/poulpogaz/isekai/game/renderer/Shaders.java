package fr.poulpogaz.isekai.game.renderer;

import java.io.IOException;

public class Shaders {

    public static Shader COLOR_2D;
    public static Shader INSTANCED_COLOR_2D;
    public static Shader TEXTURE_2D;
    public static Shader COLOR_TEXTURE_2D;
    public static Shader CIRCLE_2D;

    public static void createShaders() throws IOException {
        COLOR_2D = Shader.newShader("color_2D");
        COLOR_2D.bindAttribute(0, "vertex");
        COLOR_2D.bindAttribute(1, "color_");
        COLOR_2D.link();
        COLOR_2D.createUniform("projection");
        COLOR_2D.createUniform("modelView");


        INSTANCED_COLOR_2D = Shader.newShader("instanced_color_2D");
        INSTANCED_COLOR_2D.bindAttribute(0, "vertex");
        INSTANCED_COLOR_2D.bindAttribute(1, "color_");
        INSTANCED_COLOR_2D.bindAttribute(2, "modelView");
        INSTANCED_COLOR_2D.link();
        INSTANCED_COLOR_2D.createUniform("projection");


        TEXTURE_2D = Shader.newShader("texture_2D");
        TEXTURE_2D.bindAttribute(0, "vertex");
        TEXTURE_2D.bindAttribute(1, "texture_");
        TEXTURE_2D.link();
        TEXTURE_2D.createUniform("projection");
        TEXTURE_2D.createUniform("modelView");


        COLOR_TEXTURE_2D = Shader.newShader("color_texture_2D");
        COLOR_TEXTURE_2D.bindAttribute(0, "vertex");
        COLOR_TEXTURE_2D.bindAttribute(1, "color_");
        COLOR_TEXTURE_2D.bindAttribute(2, "texture_");
        COLOR_TEXTURE_2D.link();
        COLOR_TEXTURE_2D.createUniform("projection");
        COLOR_TEXTURE_2D.createUniform("modelView");


        CIRCLE_2D = Shader.newShader("circle_2D");
        CIRCLE_2D.bindAttribute(0, "vertex");
        CIRCLE_2D.bindAttribute(1, "texture_");
        CIRCLE_2D.link();
        CIRCLE_2D.createUniform("projection");
        CIRCLE_2D.createUniform("modelView");
    }

    public static void dispose() {
        COLOR_2D.dispose();
        INSTANCED_COLOR_2D.dispose();
        TEXTURE_2D.dispose();
        COLOR_TEXTURE_2D.dispose();
        CIRCLE_2D.dispose();
    }
}