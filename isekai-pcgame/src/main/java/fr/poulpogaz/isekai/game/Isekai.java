package fr.poulpogaz.isekai.game;

import fr.poulpogaz.isekai.game.renderer.Color;
import fr.poulpogaz.isekai.game.renderer.Colors;
import fr.poulpogaz.isekai.game.renderer.Shaders;
import fr.poulpogaz.isekai.game.renderer.Texture;
import fr.poulpogaz.isekai.game.renderer.mesh.MultiMesh;
import fr.poulpogaz.isekai.game.renderer.io.*;
import fr.poulpogaz.isekai.game.renderer.g2d.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Isekai implements IGame {

    private static final Logger LOGGER = LogManager.getLogger(Isekai.class);

    public static final boolean RENDERER_2D = true;
    public static final boolean GRAPHICS_2D = true;

    public static final int DEFAULT_WIDTH = 768; //700;
    public static final int DEFAULT_HEIGHT = 924; //500;

    private static final Isekai INSTANCE = new Isekai();

    private final Input input = Input.getInstance();

    private Window window;
    private GameEngine engine;

    private static final Matrix4f IDENTITY = new Matrix4f().identity();

    private final Matrix4f projection2D = new Matrix4f().ortho2D(0, DEFAULT_WIDTH, DEFAULT_HEIGHT, 0);
    private final Matrix4f projection3D = new Matrix4f().perspective((float) Math.toRadians(70), (float) DEFAULT_WIDTH / DEFAULT_HEIGHT, 0.1f, 1000f);

    private Texture amogus;
    private Texture tileset;

    private float hue = 0f;

    private Renderer2D renderer;
    private Graphics2D g2d;

    private final BasicCamera camera = new BasicCamera();

    private Isekai() {

    }

    @Override
    public void init(Window window, GameEngine engine) throws Exception {
        this.window = window;
        this.engine = engine;

        amogus = new Texture("amogus.png");
        tileset = new Texture("tileset.png");

        Shaders.createShaders();
        renderer = new Renderer2D(5000, 50000);
        g2d = new Graphics2D(renderer);
        g2d.setProjection(projection2D);

        glClearColor(0, 0, 0, 1);
    }

    @Override
    public void render() {
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);

        if (RENDERER_2D) {
            _2DRenderWithRenderer2D();
        }
        if (GRAPHICS_2D) {
            _2DRenderWithGraphics2D();
        }
    }

    private void _2DRenderWithRenderer2D() {
        float x = 768 / 4f + 50;
        float y = 924 / 4f + 50;

        Color color1 = Colors.fromHSV(hue, 1, 1);
        Color color2 = Colors.fromHSV(hue + 0.25f, 1, 1);
        Color color3 = Colors.fromHSV(hue + 0.5f, 1, 1);
        Color color4 = Colors.fromHSV(hue + 0.75f, 1, 1);

        renderer.begin(GL_TRIANGLES, DrawMode.COLOR_TEXTURE);
        renderer.setTexture(amogus);
        renderer.tex(0f, 0f).color(color1).pos(50, 50);
        renderer.tex(1f, 0f).color(color2).pos(x, 50);
        renderer.tex(1f, 1f).color(color3).pos(x, y);
        renderer.tex(0f, 1f).color(color4).pos(50, y);
        renderer.index((short) 0, (short) 1, (short) 2, (short) 0, (short) 2, (short) 3);
        renderer.end(projection2D, new Matrix4f().identity());


        renderer.begin(GL_TRIANGLES, DrawMode.COLOR);
        renderer.color(color1).pos(0, 0);
        renderer.color(color2).pos(50, 0);
        renderer.color(color3).pos(50, 50);
        renderer.color(color4).pos(0, 50);
        renderer.index((short) 0, (short) 1, (short) 2, (short) 0, (short) 2, (short) 3);
        renderer.end(projection2D, new Matrix4f().identity());


        float cx = window.getWidth() / 2f;
        float cy = window.getHeight() / 2f;

        float hue = 0;
        Color color = Colors.fromHSV(hue, 1, 1);

        int nPoints = 32;
        float theta = 1f / nPoints;
        float cos = (float) Math.cos(theta * Math.PI * 2);
        float sin = (float) Math.sin(theta * Math.PI * 2);

        renderer.begin(GL_TRIANGLES, DrawMode.COLOR);
        renderer.color(Colors.WHITE).pos(cx, cy);

        x = 150;
        y = 0;
        for (int i = 0; i < nPoints; i++) {
            renderer.color(color).pos(x + cx, y + cy);

            if (i > 0) {
                renderer.index(0, i, i + 1);
            }

            hue += theta;
            color.fromHSV(hue, 1, 1);

            float tempX = x;
            x = x * cos - y * sin;
            y = tempX * sin + y * cos;
        }

        renderer.index(0, nPoints, 1);
        renderer.end(projection2D, IDENTITY);

        int width = window.getWidth();

        int x2 = width - 300;

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(GL_TRIANGLES, DrawMode.TEXTURE);
        renderer.tex(0f, 0f).pos(x2, 0);
        renderer.tex(1f, 0f).pos(width, 0);
        renderer.tex(1f, 1f).pos(width, 300);
        renderer.tex(0f, 1f).pos(x2, 300);
        renderer.index((short) 0, (short) 1, (short) 2, (short) 0, (short) 2, (short) 3);

        renderer.setCustomShader(Shaders.CIRCLE_2D);
        renderer.end(projection2D, IDENTITY);
        renderer.setCustomShader(null);
        glDisable(GL_BLEND);
    }

    private void _2DRenderWithGraphics2D() {
        float scale = 2;

        g2d.setTransform(new Matrix4f());
        g2d.translate(0, window.getHeight() - 200 * scale);
        g2d.scale(scale, scale);


        g2d.setColor(Colors.MAGENTA);
        g2d.fillRect(0, 0, 50, 50);

        g2d.setPaint(new Paint.GradientPaint(Colors.BLUE, Colors.RED, Colors.BLUE, Colors.RED));
        g2d.fillRect(50, 0, 50, 50);

        g2d.setPaint(new Paint.TexturePaint(amogus, 50, 50, 50, 50, 0, 0, amogus.getWidth(), amogus.getHeight()));
        g2d.fillTriangle(75, 50, 100, 100, 50, 100);

        g2d.setColor(Colors.GREEN);
        g2d.drawTriangle(75, 50, 100, 100, 50, 100);

        g2d.setColor(Colors.DARK);
        g2d.drawRect(1, 51, 49, 49);

        g2d.drawCircle(25, 125, 25, 16);
        g2d.drawEllipse(50, 100, 25, 50, 16);
        g2d.drawEllipse(50, 100, 50, 25, 16);

        g2d.line(100, 150, 150, 200);
        g2d.line(150, 150, 100, 200);

        g2d.polyline(new float[]{0, 50, 25, 50, 0}, new float[]{150, 162.5f, 175, 187.5f, 200f}, 5);
        g2d.drawPolygon(new float[]{50, 100, 75, 100, 50}, new float[]{150, 162.5f, 175, 187.5f, 200f}, 5);

        g2d.setPaint(new Paint.GradientPaint(true, Colors.WHITE,
                                             Colors.LIGHT_GRAY, Colors.GRAY, Colors.DARK_GRAY, Colors.DARK));
        g2d.fillCircle(125, 25, 25, 16);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        g2d.setPaint(new Paint.TexturePaint(tileset, 200, 0));
        g2d.fillRect(200, 0, tileset.getWidth(), tileset.getHeight());

        g2d.setPaint(new Paint.TexturePaint(tileset, 200, 32, 16, 16, 16, 16, 16, 16));
        g2d.fillRect(200, 32, 16, 16);

        g2d.setPaint(new Paint.TexturePaint(tileset, 232, 32, 16, 16, 0, 16, 32, 16));
        g2d.fillRect(232, 32, 16, 16);

        g2d.drawSprite(tileset, 200, 48, 16, 16);
        g2d.drawSprite(tileset, 216, 48, 16, 16, 32, 16, 16, 16);

        g2d.end();
        glDisable(GL_BLEND);

        g2d.setPaint(new Paint.TexturePaint(amogus, 150, 0, 50, 50, 256, 0, 512, 512));
        g2d.fillCircle(175, 25, 25, 16);
        g2d.setPaint(new Paint.TexturePaint(amogus, 100, 50, 100, 50, 256, 0, 512, 512));
        g2d.fillEllipse(100, 50, 100, 50, 24);

        g2d.setPaint(new Paint.ColorPaint(Colors.BLUE));
        g2d.drawArc(100, 100, 25, 50, (float) Math.PI / 2, (float) Math.PI, 8, Graphics2D.OPEN);
        g2d.drawArc(125, 100, 25, 50, 0, (float) Math.PI / 2, 8, Graphics2D.CHORD);
        g2d.drawArc(150, 100, 50, 25, (float) Math.PI / 4, 1.5f * (float) Math.PI, 16, Graphics2D.PIE);


        g2d.setPaint(new Paint.TexturePaint(amogus, 200, 100, 25, 50, 256, 0, 512, 512));
        g2d.fillArc(200, 100, 25, 50, (float) Math.PI / 2, (float) Math.PI, 8, Graphics2D.OPEN);

        g2d.setPaint(new Paint.TexturePaint(amogus, 225, 100, 25, 50, 256, 0, 512, 512));
        g2d.fillArc(225, 100, 25, 50, 0, (float) Math.PI / 2, 8, Graphics2D.CHORD);

        g2d.setPaint(new Paint.TexturePaint(amogus, 250, 100, 50, 25, 256, 0, 512, 512));
        g2d.fillArc(250, 100, 50, 25, (float) Math.PI / 4, 1.5f * (float) Math.PI, 16, Graphics2D.PIE);

        g2d.end();
    }

    @Override
    public void update(float delta) {
        if (window.isResized()) {
            projection2D.setOrtho2D(0, window.getWidth(), window.getHeight(), 0);
            g2d.setProjection(projection2D);

            projection3D.setPerspective((float) Math.toRadians(70), (float) window.getWidth() / window.getHeight(), 0.1f, 1000f);

            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        if (input.keyReleased(GLFW_KEY_F11)) {
            window.setFullscreen(!window.isFullscreen());
        }

        if (input.keyReleased(GLFW_KEY_ESCAPE)) {
            engine.stop();
            return;
        }

        if (input.keyReleased(GLFW_KEY_R)) {
            camera.reset();
        }

        camera.update(delta);

        hue += 1 / 360F;
    }

    @Override
    public void terminate() {
        MultiMesh.disposeAll();
        amogus.dispose();
        tileset.dispose();
        renderer.dispose();
        g2d.dispose();
        Shaders.dispose();
    }

    public static Isekai getInstance() {
        return INSTANCE;
    }

    public Input getInput() {
        return input;
    }
}