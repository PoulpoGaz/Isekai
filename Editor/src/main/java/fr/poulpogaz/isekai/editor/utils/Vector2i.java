package fr.poulpogaz.isekai.editor.utils;

public class Vector2i {

    public int x;
    public int y;

    public Vector2i() {
        this(0, 0);
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i add(Vector2i b) {
        return add(this, b);
    }

    public Vector2i add(Vector2i a, Vector2i b) {
        a.x += b.x;
        a.y += b.y;

        return a;
    }

    public Vector2i sub(Vector2i b) {
        return sub(this, b);
    }

    public Vector2i sub(Vector2i a, Vector2i b) {
        a.x -= b.x;
        a.y -= b.y;

        return a;
    }

    public Vector2i mul(Vector2i b) {
        return mul(this, b);
    }

    public Vector2i mul(Vector2i a, Vector2i b) {
        a.x *= b.x;
        a.y *= b.y;

        return a;
    }

    public Vector2i mul(int b) {
        return mul(this, b);
    }

    public Vector2i mul(Vector2i a, int b) {
        a.x *= b;
        a.y *= b;

        return a;
    }

    public Vector2i div(Vector2i b) {
        return div(this, b);
    }

    public Vector2i div(Vector2i a, Vector2i b) {
        a.x /= b.x;
        a.y /= b.y;

        return a;
    }

    public Vector2i div(int b) {
        return div(this, b);
    }

    public Vector2i div(Vector2i a, int b) {
        float scalar = 1f / b;

        a.x *= scalar;
        a.y *= scalar;

        return a;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}