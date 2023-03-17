package utils.maths.vectors;

public class Vector4 {
    private float x, y, z, w;

    public Vector4(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(float   i               ){ this(i             , i             , i             , i             ); }
    public Vector4(Vector3 vector3, float w){ this(vector3.getX(), vector3.getY(), vector3.getZ(), w             ); }
    public Vector4(Vector4 vector4         ){ this(vector4.getX(), vector4.getY(), vector4.getZ(), vector4.getW()); }
    public Vector4(                        ){ this(0); }

    /**Getters**/

    public float getX(){ return x; }
    public float getY(){ return y; }
    public float getZ(){ return z; }
    public float getW(){ return w; }
}
