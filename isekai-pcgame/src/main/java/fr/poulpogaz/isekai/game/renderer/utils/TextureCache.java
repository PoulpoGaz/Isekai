package fr.poulpogaz.isekai.game.renderer.utils;

import fr.poulpogaz.isekai.game.renderer.Texture;
import org.lwjgl.glfw.GLFWImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class TextureCache {

    private static final HashMap<String, Texture> CACHE = new HashMap<>();

    public static void put(String path, Texture texture) {
        CACHE.put(path, texture);
    }

    public static Texture get(String path) {
        return CACHE.get(path);
    }

    public static Texture getOrCreate(String path) {
        Texture texture = CACHE.get(path);

        if (texture != null) {
            return texture;
        }

        if (!Files.exists(Path.of(path))) {
            return null;
        }

        try {
            GLFWImage image = ImageLoader.loadImage(path, false);

            texture = new Texture(image);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        CACHE.put(path, texture);

        return texture;
    }

    public static void free() {
        CACHE.values().forEach(Texture::dispose);
    }
}