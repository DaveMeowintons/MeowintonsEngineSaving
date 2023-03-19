package utils.maths.matrices;

import utils.maths.vectors.Vector3;

public class Matrix3 {
    private float m00, m01, m02,
                  m10, m11, m12,
                  m20, m21, m22;

    public Matrix3(float m00, float m01, float m02,
                   float m10, float m11, float m12,
                   float m20, float m21, float m22){ this.set(m00, m01, m02, m10, m11, m12, m20, m21, m22); }
    public Matrix3(Vector3 col0                   ,
                   Vector3 col1                   ,
                   Vector3 col2                   ){ this.set(col0         , col1         , col2         ); }
    public Matrix3(Matrix3 matrix3                ){ this.set(matrix3                                    ); }
    public Matrix3(                               ){ this.identity(); }

    public Matrix3 identity(){
        return set(
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f
        );
    }

        //Add

        //Sub

        //Mul

    public Matrix3 mul(Matrix3 matrix3, Matrix3 dest){
        return dest.set(
                this.m00() * matrix3.m00() + this.m01() * matrix3.m10() + this.m02() * matrix3.m20(),
                this.m00() * matrix3.m01() + this.m01() * matrix3.m11() + this.m02() * matrix3.m21(),
                this.m00() * matrix3.m02() + this.m01() * matrix3.m12() + this.m02() * matrix3.m22(),
                this.m10() * matrix3.m00() + this.m11() * matrix3.m10() + this.m12() * matrix3.m20(),
                this.m10() * matrix3.m01() + this.m11() * matrix3.m11() + this.m12() * matrix3.m21(),
                this.m10() * matrix3.m02() + this.m11() * matrix3.m12() + this.m12() * matrix3.m22(),
                this.m20() * matrix3.m00() + this.m21() * matrix3.m10() + this.m22() * matrix3.m20(),
                this.m20() * matrix3.m01() + this.m21() * matrix3.m11() + this.m22() * matrix3.m21(),
                this.m20() * matrix3.m02() + this.m21() * matrix3.m12() + this.m22() * matrix3.m22()
        );
    }

    public Matrix3 mul(Matrix3 matrix3){ return this.mul(matrix3, this); }

    public Matrix3 mul(float i, Matrix3 dest){
        dest.m00 *= i; dest.m01 *= i; dest.m02 *= i;
        dest.m10 *= i; dest.m11 *= i; dest.m12 *= i;
        dest.m20 *= i; dest.m21 *= i; dest.m22 *= i;
        return dest;
    }

    public Matrix3 mul(float i){ return mul(i, this); }

    public Vector3 mul(Vector3 vector, Vector3 dest, Matrix3 src) {
        return dest.set(
                src.m00 * vector.getX() + src.m01 * vector.getY() + src.m02 * vector.getZ(),
                src.m10 * vector.getX() + src.m11 * vector.getY() + src.m12 * vector.getZ(),
                src.m20 * vector.getX() + src.m21 * vector.getY() + src.m22 * vector.getZ()
        );
    }

    public Vector3 mul(Vector3 vector, Vector3 dest){ return mul(vector, dest, this); }

    public Matrix3 mul(Vector3 s, Matrix3 dest){
        return dest.set(
                this.m00 * s.getX(), this.m01 * s.getY(), this.m02 * s.getZ(),
                this.m10 * s.getX(), this.m11 * s.getY(), this.m12 * s.getZ(),
                this.m20 * s.getX(), this.m21 * s.getY(), this.m22 * s.getZ()
        );
    }

        //Div

        //Transform

    public Matrix3 transform(Vector3 vector3){
        transform(vector3, vector3);

        return this;
    }

    public Matrix3 transform(Vector3 vector3, Vector3 result){
        float x, y, z;
        x = (m00 * vector3.getX()) + (m01 * vector3.getY()) + (m02 * vector3.getZ());
        y = (m10 * vector3.getX()) + (m11 * vector3.getY()) + (m12 * vector3.getZ());
        z = (m20 * vector3.getX()) + (m21 * vector3.getY()) + (m22 * vector3.getZ());

        result.set(x, y, z);

        return this;
    }

        //Transpose

    public Matrix3 transpose(Matrix3 destination){
        return destination.set(
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22
        );
    }

    public Matrix3 transpose(){ return transpose(this); }

    /**Getters**/

    public float m00(){ return m00; } public float m01(){ return m01; } public float m02(){ return m02; }
    public float m10(){ return m10; } public float m11(){ return m11; } public float m12(){ return m12; }
    public float m20(){ return m20; } public float m21(){ return m21; } public float m22(){ return m22; }

    /**Setters**/

    public Matrix3 m00(float m00){ this.m00 = m00; return this; } public Matrix3 m01(float m01){ this.m01 = m01; return this; } public Matrix3 m02(float m02){ this.m02 = m02; return this; }
    public Matrix3 m10(float m10){ this.m10 = m10; return this; } public Matrix3 m11(float m11){ this.m11 = m11; return this; } public Matrix3 m12(float m12){ this.m12 = m12; return this; }
    public Matrix3 m20(float m20){ this.m20 = m20; return this; } public Matrix3 m21(float m21){ this.m21 = m21; return this; } public Matrix3 m22(float m22){ this.m22 = m22; return this; }

    public Matrix3 set(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22){
        return m00(m00).m01(m01).m02(m02).
               m10(m10).m11(m11).m12(m12).
               m20(m20).m21(m21).m22(m22);
    }

    public Matrix3 set(Vector3 col0, Vector3 col1, Vector3 col2){
        return m00(col0.getX()).m01(col0.getY()).m02(col0.getZ()).
               m10(col1.getX()).m11(col1.getY()).m12(col1.getZ()).
               m20(col2.getX()).m21(col2.getY()).m22(col2.getZ());
    }

    public Matrix3 set(Matrix3 matrix3){
        return m00(matrix3.m00()).m01(matrix3.m01()).m02(matrix3.m02()).
               m10(matrix3.m10()).m11(matrix3.m10()).m12(matrix3.m12()).
               m20(matrix3.m20()).m21(matrix3.m21()).m22(matrix3.m22());
    }

    public Matrix3 set(float[] m){
        return m00(m[ 0]).m01(m[ 4]).m02(m[ 8]).
               m10(m[ 1]).m11(m[ 5]).m12(m[ 9]).
               m20(m[ 2]).m21(m[ 6]).m22(m[10]);
    }
}
