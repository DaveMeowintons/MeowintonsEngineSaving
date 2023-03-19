package ecs;

import utils.maths.matrices.Matrix3;
import utils.maths.vectors.Vector3;

public class Transformation {
    private final Matrix3 basis = new Matrix3();
    private final Vector3 origin = new Vector3();

    public Transformation(               ){ }

    public Transformation(Matrix3 matrix3){ basis.set(matrix3); }

    public Transformation(Matrix3 matrix3, Vector3 vector3){ basis.set(matrix3); vector3.set(vector3); }

    public Transformation(Transformation transformation){ set(transformation); }

    public void identity(){
        basis.identity();
        origin.set(0f, 0f, 0f);
    }

        //Transform

    public void transform(Vector3 vector3){
        basis.transform(vector3);
        vector3.add(origin);
    }

        //Inverse

    public void inverse(){
        basis.transpose();
        origin.scale(-1);
        basis.transform(origin);
    }

    public void inverse(Transformation transformation){
        set(transformation);
        inverse();
    }

        //Mul

    public void mul(Transformation transformation){
        Vector3 vec = new Vector3(transformation.getOrigin());
        transform(vec);

        basis.mul(transformation.getBasis());
        origin.set(vec);
    }

    public void mul(Transformation transformation1, Transformation transformation2){
        Vector3 vec = new Vector3(transformation2.getOrigin());
        transformation1.transform(vec);

        transformation1.getBasis().mul(transformation2.getBasis(), basis);
        origin.set(vec);
    }

    /**Getters**/

    public Matrix3 getBasis(){ return basis; }
    public Vector3 getOrigin(){ return origin; }

    /**Setters**/

    public void set(Transformation transformation){
        basis.set(transformation.getBasis());
        origin.set(transformation.getOrigin());
    }

    public void set(Matrix3 matrix3){
        basis.set(matrix3);
        origin.set(0, 0, 0);
    }
}
