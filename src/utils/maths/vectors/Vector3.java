package utils.maths.vectors;

import utils.maths.matrices.Quaternion;

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

        //Add

    public Vector3 add(float x, float y, float z, Vector3 dest){
        dest.set(this.x + x, this.y + y, this.z + z);
        return dest;
    }

    public Vector3 add(float i        , Vector3 dest){ return this.add(i             , i             , i             , dest); }
    public Vector3 add(Vector3 vector3, Vector3 dest){ return this.add(vector3.getX(), vector3.getY(), vector3.getZ(), dest); }

    public Vector3 add(float x        , float y        , float z        ){ return this.add(x, y, z, this); }
    public Vector3 add(float i                                          ){ return this.add(i, i, i, this); }
    public Vector3 add(Vector3 vector3                                  ){ return this.add(vector3, this); }

        //Sub

    public Vector3 sub(float x, float y, float z, Vector3 dest){
        dest.set(this.x - x, this.y - y, this.z - z);
        return dest;
    }

    public Vector3 sub(float i        , Vector3 dest){ return this.sub(i             , i             , i             , dest); }
    public Vector3 sub(Vector3 vector3, Vector3 dest){ return this.sub(vector3.getX(), vector3.getY(), vector3.getZ(), dest); }

    public Vector3 sub(float x        , float y        , float z        ){ return this.sub(x, y, z, this); }
    public Vector3 sub(float i                                          ){ return this.sub(i, i, i, this); }
    public Vector3 sub(Vector3 vector3                                  ){ return this.sub(vector3, this); }

        //Mul

    public Vector3 mul(float x, float y, float z, Vector3 dest){
        dest.set(this.x * x, this.y * y, this.z * z);
        return dest;
    }

    public Vector3 mul(float i        , Vector3 dest){ return this.mul(i             , i             , i             , dest); }
    public Vector3 mul(Vector3 vector3, Vector3 dest){ return this.mul(vector3.getX(), vector3.getY(), vector3.getZ(), dest); }

    public Vector3 mul(float x        , float y        , float z        ){ return this.mul(x, y, z, this); }
    public Vector3 mul(float i                                          ){ return this.mul(i, i, i, this); }
    public Vector3 mul(Vector3 vector3                                  ){ return this.mul(vector3, this); }

        //Div

    public Vector3 div(float x, float y, float z, Vector3 dest){
        dest.set(this.x / x, this.y / y, this.z / z);
        return dest;
    }

    public Vector3 div(float i        , Vector3 dest){ return this.div(i             , i             , i             , dest); }
    public Vector3 div(Vector3 vector3, Vector3 dest){ return this.div(vector3.getX(), vector3.getY(), vector3.getZ(), dest); }

    public Vector3 div(float x        , float y        , float z        ){ return this.div(x, y, z, this); }
    public Vector3 div(float i                                          ){ return this.div(i, i, i, this); }
    public Vector3 div(Vector3 vector3                                  ){ return this.div(vector3, this); }

        //Scale

    public Vector3 scale(float i, Vector3 dest){ return this.mul(i, dest); }
    public Vector3 scale(float i              ){ return this.mul(i, this); }

    public Vector3 scaleAdd(float i, Vector3 add, Vector3 dest){
        dest.set(i*this.x + add.getX(), i*this.y + add.getY(), i*this.z + add.getZ());

        return dest;
    }
    public Vector3 scaleAdd(float i, Vector3 add              ){ return scaleAdd(i, add, this); }

    /**Getters**/

    public float getX(){ return x; }
    public float getY(){ return y; }
    public float getZ(){ return z; }

    /**Setters**/

    public Vector3 set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3 set(float i                  ){ return this.set(i                , i                , i                ); }
    public Vector3 set(Vector3 vector3          ){ return this.set(vector3.getX()   , vector3.getY()   , vector3.getZ()   ); }
    public Vector3 set(Quaternion quaternion    ){ return this.set(quaternion.getX(), quaternion.getY(), quaternion.getZ()); }
    public Vector3 set(                         ){ return this.set(0); }

}
