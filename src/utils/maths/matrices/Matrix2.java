package utils.maths.matrices;

import utils.maths.vectors.Vector2;

public class Matrix2 {
    private float m00, m01,
                  m10, m11;

    public Matrix2(float m00, float m01,
                   float m10, float m11){ this.set(m00, m01, m10, m11); }
    public Matrix2(Vector2 col0        ,
                   Vector2 col1        ){ this.set(col0    , col1    ); }
    public Matrix2(Matrix2 matrix2     ){ this.set(matrix2           ); }
    public Matrix2(                    ){ this.identity(); }

    public Matrix2 identity(){
        return set(
                1.0f, 0.0f,
                0.0f, 1.0f
        );
    }

    /**Getters**/

    public float m00(){ return m00; } public float m01(){ return m01; }
    public float m10(){ return m10; } public float m11(){ return m11; }

    /**Setters**/

    public Matrix2 m00(float m00){ this.m00 = m00; return this; } public Matrix2 m01(float m01){ this.m01 = m01; return this; }
    public Matrix2 m10(float m10){ this.m10 = m10; return this; } public Matrix2 m11(float m11){ this.m11 = m11; return this; }

    public Matrix2 set(float m00, float m01, float m10, float m11){
        return m00(m00).m01(m01).
               m10(m10).m11(m11);
    }

    public Matrix2 set(Vector2 col0, Vector2 col1){
        return m00(col0.getX()).m01(col0.getY()).
               m10(col1.getX()).m11(col1.getY());
    }

    public Matrix2 set(Matrix2 matrix2){
        return m00(matrix2.m00()).m01(matrix2.m01()).
               m10(matrix2.m10()).m11(matrix2.m11());
    }
}
