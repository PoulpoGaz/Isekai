package fr.poulpogaz.isekai.game.renderer.g2d;

import fr.poulpogaz.isekai.game.renderer.IColor;
import fr.poulpogaz.isekai.game.renderer.Texture;
import fr.poulpogaz.isekai.game.renderer.utils.Disposable;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public interface IGraphics2D extends Disposable {

    int OPEN = 0;

    int CHORD = 1;

    int PIE = 2;

    void translate(float x, float y);

    default void translate(Vector3f translation) {
        translate(translation.x, translation.y);
    }

    default void translate(Vector2f translation) {
        translate(translation.x, translation.y);
    }


    void scale(float sx, float sy);

    default void scale(Vector3f scale) {
        scale(scale.x, scale.y);
    }

    default void scale(Vector2f scale) {
        scale(scale.x, scale.y);
    }


    void rotate(float theta);


    default void drawSprite(Texture texture, float x, float y) {
        drawSprite(texture, x, y, texture.getWidth(), texture.getHeight(), 0, 0, texture.getWidth(), texture.getHeight());
    }

    default void drawSprite(Texture texture, float x, float y, float width, float height) {
        drawSprite(texture, x, y, width, height, 0, 0, texture.getWidth(), texture.getHeight());
    }

    void drawSprite(Texture texture, float dstX, float dstY, float dstWidth, float dstHeight, float srcX, float srcY, float srcWidth, float srcHeight);

    void drawRect(float x, float y, float width, float height);

    void fillRect(float x, float y, float width, float height);

    void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3);

    void fillTriangle(float x1, float y1, float x2, float y2, float x3, float y3);

    void line(float x1, float y1, float x2, float y2);

    void polyline(float[] x, float[] y, int nPoints);

    void drawPolygon(float[] x, float[] y, int nPoints);

    void drawCircle(float cx, float cy, float radius, int nPoints);

    void fillCircle(float cx, float cy, float radius, int nPoints);

    void drawEllipse(float x, float y, float width, float height, int nPoints);

    void fillEllipse(float x, float y, float width, float height, int nPoints);

    void drawArc(float x, float y, float width, float height, float arcStart, float arcLen, int nPoints, int arcType);

    void fillArc(float x, float y, float width, float height, float arcStart, float arcLen, int nPoints, int arcType);


    void setPaint(Paint paint);

    Paint getPaint();


    void setColor(IColor color);

    IColor getColor();


    void setLineWidth(float width);

    float getLineWidth();


    void setProjection(Matrix4f projection);

    Matrix4f getProjection();


    void setTransform(Matrix4f transform);

    Matrix4f getTransform();
}