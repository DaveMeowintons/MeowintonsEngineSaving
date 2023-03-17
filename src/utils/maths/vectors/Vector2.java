package utils.maths.vectors;

public class Vector2 {
    private float x, y;

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2(float   i      ){ this(i             , i             ); }
    public Vector2(Vector2 vector2){ this(vector2.getX(), vector2.getY()); }
    public Vector2(               ){ this(0); }

    /**Getters**/

    public float getX(){ return x; }
    public float getY(){ return y; }
}
