package fr.poulpogaz.isekai.game.renderer.g2d;

import fr.poulpogaz.isekai.game.renderer.Shader;
import fr.poulpogaz.isekai.game.renderer.Shaders;
import fr.poulpogaz.isekai.game.renderer.mesh.VertexAttribute;
import fr.poulpogaz.isekai.game.renderer.mesh.VertexAttributes;

public enum DrawMode {

    COLOR(Shaders.COLOR_2D, 6, 2, 0, false,
          VertexAttribute.position2D(0), VertexAttribute.color(1)),

    TEXTURE(Shaders.TEXTURE_2D, 4, 0, 2, true,
            VertexAttribute.position2D(0), VertexAttribute.texture(1)),

    COLOR_TEXTURE(Shaders.COLOR_TEXTURE_2D, 8, 2, 6, true,
                  VertexAttribute.position2D(0), VertexAttribute.color(1), VertexAttribute.texture(2));

    private final Shader shader;
    private final int vertexSize;
    private final int colorOffset;
    private final int textureOffset;
    private final boolean texture;
    private final VertexAttributes attributes;

    DrawMode(Shader shader, int vertexSize, int colorOffset, int textureOffset, boolean texture, VertexAttribute... attributes) {
        this.shader = shader;
        this.vertexSize = vertexSize;
        this.colorOffset = colorOffset;
        this.textureOffset = textureOffset;
        this.texture = texture;
        this.attributes = new VertexAttributes(attributes);
    }

    public Shader getShader() {
        return shader;
    }

    public int getVertexSize() {
        return vertexSize;
    }

    public int getColorOffset() {
        return colorOffset;
    }

    public int getTextureOffset() {
        return textureOffset;
    }

    public boolean isTexture() {
        return texture;
    }

    public VertexAttributes getAttributes() {
        return attributes;
    }
}