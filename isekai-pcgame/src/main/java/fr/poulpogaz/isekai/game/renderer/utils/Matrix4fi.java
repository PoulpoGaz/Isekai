package fr.poulpogaz.isekai.game.renderer.utils;

import org.joml.*;

import java.lang.Math;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Auto generated
 */
public class Matrix4fi implements Matrix4fc {

    private final Matrix4fc delegate;

    public Matrix4fi(Matrix4fc delegate) {
        this.delegate = new Matrix4f(delegate);
    }

    public Matrix4fi(Matrix4fc delegate, boolean clone) {
        if (clone) {
            this.delegate = new Matrix4f(delegate);
        } else {
            this.delegate = delegate;
        }
    }

    /**
     * Return the assumed properties of this matrix. This is a bit-combination of
     * {@link #PROPERTY_IDENTITY}, {@link #PROPERTY_AFFINE},
     * {@link #PROPERTY_TRANSLATION} and {@link #PROPERTY_PERSPECTIVE}.
     *
     * @return the properties of the matrix
     */
    public int properties() {
        return delegate.properties();
    }

    /**
     * Return the value of the matrix element at column 0 and row 0.
     *
     * @return the value of the matrix element
     */
    public float m00() {
        return delegate.m00();
    }

    /**
     * Return the value of the matrix element at column 0 and row 1.
     *
     * @return the value of the matrix element
     */
    public float m01() {
        return delegate.m01();
    }

    /**
     * Return the value of the matrix element at column 0 and row 2.
     *
     * @return the value of the matrix element
     */
    public float m02() {
        return delegate.m02();
    }

    /**
     * Return the value of the matrix element at column 0 and row 3.
     *
     * @return the value of the matrix element
     */
    public float m03() {
        return delegate.m03();
    }

    /**
     * Return the value of the matrix element at column 1 and row 0.
     *
     * @return the value of the matrix element
     */
    public float m10() {
        return delegate.m10();
    }

    /**
     * Return the value of the matrix element at column 1 and row 1.
     *
     * @return the value of the matrix element
     */
    public float m11() {
        return delegate.m11();
    }

    /**
     * Return the value of the matrix element at column 1 and row 2.
     *
     * @return the value of the matrix element
     */
    public float m12() {
        return delegate.m12();
    }

    /**
     * Return the value of the matrix element at column 1 and row 3.
     *
     * @return the value of the matrix element
     */
    public float m13() {
        return delegate.m13();
    }

    /**
     * Return the value of the matrix element at column 2 and row 0.
     *
     * @return the value of the matrix element
     */
    public float m20() {
        return delegate.m20();
    }

    /**
     * Return the value of the matrix element at column 2 and row 1.
     *
     * @return the value of the matrix element
     */
    public float m21() {
        return delegate.m21();
    }

    /**
     * Return the value of the matrix element at column 2 and row 2.
     *
     * @return the value of the matrix element
     */
    public float m22() {
        return delegate.m22();
    }

    /**
     * Return the value of the matrix element at column 2 and row 3.
     *
     * @return the value of the matrix element
     */
    public float m23() {
        return delegate.m23();
    }

    /**
     * Return the value of the matrix element at column 3 and row 0.
     *
     * @return the value of the matrix element
     */
    public float m30() {
        return delegate.m30();
    }

    /**
     * Return the value of the matrix element at column 3 and row 1.
     *
     * @return the value of the matrix element
     */
    public float m31() {
        return delegate.m31();
    }

    /**
     * Return the value of the matrix element at column 3 and row 2.
     *
     * @return the value of the matrix element
     */
    public float m32() {
        return delegate.m32();
    }

    /**
     * Return the value of the matrix element at column 3 and row 3.
     *
     * @return the value of the matrix element
     */
    public float m33() {
        return delegate.m33();
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mul(Matrix4fc right, Matrix4f dest) {
        return delegate.mul(right, dest);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * <p>
     * This method neither assumes nor checks for any matrix properties of <code>this</code> or <code>right</code>
     * and will always perform a complete 4x4 matrix multiplication. This method should only be used whenever the
     * multiplied matrices do not have any properties for which there are optimized multiplication methods available.
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mul0(Matrix4fc right, Matrix4f dest) {
        return delegate.mul0(right, dest);
    }

    /**
     * Multiply this matrix by the matrix with the supplied elements and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix whose
     * elements are supplied via the parameters, then the new matrix will be <code>M * R</code>.
     * So when transforming a vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param r00  the m00 element of the right matrix
     * @param r01  the m01 element of the right matrix
     * @param r02  the m02 element of the right matrix
     * @param r03  the m03 element of the right matrix
     * @param r10  the m10 element of the right matrix
     * @param r11  the m11 element of the right matrix
     * @param r12  the m12 element of the right matrix
     * @param r13  the m13 element of the right matrix
     * @param r20  the m20 element of the right matrix
     * @param r21  the m21 element of the right matrix
     * @param r22  the m22 element of the right matrix
     * @param r23  the m23 element of the right matrix
     * @param r30  the m30 element of the right matrix
     * @param r31  the m31 element of the right matrix
     * @param r32  the m32 element of the right matrix
     * @param r33  the m33 element of the right matrix
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mul(float r00, float r01, float r02, float r03, float r10, float r11, float r12, float r13, float r20, float r21, float r22, float r23, float r30, float r31, float r32, float r33, Matrix4f dest) {
        return delegate.mul(r00, r01, r02, r03, r10, r11, r12, r13, r20, r21, r22, r23, r30, r31, r32, r33, dest);
    }

    /**
     * Multiply this matrix by the 3x3 matrix with the supplied elements expanded to a 4x4 matrix with
     * all other matrix elements set to identity, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix whose
     * elements are supplied via the parameters, then the new matrix will be <code>M * R</code>.
     * So when transforming a vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param r00  the m00 element of the right matrix
     * @param r01  the m01 element of the right matrix
     * @param r02  the m02 element of the right matrix
     * @param r10  the m10 element of the right matrix
     * @param r11  the m11 element of the right matrix
     * @param r12  the m12 element of the right matrix
     * @param r20  the m20 element of the right matrix
     * @param r21  the m21 element of the right matrix
     * @param r22  the m22 element of the right matrix
     * @param dest the destination matrix, which will hold the result
     * @return this
     */
    public Matrix4f mul3x3(float r00, float r01, float r02, float r10, float r11, float r12, float r20, float r21, float r22, Matrix4f dest) {
        return delegate.mul3x3(r00, r01, r02, r10, r11, r12, r20, r21, r22, dest);
    }

    /**
     * Pre-multiply this matrix by the supplied <code>left</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the <code>left</code> matrix,
     * then the new matrix will be <code>L * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>L * M * v</code>, the
     * transformation of <code>this</code> matrix will be applied first!
     *
     * @param left the left operand of the matrix multiplication
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulLocal(Matrix4fc left, Matrix4f dest) {
        return delegate.mulLocal(left, dest);
    }

    /**
     * Pre-multiply this matrix by the supplied <code>left</code> matrix, both of which are assumed to be {@link #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix and the given <code>left</code> matrix both represent an {@link #isAffine() affine} transformation
     * (i.e. their last rows are equal to <code>(0, 0, 0, 1)</code>)
     * and can be used to speed up matrix multiplication if the matrices only represent affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * This method will not modify either the last row of <code>this</code> or the last row of <code>left</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the <code>left</code> matrix,
     * then the new matrix will be <code>L * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>L * M * v</code>, the
     * transformation of <code>this</code> matrix will be applied first!
     *
     * @param left the left operand of the matrix multiplication (the last row is assumed to be <code>(0, 0, 0, 1)</code>)
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulLocalAffine(Matrix4fc left, Matrix4f dest) {
        return delegate.mulLocalAffine(left, dest);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mul(Matrix3x2fc right, Matrix4f dest) {
        return delegate.mul(right, dest);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * The last row of the <code>right</code> matrix is assumed to be <code>(0, 0, 0, 1)</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mul(Matrix4x3fc right, Matrix4f dest) {
        return delegate.mul(right, dest);
    }

    /**
     * Multiply <code>this</code> symmetric perspective projection matrix by the supplied {@link #isAffine() affine} <code>view</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>P</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix,
     * then the new matrix will be <code>P * V</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>P * V * v</code>, the
     * transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view the {@link #isAffine() affine} matrix to multiply <code>this</code> symmetric perspective projection matrix by
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulPerspectiveAffine(Matrix4fc view, Matrix4f dest) {
        return delegate.mulPerspectiveAffine(view, dest);
    }

    /**
     * Multiply <code>this</code> symmetric perspective projection matrix by the supplied <code>view</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>P</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix,
     * then the new matrix will be <code>P * V</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>P * V * v</code>, the
     * transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view the matrix to multiply <code>this</code> symmetric perspective projection matrix by
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulPerspectiveAffine(Matrix4x3fc view, Matrix4f dest) {
        return delegate.mulPerspectiveAffine(view, dest);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix, which is assumed to be {@link #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that the given <code>right</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication (the last row is assumed to be <code>(0, 0, 0, 1)</code>)
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulAffineR(Matrix4fc right, Matrix4f dest) {
        return delegate.mulAffineR(right, dest);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix, both of which are assumed to be {@link #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix and the given <code>right</code> matrix both represent an {@link #isAffine() affine} transformation
     * (i.e. their last rows are equal to <code>(0, 0, 0, 1)</code>)
     * and can be used to speed up matrix multiplication if the matrices only represent affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * This method will not modify either the last row of <code>this</code> or the last row of <code>right</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication (the last row is assumed to be <code>(0, 0, 0, 1)</code>)
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulAffine(Matrix4fc right, Matrix4f dest) {
        return delegate.mulAffine(right, dest);
    }

    /**
     * Multiply this matrix, which is assumed to only contain a translation, by the supplied <code>right</code> matrix, which is assumed to be {@link #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix only contains a translation, and that the given <code>right</code> matrix represents an {@link #isAffine() affine} transformation
     * (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>).
     * <p>
     * This method will not modify either the last row of <code>this</code> or the last row of <code>right</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication (the last row is assumed to be <code>(0, 0, 0, 1)</code>)
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulTranslationAffine(Matrix4fc right, Matrix4f dest) {
        return delegate.mulTranslationAffine(right, dest);
    }

    /**
     * Multiply <code>this</code> orthographic projection matrix by the supplied {@link #isAffine() affine} <code>view</code> matrix
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix,
     * then the new matrix will be <code>M * V</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * V * v</code>, the
     * transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view the affine matrix which to multiply <code>this</code> with
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulOrthoAffine(Matrix4fc view, Matrix4f dest) {
        return delegate.mulOrthoAffine(view, dest);
    }

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code>
     * by first multiplying each component of <code>other</code>'s 4x3 submatrix by <code>otherFactor</code>,
     * adding that to <code>this</code> and storing the final result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     * <p>
     * The matrices <code>this</code> and <code>other</code> will not be changed.
     *
     * @param other       the other matrix
     * @param otherFactor the factor to multiply each of the other matrix's 4x3 components
     * @param dest        will hold the result
     * @return dest
     */
    public Matrix4f fma4x3(Matrix4fc other, float otherFactor, Matrix4f dest) {
        return delegate.fma4x3(other, otherFactor, dest);
    }

    /**
     * Component-wise add <code>this</code> and <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other addend
     * @param dest  will hold the result
     * @return dest
     */
    public Matrix4f add(Matrix4fc other, Matrix4f dest) {
        return delegate.add(other, dest);
    }

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code> and store the result in <code>dest</code>.
     *
     * @param subtrahend the subtrahend
     * @param dest       will hold the result
     * @return dest
     */
    public Matrix4f sub(Matrix4fc subtrahend, Matrix4f dest) {
        return delegate.sub(subtrahend, dest);
    }

    /**
     * Component-wise multiply <code>this</code> by <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other matrix
     * @param dest  will hold the result
     * @return dest
     */
    public Matrix4f mulComponentWise(Matrix4fc other, Matrix4f dest) {
        return delegate.mulComponentWise(other, dest);
    }

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code>
     * and store the result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     *
     * @param other the other addend
     * @param dest  will hold the result
     * @return dest
     */
    public Matrix4f add4x3(Matrix4fc other, Matrix4f dest) {
        return delegate.add4x3(other, dest);
    }

    /**
     * Component-wise subtract the upper 4x3 submatrices of <code>subtrahend</code> from <code>this</code>
     * and store the result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     *
     * @param subtrahend the subtrahend
     * @param dest       will hold the result
     * @return dest
     */
    public Matrix4f sub4x3(Matrix4fc subtrahend, Matrix4f dest) {
        return delegate.sub4x3(subtrahend, dest);
    }

    /**
     * Component-wise multiply the upper 4x3 submatrices of <code>this</code> by <code>other</code>
     * and store the result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     *
     * @param other the other matrix
     * @param dest  will hold the result
     * @return dest
     */
    public Matrix4f mul4x3ComponentWise(Matrix4fc other, Matrix4f dest) {
        return delegate.mul4x3ComponentWise(other, dest);
    }

    /**
     * Return the determinant of this matrix.
     * <p>
     * If <code>this</code> matrix represents an {@link #isAffine() affine} transformation, such as translation, rotation, scaling and shearing,
     * and thus its last row is equal to <code>(0, 0, 0, 1)</code>, then {@link #determinantAffine()} can be used instead of this method.
     *
     * @return the determinant
     * @see #determinantAffine()
     */
    public float determinant() {
        return delegate.determinant();
    }

    /**
     * Return the determinant of the upper left 3x3 submatrix of this matrix.
     *
     * @return the determinant
     */
    public float determinant3x3() {
        return delegate.determinant3x3();
    }

    /**
     * Return the determinant of this matrix by assuming that it represents an {@link #isAffine() affine} transformation and thus
     * its last row is equal to <code>(0, 0, 0, 1)</code>.
     *
     * @return the determinant
     */
    public float determinantAffine() {
        return delegate.determinantAffine();
    }

    /**
     * Invert this matrix and write the result into <code>dest</code>.
     * <p>
     * If <code>this</code> matrix represents an {@link #isAffine() affine} transformation, such as translation, rotation, scaling and shearing,
     * and thus its last row is equal to <code>(0, 0, 0, 1)</code>, then {@link #invertAffine(Matrix4f)} can be used instead of this method.
     *
     * @param dest will hold the result
     * @return dest
     * @see #invertAffine(Matrix4f)
     */
    public Matrix4f invert(Matrix4f dest) {
        return delegate.invert(dest);
    }

    /**
     * If <code>this</code> is a perspective projection matrix obtained via one of the {@link #perspective(float, float, float, float, Matrix4f) perspective()} methods,
     * that is, if <code>this</code> is a symmetrical perspective frustum transformation,
     * then this method builds the inverse of <code>this</code> and stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of a perspective projection matrix when being obtained via {@link #perspective(float, float, float, float, Matrix4f) perspective()}.
     *
     * @param dest will hold the inverse of <code>this</code>
     * @return dest
     * @see #perspective(float, float, float, float, Matrix4f)
     */
    public Matrix4f invertPerspective(Matrix4f dest) {
        return delegate.invertPerspective(dest);
    }

    /**
     * If <code>this</code> is an arbitrary perspective projection matrix obtained via one of the {@link #frustum(float, float, float, float, float, float, Matrix4f) frustum()} methods,
     * then this method builds the inverse of <code>this</code> and stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of a perspective projection matrix.
     * <p>
     * If this matrix represents a symmetric perspective frustum transformation, as obtained via {@link #perspective(float, float, float, float, Matrix4f) perspective()}, then
     * {@link #invertPerspective(Matrix4f)} should be used instead.
     *
     * @param dest will hold the inverse of <code>this</code>
     * @return dest
     * @see #frustum(float, float, float, float, float, float, Matrix4f)
     * @see #invertPerspective(Matrix4f)
     */
    public Matrix4f invertFrustum(Matrix4f dest) {
        return delegate.invertFrustum(dest);
    }

    /**
     * Invert <code>this</code> orthographic projection matrix and store the result into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of an orthographic projection matrix.
     *
     * @param dest will hold the inverse of <code>this</code>
     * @return dest
     */
    public Matrix4f invertOrtho(Matrix4f dest) {
        return delegate.invertOrtho(dest);
    }

    /**
     * If <code>this</code> is a perspective projection matrix obtained via one of the {@link #perspective(float, float, float, float, Matrix4f) perspective()} methods,
     * that is, if <code>this</code> is a symmetrical perspective frustum transformation
     * and the given <code>view</code> matrix is {@link #isAffine() affine} and has unit scaling (for example by being obtained via {@link #lookAt(float, float, float, float, float, float, float, float, float, Matrix4f) lookAt()}),
     * then this method builds the inverse of <code>this * view</code> and stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of the combination of the view and projection matrices, when both were obtained
     * via the common methods {@link #perspective(float, float, float, float, Matrix4f) perspective()} and {@link #lookAt(float, float, float, float, float, float, float, float, float, Matrix4f) lookAt()} or
     * other methods, that build affine matrices, such as {@link #translate(float, float, float, Matrix4f) translate} and {@link #rotate(float, float, float, float, Matrix4f)}, except for {@link #scale(float, float, float, Matrix4f) scale()}.
     * <p>
     * For the special cases of the matrices <code>this</code> and <code>view</code> mentioned above, this method is equivalent to the following code:
     * <pre>
     * dest.set(this).mul(view).invert();
     * </pre>
     *
     * @param view the view transformation (must be {@link #isAffine() affine} and have unit scaling)
     * @param dest will hold the inverse of <code>this * view</code>
     * @return dest
     */
    public Matrix4f invertPerspectiveView(Matrix4fc view, Matrix4f dest) {
        return delegate.invertPerspectiveView(view, dest);
    }

    /**
     * If <code>this</code> is a perspective projection matrix obtained via one of the {@link #perspective(float, float, float, float, Matrix4f) perspective()} methods,
     * that is, if <code>this</code> is a symmetrical perspective frustum transformation
     * and the given <code>view</code> matrix has unit scaling,
     * then this method builds the inverse of <code>this * view</code> and stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of the combination of the view and projection matrices, when both were obtained
     * via the common methods {@link #perspective(float, float, float, float, Matrix4f) perspective()} and {@link #lookAt(float, float, float, float, float, float, float, float, float, Matrix4f) lookAt()} or
     * other methods, that build affine matrices, such as {@link #translate(float, float, float, Matrix4f) translate} and {@link #rotate(float, float, float, float, Matrix4f)}, except for {@link #scale(float, float, float, Matrix4f) scale()}.
     * <p>
     * For the special cases of the matrices <code>this</code> and <code>view</code> mentioned above, this method is equivalent to the following code:
     * <pre>
     * dest.set(this).mul(view).invert();
     * </pre>
     *
     * @param view the view transformation (must have unit scaling)
     * @param dest will hold the inverse of <code>this * view</code>
     * @return dest
     */
    public Matrix4f invertPerspectiveView(Matrix4x3fc view, Matrix4f dest) {
        return delegate.invertPerspectiveView(view, dest);
    }

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>)
     * and write the result into <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f invertAffine(Matrix4f dest) {
        return delegate.invertAffine(dest);
    }

    /**
     * Transpose this matrix and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f transpose(Matrix4f dest) {
        return delegate.transpose(dest);
    }

    /**
     * Transpose only the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * All other matrix elements are left unchanged.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f transpose3x3(Matrix4f dest) {
        return delegate.transpose3x3(dest);
    }

    /**
     * Transpose only the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix3f transpose3x3(Matrix3f dest) {
        return delegate.transpose3x3(dest);
    }

    /**
     * Get only the translation components <code>(m30, m31, m32)</code> of this matrix and store them in the given vector <code>xyz</code>.
     *
     * @param dest will hold the translation components of this matrix
     * @return dest
     */
    public Vector3f getTranslation(Vector3f dest) {
        return delegate.getTranslation(dest);
    }

    /**
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     *
     * @param dest will hold the scaling factors for <code>x</code>, <code>y</code> and <code>z</code>
     * @return dest
     */
    public Vector3f getScale(Vector3f dest) {
        return delegate.getScale(dest);
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    public Matrix4f get(Matrix4f dest) {
        return delegate.get(dest);
    }

    /**
     * Get the current values of the upper 4x3 submatrix of <code>this</code> matrix and store them into
     * <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     * @see Matrix4x3f#set(Matrix4fc)
     */
    public Matrix4x3f get4x3(Matrix4x3f dest) {
        return delegate.get4x3(dest);
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    public Matrix4d get(Matrix4d dest) {
        return delegate.get(dest);
    }

    /**
     * Get the current values of the upper left 3x3 submatrix of <code>this</code> matrix and store them into
     * <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     * @see Matrix3f#set(Matrix4fc)
     */
    public Matrix3f get3x3(Matrix3f dest) {
        return delegate.get3x3(dest);
    }

    /**
     * Get the current values of the upper left 3x3 submatrix of <code>this</code> matrix and store them into
     * <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     * @see Matrix3d#set(Matrix4fc)
     */
    public Matrix3d get3x3(Matrix3d dest) {
        return delegate.get3x3(dest);
    }

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation
     * into the given {@link AxisAngle4f}.
     *
     * @param dest the destination {@link AxisAngle4f}
     * @return the passed in destination
     * @see AxisAngle4f#set(Matrix4fc)
     */
    public AxisAngle4f getRotation(AxisAngle4f dest) {
        return delegate.getRotation(dest);
    }

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation
     * into the given {@link AxisAngle4d}.
     *
     * @param dest the destination {@link AxisAngle4d}
     * @return the passed in destination
     * @see AxisAngle4f#set(Matrix4fc)
     */
    public AxisAngle4d getRotation(AxisAngle4d dest) {
        return delegate.getRotation(dest);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaternionf}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     *
     * @param dest the destination {@link Quaternionf}
     * @return the passed in destination
     * @see Quaternionf#setFromUnnormalized(Matrix4fc)
     */
    public Quaternionf getUnnormalizedRotation(Quaternionf dest) {
        return delegate.getUnnormalizedRotation(dest);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaternionf}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are normalized.
     *
     * @param dest the destination {@link Quaternionf}
     * @return the passed in destination
     * @see Quaternionf#setFromNormalized(Matrix4fc)
     */
    public Quaternionf getNormalizedRotation(Quaternionf dest) {
        return delegate.getNormalizedRotation(dest);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaterniond}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     *
     * @param dest the destination {@link Quaterniond}
     * @return the passed in destination
     * @see Quaterniond#setFromUnnormalized(Matrix4fc)
     */
    public Quaterniond getUnnormalizedRotation(Quaterniond dest) {
        return delegate.getUnnormalizedRotation(dest);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaterniond}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are normalized.
     *
     * @param dest the destination {@link Quaterniond}
     * @return the passed in destination
     * @see Quaterniond#setFromNormalized(Matrix4fc)
     */
    public Quaterniond getNormalizedRotation(Quaterniond dest) {
        return delegate.getNormalizedRotation(dest);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     * @see #get(int, FloatBuffer)
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return delegate.get(buffer);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        return delegate.get(index, buffer);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     * @see #get(int, ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return delegate.get(buffer);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        return delegate.get(index, buffer);
    }

    /**
     * Store the upper 4x3 submatrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of the upper 4x3 submatrix in column-major order at its current position
     * @return the passed in buffer
     * @see #get(int, FloatBuffer)
     */
    public FloatBuffer get4x3(FloatBuffer buffer) {
        return delegate.get4x3(buffer);
    }

    /**
     * Store the upper 4x3 submatrix in column-major order into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of the upper 4x3 submatrix in column-major order
     * @return the passed in buffer
     */
    public FloatBuffer get4x3(int index, FloatBuffer buffer) {
        return delegate.get4x3(index, buffer);
    }

    /**
     * Store the upper 4x3 submatrix in column-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of the upper 4x3 submatrix in column-major order at its current position
     * @return the passed in buffer
     * @see #get(int, ByteBuffer)
     */
    public ByteBuffer get4x3(ByteBuffer buffer) {
        return delegate.get4x3(buffer);
    }

    /**
     * Store the upper 4x3 submatrix in column-major order into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of the upper 4x3 submatrix in column-major order
     * @return the passed in buffer
     */
    public ByteBuffer get4x3(int index, ByteBuffer buffer) {
        return delegate.get4x3(index, buffer);
    }

    /**
     * Store the left 3x4 submatrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #get3x4(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of the left 3x4 submatrix in column-major order at its current position
     * @return the passed in buffer
     * @see #get3x4(int, FloatBuffer)
     */
    public FloatBuffer get3x4(FloatBuffer buffer) {
        return delegate.get3x4(buffer);
    }

    /**
     * Store the left 3x4 submatrix in column-major order into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of the left 3x4 submatrix in column-major order
     * @return the passed in buffer
     */
    public FloatBuffer get3x4(int index, FloatBuffer buffer) {
        return delegate.get3x4(index, buffer);
    }

    /**
     * Store the left 3x4 submatrix in column-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #get3x4(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of the left 3x4 submatrix in column-major order at its current position
     * @return the passed in buffer
     * @see #get3x4(int, ByteBuffer)
     */
    public ByteBuffer get3x4(ByteBuffer buffer) {
        return delegate.get3x4(buffer);
    }

    /**
     * Store the left 3x4 submatrix in column-major order into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of the left 3x4 submatrix in column-major order
     * @return the passed in buffer
     */
    public ByteBuffer get3x4(int index, ByteBuffer buffer) {
        return delegate.get3x4(index, buffer);
    }

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #getTransposed(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     * @see #getTransposed(int, FloatBuffer)
     */
    public FloatBuffer getTransposed(FloatBuffer buffer) {
        return delegate.getTransposed(buffer);
    }

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public FloatBuffer getTransposed(int index, FloatBuffer buffer) {
        return delegate.getTransposed(index, buffer);
    }

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #getTransposed(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     * @see #getTransposed(int, ByteBuffer)
     */
    public ByteBuffer getTransposed(ByteBuffer buffer) {
        return delegate.getTransposed(buffer);
    }

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public ByteBuffer getTransposed(int index, ByteBuffer buffer) {
        return delegate.getTransposed(index, buffer);
    }

    /**
     * Store the upper 4x3 submatrix of <code>this</code> matrix in row-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #get4x3Transposed(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of the upper 4x3 submatrix in row-major order at its current position
     * @return the passed in buffer
     * @see #get4x3Transposed(int, FloatBuffer)
     */
    public FloatBuffer get4x3Transposed(FloatBuffer buffer) {
        return delegate.get4x3Transposed(buffer);
    }

    /**
     * Store the upper 4x3 submatrix of <code>this</code> matrix in row-major order into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of the upper 4x3 submatrix in row-major order
     * @return the passed in buffer
     */
    public FloatBuffer get4x3Transposed(int index, FloatBuffer buffer) {
        return delegate.get4x3Transposed(index, buffer);
    }

    /**
     * Store the upper 4x3 submatrix of <code>this</code> matrix in row-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #get4x3Transposed(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of the upper 4x3 submatrix in row-major order at its current position
     * @return the passed in buffer
     * @see #get4x3Transposed(int, ByteBuffer)
     */
    public ByteBuffer get4x3Transposed(ByteBuffer buffer) {
        return delegate.get4x3Transposed(buffer);
    }

    /**
     * Store the upper 4x3 submatrix of <code>this</code> matrix in row-major order into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of the upper 4x3 submatrix in row-major order
     * @return the passed in buffer
     */
    public ByteBuffer get4x3Transposed(int index, ByteBuffer buffer) {
        return delegate.get4x3Transposed(index, buffer);
    }

    /**
     * Store this matrix in column-major order at the given off-heap address.
     * <p>
     * This method will throw an {@link UnsupportedOperationException} when JOML is used with `-Djoml.nounsafe`.
     * <p>
     * <em>This method is unsafe as it can result in a crash of the JVM process when the specified address range does not belong to this process.</em>
     *
     * @param address the off-heap address where to store this matrix
     * @return this
     */
    public Matrix4fc getToAddress(long address) {
        return delegate.getToAddress(address);
    }

    /**
     * Store this matrix into the supplied float array in column-major order at the given offset.
     *
     * @param arr    the array to write the matrix values into
     * @param offset the offset into the array
     * @return the passed in array
     */
    public float[] get(float[] arr, int offset) {
        return delegate.get(arr, offset);
    }

    /**
     * Store this matrix into the supplied float array in column-major order.
     * <p>
     * In order to specify an explicit offset into the array, use the method {@link #get(float[], int)}.
     *
     * @param arr the array to write the matrix values into
     * @return the passed in array
     * @see #get(float[], int)
     */
    public float[] get(float[] arr) {
        return delegate.get(arr);
    }

    /**
     * Transform/multiply the given vector by this matrix and store the result in that vector.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     * @see Vector4f#mul(Matrix4fc)
     */
    public Vector4f transform(Vector4f v) {
        return delegate.transform(v);
    }

    /**
     * Transform/multiply the given vector by this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     * @see Vector4f#mul(Matrix4fc, Vector4f)
     */
    public Vector4f transform(Vector4fc v, Vector4f dest) {
        return delegate.transform(v, dest);
    }

    /**
     * Transform/multiply the vector <code>(x, y, z, w)</code> by this matrix and store the result in <code>dest</code>.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param w    the w coordinate of the vector to transform
     * @param dest will contain the result
     * @return dest
     */
    public Vector4f transform(float x, float y, float z, float w, Vector4f dest) {
        return delegate.transform(x, y, z, w, dest);
    }

    /**
     * Transform/multiply the given vector by the transpose of this matrix and store the result in that vector.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     * @see Vector4f#mulTranspose(Matrix4fc)
     */
    public Vector4f transformTranspose(Vector4f v) {
        return delegate.transformTranspose(v);
    }

    /**
     * Transform/multiply the given vector by the transpose of this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     * @see Vector4f#mulTranspose(Matrix4fc, Vector4f)
     */
    public Vector4f transformTranspose(Vector4fc v, Vector4f dest) {
        return delegate.transformTranspose(v, dest);
    }

    /**
     * Transform/multiply the vector <code>(x, y, z, w)</code> by the transpose of this matrix and store the result in <code>dest</code>.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param w    the w coordinate of the vector to transform
     * @param dest will contain the result
     * @return dest
     */
    public Vector4f transformTranspose(float x, float y, float z, float w, Vector4f dest) {
        return delegate.transformTranspose(x, y, z, w, dest);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in that vector.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     * @see Vector4f#mulProject(Matrix4fc)
     */
    public Vector4f transformProject(Vector4f v) {
        return delegate.transformProject(v);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     * @see Vector4f#mulProject(Matrix4fc, Vector4f)
     */
    public Vector4f transformProject(Vector4fc v, Vector4f dest) {
        return delegate.transformProject(v, dest);
    }

    /**
     * Transform/multiply the vector <code>(x, y, z, w)</code> by this matrix, perform perspective divide and store the result in <code>dest</code>.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param w    the w coordinate of the vector to transform
     * @param dest will contain the result
     * @return dest
     */
    public Vector4f transformProject(float x, float y, float z, float w, Vector4f dest) {
        return delegate.transformProject(x, y, z, w, dest);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in that vector.
     * <p>
     * This method uses <code>w=1.0</code> as the fourth vector component.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     * @see Vector3f#mulProject(Matrix4fc)
     */
    public Vector3f transformProject(Vector3f v) {
        return delegate.transformProject(v);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in <code>dest</code>.
     * <p>
     * This method uses <code>w=1.0</code> as the fourth vector component.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     * @see Vector3f#mulProject(Matrix4fc, Vector3f)
     */
    public Vector3f transformProject(Vector3fc v, Vector3f dest) {
        return delegate.transformProject(v, dest);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the <code>(x, y, z)</code> components of the result
     * @return dest
     * @see Vector4f#mulProject(Matrix4fc, Vector4f)
     */
    public Vector3f transformProject(Vector4fc v, Vector3f dest) {
        return delegate.transformProject(v, dest);
    }

    /**
     * Transform/multiply the vector <code>(x, y, z)</code> by this matrix, perform perspective divide and store the result in <code>dest</code>.
     * <p>
     * This method uses <code>w=1.0</code> as the fourth vector component.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param dest will contain the result
     * @return dest
     */
    public Vector3f transformProject(float x, float y, float z, Vector3f dest) {
        return delegate.transformProject(x, y, z, dest);
    }

    /**
     * Transform/multiply the vector <code>(x, y, z, w)</code> by this matrix, perform perspective divide and store
     * <code>(x, y, z)</code> of the result in <code>dest</code>.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param w    the w coordinate of the vector to transform
     * @param dest will contain the <code>(x, y, z)</code> components of the result
     * @return dest
     */
    public Vector3f transformProject(float x, float y, float z, float w, Vector3f dest) {
        return delegate.transformProject(x, y, z, w, dest);
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by
     * this matrix and store the result in that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it
     * will represent a position/location in 3D-space rather than a direction. This method is therefore
     * not suited for perspective projection transformations as it will not save the
     * <code>w</code> component of the transformed vector.
     * For perspective projection use {@link #transform(Vector4f)} or {@link #transformProject(Vector3f)}
     * when perspective divide should be applied, too.
     * <p>
     * In order to store the result in another vector, use {@link #transformPosition(Vector3fc, Vector3f)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     * @see #transformPosition(Vector3fc, Vector3f)
     * @see #transform(Vector4f)
     * @see #transformProject(Vector3f)
     */
    public Vector3f transformPosition(Vector3f v) {
        return delegate.transformPosition(v);
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by
     * this matrix and store the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it
     * will represent a position/location in 3D-space rather than a direction. This method is therefore
     * not suited for perspective projection transformations as it will not save the
     * <code>w</code> component of the transformed vector.
     * For perspective projection use {@link #transform(Vector4fc, Vector4f)} or
     * {@link #transformProject(Vector3fc, Vector3f)} when perspective divide should be applied, too.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPosition(Vector3f)}.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     * @see #transformPosition(Vector3f)
     * @see #transform(Vector4fc, Vector4f)
     * @see #transformProject(Vector3fc, Vector3f)
     */
    public Vector3f transformPosition(Vector3fc v, Vector3f dest) {
        return delegate.transformPosition(v, dest);
    }

    /**
     * Transform/multiply the 3D-vector <code>(x, y, z)</code>, as if it was a 4D-vector with w=1, by
     * this matrix and store the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it
     * will represent a position/location in 3D-space rather than a direction. This method is therefore
     * not suited for perspective projection transformations as it will not save the
     * <code>w</code> component of the transformed vector.
     * For perspective projection use {@link #transform(float, float, float, float, Vector4f)} or
     * {@link #transformProject(float, float, float, Vector3f)} when perspective divide should be applied, too.
     *
     * @param x    the x coordinate of the position
     * @param y    the y coordinate of the position
     * @param z    the z coordinate of the position
     * @param dest will hold the result
     * @return dest
     * @see #transform(float, float, float, float, Vector4f)
     * @see #transformProject(float, float, float, Vector3f)
     */
    public Vector3f transformPosition(float x, float y, float z, Vector3f dest) {
        return delegate.transformPosition(x, y, z, dest);
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by
     * this matrix and store the result in that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <code>0.0</code>, so it
     * will represent a direction in 3D-space rather than a position. This method will therefore
     * not take the translation part of the matrix into account.
     * <p>
     * In order to store the result in another vector, use {@link #transformDirection(Vector3fc, Vector3f)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     * @see #transformDirection(Vector3fc, Vector3f)
     */
    public Vector3f transformDirection(Vector3f v) {
        return delegate.transformDirection(v);
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by
     * this matrix and store the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <code>0.0</code>, so it
     * will represent a direction in 3D-space rather than a position. This method will therefore
     * not take the translation part of the matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector3f)}.
     *
     * @param v    the vector to transform and to hold the final result
     * @param dest will hold the result
     * @return dest
     * @see #transformDirection(Vector3f)
     */
    public Vector3f transformDirection(Vector3fc v, Vector3f dest) {
        return delegate.transformDirection(v, dest);
    }

    /**
     * Transform/multiply the given 3D-vector <code>(x, y, z)</code>, as if it was a 4D-vector with w=0, by
     * this matrix and store the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <code>0.0</code>, so it
     * will represent a direction in 3D-space rather than a position. This method will therefore
     * not take the translation part of the matrix into account.
     *
     * @param x    the x coordinate of the direction to transform
     * @param y    the y coordinate of the direction to transform
     * @param z    the z coordinate of the direction to transform
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f transformDirection(float x, float y, float z, Vector3f dest) {
        return delegate.transformDirection(x, y, z, dest);
    }

    /**
     * Transform/multiply the given 4D-vector by assuming that <code>this</code> matrix represents an {@link #isAffine() affine} transformation
     * (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>).
     * <p>
     * In order to store the result in another vector, use {@link #transformAffine(Vector4fc, Vector4f)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     * @see #transformAffine(Vector4fc, Vector4f)
     */
    public Vector4f transformAffine(Vector4f v) {
        return delegate.transformAffine(v);
    }

    /**
     * Transform/multiply the given 4D-vector by assuming that <code>this</code> matrix represents an {@link #isAffine() affine} transformation
     * (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>) and store the result in <code>dest</code>.
     * <p>
     * In order to store the result in the same vector, use {@link #transformAffine(Vector4f)}.
     *
     * @param v    the vector to transform and to hold the final result
     * @param dest will hold the result
     * @return dest
     * @see #transformAffine(Vector4f)
     */
    public Vector4f transformAffine(Vector4fc v, Vector4f dest) {
        return delegate.transformAffine(v, dest);
    }

    /**
     * Transform/multiply the 4D-vector <code>(x, y, z, w)</code> by assuming that <code>this</code> matrix represents an {@link #isAffine() affine} transformation
     * (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>) and store the result in <code>dest</code>.
     *
     * @param x    the x coordinate of the direction to transform
     * @param y    the y coordinate of the direction to transform
     * @param z    the z coordinate of the direction to transform
     * @param w    the w coordinate of the direction to transform
     * @param dest will hold the result
     * @return dest
     */
    public Vector4f transformAffine(float x, float y, float z, float w, Vector4f dest) {
        return delegate.transformAffine(x, y, z, w, dest);
    }

    /**
     * Apply scaling to <code>this</code> matrix by scaling the base axes by the given <code>xyz.x</code>,
     * <code>xyz.y</code> and <code>xyz.z</code> factors, respectively and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     *
     * @param xyz  the factors of the x, y and z component, respectively
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f scale(Vector3fc xyz, Matrix4f dest) {
        return delegate.scale(xyz, dest);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * scaling will be applied first!
     * <p>
     * Individual scaling of all three axes can be applied using {@link #scale(float, float, float, Matrix4f)}.
     *
     * @param xyz  the factor for all components
     * @param dest will hold the result
     * @return dest
     * @see #scale(float, float, float, Matrix4f)
     */
    public Matrix4f scale(float xyz, Matrix4f dest) {
        return delegate.scale(xyz, dest);
    }

    /**
     * Apply scaling to this matrix by by scaling the X axis by <code>x</code> and the Y axis by <code>y</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * scaling will be applied first!
     *
     * @param x    the factor of the x component
     * @param y    the factor of the y component
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f scaleXY(float x, float y, Matrix4f dest) {
        return delegate.scaleXY(x, y, dest);
    }

    /**
     * Apply scaling to <code>this</code> matrix by scaling the base axes by the given x,
     * y and z factors and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     *
     * @param x    the factor of the x component
     * @param y    the factor of the y component
     * @param z    the factor of the z component
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f scale(float x, float y, float z, Matrix4f dest) {
        return delegate.scale(x, y, z, dest);
    }

    /**
     * Apply scaling to <code>this</code> matrix by scaling the base axes by the given sx,
     * sy and sz factors while using <code>(ox, oy, oz)</code> as the scaling origin,
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * <p>
     * This method is equivalent to calling: <code>translate(ox, oy, oz, dest).scale(sx, sy, sz).translate(-ox, -oy, -oz)</code>
     *
     * @param sx   the scaling factor of the x component
     * @param sy   the scaling factor of the y component
     * @param sz   the scaling factor of the z component
     * @param ox   the x coordinate of the scaling origin
     * @param oy   the y coordinate of the scaling origin
     * @param oz   the z coordinate of the scaling origin
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f scaleAround(float sx, float sy, float sz, float ox, float oy, float oz, Matrix4f dest) {
        return delegate.scaleAround(sx, sy, sz, ox, oy, oz, dest);
    }

    /**
     * Apply scaling to this matrix by scaling all three base axes by the given <code>factor</code>
     * while using <code>(ox, oy, oz)</code> as the scaling origin,
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * scaling will be applied first!
     * <p>
     * This method is equivalent to calling: <code>translate(ox, oy, oz, dest).scale(factor).translate(-ox, -oy, -oz)</code>
     *
     * @param factor the scaling factor for all three axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @param oz     the z coordinate of the scaling origin
     * @param dest   will hold the result
     * @return this
     */
    public Matrix4f scaleAround(float factor, float ox, float oy, float oz, Matrix4f dest) {
        return delegate.scaleAround(factor, ox, oy, oz, dest);
    }

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling all base axes by the given <code>xyz</code> factor,
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>S * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>S * M * v</code>
     * , the scaling will be applied last!
     *
     * @param xyz  the factor to scale all three base axes by
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f scaleLocal(float xyz, Matrix4f dest) {
        return delegate.scaleLocal(xyz, dest);
    }

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling the base axes by the given x,
     * y and z factors and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>S * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>S * M * v</code>
     * , the scaling will be applied last!
     *
     * @param x    the factor of the x component
     * @param y    the factor of the y component
     * @param z    the factor of the z component
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f scaleLocal(float x, float y, float z, Matrix4f dest) {
        return delegate.scaleLocal(x, y, z, dest);
    }

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling the base axes by the given sx,
     * sy and sz factors while using the given <code>(ox, oy, oz)</code> as the scaling origin,
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>S * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>S * M * v</code>
     * , the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <code>new Matrix4f().translate(ox, oy, oz).scale(sx, sy, sz).translate(-ox, -oy, -oz).mul(this, dest)</code>
     *
     * @param sx   the scaling factor of the x component
     * @param sy   the scaling factor of the y component
     * @param sz   the scaling factor of the z component
     * @param ox   the x coordinate of the scaling origin
     * @param oy   the y coordinate of the scaling origin
     * @param oz   the z coordinate of the scaling origin
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f scaleAroundLocal(float sx, float sy, float sz, float ox, float oy, float oz, Matrix4f dest) {
        return delegate.scaleAroundLocal(sx, sy, sz, ox, oy, oz, dest);
    }

    /**
     * Pre-multiply scaling to this matrix by scaling all three base axes by the given <code>factor</code>
     * while using <code>(ox, oy, oz)</code> as the scaling origin,
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>S * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>S * M * v</code>, the
     * scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <code>new Matrix4f().translate(ox, oy, oz).scale(factor).translate(-ox, -oy, -oz).mul(this, dest)</code>
     *
     * @param factor the scaling factor for all three axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @param oz     the z coordinate of the scaling origin
     * @param dest   will hold the result
     * @return this
     */
    public Matrix4f scaleAroundLocal(float factor, float ox, float oy, float oz, Matrix4f dest) {
        return delegate.scaleAroundLocal(factor, ox, oy, oz, dest);
    }

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of radians
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateX(float ang, Matrix4f dest) {
        return delegate.rotateX(ang, dest);
    }

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of radians
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateY(float ang, Matrix4f dest) {
        return delegate.rotateY(ang, dest);
    }

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of radians
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateZ(float ang, Matrix4f dest) {
        return delegate.rotateZ(ang, dest);
    }

    /**
     * Apply rotation about the Z axis to align the local <code>+X</code> towards <code>(dirX, dirY)</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * The vector <code>(dirX, dirY)</code> must be a unit vector.
     *
     * @param dirX the x component of the normalized direction
     * @param dirY the y component of the normalized direction
     * @param dest will hold the result
     * @return this
     */
    public Matrix4f rotateTowardsXY(float dirX, float dirY, Matrix4f dest) {
        return delegate.rotateTowardsXY(dirX, dirY, dest);
    }

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <code>rotateX(angleX, dest).rotateY(angleY).rotateZ(angleZ)</code>
     *
     * @param angleX the angle to rotate about X
     * @param angleY the angle to rotate about Y
     * @param angleZ the angle to rotate about Z
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f rotateXYZ(float angleX, float angleY, float angleZ, Matrix4f dest) {
        return delegate.rotateXYZ(angleX, angleY, angleZ, dest);
    }

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     *
     * @param angleX the angle to rotate about X
     * @param angleY the angle to rotate about Y
     * @param angleZ the angle to rotate about Z
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f rotateAffineXYZ(float angleX, float angleY, float angleZ, Matrix4f dest) {
        return delegate.rotateAffineXYZ(angleX, angleY, angleZ, dest);
    }

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <code>rotateZ(angleZ, dest).rotateY(angleY).rotateX(angleX)</code>
     *
     * @param angleZ the angle to rotate about Z
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f rotateZYX(float angleZ, float angleY, float angleX, Matrix4f dest) {
        return delegate.rotateZYX(angleZ, angleY, angleX, dest);
    }

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     *
     * @param angleZ the angle to rotate about Z
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f rotateAffineZYX(float angleZ, float angleY, float angleX, Matrix4f dest) {
        return delegate.rotateAffineZYX(angleZ, angleY, angleX, dest);
    }

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code> radians about the X axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <code>rotateY(angleY, dest).rotateX(angleX).rotateZ(angleZ)</code>
     *
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param angleZ the angle to rotate about Z
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f rotateYXZ(float angleY, float angleX, float angleZ, Matrix4f dest) {
        return delegate.rotateYXZ(angleY, angleX, angleZ, dest);
    }

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code> radians about the X axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <code>(0, 0, 0, 1)</code>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     *
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param angleZ the angle to rotate about Z
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f rotateAffineYXZ(float angleY, float angleX, float angleZ, Matrix4f dest) {
        return delegate.rotateAffineYXZ(angleY, angleX, angleZ, dest);
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of radians
     * about the specified <code>(x, y, z)</code> axis and store the result in <code>dest</code>.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotate(float ang, float x, float y, float z, Matrix4f dest) {
        return delegate.rotate(ang, x, y, z, dest);
    }

    /**
     * Apply rotation to this matrix, which is assumed to only contain a translation, by rotating the given amount of radians
     * about the specified <code>(x, y, z)</code> axis and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to only contain a translation.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateTranslation(float ang, float x, float y, float z, Matrix4f dest) {
        return delegate.rotateTranslation(ang, x, y, z, dest);
    }

    /**
     * Apply rotation to this {@link #isAffine() affine} matrix by rotating the given amount of radians
     * about the specified <code>(x, y, z)</code> axis and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be {@link #isAffine() affine}.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateAffine(float ang, float x, float y, float z, Matrix4f dest) {
        return delegate.rotateAffine(ang, x, y, z, dest);
    }

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians
     * about the specified <code>(x, y, z)</code> axis and store the result in <code>dest</code>.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>R * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>R * M * v</code>, the
     * rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateLocal(float ang, float x, float y, float z, Matrix4f dest) {
        return delegate.rotateLocal(ang, x, y, z, dest);
    }

    /**
     * Pre-multiply a rotation around the X axis to this matrix by rotating the given amount of radians
     * about the X axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>R * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>R * M * v</code>, the
     * rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians to rotate about the X axis
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateLocalX(float ang, Matrix4f dest) {
        return delegate.rotateLocalX(ang, dest);
    }

    /**
     * Pre-multiply a rotation around the Y axis to this matrix by rotating the given amount of radians
     * about the Y axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>R * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>R * M * v</code>, the
     * rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians to rotate about the Y axis
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateLocalY(float ang, Matrix4f dest) {
        return delegate.rotateLocalY(ang, dest);
    }

    /**
     * Pre-multiply a rotation around the Z axis to this matrix by rotating the given amount of radians
     * about the Z axis and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>R * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>R * M * v</code>, the
     * rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians to rotate about the Z axis
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateLocalZ(float ang, Matrix4f dest) {
        return delegate.rotateLocalZ(ang, dest);
    }

    /**
     * Apply a translation to this matrix by translating by the given number of
     * units in x, y and z and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     *
     * @param offset the number of units in x, y and z by which to translate
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f translate(Vector3fc offset, Matrix4f dest) {
        return delegate.translate(offset, dest);
    }

    /**
     * Apply a translation to this matrix by translating by the given number of
     * units in x, y and z and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     *
     * @param x    the offset to translate in x
     * @param y    the offset to translate in y
     * @param z    the offset to translate in z
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f translate(float x, float y, float z, Matrix4f dest) {
        return delegate.translate(x, y, z, dest);
    }

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of
     * units in x, y and z and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>T * M</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>T * M * v</code>, the translation will be applied last!
     *
     * @param offset the number of units in x, y and z by which to translate
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f translateLocal(Vector3fc offset, Matrix4f dest) {
        return delegate.translateLocal(offset, dest);
    }

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of
     * units in x, y and z and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>T * M</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>T * M * v</code>, the translation will be applied last!
     *
     * @param x    the offset to translate in x
     * @param y    the offset to translate in y
     * @param z    the offset to translate in z
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f translateLocal(float x, float y, float z, Matrix4f dest) {
        return delegate.translateLocal(x, y, z, dest);
    }

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left       the distance from the center to the left frustum edge
     * @param right      the distance from the center to the right frustum edge
     * @param bottom     the distance from the center to the bottom frustum edge
     * @param top        the distance from the center to the top frustum edge
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @param dest       will hold the result
     * @return dest
     */
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.ortho(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return delegate.ortho(left, right, bottom, top, zNear, zFar, dest);
    }

    /**
     * Apply an orthographic projection transformation for a left-handed coordiante system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left       the distance from the center to the left frustum edge
     * @param right      the distance from the center to the right frustum edge
     * @param bottom     the distance from the center to the bottom frustum edge
     * @param top        the distance from the center to the top frustum edge
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @param dest       will hold the result
     * @return dest
     */
    public Matrix4f orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.orthoLH(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply an orthographic projection transformation for a left-handed coordiante system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return delegate.orthoLH(left, right, bottom, top, zNear, zFar, dest);
    }

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, boolean, Matrix4f) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width      the distance between the right and left frustum edges
     * @param height     the distance between the top and bottom frustum edges
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param dest       will hold the result
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @return dest
     */
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.orthoSymmetric(width, height, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, Matrix4f) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, Matrix4f dest) {
        return delegate.orthoSymmetric(width, height, zNear, zFar, dest);
    }

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float, boolean, Matrix4f) orthoLH()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width      the distance between the right and left frustum edges
     * @param height     the distance between the top and bottom frustum edges
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param dest       will hold the result
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @return dest
     */
    public Matrix4f orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.orthoSymmetricLH(width, height, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float, Matrix4f) orthoLH()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f orthoSymmetricLH(float width, float height, float zNear, float zFar, Matrix4f dest) {
        return delegate.orthoSymmetricLH(width, height, zNear, zFar, dest);
    }

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system to this matrix
     * and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, Matrix4f) ortho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param dest   will hold the result
     * @return dest
     * @see #ortho(float, float, float, float, float, float, Matrix4f)
     */
    public Matrix4f ortho2D(float left, float right, float bottom, float top, Matrix4f dest) {
        return delegate.ortho2D(left, right, bottom, top, dest);
    }

    /**
     * Apply an orthographic projection transformation for a left-handed coordinate system to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float, Matrix4f) orthoLH()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param dest   will hold the result
     * @return dest
     * @see #orthoLH(float, float, float, float, float, float, Matrix4f)
     */
    public Matrix4f ortho2DLH(float left, float right, float bottom, float top, Matrix4f dest) {
        return delegate.ortho2DLH(left, right, bottom, top, dest);
    }

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling
     * {@link #lookAt(Vector3fc, Vector3fc, Vector3fc, Matrix4f) lookAt}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     *
     * @param dir  the direction in space to look along
     * @param up   the direction of 'up'
     * @param dest will hold the result
     * @return dest
     * @see #lookAlong(float, float, float, float, float, float, Matrix4f)
     * @see #lookAt(Vector3fc, Vector3fc, Vector3fc, Matrix4f)
     */
    public Matrix4f lookAlong(Vector3fc dir, Vector3fc up, Matrix4f dest) {
        return delegate.lookAlong(dir, up, dest);
    }

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling
     * {@link #lookAt(float, float, float, float, float, float, float, float, float, Matrix4f) lookAt()}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @param dest will hold the result
     * @return dest
     * @see #lookAt(float, float, float, float, float, float, float, float, float, Matrix4f)
     */
    public Matrix4f lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4f dest) {
        return delegate.lookAlong(dirX, dirY, dirZ, upX, upY, upZ, dest);
    }

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system,
     * that aligns <code>-z</code> with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @param dest   will hold the result
     * @return dest
     * @see #lookAt(float, float, float, float, float, float, float, float, float, Matrix4f)
     */
    public Matrix4f lookAt(Vector3fc eye, Vector3fc center, Vector3fc up, Matrix4f dest) {
        return delegate.lookAt(eye, center, up, dest);
    }

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system,
     * that aligns <code>-z</code> with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     *
     * @param eyeX    the x-coordinate of the eye/camera location
     * @param eyeY    the y-coordinate of the eye/camera location
     * @param eyeZ    the z-coordinate of the eye/camera location
     * @param centerX the x-coordinate of the point to look at
     * @param centerY the y-coordinate of the point to look at
     * @param centerZ the z-coordinate of the point to look at
     * @param upX     the x-coordinate of the up vector
     * @param upY     the y-coordinate of the up vector
     * @param upZ     the z-coordinate of the up vector
     * @param dest    will hold the result
     * @return dest
     * @see #lookAt(Vector3fc, Vector3fc, Vector3fc, Matrix4f)
     */
    public Matrix4f lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return delegate.lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system,
     * that aligns <code>-z</code> with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be a perspective transformation, obtained via
     * {@link #frustum(float, float, float, float, float, float, Matrix4f) frustum()} or {@link #perspective(float, float, float, float, Matrix4f) perspective()} or
     * one of their overloads.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     *
     * @param eyeX    the x-coordinate of the eye/camera location
     * @param eyeY    the y-coordinate of the eye/camera location
     * @param eyeZ    the z-coordinate of the eye/camera location
     * @param centerX the x-coordinate of the point to look at
     * @param centerY the y-coordinate of the point to look at
     * @param centerZ the z-coordinate of the point to look at
     * @param upX     the x-coordinate of the up vector
     * @param upY     the y-coordinate of the up vector
     * @param upZ     the z-coordinate of the up vector
     * @param dest    will hold the result
     * @return dest
     */
    public Matrix4f lookAtPerspective(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return delegate.lookAtPerspective(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system,
     * that aligns <code>+z</code> with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @param dest   will hold the result
     * @return dest
     * @see #lookAtLH(float, float, float, float, float, float, float, float, float, Matrix4f)
     */
    public Matrix4f lookAtLH(Vector3fc eye, Vector3fc center, Vector3fc up, Matrix4f dest) {
        return delegate.lookAtLH(eye, center, up, dest);
    }

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system,
     * that aligns <code>+z</code> with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     *
     * @param eyeX    the x-coordinate of the eye/camera location
     * @param eyeY    the y-coordinate of the eye/camera location
     * @param eyeZ    the z-coordinate of the eye/camera location
     * @param centerX the x-coordinate of the point to look at
     * @param centerY the y-coordinate of the point to look at
     * @param centerZ the z-coordinate of the point to look at
     * @param upX     the x-coordinate of the up vector
     * @param upY     the y-coordinate of the up vector
     * @param upZ     the z-coordinate of the up vector
     * @param dest    will hold the result
     * @return dest
     * @see #lookAtLH(Vector3fc, Vector3fc, Vector3fc, Matrix4f)
     */
    public Matrix4f lookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return delegate.lookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system,
     * that aligns <code>+z</code> with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be a perspective transformation, obtained via
     * {@link #frustumLH(float, float, float, float, float, float, Matrix4f) frustumLH()} or {@link #perspectiveLH(float, float, float, float, Matrix4f) perspectiveLH()} or
     * one of their overloads.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     *
     * @param eyeX    the x-coordinate of the eye/camera location
     * @param eyeY    the y-coordinate of the eye/camera location
     * @param eyeZ    the z-coordinate of the eye/camera location
     * @param centerX the x-coordinate of the point to look at
     * @param centerY the y-coordinate of the point to look at
     * @param centerZ the z-coordinate of the point to look at
     * @param upX     the x-coordinate of the up vector
     * @param upY     the y-coordinate of the up vector
     * @param upZ     the z-coordinate of the up vector
     * @param dest    will hold the result
     * @return dest
     */
    public Matrix4f lookAtPerspectiveLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return delegate.lookAtPerspectiveLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation for a right-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param fovy       the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect     the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear      near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                   In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                   In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest       will hold the result
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @return dest
     */
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.perspective(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param fovy   the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear  near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *               In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *               In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, Matrix4f dest) {
        return delegate.perspective(fovy, aspect, zNear, zFar, dest);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation for a right-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param width      the width of the near frustum plane
     * @param height     the height of the near frustum plane
     * @param zNear      near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                   In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                   In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest       will hold the result
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @return dest
     */
    public Matrix4f perspectiveRect(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.perspectiveRect(width, height, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param width  the width of the near frustum plane
     * @param height the height of the near frustum plane
     * @param zNear  near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *               In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *               In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f perspectiveRect(float width, float height, float zNear, float zFar, Matrix4f dest) {
        return delegate.perspectiveRect(width, height, zNear, zFar, dest);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation using for a right-handed coordinate system
     * the given NDC z range to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param width      the width of the near frustum plane
     * @param height     the height of the near frustum plane
     * @param zNear      near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                   In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                   In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @return this
     */
    public Matrix4f perspectiveRect(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return delegate.perspectiveRect(width, height, zNear, zFar, zZeroToOne);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param width  the width of the near frustum plane
     * @param height the height of the near frustum plane
     * @param zNear  near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *               In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *               In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @return this
     */
    public Matrix4f perspectiveRect(float width, float height, float zNear, float zFar) {
        return delegate.perspectiveRect(width, height, zNear, zFar);
    }

    /**
     * Apply an asymmetric off-center perspective projection frustum transformation for a right-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * The given angles <code>offAngleX</code> and <code>offAngleY</code> are the horizontal and vertical angles between
     * the line of sight and the line given by the center of the near and far frustum planes. So, when <code>offAngleY</code>
     * is just <code>fovy/2</code> then the projection frustum is rotated towards +Y and the bottom frustum plane
     * is parallel to the XZ-plane.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param fovy       the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param offAngleX  the horizontal angle between the line of sight and the line crossing the center of the near and far frustum planes
     * @param offAngleY  the vertical angle between the line of sight and the line crossing the center of the near and far frustum planes
     * @param aspect     the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear      near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                   In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                   In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest       will hold the result
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @return dest
     */
    public Matrix4f perspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.perspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply an asymmetric off-center perspective projection frustum transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * The given angles <code>offAngleX</code> and <code>offAngleY</code> are the horizontal and vertical angles between
     * the line of sight and the line given by the center of the near and far frustum planes. So, when <code>offAngleY</code>
     * is just <code>fovy/2</code> then the projection frustum is rotated towards +Y and the bottom frustum plane
     * is parallel to the XZ-plane.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param fovy      the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param offAngleX the horizontal angle between the line of sight and the line crossing the center of the near and far frustum planes
     * @param offAngleY the vertical angle between the line of sight and the line crossing the center of the near and far frustum planes
     * @param aspect    the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear     near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                  In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar      far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                  In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest      will hold the result
     * @return dest
     */
    public Matrix4f perspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar, Matrix4f dest) {
        return delegate.perspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar, dest);
    }

    /**
     * Apply an asymmetric off-center perspective projection frustum transformation using for a right-handed coordinate system
     * the given NDC z range to this matrix.
     * <p>
     * The given angles <code>offAngleX</code> and <code>offAngleY</code> are the horizontal and vertical angles between
     * the line of sight and the line given by the center of the near and far frustum planes. So, when <code>offAngleY</code>
     * is just <code>fovy/2</code> then the projection frustum is rotated towards +Y and the bottom frustum plane
     * is parallel to the XZ-plane.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param fovy       the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param offAngleX  the horizontal angle between the line of sight and the line crossing the center of the near and far frustum planes
     * @param offAngleY  the vertical angle between the line of sight and the line crossing the center of the near and far frustum planes
     * @param aspect     the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear      near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                   In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                   In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @return this
     */
    public Matrix4f perspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return delegate.perspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar, zZeroToOne);
    }

    /**
     * Apply an asymmetric off-center perspective projection frustum transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix.
     * <p>
     * The given angles <code>offAngleX</code> and <code>offAngleY</code> are the horizontal and vertical angles between
     * the line of sight and the line given by the center of the near and far frustum planes. So, when <code>offAngleY</code>
     * is just <code>fovy/2</code> then the projection frustum is rotated towards +Y and the bottom frustum plane
     * is parallel to the XZ-plane.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param fovy      the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param offAngleX the horizontal angle between the line of sight and the line crossing the center of the near and far frustum planes
     * @param offAngleY the vertical angle between the line of sight and the line crossing the center of the near and far frustum planes
     * @param aspect    the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear     near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                  In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar      far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                  In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @return this
     */
    public Matrix4f perspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar) {
        return delegate.perspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation for a left-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param fovy       the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect     the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear      near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                   In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                   In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @param dest       will hold the result
     * @return dest
     */
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.perspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation for a left-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     *
     * @param fovy   the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear  near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *               In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *               In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, Matrix4f dest) {
        return delegate.perspectiveLH(fovy, aspect, zNear, zFar, dest);
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation for a right-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     *
     * @param left       the distance along the x-axis to the left frustum edge
     * @param right      the distance along the x-axis to the right frustum edge
     * @param bottom     the distance along the y-axis to the bottom frustum edge
     * @param top        the distance along the y-axis to the top frustum edge
     * @param zNear      near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                   In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                   In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @param dest       will hold the result
     * @return dest
     */
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.frustum(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     *
     * @param left   the distance along the x-axis to the left frustum edge
     * @param right  the distance along the x-axis to the right frustum edge
     * @param bottom the distance along the y-axis to the bottom frustum edge
     * @param top    the distance along the y-axis to the top frustum edge
     * @param zNear  near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *               In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *               In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return delegate.frustum(left, right, bottom, top, zNear, zFar, dest);
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation for a left-handed coordinate system
     * using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     *
     * @param left       the distance along the x-axis to the left frustum edge
     * @param right      the distance along the x-axis to the right frustum edge
     * @param bottom     the distance along the y-axis to the bottom frustum edge
     * @param top        the distance along the y-axis to the top frustum edge
     * @param zNear      near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *                   In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *                   In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <code>[0..+1]</code> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <code>[-1..+1]</code> when <code>false</code>
     * @param dest       will hold the result
     * @return dest
     */
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return delegate.frustumLH(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation for a left-handed coordinate system
     * using OpenGL's NDC z range of <code>[-1..+1]</code> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     *
     * @param left   the distance along the x-axis to the left frustum edge
     * @param right  the distance along the x-axis to the right frustum edge
     * @param bottom the distance along the y-axis to the bottom frustum edge
     * @param top    the distance along the y-axis to the top frustum edge
     * @param zNear  near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *               In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *               In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return delegate.frustumLH(left, right, bottom, top, zNear, zFar, dest);
    }

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link Quaternionfc} to this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link Quaternionfc}
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotate(Quaternionfc quat, Matrix4f dest) {
        return delegate.rotate(quat, dest);
    }

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link Quaternionfc} to this {@link #isAffine() affine} matrix and store
     * the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be {@link #isAffine() affine}.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link Quaternionfc}
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateAffine(Quaternionfc quat, Matrix4f dest) {
        return delegate.rotateAffine(quat, dest);
    }

    /**
     * Apply the rotation - and possibly scaling - ransformation of the given {@link Quaternionfc} to this matrix, which is assumed to only contain a translation, and store
     * the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to only contain a translation.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link Quaternionfc}
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateTranslation(Quaternionfc quat, Matrix4f dest) {
        return delegate.rotateTranslation(quat, dest);
    }

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link Quaternionfc} to this {@link #isAffine() affine}
     * matrix while using <code>(ox, oy, oz)</code> as the rotation origin, and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * This method is only applicable if <code>this</code> is an {@link #isAffine() affine} matrix.
     * <p>
     * This method is equivalent to calling: <code>translate(ox, oy, oz, dest).rotate(quat).translate(-ox, -oy, -oz)</code>
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link Quaternionfc}
     * @param ox   the x coordinate of the rotation origin
     * @param oy   the y coordinate of the rotation origin
     * @param oz   the z coordinate of the rotation origin
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateAroundAffine(Quaternionfc quat, float ox, float oy, float oz, Matrix4f dest) {
        return delegate.rotateAroundAffine(quat, ox, oy, oz, dest);
    }

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link Quaternionfc} to this matrix while using <code>(ox, oy, oz)</code> as the rotation origin,
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <code>translate(ox, oy, oz, dest).rotate(quat).translate(-ox, -oy, -oz)</code>
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link Quaternionfc}
     * @param ox   the x coordinate of the rotation origin
     * @param oy   the y coordinate of the rotation origin
     * @param oz   the z coordinate of the rotation origin
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateAround(Quaternionfc quat, float ox, float oy, float oz, Matrix4f dest) {
        return delegate.rotateAround(quat, ox, oy, oz, dest);
    }

    /**
     * Pre-multiply the rotation - and possibly scaling - transformation of the given {@link Quaternionfc} to this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>Q * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>Q * M * v</code>,
     * the quaternion rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link Quaternionfc}
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateLocal(Quaternionfc quat, Matrix4f dest) {
        return delegate.rotateLocal(quat, dest);
    }

    /**
     * Pre-multiply the rotation - and possibly scaling - transformation of the given {@link Quaternionfc} to this matrix while using <code>(ox, oy, oz)</code>
     * as the rotation origin, and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>Q * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>Q * M * v</code>,
     * the quaternion rotation will be applied last!
     * <p>
     * This method is equivalent to calling: <code>translateLocal(-ox, -oy, -oz, dest).rotateLocal(quat).translateLocal(ox, oy, oz)</code>
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link Quaternionfc}
     * @param ox   the x coordinate of the rotation origin
     * @param oy   the y coordinate of the rotation origin
     * @param oz   the z coordinate of the rotation origin
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f rotateAroundLocal(Quaternionfc quat, float ox, float oy, float oz, Matrix4f dest) {
        return delegate.rotateAroundLocal(quat, ox, oy, oz, dest);
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4f} and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link AxisAngle4f},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link AxisAngle4f} rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param axisAngle the {@link AxisAngle4f} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @param dest      will hold the result
     * @return dest
     * @see #rotate(float, float, float, float, Matrix4f)
     */
    public Matrix4f rotate(AxisAngle4f axisAngle, Matrix4f dest) {
        return delegate.rotate(axisAngle, dest);
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in <code>dest</code>.
     * <p>
     * The axis described by the <code>axis</code> vector needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given axis-angle,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the axis-angle rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param axis  the rotation axis (needs to be {@link Vector3fc#normalize(Vector3f) normalized})
     * @param dest  will hold the result
     * @return dest
     * @see #rotate(float, float, float, float, Matrix4f)
     */
    public Matrix4f rotate(float angle, Vector3fc axis, Matrix4f dest) {
        return delegate.rotate(angle, axis, dest);
    }

    /**
     * Unproject the given window coordinates <code>(winX, winY, winZ)</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <code>[-1..1]</code>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * The depth range of <code>winZ</code> is assumed to be <code>[0..1]</code>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInv(float, float, float, int[], Vector4f) unprojectInv()} can be invoked on it.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param winZ     the z-coordinate, which is the depth value in <code>[0..1]</code>
     * @param viewport the viewport described by <code>[x, y, width, height]</code>
     * @param dest     will hold the unprojected position
     * @return dest
     * @see #unprojectInv(float, float, float, int[], Vector4f)
     * @see #invert(Matrix4f)
     */
    public Vector4f unproject(float winX, float winY, float winZ, int[] viewport, Vector4f dest) {
        return delegate.unproject(winX, winY, winZ, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>(winX, winY, winZ)</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <code>[-1..1]</code>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * The depth range of <code>winZ</code> is assumed to be <code>[0..1]</code>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInv(float, float, float, int[], Vector3f) unprojectInv()} can be invoked on it.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param winZ     the z-coordinate, which is the depth value in <code>[0..1]</code>
     * @param viewport the viewport described by <code>[x, y, width, height]</code>
     * @param dest     will hold the unprojected position
     * @return dest
     * @see #unprojectInv(float, float, float, int[], Vector3f)
     * @see #invert(Matrix4f)
     */
    public Vector3f unproject(float winX, float winY, float winZ, int[] viewport, Vector3f dest) {
        return delegate.unproject(winX, winY, winZ, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <code>[-1..1]</code>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * The depth range of <code>winCoords.z</code> is assumed to be <code>[0..1]</code>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInv(float, float, float, int[], Vector4f) unprojectInv()} can be invoked on it.
     *
     * @param winCoords the window coordinates to unproject
     * @param viewport  the viewport described by <code>[x, y, width, height]</code>
     * @param dest      will hold the unprojected position
     * @return dest
     * @see #unprojectInv(float, float, float, int[], Vector4f)
     * @see #unproject(float, float, float, int[], Vector4f)
     * @see #invert(Matrix4f)
     */
    public Vector4f unproject(Vector3fc winCoords, int[] viewport, Vector4f dest) {
        return delegate.unproject(winCoords, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <code>[-1..1]</code>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * The depth range of <code>winCoords.z</code> is assumed to be <code>[0..1]</code>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInv(float, float, float, int[], Vector3f) unprojectInv()} can be invoked on it.
     *
     * @param winCoords the window coordinates to unproject
     * @param viewport  the viewport described by <code>[x, y, width, height]</code>
     * @param dest      will hold the unprojected position
     * @return dest
     * @see #unprojectInv(float, float, float, int[], Vector3f)
     * @see #unproject(float, float, float, int[], Vector3f)
     * @see #invert(Matrix4f)
     */
    public Vector3f unproject(Vector3fc winCoords, int[] viewport, Vector3f dest) {
        return delegate.unproject(winCoords, viewport, dest);
    }

    /**
     * Unproject the given 2D window coordinates <code>(winX, winY)</code> by <code>this</code> matrix using the specified viewport
     * and compute the origin and the direction of the resulting ray which starts at NDC <code>z = -1.0</code> and goes through NDC <code>z = +1.0</code>.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <code>[-1..1]</code>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInvRay(float, float, int[], Vector3f, Vector3f) unprojectInvRay()} can be invoked on it.
     *
     * @param winX       the x-coordinate in window coordinates (pixels)
     * @param winY       the y-coordinate in window coordinates (pixels)
     * @param viewport   the viewport described by <code>[x, y, width, height]</code>
     * @param originDest will hold the ray origin
     * @param dirDest    will hold the (unnormalized) ray direction
     * @return this
     * @see #unprojectInvRay(float, float, int[], Vector3f, Vector3f)
     * @see #invert(Matrix4f)
     */
    public Matrix4f unprojectRay(float winX, float winY, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return delegate.unprojectRay(winX, winY, viewport, originDest, dirDest);
    }

    /**
     * Unproject the given 2D window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport
     * and compute the origin and the direction of the resulting ray which starts at NDC <code>z = -1.0</code> and goes through NDC <code>z = +1.0</code>.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <code>[-1..1]</code>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInvRay(float, float, int[], Vector3f, Vector3f) unprojectInvRay()} can be invoked on it.
     *
     * @param winCoords  the window coordinates to unproject
     * @param viewport   the viewport described by <code>[x, y, width, height]</code>
     * @param originDest will hold the ray origin
     * @param dirDest    will hold the (unnormalized) ray direction
     * @return this
     * @see #unprojectInvRay(float, float, int[], Vector3f, Vector3f)
     * @see #unprojectRay(float, float, int[], Vector3f, Vector3f)
     * @see #invert(Matrix4f)
     */
    public Matrix4f unprojectRay(Vector2fc winCoords, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return delegate.unprojectRay(winCoords, viewport, originDest, dirDest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(Vector3fc, int[], Vector4f) unproject()}
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * The depth range of <code>winCoords.z</code> is assumed to be <code>[0..1]</code>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the given int[].
     *
     * @param winCoords the window coordinates to unproject
     * @param viewport  the viewport described by <code>[x, y, width, height]</code>
     * @param dest      will hold the unprojected position
     * @return dest
     * @see #unproject(Vector3fc, int[], Vector4f)
     */
    public Vector4f unprojectInv(Vector3fc winCoords, int[] viewport, Vector4f dest) {
        return delegate.unprojectInv(winCoords, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>(winX, winY, winZ)</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(float, float, float, int[], Vector4f) unproject()}
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * The depth range of <code>winZ</code> is assumed to be <code>[0..1]</code>, which is also the OpenGL default.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param winZ     the z-coordinate, which is the depth value in <code>[0..1]</code>
     * @param viewport the viewport described by <code>[x, y, width, height]</code>
     * @param dest     will hold the unprojected position
     * @return dest
     * @see #unproject(float, float, float, int[], Vector4f)
     */
    public Vector4f unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector4f dest) {
        return delegate.unprojectInv(winX, winY, winZ, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport
     * and compute the origin and the direction of the resulting ray which starts at NDC <code>z = -1.0</code> and goes through NDC <code>z = +1.0</code>.
     * <p>
     * This method differs from {@link #unprojectRay(Vector2fc, int[], Vector3f, Vector3f) unprojectRay()}
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     *
     * @param winCoords  the window coordinates to unproject
     * @param viewport   the viewport described by <code>[x, y, width, height]</code>
     * @param originDest will hold the ray origin
     * @param dirDest    will hold the (unnormalized) ray direction
     * @return this
     * @see #unprojectRay(Vector2fc, int[], Vector3f, Vector3f)
     */
    public Matrix4f unprojectInvRay(Vector2fc winCoords, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return delegate.unprojectInvRay(winCoords, viewport, originDest, dirDest);
    }

    /**
     * Unproject the given 2D window coordinates <code>(winX, winY)</code> by <code>this</code> matrix using the specified viewport
     * and compute the origin and the direction of the resulting ray which starts at NDC <code>z = -1.0</code> and goes through NDC <code>z = +1.0</code>.
     * <p>
     * This method differs from {@link #unprojectRay(float, float, int[], Vector3f, Vector3f) unprojectRay()}
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     *
     * @param winX       the x-coordinate in window coordinates (pixels)
     * @param winY       the y-coordinate in window coordinates (pixels)
     * @param viewport   the viewport described by <code>[x, y, width, height]</code>
     * @param originDest will hold the ray origin
     * @param dirDest    will hold the (unnormalized) ray direction
     * @return this
     * @see #unprojectRay(float, float, int[], Vector3f, Vector3f)
     */
    public Matrix4f unprojectInvRay(float winX, float winY, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return delegate.unprojectInvRay(winX, winY, viewport, originDest, dirDest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(Vector3fc, int[], Vector3f) unproject()}
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * The depth range of <code>winCoords.z</code> is assumed to be <code>[0..1]</code>, which is also the OpenGL default.
     *
     * @param winCoords the window coordinates to unproject
     * @param viewport  the viewport described by <code>[x, y, width, height]</code>
     * @param dest      will hold the unprojected position
     * @return dest
     * @see #unproject(Vector3fc, int[], Vector3f)
     */
    public Vector3f unprojectInv(Vector3fc winCoords, int[] viewport, Vector3f dest) {
        return delegate.unprojectInv(winCoords, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>(winX, winY, winZ)</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(float, float, float, int[], Vector3f) unproject()}
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * The depth range of <code>winZ</code> is assumed to be <code>[0..1]</code>, which is also the OpenGL default.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param winZ     the z-coordinate, which is the depth value in <code>[0..1]</code>
     * @param viewport the viewport described by <code>[x, y, width, height]</code>
     * @param dest     will hold the unprojected position
     * @return dest
     * @see #unproject(float, float, float, int[], Vector3f)
     */
    public Vector3f unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector3f dest) {
        return delegate.unprojectInv(winX, winY, winZ, viewport, dest);
    }

    /**
     * Project the given <code>(x, y, z)</code> position via <code>this</code> matrix using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <code>[x, y, width, height]</code>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <code>[0..1]</code>, which is also the OpenGL default.
     *
     * @param x             the x-coordinate of the position to project
     * @param y             the y-coordinate of the position to project
     * @param z             the z-coordinate of the position to project
     * @param viewport      the viewport described by <code>[x, y, width, height]</code>
     * @param winCoordsDest will hold the projected window coordinates
     * @return winCoordsDest
     */
    public Vector4f project(float x, float y, float z, int[] viewport, Vector4f winCoordsDest) {
        return delegate.project(x, y, z, viewport, winCoordsDest);
    }

    /**
     * Project the given <code>(x, y, z)</code> position via <code>this</code> matrix using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <code>[x, y, width, height]</code>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <code>[0..1]</code>, which is also the OpenGL default.
     *
     * @param x             the x-coordinate of the position to project
     * @param y             the y-coordinate of the position to project
     * @param z             the z-coordinate of the position to project
     * @param viewport      the viewport described by <code>[x, y, width, height]</code>
     * @param winCoordsDest will hold the projected window coordinates
     * @return winCoordsDest
     */
    public Vector3f project(float x, float y, float z, int[] viewport, Vector3f winCoordsDest) {
        return delegate.project(x, y, z, viewport, winCoordsDest);
    }

    /**
     * Project the given <code>position</code> via <code>this</code> matrix using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <code>[x, y, width, height]</code>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <code>[0..1]</code>, which is also the OpenGL default.
     *
     * @param position      the position to project into window coordinates
     * @param viewport      the viewport described by <code>[x, y, width, height]</code>
     * @param winCoordsDest will hold the projected window coordinates
     * @return winCoordsDest
     * @see #project(float, float, float, int[], Vector4f)
     */
    public Vector4f project(Vector3fc position, int[] viewport, Vector4f winCoordsDest) {
        return delegate.project(position, viewport, winCoordsDest);
    }

    /**
     * Project the given <code>position</code> via <code>this</code> matrix using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <code>[x, y, width, height]</code>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <code>[0..1]</code>, which is also the OpenGL default.
     *
     * @param position      the position to project into window coordinates
     * @param viewport      the viewport described by <code>[x, y, width, height]</code>
     * @param winCoordsDest will hold the projected window coordinates
     * @return winCoordsDest
     * @see #project(float, float, float, int[], Vector4f)
     */
    public Vector3f project(Vector3fc position, int[] viewport, Vector3f winCoordsDest) {
        return delegate.project(position, viewport, winCoordsDest);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the equation <code>x*a + y*b + z*c + d = 0</code> and store the result in <code>dest</code>.
     * <p>
     * The vector <code>(a, b, c)</code> must be a unit vector.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * <p>
     * Reference: <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/bb281733(v=vs.85).aspx">msdn.microsoft.com</a>
     *
     * @param a    the x factor in the plane equation
     * @param b    the y factor in the plane equation
     * @param c    the z factor in the plane equation
     * @param d    the constant in the plane equation
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f reflect(float a, float b, float c, float d, Matrix4f dest) {
        return delegate.reflect(a, b, c, d, dest);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the plane normal and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     *
     * @param nx   the x-coordinate of the plane normal
     * @param ny   the y-coordinate of the plane normal
     * @param nz   the z-coordinate of the plane normal
     * @param px   the x-coordinate of a point on the plane
     * @param py   the y-coordinate of a point on the plane
     * @param pz   the z-coordinate of a point on the plane
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f reflect(float nx, float ny, float nz, float px, float py, float pz, Matrix4f dest) {
        return delegate.reflect(nx, ny, nz, px, py, pz, dest);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane
     * specified via the plane orientation and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the scene.
     * It is assumed that the default mirror plane's normal is <code>(0, 0, 1)</code>. So, if the given {@link Quaternionfc} is
     * the identity (does not apply any additional rotation), the reflection plane will be <code>z=0</code>, offset by the given <code>point</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     *
     * @param orientation the plane orientation relative to an implied normal vector of <code>(0, 0, 1)</code>
     * @param point       a point on the plane
     * @param dest        will hold the result
     * @return dest
     */
    public Matrix4f reflect(Quaternionfc orientation, Vector3fc point, Matrix4f dest) {
        return delegate.reflect(orientation, point, dest);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the plane normal and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     *
     * @param normal the plane normal
     * @param point  a point on the plane
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f reflect(Vector3fc normal, Vector3fc point, Matrix4f dest) {
        return delegate.reflect(normal, point, dest);
    }

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row  the row index in <code>[0..3]</code>
     * @param dest will hold the row components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>row</code> is not in <code>[0..3]</code>
     */
    public Vector4f getRow(int row, Vector4f dest) {
        return delegate.getRow(row, dest);
    }

    /**
     * Get the first three components of the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row  the row index in <code>[0..3]</code>
     * @param dest will hold the first three row components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>row</code> is not in <code>[0..3]</code>
     */
    public Vector3f getRow(int row, Vector3f dest) {
        return delegate.getRow(row, dest);
    }

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <code>[0..3]</code>
     * @param dest   will hold the column components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>column</code> is not in <code>[0..3]</code>
     */
    public Vector4f getColumn(int column, Vector4f dest) {
        return delegate.getColumn(column, dest);
    }

    /**
     * Get the first three components of the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <code>[0..3]</code>
     * @param dest   will hold the first three column components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>column</code> is not in <code>[0..3]</code>
     */
    public Vector3f getColumn(int column, Vector3f dest) {
        return delegate.getColumn(column, dest);
    }

    /**
     * Get the matrix element value at the given column and row.
     *
     * @param column the colum index in <code>[0..3]</code>
     * @param row    the row index in <code>[0..3]</code>
     * @return the element value
     */
    public float get(int column, int row) {
        return delegate.get(column, row);
    }

    /**
     * Get the matrix element value at the given row and column.
     *
     * @param row    the row index in <code>[0..3]</code>
     * @param column the colum index in <code>[0..3]</code>
     * @return the element value
     */
    public float getRowColumn(int row, int column) {
        return delegate.getRowColumn(row, column);
    }

    /**
     * Compute a normal matrix from the upper left 3x3 submatrix of <code>this</code>
     * and store it into the upper left 3x3 submatrix of <code>dest</code>.
     * All other values of <code>dest</code> will be set to identity.
     * <p>
     * The normal matrix of <code>m</code> is the transpose of the inverse of <code>m</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f normal(Matrix4f dest) {
        return delegate.normal(dest);
    }

    /**
     * Compute a normal matrix from the upper left 3x3 submatrix of <code>this</code>
     * and store it into <code>dest</code>.
     * <p>
     * The normal matrix of <code>m</code> is the transpose of the inverse of <code>m</code>.
     *
     * @param dest will hold the result
     * @return dest
     * @see Matrix3f#set(Matrix4fc)
     * @see #get3x3(Matrix3f)
     */
    public Matrix3f normal(Matrix3f dest) {
        return delegate.normal(dest);
    }

    /**
     * Compute the cofactor matrix of the upper left 3x3 submatrix of <code>this</code>
     * and store it into <code>dest</code>.
     * <p>
     * The cofactor matrix can be used instead of {@link #normal(Matrix3f)} to transform normals
     * when the orientation of the normals with respect to the surface should be preserved.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix3f cofactor3x3(Matrix3f dest) {
        return delegate.cofactor3x3(dest);
    }

    /**
     * Compute the cofactor matrix of the upper left 3x3 submatrix of <code>this</code>
     * and store it into <code>dest</code>.
     * All other values of <code>dest</code> will be set to identity.
     * <p>
     * The cofactor matrix can be used instead of {@link #normal(Matrix4f)} to transform normals
     * when the orientation of the normals with respect to the surface should be preserved.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f cofactor3x3(Matrix4f dest) {
        return delegate.cofactor3x3(dest);
    }

    /**
     * Normalize the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit
     * vectors need not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself
     * (i.e. had <i>skewing</i>).
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f normalize3x3(Matrix4f dest) {
        return delegate.normalize3x3(dest);
    }

    /**
     * Normalize the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit
     * vectors need not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself
     * (i.e. had <i>skewing</i>).
     *
     * @param dest will hold the result
     * @return dest
     */
    public Matrix3f normalize3x3(Matrix3f dest) {
        return delegate.normalize3x3(dest);
    }

    /**
     * Calculate a frustum plane of <code>this</code> matrix, which
     * can be a projection matrix or a combined modelview-projection matrix, and store the result
     * in the given <code>planeEquation</code>.
     * <p>
     * Generally, this method computes the frustum plane in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * The frustum plane will be given in the form of a general plane equation:
     * <code>a*x + b*y + c*z + d = 0</code>, where the given {@link Vector4f} components will
     * hold the <code>(a, b, c, d)</code> values of the equation.
     * <p>
     * The plane normal, which is <code>(a, b, c)</code>, is directed "inwards" of the frustum.
     * Any plane/point test using <code>a*x + b*y + c*z + d</code> therefore will yield a result greater than zero
     * if the point is within the frustum (i.e. at the <i>positive</i> side of the frustum plane).
     * <p>
     * For performing frustum culling, the class {@link FrustumIntersection} should be used instead of
     * manually obtaining the frustum planes and testing them against points, spheres or axis-aligned boxes.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param plane         one of the six possible planes, given as numeric constants
     *                      {@link #PLANE_NX}, {@link #PLANE_PX},
     *                      {@link #PLANE_NY}, {@link #PLANE_PY},
     *                      {@link #PLANE_NZ} and {@link #PLANE_PZ}
     * @param planeEquation will hold the computed plane equation.
     *                      The plane equation will be normalized, meaning that <code>(a, b, c)</code> will be a unit vector
     * @return planeEquation
     */
    public Vector4f frustumPlane(int plane, Vector4f planeEquation) {
        return delegate.frustumPlane(plane, planeEquation);
    }

    /**
     * Compute the corner coordinates of the frustum defined by <code>this</code> matrix, which
     * can be a projection matrix or a combined modelview-projection matrix, and store the result
     * in the given <code>point</code>.
     * <p>
     * Generally, this method computes the frustum corners in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param corner one of the eight possible corners, given as numeric constants
     *               {@link #CORNER_NXNYNZ}, {@link #CORNER_PXNYNZ}, {@link #CORNER_PXPYNZ}, {@link #CORNER_NXPYNZ},
     *               {@link #CORNER_PXNYPZ}, {@link #CORNER_NXNYPZ}, {@link #CORNER_NXPYPZ}, {@link #CORNER_PXPYPZ}
     * @param point  will hold the resulting corner point coordinates
     * @return point
     */
    public Vector3f frustumCorner(int corner, Vector3f point) {
        return delegate.frustumCorner(corner, point);
    }

    /**
     * Compute the eye/origin of the perspective frustum transformation defined by <code>this</code> matrix,
     * which can be a projection matrix or a combined modelview-projection matrix, and store the result
     * in the given <code>origin</code>.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the
     * perspective methods, such as {@link #perspective(float, float, float, float, Matrix4f) perspective()}
     * or {@link #frustum(float, float, float, float, float, float, Matrix4f) frustum()}.
     * <p>
     * Generally, this method computes the origin in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * This method is equivalent to calling: <code>invert(new Matrix4f()).transformProject(0, 0, -1, 0, origin)</code>
     * and in the case of an already available inverse of <code>this</code> matrix, the method {@link #perspectiveInvOrigin(Vector3f)}
     * on the inverse of the matrix should be used instead.
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param origin will hold the origin of the coordinate system before applying <code>this</code>
     *               perspective projection transformation
     * @return origin
     */
    public Vector3f perspectiveOrigin(Vector3f origin) {
        return delegate.perspectiveOrigin(origin);
    }

    /**
     * Compute the eye/origin of the inverse of the perspective frustum transformation defined by <code>this</code> matrix,
     * which can be the inverse of a projection matrix or the inverse of a combined modelview-projection matrix, and store the result
     * in the given <code>dest</code>.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the
     * perspective methods, such as {@link #perspective(float, float, float, float, Matrix4f) perspective()}
     * or {@link #frustum(float, float, float, float, float, float, Matrix4f) frustum()}.
     * <p>
     * If the inverse of the modelview-projection matrix is not available, then calling {@link #perspectiveOrigin(Vector3f)}
     * on the original modelview-projection matrix is preferred.
     *
     * @param dest will hold the result
     * @return dest
     * @see #perspectiveOrigin(Vector3f)
     */
    public Vector3f perspectiveInvOrigin(Vector3f dest) {
        return delegate.perspectiveInvOrigin(dest);
    }

    /**
     * Return the vertical field-of-view angle in radians of this perspective transformation matrix.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the
     * perspective methods, such as {@link #perspective(float, float, float, float, Matrix4f) perspective()}
     * or {@link #frustum(float, float, float, float, float, float, Matrix4f) frustum()}.
     * <p>
     * For orthogonal transformations this method will return <code>0.0</code>.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @return the vertical field-of-view angle in radians
     */
    public float perspectiveFov() {
        return delegate.perspectiveFov();
    }

    /**
     * Extract the near clip plane distance from <code>this</code> perspective projection matrix.
     * <p>
     * This method only works if <code>this</code> is a perspective projection matrix, for example obtained via {@link #perspective(float, float, float, float, Matrix4f)}.
     *
     * @return the near clip plane distance
     */
    public float perspectiveNear() {
        return delegate.perspectiveNear();
    }

    /**
     * Extract the far clip plane distance from <code>this</code> perspective projection matrix.
     * <p>
     * This method only works if <code>this</code> is a perspective projection matrix, for example obtained via {@link #perspective(float, float, float, float, Matrix4f)}.
     *
     * @return the far clip plane distance
     */
    public float perspectiveFar() {
        return delegate.perspectiveFar();
    }

    /**
     * Obtain the direction of a ray starting at the center of the coordinate system and going
     * through the near frustum plane.
     * <p>
     * This method computes the <code>dir</code> vector in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * The parameters <code>x</code> and <code>y</code> are used to interpolate the generated ray direction
     * from the bottom-left to the top-right frustum corners.
     * <p>
     * For optimal efficiency when building many ray directions over the whole frustum,
     * it is recommended to use this method only in order to compute the four corner rays at
     * <code>(0, 0)</code>, <code>(1, 0)</code>, <code>(0, 1)</code> and <code>(1, 1)</code>
     * and then bilinearly interpolating between them; or to use the {@link FrustumRayBuilder}.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param x   the interpolation factor along the left-to-right frustum planes, within <code>[0..1]</code>
     * @param y   the interpolation factor along the bottom-to-top frustum planes, within <code>[0..1]</code>
     * @param dir will hold the normalized ray direction in the local frame of the coordinate system before
     *            transforming to homogeneous clipping space using <code>this</code> matrix
     * @return dir
     */
    public Vector3f frustumRayDir(float x, float y, Vector3f dir) {
        return delegate.frustumRayDir(x, y, dir);
    }

    /**
     * Obtain the direction of <code>+Z</code> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction
     * that is transformed to <code>+Z</code> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invert();
     * inv.transformDirection(dir.set(0, 0, 1)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveZ(Vector3f)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <code>+Z</code>
     * @return dir
     */
    public Vector3f positiveZ(Vector3f dir) {
        return delegate.positiveZ(dir);
    }

    /**
     * Obtain the direction of <code>+Z</code> before the transformation represented by <code>this</code> <i>orthogonal</i> matrix is applied.
     * This method only produces correct results if <code>this</code> is an <i>orthogonal</i> matrix.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction
     * that is transformed to <code>+Z</code> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).transpose();
     * inv.transformDirection(dir.set(0, 0, 1));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <code>+Z</code>
     * @return dir
     */
    public Vector3f normalizedPositiveZ(Vector3f dir) {
        return delegate.normalizedPositiveZ(dir);
    }

    /**
     * Obtain the direction of <code>+X</code> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction
     * that is transformed to <code>+X</code> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invert();
     * inv.transformDirection(dir.set(1, 0, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveX(Vector3f)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <code>+X</code>
     * @return dir
     */
    public Vector3f positiveX(Vector3f dir) {
        return delegate.positiveX(dir);
    }

    /**
     * Obtain the direction of <code>+X</code> before the transformation represented by <code>this</code> <i>orthogonal</i> matrix is applied.
     * This method only produces correct results if <code>this</code> is an <i>orthogonal</i> matrix.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction
     * that is transformed to <code>+X</code> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).transpose();
     * inv.transformDirection(dir.set(1, 0, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <code>+X</code>
     * @return dir
     */
    public Vector3f normalizedPositiveX(Vector3f dir) {
        return delegate.normalizedPositiveX(dir);
    }

    /**
     * Obtain the direction of <code>+Y</code> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction
     * that is transformed to <code>+Y</code> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invert();
     * inv.transformDirection(dir.set(0, 1, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveY(Vector3f)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <code>+Y</code>
     * @return dir
     */
    public Vector3f positiveY(Vector3f dir) {
        return delegate.positiveY(dir);
    }

    /**
     * Obtain the direction of <code>+Y</code> before the transformation represented by <code>this</code> <i>orthogonal</i> matrix is applied.
     * This method only produces correct results if <code>this</code> is an <i>orthogonal</i> matrix.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction
     * that is transformed to <code>+Y</code> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).transpose();
     * inv.transformDirection(dir.set(0, 1, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <code>+Y</code>
     * @return dir
     */
    public Vector3f normalizedPositiveY(Vector3f dir) {
        return delegate.normalizedPositiveY(dir);
    }

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> {@link #isAffine() affine} matrix.
     * This can be used to get the position of the "camera" from a given <i>view</i> transformation matrix.
     * <p>
     * This method only works with {@link #isAffine() affine} matrices.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invertAffine();
     * inv.transformPosition(origin.set(0, 0, 0));
     * </pre>
     *
     * @param origin will hold the position transformed to the origin
     * @return origin
     */
    public Vector3f originAffine(Vector3f origin) {
        return delegate.originAffine(origin);
    }

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> matrix.
     * This can be used to get the position of the "camera" from a given <i>view/projection</i> transformation matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invert();
     * inv.transformPosition(origin.set(0, 0, 0));
     * </pre>
     *
     * @param origin will hold the position transformed to the origin
     * @return origin
     */
    public Vector3f origin(Vector3f origin) {
        return delegate.origin(origin);
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane equation
     * <code>x*a + y*b + z*c + d = 0</code> as if casting a shadow from a given light position/direction <code>light</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>light.w</code> is <code>0.0</code> the light is being treated as a directional light; if it is <code>1.0</code> it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * reflection will be applied first!
     * <p>
     * Reference: <a href="ftp://ftp.sgi.com/opengl/contrib/blythe/advanced99/notes/node192.html">ftp.sgi.com</a>
     *
     * @param light the light's vector
     * @param a     the x factor in the plane equation
     * @param b     the y factor in the plane equation
     * @param c     the z factor in the plane equation
     * @param d     the constant in the plane equation
     * @param dest  will hold the result
     * @return dest
     */
    public Matrix4f shadow(Vector4f light, float a, float b, float c, float d, Matrix4f dest) {
        return delegate.shadow(light, a, b, c, d, dest);
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane equation
     * <code>x*a + y*b + z*c + d = 0</code> as if casting a shadow from a given light position/direction <code>(lightX, lightY, lightZ, lightW)</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>lightW</code> is <code>0.0</code> the light is being treated as a directional light; if it is <code>1.0</code> it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * reflection will be applied first!
     * <p>
     * Reference: <a href="ftp://ftp.sgi.com/opengl/contrib/blythe/advanced99/notes/node192.html">ftp.sgi.com</a>
     *
     * @param lightX the x-component of the light's vector
     * @param lightY the y-component of the light's vector
     * @param lightZ the z-component of the light's vector
     * @param lightW the w-component of the light's vector
     * @param a      the x factor in the plane equation
     * @param b      the y factor in the plane equation
     * @param c      the z factor in the plane equation
     * @param d      the constant in the plane equation
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d, Matrix4f dest) {
        return delegate.shadow(lightX, lightY, lightZ, lightW, a, b, c, d, dest);
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <code>y = 0</code> as if casting a shadow from a given light position/direction <code>light</code>
     * and store the result in <code>dest</code>.
     * <p>
     * Before the shadow projection is applied, the plane is transformed via the specified <code>planeTransformation</code>.
     * <p>
     * If <code>light.w</code> is <code>0.0</code> the light is being treated as a directional light; if it is <code>1.0</code> it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * reflection will be applied first!
     *
     * @param light          the light's vector
     * @param planeTransform the transformation to transform the implied plane <code>y = 0</code> before applying the projection
     * @param dest           will hold the result
     * @return dest
     */
    public Matrix4f shadow(Vector4f light, Matrix4fc planeTransform, Matrix4f dest) {
        return delegate.shadow(light, planeTransform, dest);
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <code>y = 0</code> as if casting a shadow from a given light position/direction <code>(lightX, lightY, lightZ, lightW)</code>
     * and store the result in <code>dest</code>.
     * <p>
     * Before the shadow projection is applied, the plane is transformed via the specified <code>planeTransformation</code>.
     * <p>
     * If <code>lightW</code> is <code>0.0</code> the light is being treated as a directional light; if it is <code>1.0</code> it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * reflection will be applied first!
     *
     * @param lightX         the x-component of the light vector
     * @param lightY         the y-component of the light vector
     * @param lightZ         the z-component of the light vector
     * @param lightW         the w-component of the light vector
     * @param planeTransform the transformation to transform the implied plane <code>y = 0</code> before applying the projection
     * @param dest           will hold the result
     * @return dest
     */
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4fc planeTransform, Matrix4f dest) {
        return delegate.shadow(lightX, lightY, lightZ, lightW, planeTransform, dest);
    }

    /**
     * Apply a picking transformation to this matrix using the given window coordinates <code>(x, y)</code> as the pick center
     * and the given <code>(width, height)</code> as the size of the picking region in window coordinates, and store the result
     * in <code>dest</code>.
     *
     * @param x        the x coordinate of the picking region center in window coordinates
     * @param y        the y coordinate of the picking region center in window coordinates
     * @param width    the width of the picking region in window coordinates
     * @param height   the height of the picking region in window coordinates
     * @param viewport the viewport described by <code>[x, y, width, height]</code>
     * @param dest     the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f pick(float x, float y, float width, float height, int[] viewport, Matrix4f dest) {
        return delegate.pick(x, y, width, height, viewport, dest);
    }

    /**
     * Determine whether this matrix describes an affine transformation. This is the case iff its last row is equal to <code>(0, 0, 0, 1)</code>.
     *
     * @return <code>true</code> iff this matrix is affine; <code>false</code> otherwise
     */
    public boolean isAffine() {
        return delegate.isAffine();
    }

    /**
     * Apply an arcball view transformation to this matrix with the given <code>radius</code> and center <code>(centerX, centerY, centerZ)</code>
     * position of the arcball and the specified X and Y rotation angles, and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <code>translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-centerX, -centerY, -centerZ)</code>
     *
     * @param radius  the arcball radius
     * @param centerX the x coordinate of the center position of the arcball
     * @param centerY the y coordinate of the center position of the arcball
     * @param centerZ the z coordinate of the center position of the arcball
     * @param angleX  the rotation angle around the X axis in radians
     * @param angleY  the rotation angle around the Y axis in radians
     * @param dest    will hold the result
     * @return dest
     */
    public Matrix4f arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY, Matrix4f dest) {
        return delegate.arcball(radius, centerX, centerY, centerZ, angleX, angleY, dest);
    }

    /**
     * Apply an arcball view transformation to this matrix with the given <code>radius</code> and <code>center</code>
     * position of the arcball and the specified X and Y rotation angles, and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <code>translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-center.x, -center.y, -center.z)</code>
     *
     * @param radius the arcball radius
     * @param center the center position of the arcball
     * @param angleX the rotation angle around the X axis in radians
     * @param angleY the rotation angle around the Y axis in radians
     * @param dest   will hold the result
     * @return dest
     */
    public Matrix4f arcball(float radius, Vector3fc center, float angleX, float angleY, Matrix4f dest) {
        return delegate.arcball(radius, center, angleX, angleY, dest);
    }

    /**
     * Compute the axis-aligned bounding box of the frustum described by <code>this</code> matrix and store the minimum corner
     * coordinates in the given <code>min</code> and the maximum corner coordinates in the given <code>max</code> vector.
     * <p>
     * The matrix <code>this</code> is assumed to be the {@link #invert(Matrix4f) inverse} of the origial view-projection matrix
     * for which to compute the axis-aligned bounding box in world-space.
     * <p>
     * The axis-aligned bounding box of the unit frustum is <code>(-1, -1, -1)</code>, <code>(1, 1, 1)</code>.
     *
     * @param min will hold the minimum corner coordinates of the axis-aligned bounding box
     * @param max will hold the maximum corner coordinates of the axis-aligned bounding box
     * @return this
     */
    public Matrix4f frustumAabb(Vector3f min, Vector3f max) {
        return delegate.frustumAabb(min, max);
    }

    /**
     * Compute the <i>range matrix</i> for the Projected Grid transformation as described in chapter "2.4.2 Creating the range conversion matrix"
     * of the paper <a href="http://fileadmin.cs.lth.se/graphics/theses/projects/projgrid/projgrid-lq.pdf">Real-time water rendering - Introducing the projected grid concept</a>
     * based on the <i>inverse</i> of the view-projection matrix which is assumed to be <code>this</code>, and store that range matrix into <code>dest</code>.
     * <p>
     * If the projected grid will not be visible then this method returns <code>null</code>.
     * <p>
     * This method uses the <code>y = 0</code> plane for the projection.
     *
     * @param projector the projector view-projection transformation
     * @param sLower    the lower (smallest) Y-coordinate which any transformed vertex might have while still being visible on the projected grid
     * @param sUpper    the upper (highest) Y-coordinate which any transformed vertex might have while still being visible on the projected grid
     * @param dest      will hold the resulting range matrix
     * @return the computed range matrix; or <code>null</code> if the projected grid will not be visible
     */
    public Matrix4f projectedGridRange(Matrix4fc projector, float sLower, float sUpper, Matrix4f dest) {
        return delegate.projectedGridRange(projector, sLower, sUpper, dest);
    }

    /**
     * Change the near and far clip plane distances of <code>this</code> perspective frustum transformation matrix
     * and store the result in <code>dest</code>.
     * <p>
     * This method only works if <code>this</code> is a perspective projection frustum transformation, for example obtained
     * via {@link #perspective(float, float, float, float, Matrix4f) perspective()} or {@link #frustum(float, float, float, float, float, float, Matrix4f) frustum()}.
     *
     * @param near the new near clip plane distance
     * @param far  the new far clip plane distance
     * @param dest will hold the resulting matrix
     * @return dest
     * @see #perspective(float, float, float, float, Matrix4f)
     * @see #frustum(float, float, float, float, float, float, Matrix4f)
     */
    public Matrix4f perspectiveFrustumSlice(float near, float far, Matrix4f dest) {
        return delegate.perspectiveFrustumSlice(near, far, dest);
    }

    /**
     * Build an ortographic projection transformation that fits the view-projection transformation represented by <code>this</code>
     * into the given affine <code>view</code> transformation.
     * <p>
     * The transformation represented by <code>this</code> must be given as the {@link #invert(Matrix4f) inverse} of a typical combined camera view-projection
     * transformation, whose projection can be either orthographic or perspective.
     * <p>
     * The <code>view</code> must be an {@link #isAffine() affine} transformation which in the application of Cascaded Shadow Maps is usually the light view transformation.
     * It be obtained via any affine transformation or for example via {@link #lookAt(float, float, float, float, float, float, float, float, float, Matrix4f) lookAt()}.
     * <p>
     * Reference: <a href="http://developer.download.nvidia.com/SDK/10.5/opengl/screenshots/samples/cascaded_shadow_maps.html">OpenGL SDK - Cascaded Shadow Maps</a>
     *
     * @param view the view transformation to build a corresponding orthographic projection to fit the frustum of <code>this</code>
     * @param dest will hold the crop projection transformation
     * @return dest
     */
    public Matrix4f orthoCrop(Matrix4fc view, Matrix4f dest) {
        return delegate.orthoCrop(view, dest);
    }

    /**
     * Transform the axis-aligned box given as the minimum corner <code>(minX, minY, minZ)</code> and maximum corner <code>(maxX, maxY, maxZ)</code>
     * by <code>this</code> {@link #isAffine() affine} matrix and compute the axis-aligned box of the result whose minimum corner is stored in <code>outMin</code>
     * and maximum corner stored in <code>outMax</code>.
     * <p>
     * Reference: <a href="http://dev.theomader.com/transform-bounding-boxes/">http://dev.theomader.com</a>
     *
     * @param minX   the x coordinate of the minimum corner of the axis-aligned box
     * @param minY   the y coordinate of the minimum corner of the axis-aligned box
     * @param minZ   the z coordinate of the minimum corner of the axis-aligned box
     * @param maxX   the x coordinate of the maximum corner of the axis-aligned box
     * @param maxY   the y coordinate of the maximum corner of the axis-aligned box
     * @param maxZ   the y coordinate of the maximum corner of the axis-aligned box
     * @param outMin will hold the minimum corner of the resulting axis-aligned box
     * @param outMax will hold the maximum corner of the resulting axis-aligned box
     * @return this
     */
    public Matrix4f transformAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Vector3f outMin, Vector3f outMax) {
        return delegate.transformAab(minX, minY, minZ, maxX, maxY, maxZ, outMin, outMax);
    }

    /**
     * Transform the axis-aligned box given as the minimum corner <code>min</code> and maximum corner <code>max</code>
     * by <code>this</code> {@link #isAffine() affine} matrix and compute the axis-aligned box of the result whose minimum corner is stored in <code>outMin</code>
     * and maximum corner stored in <code>outMax</code>.
     *
     * @param min    the minimum corner of the axis-aligned box
     * @param max    the maximum corner of the axis-aligned box
     * @param outMin will hold the minimum corner of the resulting axis-aligned box
     * @param outMax will hold the maximum corner of the resulting axis-aligned box
     * @return this
     */
    public Matrix4f transformAab(Vector3fc min, Vector3fc max, Vector3f outMin, Vector3f outMax) {
        return delegate.transformAab(min, max, outMin, outMax);
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <code>0.0</code> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     *
     * @param other the other matrix
     * @param t     the interpolation factor between 0.0 and 1.0
     * @param dest  will hold the result
     * @return dest
     */
    public Matrix4f lerp(Matrix4fc other, float t, Matrix4f dest) {
        return delegate.lerp(other, t, dest);
    }

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system,
     * that aligns the local <code>+Z</code> axis with <code>dir</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <code>mulAffine(new Matrix4f().lookAt(new Vector3f(), new Vector3f(dir).negate(), up).invertAffine(), dest)</code>
     *
     * @param dir  the direction to rotate towards
     * @param up   the up vector
     * @param dest will hold the result
     * @return dest
     * @see #rotateTowards(float, float, float, float, float, float, Matrix4f)
     */
    public Matrix4f rotateTowards(Vector3fc dir, Vector3fc up, Matrix4f dest) {
        return delegate.rotateTowards(dir, up, dest);
    }

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system,
     * that aligns the local <code>+Z</code> axis with <code>(dirX, dirY, dirZ)</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <code>mulAffine(new Matrix4f().lookAt(0, 0, 0, -dirX, -dirY, -dirZ, upX, upY, upZ).invertAffine(), dest)</code>
     *
     * @param dirX the x-coordinate of the direction to rotate towards
     * @param dirY the y-coordinate of the direction to rotate towards
     * @param dirZ the z-coordinate of the direction to rotate towards
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @param dest will hold the result
     * @return dest
     * @see #rotateTowards(Vector3fc, Vector3fc, Matrix4f)
     */
    public Matrix4f rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4f dest) {
        return delegate.rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, dest);
    }

    /**
     * Extract the Euler angles from the rotation represented by the upper left 3x3 submatrix of <code>this</code>
     * and store the extracted Euler angles in <code>dest</code>.
     * <p>
     * This method assumes that the upper left of <code>this</code> only represents a rotation without scaling.
     * <p>
     * Note that the returned Euler angles must be applied in the order <code>Z * Y * X</code> to obtain the identical matrix.
     * This means that calling {@link Matrix4fc#rotateZYX(float, float, float, Matrix4f)} using the obtained Euler angles will yield
     * the same rotation as the original matrix from which the Euler angles were obtained, so in the below code the matrix
     * <code>m2</code> should be identical to <code>m</code> (disregarding possible floating-point inaccuracies).
     * <pre>
     * Matrix4f m = ...; // &lt;- matrix only representing rotation
     * Matrix4f n = new Matrix4f();
     * n.rotateZYX(m.getEulerAnglesZYX(new Vector3f()));
     * </pre>
     * <p>
     * Reference: <a href="http://nghiaho.com/?page_id=846">http://nghiaho.com/</a>
     *
     * @param dest will hold the extracted Euler angles
     * @return dest
     */
    public Vector3f getEulerAnglesZYX(Vector3f dest) {
        return delegate.getEulerAnglesZYX(dest);
    }

    /**
     * Test whether the given point <code>(x, y, z)</code> is within the frustum defined by <code>this</code> matrix.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space <code>M</code>
     * into standard OpenGL clip space and tests whether the given point with the coordinates <code>(x, y, z)</code> given
     * in space <code>M</code> is within the clip space.
     * <p>
     * When testing multiple points using the same transformation matrix, {@link FrustumIntersection} should be used instead.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return <code>true</code> if the given point is inside the frustum; <code>false</code> otherwise
     */
    public boolean testPoint(float x, float y, float z) {
        return delegate.testPoint(x, y, z);
    }

    /**
     * Test whether the given sphere is partly or completely within or outside of the frustum defined by <code>this</code> matrix.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space <code>M</code>
     * into standard OpenGL clip space and tests whether the given sphere with the coordinates <code>(x, y, z)</code> given
     * in space <code>M</code> is within the clip space.
     * <p>
     * When testing multiple spheres using the same transformation matrix, or more sophisticated/optimized intersection algorithms are required,
     * {@link FrustumIntersection} should be used instead.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false positive</i>
     * can occur, when the method returns <code>true</code> for spheres that are actually not visible.
     * See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination of this problem.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param x the x-coordinate of the sphere's center
     * @param y the y-coordinate of the sphere's center
     * @param z the z-coordinate of the sphere's center
     * @param r the sphere's radius
     * @return <code>true</code> if the given sphere is partly or completely inside the frustum; <code>false</code> otherwise
     */
    public boolean testSphere(float x, float y, float z, float r) {
        return delegate.testSphere(x, y, z, r);
    }

    /**
     * Test whether the given axis-aligned box is partly or completely within or outside of the frustum defined by <code>this</code> matrix.
     * The box is specified via its min and max corner coordinates.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space <code>M</code>
     * into standard OpenGL clip space and tests whether the given axis-aligned box with its minimum corner coordinates <code>(minX, minY, minZ)</code>
     * and maximum corner coordinates <code>(maxX, maxY, maxZ)</code> given in space <code>M</code> is within the clip space.
     * <p>
     * When testing multiple axis-aligned boxes using the same transformation matrix, or more sophisticated/optimized intersection algorithms are required,
     * {@link FrustumIntersection} should be used instead.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false positive</i>
     * can occur, when the method returns <code>-1</code> for boxes that are actually not visible/do not intersect the frustum.
     * See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination of this problem.
     * <p>
     * Reference: <a href="http://old.cescg.org/CESCG-2002/DSykoraJJelinek/">Efficient View Frustum Culling</a>
     * <br>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param minX the x-coordinate of the minimum corner
     * @param minY the y-coordinate of the minimum corner
     * @param minZ the z-coordinate of the minimum corner
     * @param maxX the x-coordinate of the maximum corner
     * @param maxY the y-coordinate of the maximum corner
     * @param maxZ the z-coordinate of the maximum corner
     * @return <code>true</code> if the axis-aligned box is completely or partly inside of the frustum; <code>false</code> otherwise
     */
    public boolean testAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return delegate.testAab(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Apply an oblique projection transformation to this matrix with the given values for <code>a</code> and
     * <code>b</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the oblique transformation matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * oblique transformation will be applied first!
     * <p>
     * The oblique transformation is defined as:
     * <pre>
     * x' = x + a*z
     * y' = y + a*z
     * z' = z
     * </pre>
     * or in matrix form:
     * <pre>
     * 1 0 a 0
     * 0 1 b 0
     * 0 0 1 0
     * 0 0 0 1
     * </pre>
     *
     * @param a    the value for the z factor that applies to x
     * @param b    the value for the z factor that applies to y
     * @param dest will hold the result
     * @return dest
     */
    public Matrix4f obliqueZ(float a, float b, Matrix4f dest) {
        return delegate.obliqueZ(a, b, dest);
    }

    /**
     * Apply a transformation to this matrix to ensure that the local Y axis (as obtained by {@link #positiveY(Vector3f)})
     * will be coplanar to the plane spanned by the local Z axis (as obtained by {@link #positiveZ(Vector3f)}) and the
     * given vector <code>up</code>, and store the result in <code>dest</code>.
     * <p>
     * This effectively ensures that the resulting matrix will be equal to the one obtained from calling
     * {@link Matrix4f#setLookAt(Vector3fc, Vector3fc, Vector3fc)} with the current
     * local origin of this matrix (as obtained by {@link #originAffine(Vector3f)}), the sum of this position and the
     * negated local Z axis as well as the given vector <code>up</code>.
     * <p>
     * This method must only be called on {@link #isAffine()} matrices.
     *
     * @param up   the up vector
     * @param dest will hold the result
     * @return this
     */
    public Matrix4f withLookAtUp(Vector3fc up, Matrix4f dest) {
        return delegate.withLookAtUp(up, dest);
    }

    /**
     * Apply a transformation to this matrix to ensure that the local Y axis (as obtained by {@link #positiveY(Vector3f)})
     * will be coplanar to the plane spanned by the local Z axis (as obtained by {@link #positiveZ(Vector3f)}) and the
     * given vector <code>(upX, upY, upZ)</code>, and store the result in <code>dest</code>.
     * <p>
     * This effectively ensures that the resulting matrix will be equal to the one obtained from calling
     * {@link Matrix4f#setLookAt(float, float, float, float, float, float, float, float, float)} called with the current
     * local origin of this matrix (as obtained by {@link #originAffine(Vector3f)}), the sum of this position and the
     * negated local Z axis as well as the given vector <code>(upX, upY, upZ)</code>.
     * <p>
     * This method must only be called on {@link #isAffine()} matrices.
     *
     * @param upX  the x coordinate of the up vector
     * @param upY  the y coordinate of the up vector
     * @param upZ  the z coordinate of the up vector
     * @param dest will hold the result
     * @return this
     */
    public Matrix4f withLookAtUp(float upX, float upY, float upZ, Matrix4f dest) {
        return delegate.withLookAtUp(upX, upY, upZ, dest);
    }

    /**
     * Compare the matrix elements of <code>this</code> matrix with the given matrix using the given <code>delta</code>
     * and return whether all of them are equal within a maximum difference of <code>delta</code>.
     * <p>
     * Please note that this method is not used by any data structure such as {@link ArrayList} {@link HashSet} or {@link HashMap}
     * and their operations, such as {@link ArrayList#contains(Object)} or {@link HashSet#remove(Object)}, since those
     * data structures only use the {@link Object#equals(Object)} and {@link Object#hashCode()} methods.
     *
     * @param m     the other matrix
     * @param delta the allowed maximum difference
     * @return <code>true</code> whether all of the matrix elements are equal; <code>false</code> otherwise
     */
    public boolean equals(Matrix4fc m, float delta) {
        return delegate.equals(m, delta);
    }

    /**
     * Determine whether all matrix elements are finite floating-point values, that
     * is, they are not {@link Float#isNaN() NaN} and not
     * {@link Float#isInfinite() infinity}.
     *
     * @return {@code true} if all components are finite floating-point values;
     * {@code false} otherwise
     */
    public boolean isFinite() {
        return delegate.isFinite();
    }

}
