package fr.poulpogaz.isekai.game.renderer.mesh;

import fr.poulpogaz.isekai.game.renderer.utils.Disposable;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class VBOWithVAO implements Disposable {

    private final VertexAttributes attributes;
    private final FloatBuffer buffer;
    private final ByteBuffer byteBuffer;
    private boolean dirty = true;

    private final int vao;
    private final int bufferPointer;
    private final int usage;

    public VBOWithVAO(VertexAttributes attributes, int nVertices, int usage) {
        this.attributes = attributes;
        this.usage = usage;

        byteBuffer = MemoryUtil.memAlloc(nVertices * attributes.vertexSizeInBytes());
        buffer = byteBuffer.asFloatBuffer();

        byteBuffer.flip();
        buffer.flip();

        vao = glGenVertexArrays();
        bufferPointer = glGenBuffers();
    }

    public void setVertices(FloatBuffer buffer) {
        dirty = true;
        this.buffer.clear();

        buffer.position(0);

        this.buffer.put(buffer);
        this.buffer.limit(buffer.limit());
    }

    public void setVertices(float[] array) {
        setVertices(array, 0, array.length, 0);
    }

    public void setVertices(float[] array, int offset) {
        setVertices(array, offset, array.length, 0);
    }

    public void setVertices(float[] array, int offset, int length) {
        setVertices(array, offset, length, 0);
    }

    public void setVertices(float[] array, int offset, int length, int offsetInDst) {
        dirty = true;
        buffer.limit(offsetInDst + length);
        buffer.position(offsetInDst);
        buffer.put(array, offset, length);
        buffer.position(0);
    }

    public float[] getVertices(float[] array) {
        if (array == null) {
            array = new float[buffer.limit()];
        }

        return getVertices(array, 0, array.length, 0);
    }

    public float[] getVertices(float[] array, int offset, int length) {
        if (array == null) {
            array = new float[length];
        }

        return getVertices(array, offset, length, 0);
    }

    public float[] getVertices(float[] array, int offset, int length, int offsetInDst) {
        buffer.get(offset, array, offsetInDst, length);

        return array;
    }

    public int getNumVertices() {
        return buffer.limit() * 4 / attributes.vertexSizeInBytes();
    }

    public int getMaxVertices() {
        return byteBuffer.capacity() / attributes.vertexSizeInBytes();
    }

    public FloatBuffer getBuffer() {
        dirty = true;
        return buffer;
    }

    public VertexAttributes getAttributes() {
        return attributes;
    }

    public int getUsage() {
        return usage;
    }

    protected void updateData() {
        if (dirty) {
            glBindBuffer(GL_ARRAY_BUFFER, bufferPointer);
            byteBuffer.limit(buffer.limit() * 4);
            byteBuffer.position(0);
            glBufferData(GL_ARRAY_BUFFER, byteBuffer, usage);

            dirty = false;
        }
    }

    public void bind() {
        glBindVertexArray(vao);

        updateData();
    }

    public void bindBuffer() {
        glBindBuffer(GL_ARRAY_BUFFER, bufferPointer);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    @Override
    public void dispose() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(bufferPointer);

        glBindVertexArray(0);
        glDeleteVertexArrays(vao);

        MemoryUtil.memFree(byteBuffer);
    }
}