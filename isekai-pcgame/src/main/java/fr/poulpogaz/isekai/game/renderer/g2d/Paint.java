package fr.poulpogaz.isekai.game.renderer.g2d;

import fr.poulpogaz.isekai.game.renderer.IColor;
import fr.poulpogaz.isekai.game.renderer.Texture;

public abstract class Paint {

    protected Renderer2D renderer;

    abstract Renderer2D paint(float x, float y, int index);

    abstract boolean compatible(Paint paint);

    abstract DrawMode drawMode();

    void set(Renderer2D renderer) {
        this.renderer = renderer;
    }

    void setRenderer(Renderer2D renderer) {
        this.renderer = renderer;
    }

    public static class ColorPaint extends Paint {

        private IColor color;

        public ColorPaint(IColor color) {
            this.color = color;
        }

        @Override
        Renderer2D paint(float x, float y, int index) {
            return renderer.color(color);
        }

        @Override
        boolean compatible(Paint paint) {
            return paint.drawMode() == drawMode();
        }

        @Override
        DrawMode drawMode() {
            return DrawMode.COLOR;
        }

        public IColor getColor() {
            return color;
        }

        public void setColor(IColor color) {
            this.color = color;
        }
    }

    public static class GradientPaint extends Paint {

        private IColor[] colors;
        private boolean loop = false;

        public GradientPaint(IColor... colors) {
            this.colors = colors;
        }

        public GradientPaint(boolean loop, IColor... colors) {
            this.colors = colors;
            this.loop = loop;
        }

        @Override
        Renderer2D paint(float x, float y, int index) {
            if (loop) {
                return renderer.color(colors[index % colors.length]);
            } else {
                return renderer.color(colors[index]);
            }
        }

        @Override
        boolean compatible(Paint paint) {
            return paint.drawMode() == drawMode();
        }

        @Override
        DrawMode drawMode() {
            return DrawMode.COLOR;
        }

        public IColor[] getColors() {
            return colors;
        }

        public void setColors(IColor[] colors) {
            this.colors = colors;
        }

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }
    }

    /**
     * u = srcX / IMAGE_WIDTH + (x - dstX) / dstWidth * srcWidth / IMAGE_WIDTH
     * v = srcY / IMAGE_HEIGHT + (y - dstY) / dstHeight * srcHeight / IMAGE_HEIGHT
     * <p>
     * let sx = 1f / dstWidth * srcWidth / IMAGE_WIDTH
     * let sy = 1f / dstHeight * srcHeight / IMAGE_HEIGHT
     * let tx = srcX / IMAGE_WIDTH - dstX * sx
     * let ty = srcY / IMAGE_HEIGHT - dstY * sy
     * <p>
     * so
     * u = tx + x * sx
     * v = ty + y * sy
     */
    public static class TexturePaint extends Paint {

        private Texture texture;

        private float tx;
        private float ty;

        private float sx;
        private float sy;

        TexturePaint() {

        }

        public TexturePaint(Texture texture, float dstX, float dstY) {
            this(texture, dstX, dstY, texture.getWidth(), texture.getHeight(), 0, 0, texture.getWidth(), texture.getHeight());
        }

        public TexturePaint(Texture texture, float dstX, float dstY, float dstWidth, float dstHeight) {
            this(texture, dstX, dstY, dstWidth, dstHeight, 0, 0, texture.getWidth(), texture.getHeight());
        }

        public TexturePaint(Texture texture, float dstX, float dstY, float dstWidth, float dstHeight, float srcX, float srcY, float srcWidth, float srcHeight) {
            setTexture(texture);
            set(dstX, dstY, dstWidth, dstHeight, srcX, srcY, srcWidth, srcHeight);
        }

        @Override
        Renderer2D paint(float x, float y, int index) {
            float u = tx + x * sx;
            float v = ty + y * sy;

            return renderer.tex(u, v);
        }

        @Override
        boolean compatible(Paint paint) {
            if (paint instanceof TexturePaint tex) {
                return tex.getTexture() == texture;
            }

            return false;
        }

        @Override
        DrawMode drawMode() {
            return DrawMode.TEXTURE;
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public void set(float dstX, float dstY, float dstWidth, float dstHeight, float srcX, float srcY, float srcWidth, float srcHeight) {
            float invWidth = 1f / texture.getWidth();
            float invHeight = 1f / texture.getHeight();

            this.sx = 1 / dstWidth * srcWidth * invWidth;
            this.sy = 1 / dstHeight * srcHeight * invHeight;

            this.tx = srcX * invWidth - dstX * sx;
            this.ty = srcY * invHeight - dstY * sy;
        }
    }
}