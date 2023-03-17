package utils.maths.matrices;

import utils.maths.vectors.Vector4;

public class Matrix4 {
    public float m00, m01, m02, m03,
                 m10, m11, m12, m13,
                 m20, m21, m22, m23,
                 m30, m31, m32, m33;

    public Matrix4(float m00, float m01, float m02, float m03,
                   float m10, float m11, float m12, float m13,
                   float m20, float m21, float m22, float m23,
                   float m30, float m31, float m32, float m33){ this.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33); }
    public Matrix4(Vector4 col0                              ,
                   Vector4 col1                              ,
                   Vector4 col2                              ,
                   Vector4 col3                              ){ this.set(col0              , col1              , col2              , col3              ); }
    public Matrix4(Matrix4 matrix4                           ){ this.set(matrix4                                                                       ); }
    public Matrix4(                                          ){ this.identity(); }

    public Matrix4 identity(){
        return set(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        );
    }

    /**Getters**/

    public float m00(){ return m00; } public float m01(){ return m01; } public float m02(){ return m02; } public float m03(){ return m03; }
    public float m10(){ return m10; } public float m11(){ return m11; } public float m12(){ return m12; } public float m13(){ return m13; }
    public float m20(){ return m20; } public float m21(){ return m21; } public float m22(){ return m22; } public float m23(){ return m23; }
    public float m30(){ return m30; } public float m31(){ return m31; } public float m32(){ return m32; } public float m33(){ return m33; }


    /**Setters**/

    public Matrix4 m00(float m00){ this.m00 = m00; return this; } public Matrix4 m01(float m01){ this.m01 = m01; return this; } public Matrix4 m02(float m02){ this.m02 = m02; return this; } public Matrix4 m03(float m03){ this.m03 = m03; return this; }
    public Matrix4 m10(float m10){ this.m10 = m10; return this; } public Matrix4 m11(float m11){ this.m11 = m11; return this; } public Matrix4 m12(float m12){ this.m12 = m12; return this; } public Matrix4 m13(float m13){ this.m13 = m13; return this; }
    public Matrix4 m20(float m20){ this.m20 = m20; return this; } public Matrix4 m21(float m21){ this.m21 = m21; return this; } public Matrix4 m22(float m22){ this.m22 = m22; return this; } public Matrix4 m23(float m23){ this.m23 = m23; return this; }
    public Matrix4 m30(float m30){ this.m30 = m30; return this; } public Matrix4 m31(float m31){ this.m31 = m31; return this; } public Matrix4 m32(float m32){ this.m32 = m32; return this; } public Matrix4 m33(float m33){ this.m33 = m33; return this; }

    public Matrix4 set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33){
        return m00(m00).m01(m01).m02(m02).m03(m03).
               m10(m10).m11(m11).m12(m12).m13(m13).
               m20(m20).m21(m21).m22(m22).m23(m23).
               m30(m30).m31(m31).m32(m32).m33(m33);
    }

    public Matrix4 set(Vector4 col0, Vector4 col1, Vector4 col2, Vector4 col3){
        return m00(col0.getX()).m01(col0.getY()).m02(col0.getZ()).m03(col0.getW()).
               m10(col1.getX()).m11(col1.getY()).m12(col1.getZ()).m13(col1.getW()).
               m20(col2.getX()).m21(col2.getY()).m22(col2.getZ()).m23(col2.getW()).
               m30(col3.getX()).m31(col3.getY()).m32(col3.getZ()).m33(col3.getW());
    }

    public Matrix4 set(Matrix4 matrix4){
        return m00(matrix4.m00()).m01(matrix4.m01()).m02(matrix4.m02()).m03(matrix4.m03()).
               m10(matrix4.m10()).m11(matrix4.m11()).m12(matrix4.m12()).m13(matrix4.m13()).
               m20(matrix4.m20()).m21(matrix4.m21()).m22(matrix4.m22()).m23(matrix4.m23()).
               m30(matrix4.m30()).m31(matrix4.m31()).m32(matrix4.m32()).m33(matrix4.m33());
    }

    public Matrix4 set(Matrix3 matrix3){
        return m00(matrix3.m00()).m01(matrix3.m01()).m02(matrix3.m02()).m03(0.0f         ).
               m10(matrix3.m10()).m11(matrix3.m11()).m12(matrix3.m12()).m13(0.0f         ).
               m20(matrix3.m20()).m21(matrix3.m21()).m22(matrix3.m22()).m23(0.0f         ).
               m30(0.0f         ).m31(0.0f         ).m32(0.0f         ).m33(1.0f         );
    }
}
