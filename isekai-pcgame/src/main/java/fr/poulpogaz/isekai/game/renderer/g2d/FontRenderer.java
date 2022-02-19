package fr.poulpogaz.isekai.game.renderer.g2d;

import fr.poulpogaz.isekai.game.renderer.IColor;
import fr.poulpogaz.isekai.game.renderer.Texture;
import fr.poulpogaz.isekai.game.renderer.mesh.Mesh;
import fr.poulpogaz.isekai.game.renderer.mesh.VertexAttribute;
import fr.poulpogaz.isekai.game.renderer.shaders.Program;
import fr.poulpogaz.isekai.game.renderer.shaders.Shaders;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

import static fr.poulpogaz.isekai.game.renderer.mesh.DataType.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

/**
 * /!\ Code doesn't check if the renderer has sufficiently
 * space for drawing.
 */
public class FontRenderer implements IFontRenderer {

    private static final float[] VERTICES = new float[] {
            0, 0,
            1, 0,
            1, 1,
            0, 1};

    private static final int[] INDICES = new int[] {
            0, 1, 2,
            0, 2, 3};

    private final Mesh mesh;
    private final FloatBuffer instanceBuffer;

    private Program shader = Shaders.FONT;
    private Program newShader;

    private ImageFont font;
    private ImageFont newFont;

    private Matrix4f projection;
    private Matrix4f transform = new Matrix4f().identity();

    private Matrix4f newProjection;
    private IColor color;

    private boolean drawing;
    private boolean dirty = false;

    public FontRenderer(int numInstances) {
        mesh = new Mesh(4, 6, GL_STATIC_DRAW, VertexAttribute.position2D(0));
        mesh.setVertices(VERTICES);
        mesh.setIndices(INDICES);

        mesh.enableInstanceRendering(numInstances, GL_DYNAMIC_DRAW,
                new VertexAttribute(1, FLOAT_VEC_3, 1),
                new VertexAttribute(2, FLOAT_VEC_4, 1),
                new VertexAttribute(3, MAT_4, 1));
        instanceBuffer = mesh.getInstanceData();
        instanceBuffer.limit(instanceBuffer.capacity());
    }

    @Override
    public void translate(float x, float y) {
        transform.translate(x, y, 0);
    }

    @Override
    public void scale(float sx, float sy) {
        transform.scale(sx, sy, 0);
    }

    @Override
    public void rotate(float theta) {
        transform.rotateZ(theta);
    }

    @Override
    public void drawString(String str, int x, int y) {
        check();
        drawing = true;

        Matrix4f transform = new Matrix4f(this.transform).identity();
        transform.translate(x, y, 0);

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            Glyph glyph = font.getGlyph(c);

            // glyph info
            instanceBuffer.put(glyph.getX())
                    .put(glyph.getY())
                    .put(glyph.getWidth())
            // color
                    .put(color.getRed())
                    .put(color.getGreen())
                    .put(color.getBlue())
                    .put(color.getAlpha());
            transform.get(instanceBuffer);
            instanceBuffer.position(instanceBuffer.position() + 16);

            transform.translate(glyph.getWidth(), 0, 0);
        }
    }

    @Override
    public void drawStringColored(String str, int x, int y) {
        check();
    }

    @Override
    public void flush() {
        if (drawing) {
            instanceBuffer.flip();
            mesh.getInstanceBuffer().markDirty();

            shader.bind();
            shader.setUniform("projection", projection);
            shader.setUniformi("texture_size", font.getTextureSize());
            shader.setUniformi("font_height", font.getHeight());
            font.getTexture().bind();

            mesh.render(GL_TRIANGLES);

            Texture.unbind();
            Program.unbind();

            drawing = false;
            clean();
        }
    }

    private void check() {
        if (dirty) {
            clean();
        }
    }

    private void clean() {
        if (newFont != null) {
            font = newFont;
            newFont = null;
        }
        if (newProjection != null) {
            projection = newProjection;
            newProjection = null;
        }
        if (newShader != null) {
            shader = newShader;
            newShader = null;
        }
        instanceBuffer.position(0);
        instanceBuffer.limit(instanceBuffer.capacity());
    }

    @Override
    public boolean isDrawing() {
        return drawing;
    }

    @Override
    public void setFont(ImageFont font) {
        if (font != null) {
            newFont = font;
            dirty = true;
        }
    }

    @Override
    public ImageFont getFont() {
        return newFont;
    }

    @Override
    public void setColor(IColor color) {
        if (color != null) {
            this.color = color;
        }
    }

    @Override
    public IColor getColor() {
        return color;
    }

    @Override
    public void setShader(Program shader) {
        if (shader != null) {
            newShader = shader;
            dirty = true;
        }
    }

    @Override
    public Program getShader() {
        return shader;
    }

    @Override
    public void setProjection(Matrix4f projection) {
        if (projection != null) {
            newProjection = projection;
            dirty = true;
        }
    }

    @Override
    public Matrix4f getProjection() {
        return projection;
    }

    @Override
    public void setTransform(Matrix4f transform) {
        if (transform != null) {
            this.transform = transform;
        }
    }

    @Override
    public Matrix4f getTransform() {
        return transform;
    }
}
