package fr.poulpogaz.isekai.game.renderer.utils;

import org.joml.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Auto generated
 */
public class Vector3fi implements Vector3fc {

    private final Vector3fc delegate;

    public Vector3fi(Vector3fc delegate) {
        this.delegate = delegate;
    }

    public Vector3fi(Vector3fc delegate, boolean clone) {
        if (clone) {
            this.delegate = new Vector3f(delegate);
        } else {
            this.delegate = delegate;
        }
    }

    public Vector3fi(float x, float y, float z) {
        this.delegate = new Vector3f(x, y, z);
    }

    /**
     * @return the value of the x component
     */
    public float x() {
        return delegate.x();
    }

    /**
     * @return the value of the y component
     */
    public float y() {
        return delegate.y();
    }

    /**
     * @return the value of the z component
     */
    public float z() {
        return delegate.z();
    }

    /**
     * Store this vector into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is stored, use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <code>x, y, z</code> order
     * @return the passed in buffer
     * @see #get(int, FloatBuffer)
     * @see #get(int, FloatBuffer)
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return delegate.get(buffer);
    }

    /**
     * Store this vector into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this vector in <code>x, y, z</code> order
     * @return the passed in buffer
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        return delegate.get(index, buffer);
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is stored, use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <code>x, y, z</code> order
     * @return the passed in buffer
     * @see #get(int, ByteBuffer)
     * @see #get(int, ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return delegate.get(buffer);
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this vector in <code>x, y, z</code> order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        return delegate.get(index, buffer);
    }

    /**
     * Store this vector at the given off-heap memory address.
     * <p>
     * This method will throw an {@link UnsupportedOperationException} when JOML is used with `-Djoml.nounsafe`.
     * <p>
     * <em>This method is unsafe as it can result in a crash of the JVM process when the specified address range does not belong to this process.</em>
     *
     * @param address the off-heap address where to store this vector
     * @return this
     */
    public Vector3fc getToAddress(long address) {
        return delegate.getToAddress(address);
    }

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f sub(Vector3fc v, Vector3f dest) {
        return delegate.sub(v, dest);
    }

    /**
     * Decrement the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param z    the z component to subtract
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f sub(float x, float y, float z, Vector3f dest) {
        return delegate.sub(x, y, z, dest);
    }

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f add(Vector3fc v, Vector3f dest) {
        return delegate.add(v, dest);
    }

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to add
     * @param y    the y component to add
     * @param z    the z component to add
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f add(float x, float y, float z, Vector3f dest) {
        return delegate.add(x, y, z, dest);
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f fma(Vector3fc a, Vector3fc b, Vector3f dest) {
        return delegate.fma(a, b, dest);
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f fma(float a, Vector3fc b, Vector3f dest) {
        return delegate.fma(a, b, dest);
    }

    /**
     * Add the component-wise multiplication of <code>this * a</code> to <code>b</code>
     * and store the result in <code>dest</code>.
     *
     * @param a    the multiplicand
     * @param b    the addend
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulAdd(Vector3fc a, Vector3fc b, Vector3f dest) {
        return delegate.mulAdd(a, b, dest);
    }

    /**
     * Add the component-wise multiplication of <code>this * a</code> to <code>b</code>
     * and store the result in <code>dest</code>.
     *
     * @param a    the multiplicand
     * @param b    the addend
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulAdd(float a, Vector3fc b, Vector3f dest) {
        return delegate.mulAdd(a, b, dest);
    }

    /**
     * Multiply this Vector3f component-wise by another Vector3f and store the result in <code>dest</code>.
     *
     * @param v    the vector to multiply by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mul(Vector3fc v, Vector3f dest) {
        return delegate.mul(v, dest);
    }

    /**
     * Divide this Vector3f component-wise by another Vector3f and store the result in <code>dest</code>.
     *
     * @param v    the vector to divide by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f div(Vector3fc v, Vector3f dest) {
        return delegate.div(v, dest);
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3f, perform perspective division
     * and store the result in <code>dest</code>.
     * <p>
     * This method uses <code>w=1.0</code> as the fourth vector component.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulProject(Matrix4fc mat, Vector3f dest) {
        return delegate.mulProject(mat, dest);
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3f, perform perspective division
     * and store the result in <code>dest</code>.
     * <p>
     * This method uses the given <code>w</code> as the fourth vector component.
     *
     * @param mat  the matrix to multiply this vector by
     * @param w    the w component to use
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulProject(Matrix4fc mat, float w, Vector3f dest) {
        return delegate.mulProject(mat, w, dest);
    }

    /**
     * Multiply the given matrix with this Vector3f and store the result in <code>dest</code>.
     *
     * @param mat  the matrix
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mul(Matrix3fc mat, Vector3f dest) {
        return delegate.mul(mat, dest);
    }

    /**
     * Multiply the given matrix with this Vector3f and store the result in <code>dest</code>.
     *
     * @param mat  the matrix
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mul(Matrix3dc mat, Vector3f dest) {
        return delegate.mul(mat, dest);
    }

    /**
     * Multiply the given matrix <code>mat</code> with <code>this</code> by assuming a
     * third row in the matrix of <code>(0, 0, 1)</code> and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mul(Matrix3x2fc mat, Vector3f dest) {
        return delegate.mul(mat, dest);
    }

    /**
     * Multiply the transpose of the given matrix with this Vector3f and store the result in <code>dest</code>.
     *
     * @param mat  the matrix
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulTranspose(Matrix3fc mat, Vector3f dest) {
        return delegate.mulTranspose(mat, dest);
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulPosition(Matrix4fc mat, Vector3f dest) {
        return delegate.mulPosition(mat, dest);
    }

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulPosition(Matrix4x3fc mat, Vector3f dest) {
        return delegate.mulPosition(mat, dest);
    }

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     *
     * @param mat  the matrix whose transpose to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulTransposePosition(Matrix4fc mat, Vector3f dest) {
        return delegate.mulTransposePosition(mat, dest);
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>, store the
     * result in <code>dest</code> and return the <i>w</i> component of the resulting 4D vector.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the <code>(x, y, z)</code> components of the resulting vector
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    public float mulPositionW(Matrix4fc mat, Vector3f dest) {
        return delegate.mulPositionW(mat, dest);
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulDirection(Matrix4dc mat, Vector3f dest) {
        return delegate.mulDirection(mat, dest);
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulDirection(Matrix4fc mat, Vector3f dest) {
        return delegate.mulDirection(mat, dest);
    }

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulDirection(Matrix4x3fc mat, Vector3f dest) {
        return delegate.mulDirection(mat, dest);
    }

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     *
     * @param mat  the matrix whose transpose to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mulTransposeDirection(Matrix4fc mat, Vector3f dest) {
        return delegate.mulTransposeDirection(mat, dest);
    }

    /**
     * Multiply all components of this {@link Vector3f} by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar the scalar to multiply this vector by
     * @param dest   will hold the result
     * @return dest
     */
    public Vector3f mul(float scalar, Vector3f dest) {
        return delegate.mul(scalar, dest);
    }

    /**
     * Multiply the components of this Vector3f by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to multiply this vector by
     * @param y    the y component to multiply this vector by
     * @param z    the z component to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f mul(float x, float y, float z, Vector3f dest) {
        return delegate.mul(x, y, z, dest);
    }

    /**
     * Divide all components of this {@link Vector3f} by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar the scalar to divide by
     * @param dest   will hold the result
     * @return dest
     */
    public Vector3f div(float scalar, Vector3f dest) {
        return delegate.div(scalar, dest);
    }

    /**
     * Divide the components of this Vector3f by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to divide this vector by
     * @param y    the y component to divide this vector by
     * @param z    the z component to divide this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f div(float x, float y, float z, Vector3f dest) {
        return delegate.div(x, y, z, dest);
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     *
     * @param quat the quaternion to rotate this vector
     * @param dest will hold the result
     * @return dest
     * @see Quaternionfc#transform(Vector3f)
     */
    public Vector3f rotate(Quaternionfc quat, Vector3f dest) {
        return delegate.rotate(quat, dest);
    }

    /**
     * Compute the quaternion representing a rotation of <code>this</code> vector to point along <code>toDir</code>
     * and store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     *
     * @param toDir the destination direction
     * @param dest  will hold the result
     * @return dest
     * @see Quaternionf#rotationTo(Vector3fc, Vector3fc)
     */
    public Quaternionf rotationTo(Vector3fc toDir, Quaternionf dest) {
        return delegate.rotationTo(toDir, dest);
    }

    /**
     * Compute the quaternion representing a rotation of <code>this</code> vector to point along <code>(toDirX, toDirY, toDirZ)</code>
     * and store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     *
     * @param toDirX the x coordinate of the destination direction
     * @param toDirY the y coordinate of the destination direction
     * @param toDirZ the z coordinate of the destination direction
     * @param dest   will hold the result
     * @return dest
     * @see Quaternionf#rotationTo(float, float, float, float, float, float)
     */
    public Quaternionf rotationTo(float toDirX, float toDirY, float toDirZ, Quaternionf dest) {
        return delegate.rotationTo(toDirX, toDirY, toDirZ, dest);
    }

    /**
     * Rotate this vector the specified radians around the given rotation axis and store the result
     * into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param aX    the x component of the rotation axis
     * @param aY    the y component of the rotation axis
     * @param aZ    the z component of the rotation axis
     * @param dest  will hold the result
     * @return dest
     */
    public Vector3f rotateAxis(float angle, float aX, float aY, float aZ, Vector3f dest) {
        return delegate.rotateAxis(angle, aX, aY, aZ, dest);
    }

    /**
     * Rotate this vector the specified radians around the X axis and store the result
     * into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    public Vector3f rotateX(float angle, Vector3f dest) {
        return delegate.rotateX(angle, dest);
    }

    /**
     * Rotate this vector the specified radians around the Y axis and store the result
     * into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    public Vector3f rotateY(float angle, Vector3f dest) {
        return delegate.rotateY(angle, dest);
    }

    /**
     * Rotate this vector the specified radians around the Z axis and store the result
     * into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    public Vector3f rotateZ(float angle, Vector3f dest) {
        return delegate.rotateZ(angle, dest);
    }

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    public float lengthSquared() {
        return delegate.lengthSquared();
    }

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    public float length() {
        return delegate.length();
    }

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f normalize(Vector3f dest) {
        return delegate.normalize(dest);
    }

    /**
     * Scale this vector to have the given length and store the result in <code>dest</code>.
     *
     * @param length the desired length
     * @param dest   will hold the result
     * @return dest
     */
    public Vector3f normalize(float length, Vector3f dest) {
        return delegate.normalize(length, dest);
    }

    /**
     * Compute the cross product of this vector and <code>v</code> and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f cross(Vector3fc v, Vector3f dest) {
        return delegate.cross(v, dest);
    }

    /**
     * Compute the cross product of this vector and <code>(x, y, z)</code> and store the result in <code>dest</code>.
     *
     * @param x    the x component of the other vector
     * @param y    the y component of the other vector
     * @param z    the z component of the other vector
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f cross(float x, float y, float z, Vector3f dest) {
        return delegate.cross(x, y, z, dest);
    }

    /**
     * Return the distance between this Vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the distance
     */
    public float distance(Vector3fc v) {
        return delegate.distance(v);
    }

    /**
     * Return the distance between <code>this</code> vector and <code>(x, y, z)</code>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the euclidean distance
     */
    public float distance(float x, float y, float z) {
        return delegate.distance(x, y, z);
    }

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the squared of the distance
     */
    public float distanceSquared(Vector3fc v) {
        return delegate.distanceSquared(v);
    }

    /**
     * Return the square of the distance between <code>this</code> vector and <code>(x, y, z)</code>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the square of the distance
     */
    public float distanceSquared(float x, float y, float z) {
        return delegate.distanceSquared(x, y, z);
    }

    /**
     * Return the dot product of this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the dot product
     */
    public float dot(Vector3fc v) {
        return delegate.dot(v);
    }

    /**
     * Return the dot product of this vector and the vector <code>(x, y, z)</code>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the dot product
     */
    public float dot(float x, float y, float z) {
        return delegate.dot(x, y, z);
    }

    /**
     * Return the cosine of the angle between this vector and the supplied vector. Use this instead of Math.cos(this.angle(v)).
     *
     * @param v the other vector
     * @return the cosine of the angle
     * @see #angle(Vector3fc)
     */
    public float angleCos(Vector3fc v) {
        return delegate.angleCos(v);
    }

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the angle, in radians
     * @see #angleCos(Vector3fc)
     */
    public float angle(Vector3fc v) {
        return delegate.angle(v);
    }

    /**
     * Return the signed angle between this vector and the supplied vector with
     * respect to the plane with the given normal vector <code>n</code>.
     *
     * @param v the other vector
     * @param n the plane's normal vector
     * @return the angle, in radians
     * @see #angleCos(Vector3fc)
     */
    public float angleSigned(Vector3fc v, Vector3fc n) {
        return delegate.angleSigned(v, n);
    }

    /**
     * Return the signed angle between this vector and the supplied vector with
     * respect to the plane with the given normal vector <code>(nx, ny, nz)</code>.
     *
     * @param x  the x coordinate of the other vector
     * @param y  the y coordinate of the other vector
     * @param z  the z coordinate of the other vector
     * @param nx the x coordinate of the plane's normal vector
     * @param ny the y coordinate of the plane's normal vector
     * @param nz the z coordinate of the plane's normal vector
     * @return the angle, in radians
     */
    public float angleSigned(float x, float y, float z, float nx, float ny, float nz) {
        return delegate.angleSigned(x, y, z, nx, ny, nz);
    }

    /**
     * Set the components of <code>dest</code> to be the component-wise minimum of this and the other vector.
     *
     * @param v    the other vector
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f min(Vector3fc v, Vector3f dest) {
        return delegate.min(v, dest);
    }

    /**
     * Set the components of <code>dest</code> to be the component-wise maximum of this and the other vector.
     *
     * @param v    the other vector
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f max(Vector3fc v, Vector3f dest) {
        return delegate.max(v, dest);
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f negate(Vector3f dest) {
        return delegate.negate(dest);
    }

    /**
     * Compute the absolute values of the individual components of <code>this</code> and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f absolute(Vector3f dest) {
        return delegate.absolute(dest);
    }

    /**
     * Reflect this vector about the given <code>normal</code> vector and store the result in <code>dest</code>.
     *
     * @param normal the vector to reflect about
     * @param dest   will hold the result
     * @return dest
     */
    public Vector3f reflect(Vector3fc normal, Vector3f dest) {
        return delegate.reflect(normal, dest);
    }

    /**
     * Reflect this vector about the given normal vector and store the result in <code>dest</code>.
     *
     * @param x    the x component of the normal
     * @param y    the y component of the normal
     * @param z    the z component of the normal
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f reflect(float x, float y, float z, Vector3f dest) {
        return delegate.reflect(x, y, z, dest);
    }

    /**
     * Compute the half vector between this and the other vector and store the result in <code>dest</code>.
     *
     * @param other the other vector
     * @param dest  will hold the result
     * @return dest
     */
    public Vector3f half(Vector3fc other, Vector3f dest) {
        return delegate.half(other, dest);
    }

    /**
     * Compute the half vector between this and the vector <code>(x, y, z)</code>
     * and store the result in <code>dest</code>.
     *
     * @param x    the x component of the other vector
     * @param y    the y component of the other vector
     * @param z    the z component of the other vector
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f half(float x, float y, float z, Vector3f dest) {
        return delegate.half(x, y, z, dest);
    }

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation
     * between <code>this</code> vector and the given vector <code>v</code> and
     * store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param t    the interpolation factor, within <code>[0..1]</code>
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f smoothStep(Vector3fc v, float t, Vector3f dest) {
        return delegate.smoothStep(v, t, dest);
    }

    /**
     * Compute a hermite interpolation between <code>this</code> vector with its
     * associated tangent <code>t0</code> and the given vector <code>v</code>
     * with its tangent <code>t1</code> and store the result in
     * <code>dest</code>.
     *
     * @param t0   the tangent of <code>this</code> vector
     * @param v1   the other vector
     * @param t1   the tangent of the other vector
     * @param t    the interpolation factor, within <code>[0..1]</code>
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f hermite(Vector3fc t0, Vector3fc v1, Vector3fc t1, float t, Vector3f dest) {
        return delegate.hermite(t0, v1, t1, t, dest);
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <code>0.0</code> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     *
     * @param other the other vector
     * @param t     the interpolation factor between 0.0 and 1.0
     * @param dest  will hold the result
     * @return dest
     */
    public Vector3f lerp(Vector3fc other, float t, Vector3f dest) {
        return delegate.lerp(other, t, dest);
    }

    /**
     * Get the value of the specified component of this vector.
     *
     * @param component the component, within <code>[0..2]</code>
     * @return the value
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..2]</code>
     */
    public float get(int component) {
        return delegate.get(component);
    }

    /**
     * Set the components of the given vector <code>dest</code> to those of <code>this</code> vector
     * using the given {@link RoundingMode}.
     *
     * @param mode the {@link RoundingMode} to use
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i get(int mode, Vector3i dest) {
        return delegate.get(mode, dest);
    }

    /**
     * Set the components of the given vector <code>dest</code> to those of <code>this</code> vector.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f get(Vector3f dest) {
        return delegate.get(dest);
    }

    /**
     * Set the components of the given vector <code>dest</code> to those of <code>this</code> vector.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3d get(Vector3d dest) {
        return delegate.get(dest);
    }

    /**
     * Determine the component with the biggest absolute value.
     *
     * @return the component index, within <code>[0..2]</code>
     */
    public int maxComponent() {
        return delegate.maxComponent();
    }

    /**
     * Determine the component with the smallest (towards zero) absolute value.
     *
     * @return the component index, within <code>[0..2]</code>
     */
    public int minComponent() {
        return delegate.minComponent();
    }

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given vector <code>v</code>, normalize the result and store it into <code>dest</code>.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     *
     * @param v    the reference vector which the result should be orthogonal to
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f orthogonalize(Vector3fc v, Vector3f dest) {
        return delegate.orthogonalize(v, dest);
    }

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given unit vector <code>v</code>, normalize the result and store it into <code>dest</code>.
     * <p>
     * The vector <code>v</code> is assumed to be a {@link #normalize(Vector3f) unit} vector.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     *
     * @param v    the reference unit vector which the result should be orthogonal to
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f orthogonalizeUnit(Vector3fc v, Vector3f dest) {
        return delegate.orthogonalizeUnit(v, dest);
    }

    /**
     * Compute for each component of this vector the largest (closest to positive
     * infinity) {@code float} value that is less than or equal to that
     * component and is equal to a mathematical integer and store the result in
     * <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f floor(Vector3f dest) {
        return delegate.floor(dest);
    }

    /**
     * Compute for each component of this vector the smallest (closest to negative
     * infinity) {@code float} value that is greater than or equal to that
     * component and is equal to a mathematical integer and store the result in
     * <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f ceil(Vector3f dest) {
        return delegate.ceil(dest);
    }

    /**
     * Compute for each component of this vector the closest float that is equal to
     * a mathematical integer, with ties rounding to positive infinity and store
     * the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f round(Vector3f dest) {
        return delegate.round(dest);
    }

    /**
     * Determine whether all components are finite floating-point values, that
     * is, they are not {@link Float#isNaN() NaN} and not
     * {@link Float#isInfinite() infinity}.
     *
     * @return {@code true} if all components are finite floating-point values;
     * {@code false} otherwise
     */
    public boolean isFinite() {
        return delegate.isFinite();
    }

    /**
     * Compare the vector components of <code>this</code> vector with the given vector using the given <code>delta</code>
     * and return whether all of them are equal within a maximum difference of <code>delta</code>.
     * <p>
     * Please note that this method is not used by any data structure such as {@link ArrayList} {@link HashSet} or {@link HashMap}
     * and their operations, such as {@link ArrayList#contains(Object)} or {@link HashSet#remove(Object)}, since those
     * data structures only use the {@link Object#equals(Object)} and {@link Object#hashCode()} methods.
     *
     * @param v     the other vector
     * @param delta the allowed maximum difference
     * @return <code>true</code> whether all of the vector components are equal; <code>false</code> otherwise
     */
    public boolean equals(Vector3fc v, float delta) {
        return delegate.equals(v, delta);
    }

    /**
     * Compare the vector components of <code>this</code> vector with the given <code>(x, y, z)</code>
     * and return whether all of them are equal.
     *
     * @param x the x component to compare to
     * @param y the y component to compare to
     * @param z the z component to compare to
     * @return <code>true</code> if all the vector components are equal
     */
    public boolean equals(float x, float y, float z) {
        return delegate.equals(x, y, z);
    }

}
