package fr.poulpogaz.isekai.game.renderer.g2d;

import fr.poulpogaz.isekai.game.renderer.Colors;
import fr.poulpogaz.isekai.game.renderer.IColor;
import fr.poulpogaz.isekai.game.renderer.Texture;
import org.joml.Matrix4f;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

/**
 * /!\ Code doesn't check if the renderer has sufficiently
 * space for drawing.
 */
@SuppressWarnings("DuplicatedCode")
public class Graphics2D implements IGraphics2D {

    private static final float TWO_PI = (float) (Math.PI * 2);

    private final Renderer2D renderer;
    private final boolean ownsRenderer;

    private final Paint.TexturePaint myTexturePaint = new Paint.TexturePaint();

    private final Paint.ColorPaint color = new Paint.ColorPaint(Colors.WHITE); // never null, default paint
    private Paint paint = color; // never null
    private Paint newPaint;

    private Matrix4f projection;
    private Matrix4f transform;

    private Matrix4f newProjection;
    private Matrix4f newTransform;

    private boolean dirty = false;

    private int primitive;

    public Graphics2D() {
        this(5000, 5000);
    }

    public Graphics2D(int numVertices, int numIndices) {
        this.renderer = new Renderer2D(numVertices, numIndices);
        ownsRenderer = true;

        color.set(renderer);
    }

    public Graphics2D(Renderer2D renderer) {
        this.renderer = Objects.requireNonNull(renderer);
        ownsRenderer = false;

        color.set(renderer);
    }

    @Override
    public void translate(float x, float y) {
        Matrix4f transform = newTransform == null ? new Matrix4f() : newTransform;
        transform.translate(x, y, 0);
        newTransform = transform;
        dirty = true;
    }

    @Override
    public void scale(float sx, float sy) {
        Matrix4f transform = newTransform == null ? new Matrix4f() : newTransform;
        transform.scale(sx, sy, 0);
        newTransform = transform;
        dirty = true;
    }

    @Override
    public void rotate(float theta) {
        Matrix4f transform = newTransform == null ? new Matrix4f() : newTransform;
        transform.rotateZ(theta);
        newTransform = transform;
        dirty = true;
    }

    @Override
    public void drawSprite(Texture texture, float dstX, float dstY, float dstWidth, float dstHeight, float srcX, float srcY, float srcWidth, float srcHeight) {
        if (paint instanceof Paint.TexturePaint texturePaint) {
            if (texturePaint.getTexture() != texture) {
                texturePaint.setTexture(texture);
                dirty = true;
            }
            texturePaint.set(dstX, dstY, dstWidth, dstHeight, srcX, srcY, srcWidth, srcHeight);
        } else {
            myTexturePaint.setTexture(texture);
            myTexturePaint.set(dstX, dstY, dstWidth, dstHeight, srcX, srcY, srcWidth, srcHeight);
            setPaint(myTexturePaint);
        }

        fillRect(dstX, dstY, dstWidth, dstHeight);
    }

    @Override
    public void drawRect(float x, float y, float width, float height) {
        check(GL_LINES, -1, 4, 8);

        int i = renderer.getNumVertices();
        renderer.index(i, i + 1,
                       i + 1, i + 2,
                       i + 2, i + 3,
                       i + 3, i);

        float x2 = x + width;
        float y2 = y + height;

        paint.paint(x, y, 0).pos(x, y);
        paint.paint(x2, y, 1).pos(x2, y);
        paint.paint(x2, y2, 2).pos(x2, y2);
        paint.paint(x, y2, 3).pos(x, y2);
    }

    @Override
    public void fillRect(float x, float y, float width, float height) {
        check(GL_TRIANGLES, -1, 4, 6);

        int i = renderer.getNumVertices();
        renderer.index(i, i + 1, i + 2,
                       i, i + 2, i + 3);

        float x2 = x + width;
        float y2 = y + height;

        paint.paint(x, y, 0).pos(x, y);
        paint.paint(x2, y, 1).pos(x2, y);
        paint.paint(x2, y2, 2).pos(x2, y2);
        paint.paint(x, y2, 3).pos(x, y2);
    }

    @Override
    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        check(GL_LINES, 0, 3, 6);

        int i = renderer.getNumVertices();
        renderer.index(i, i + 1,
                       i + 1, i + 2,
                       i + 2, i);

        paint.paint(x1, y1, 0).pos(x1, y1);
        paint.paint(x2, y2, 1).pos(x2, y2);
        paint.paint(x3, y3, 2).pos(x3, y3);
    }

    @Override
    public void fillTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        check(GL_TRIANGLES, -1, 3, 3);

        int i = renderer.getNumVertices();
        renderer.index(i, i + 1, i + 2);

        paint.paint(x1, y1, 0).pos(x1, y1);
        paint.paint(x2, y2, 1).pos(x2, y2);
        paint.paint(x3, y3, 2).pos(x3, y3);
    }

    @Override
    public void line(float x1, float y1, float x2, float y2) {
        check(GL_LINES, -1, 2, 2);

        int i = renderer.getNumVertices();
        renderer.index(i, i + 1);
        paint.paint(x1, y1, 0).pos(x1, y1);
        paint.paint(x2, y2, 1).pos(x2, y2);
    }

    @Override
    public void polyline(float[] x, float[] y, int nPoints) {
        if (nPoints <= 1) {
            return;
        }

        check(GL_LINES, -1, nPoints, (nPoints - 1) * 2);

        int j = renderer.getNumVertices();

        for (int i = 0; i < nPoints; i++) {
            if (i < nPoints - 1) {
                renderer.index(j, j + 1);
            }
            paint.paint(x[i], y[i], i).pos(x[i], y[i]);

            j++;
        }
    }

    @Override
    public void drawPolygon(float[] x, float[] y, int nPoints) {
        check(GL_LINES, -1, nPoints, nPoints * 2);

        int j = renderer.getNumVertices();
        int k = j;
        for (int i = 0; i < nPoints; i++) {
            if (i == 0 || i < nPoints - 1) {
                renderer.index(j, j + 1);
                j++;
            }

            paint.paint(x[i], y[i], i).pos(x[i], y[i]);
        }

        renderer.index(k, j);
    }

    @Override
    public void drawCircle(float x, float y, float radius, int nPoints) {
        if (nPoints <= 1) {
            return;
        }

        check(GL_LINES, -1, nPoints, nPoints * 2);

        float theta = TWO_PI / nPoints;
        float sin = (float) Math.sin(theta);
        float cos = (float) Math.cos(theta);

        float x2 = radius;
        float y2 = 0;

        int j = renderer.getNumVertices();
        int k = j;

        for (int i = 0; i < nPoints; i++) {
            if (i < nPoints - 1) {
                renderer.index(j, j + 1);
                j++;
            }

            float x3 = x + x2;
            float y3 = y - y2;

            paint.paint(x3, y3, i).pos(x3, y3);

            float temp = x2;
            x2 = x2 * cos - y2 * sin;
            y2 = temp * sin + y2 * cos;
        }

        renderer.index(k, j);
    }

    /**
     * be aware of the central point
     */
    @Override
    public void fillCircle(float x, float y, float radius, int nPoints) {
        if (nPoints <= 1) {
            return;
        }

        check(GL_TRIANGLES, -1, nPoints + 1, nPoints * 3);

        float theta = TWO_PI / nPoints;
        float sin = (float) Math.sin(theta);
        float cos = (float) Math.cos(theta);

        float x2 = radius;
        float y2 = 0;

        int j = renderer.getNumVertices();

        paint.paint(x, y, 0).pos(x, y);

        for (int i = 0; i < nPoints; i++) {
            float x3 = x + x2;
            float y3 = y - y2;

            paint.paint(x3, y3, i + 1).pos(x3, y3);
            if (i < nPoints - 1) {
                renderer.index(j, j + i + 1, j + i + 2);
            }

            float tempX = x2;
            x2 = x2 * cos - y2 * sin;
            y2 = tempX * sin + y2 * cos;
        }

        renderer.index(j, j + 1, j + nPoints);
    }

    @Override
    public void drawEllipse(float x, float y, float width, float height, int nPoints) {
        if (nPoints <= 1) {
            return;
        }

        check(GL_LINES, -1, nPoints, nPoints * 2);

        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        float cx = x + halfWidth;
        float cy = y + halfHeight;

        float theta = TWO_PI / nPoints;
        float sin = (float) Math.sin(theta);
        float cos = (float) Math.cos(theta);

        float x2 = 1;
        float y2 = 0;

        int j = renderer.getNumVertices();
        int k = j;

        for (int i = 0; i < nPoints; i++) {
            if (i == 0 || i < nPoints - 1) {
                renderer.index(j, j + 1);
                j++;
            }

            float x3 = cx + x2 * halfWidth;
            float y3 = cy - y2 * halfHeight;

            paint.paint(x3, y3, i).pos(x3, y3);

            float temp = x2;
            x2 = x2 * cos - y2 * sin;
            y2 = temp * sin + y2 * cos;
        }

        renderer.index(k, j);
    }

    @Override
    public void fillEllipse(float x, float y, float width, float height, int nPoints) {
        if (nPoints <= 1) {
            return;
        }

        check(GL_TRIANGLES, -1, nPoints + 1, nPoints * 3);

        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        float cx = x + halfWidth;
        float cy = y + halfHeight;

        float theta = TWO_PI / nPoints;
        float sin = (float) Math.sin(theta);
        float cos = (float) Math.cos(theta);

        float x2 = 1;
        float y2 = 0;

        int j = renderer.getNumVertices();

        paint.paint(cx, cy, 0).pos(cx, cy);

        for (int i = 0; i < nPoints; i++) {
            float x3 = cx + x2 * halfWidth;
            float y3 = cy - y2 * halfHeight;

            paint.paint(x3, y3, i + 1).pos(x3, y3);
            if (i < nPoints - 1) {
                renderer.index(j, j + i + 1, j + i + 2);
            }

            float tempX = x2;
            x2 = x2 * cos - y2 * sin;
            y2 = tempX * sin + y2 * cos;
        }

        renderer.index(j, j + 1, j + nPoints);
    }

    @Override
    public void drawArc(float x, float y, float width, float height, float arcStart, float arcLen, int nPoints, int arcType) {
        if (nPoints <= 1) {
            return;
        }

        int nVertices;
        int nIndices;
        if (arcType == OPEN) {
            nIndices = (nPoints - 1) * 2;
            nVertices = nPoints;
        } else if (arcType == CHORD) {
            nIndices = nPoints * 2;
            nVertices = nPoints;
        } else if (arcType == PIE) {
            nVertices = nPoints + 1;
            nIndices = nVertices * 2;
        } else {
            return;
        }

        check(GL_LINES, -1, nVertices, nIndices);

        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        float cx = x + halfWidth;
        float cy = y + halfHeight;

        float theta = arcLen / (nPoints - 1);
        float sin = (float) Math.sin(theta);
        float cos = (float) Math.cos(theta);

        float x2 = (float) Math.cos(arcStart); // 1 * cos - 0 * sin
        float y2 = (float) Math.sin(arcStart); // 1 * sin + 0 * cos

        int j = renderer.getNumVertices();

        int k = 0;
        if (arcType == CHORD) {
            renderer.index(j, j + nPoints - 1);
        } else if (arcType == PIE) {
            paint.paint(cx, cy, 0).pos(cx, cy);
            renderer.index(j, j + 1);
            renderer.index(j, j + nPoints);
            k++;
            j++;
        }

        for (int i = 0; i < nPoints; i++, k++, j++) {
            if (i < nPoints - 1) {
                renderer.index(j, j + 1);
            }

            float x3 = cx + x2 * halfWidth;
            float y3 = cy - y2 * halfHeight;

            paint.paint(x3, y3, k).pos(x3, y3);

            float tempX = x2;
            x2 = x2 * cos - y2 * sin;
            y2 = tempX * sin + y2 * cos;
        }
    }

    @Override
    public void fillArc(float x, float y, float width, float height, float arcStart, float arcLen, int nPoints, int arcType) {
        if (nPoints <= 1) {
            return;
        }

        switch (arcType) {
            case OPEN, CHORD -> {
                check(GL_TRIANGLES, -1, nPoints, (nPoints - 2) * 3);
                fillArcChord(x, y, width, height, arcStart, arcLen, nPoints);
            }
            case PIE -> {
                fillArcPie(x, y, width, height, arcStart, arcLen, nPoints);
            }
        }
    }

    protected void fillArcChord(float x, float y, float width, float height, float arcStart, float arcLen, int nPoints) {
        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        float cx = x + halfWidth;
        float cy = y + halfHeight;

        float theta = arcLen / (nPoints - 1);
        float sin = (float) Math.sin(theta);
        float cos = (float) Math.cos(theta);

        float x2 = (float) Math.cos(arcStart); // 1 * cos - 0 * sin
        float y2 = (float) Math.sin(arcStart); // 1 * sin + 0 * cos

        int j = renderer.getNumVertices();
        int k = j + 1;

        for (int i = 0; i < nPoints; i++, k++) {
            if (i < nPoints - 2) {
                renderer.index(j, k, k + 1);
            }

            float x3 = cx + x2 * halfWidth;
            float y3 = cy - y2 * halfHeight;

            paint.paint(x3, y3, i).pos(x3, y3);

            float tempX = x2;
            x2 = x2 * cos - y2 * sin;
            y2 = tempX * sin + y2 * cos;
        }
    }

    protected void fillArcPie(float x, float y, float width, float height, float arcStart, float arcLen, int nPoints) {
        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        float cx = x + halfWidth;
        float cy = y + halfHeight;

        float theta = arcLen / (nPoints - 1);
        float sin = (float) Math.sin(theta);
        float cos = (float) Math.cos(theta);

        float x2 = (float) Math.cos(arcStart); // 1 * cos - 0 * sin
        float y2 = (float) Math.sin(arcStart); // 1 * sin + 0 * cos

        int j = renderer.getNumVertices();
        int k = j + 1;

        paint.paint(cx, cy, 0).pos(cx, cy);

        for (int i = 0; i < nPoints; i++, k++) {
            if (i < nPoints - 1) {
                renderer.index(j, k, k + 1);
            }

            float x3 = cx + x2 * halfWidth;
            float y3 = cy - y2 * halfHeight;

            paint.paint(x3, y3, i + 1).pos(x3, y3);

            float tempX = x2;
            x2 = x2 * cos - y2 * sin;
            y2 = tempX * sin + y2 * cos;
        }
    }

    private void check(int prefPrimitive, int alternative, int nVertices, int nIndices) {
        if (renderer.isDrawing()) {
            boolean primitiveOk = primitive == prefPrimitive || primitive == alternative;

            if (!primitiveOk ||
                    dirty ||
                    renderer.getNumVertices() + nVertices > renderer.getMaxVertices() ||
                    renderer.getNumIndices() + nIndices > renderer.getMaxIndices()) {
                primitive = prefPrimitive;

                flush();
            }
        } else {
            primitive = prefPrimitive;
            clean();
            begin();
        }
    }

    private void begin() {
        renderer.begin(primitive, paint.drawMode());
    }

    public void flush() {
        end();
        clean();
        begin();
    }

    public void end() {
        if (!renderer.isDrawing()) {
            return;
        }

        renderer.end(projection, transform);
    }

    private void clean() {
        if (!dirty) {
            return;
        }

        if (newPaint != null) {
            paint = newPaint;
            newPaint = null;

            if (paint instanceof Paint.TexturePaint texturePaint) {
                renderer.setTexture(texturePaint.getTexture());
            }
        }
        if (newProjection != null) {
            projection = newProjection;
            newProjection = null;
        }
        if (newTransform != null) {
            transform = newTransform;
            newTransform = null;
        }
        dirty = false;
    }


    @Override
    public void setPaint(Paint paint) {
        if (paint != this.paint) {
            Paint newPaint = paint == null ? color : paint;

            if (this.paint.compatible(newPaint)) {
                this.paint = newPaint;
            } else {
                this.newPaint = newPaint;
                dirty = true;
            }

            newPaint.set(renderer);
        }
    }

    @Override
    public Paint getPaint() {
        return paint;
    }

    @Override
    public void setColor(IColor color) {
        this.color.setColor(color);
        setPaint(this.color);
    }

    @Override
    public IColor getColor() {
        return color.getColor();
    }

    @Override
    public void setLineWidth(float width) {

    }

    @Override
    public float getLineWidth() {
        return 0;
    }

    @Override
    public void setProjection(Matrix4f projection) {
        newProjection = projection;
        dirty = true;
    }

    @Override
    public Matrix4f getProjection() {
        return projection;
    }

    @Override
    public void setTransform(Matrix4f transform) {
        newTransform = transform;
        dirty = true;
    }

    @Override
    public Matrix4f getTransform() {
        return transform;
    }

    @Override
    public void dispose() {
        if (ownsRenderer) {
            renderer.dispose();
        }
    }
}