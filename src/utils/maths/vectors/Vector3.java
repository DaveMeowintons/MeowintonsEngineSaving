package utils.maths.vectors;

public class Vector3 {
    private float x, y, z;

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(float   i               ){ this(i             , i             , i             ); }
    public Vector3(Vector2 vector2, float z){ this(vector2.getX(), vector2.getY(), z             ); }
    public Vector3(Vector3 vector3         ){ this(vector3.getX(), vector3.getY(), vector3.getZ()); }
    public Vector3(                        ){ this(0); }

    /**Getters**/

    public float getX(){ return x; }
    public float getY(){ return y; }
    public float getZ(){ return z; }
}
