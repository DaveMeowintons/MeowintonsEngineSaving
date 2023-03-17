package utils.maths.matrices;

public class Quaternion {
    private float x, y, z, w;

    public Quaternion(float x, float y, float z, float w){
        float mag = (float)(1.0/Math.sqrt(x*x + y*y + z*z + w*w));
        this.x = x * mag;
        this.y = y * mag;
        this.z = z * mag;
        this.w = w * mag;
    }

    public Quaternion(float i){ this(i, i, i, i); }
    public Quaternion(       ){ this(0); }

    /**Getters**/

    public float getX(){ return x; }
    public float getY(){ return y; }
    public float getZ(){ return z; }
    public float getW(){ return w; }
}
