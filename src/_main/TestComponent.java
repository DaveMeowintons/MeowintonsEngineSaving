package _main;

import ecs.components.Component;
import utils.maths.matrices.Matrix2;
import utils.maths.matrices.Matrix3;
import utils.maths.matrices.Matrix4;
import utils.maths.matrices.Quaternion;
import utils.maths.vectors.Vector2;
import utils.maths.vectors.Vector3;
import utils.maths.vectors.Vector4;

import java.util.List;
import java.util.ArrayList;

public class TestComponent extends Component {
    private byte aByte = 0x01;
    private byte[] bytes = { 0x00, 0x01 };
    private boolean aBoolean = false;
    private boolean[] booleans = { true, false };
    private int anInt = 32;
    private int[] ints = { 32, 64 };
    private short aShort = 24;
    private short[] shorts = { 24, 48 };
    private long aLong = 512;
    private long[] longs = { 512, 1024 };
    private double aDouble = 24.0;
    private double[] doubles = { 24.0, 48.0 };
    private float aFloat = 32.0f;
    private float[] floats = { 32.0f, 64.0f };
    private char aChar = 'a';
    private char[] chars = { 'a', 'b' };
    private String aString = "string";
    private String[] strings = { "string", "string two!" };

    private Vector2 vector2 = new Vector2(0, 4);
    private Vector2[] vector2s = new Vector2[]{ new Vector2(0, 4), new Vector2(0, 8) };

    private Vector3 vector3 = new Vector3(2, 4, 5);
    private Vector3[] vector3s = new Vector3[]{ new Vector3(2, 4, 5), new Vector3(4, 8, 10) };

    private Vector4 vector4 = new Vector4(1, 1, 1, 1);
    private Vector4[] vector4s = new Vector4[]{ new Vector4(1, 1, 1, 1), new Vector4(1, 0, 1, 1) };

    private Quaternion quaternion = new Quaternion(1, 2, 5, 0);
    private Quaternion[] quaternions = new Quaternion[]{ new Quaternion(1, 2, 5, 0), new Quaternion(2, 4, 10, 2) };

    private Matrix2 matrix2 = new Matrix2(1, 2, 3, 4);
    private Matrix2[] matrix2s = new Matrix2[]{ new Matrix2(1, 2, 3, 4), new Matrix2(2, 4, 6, 8) };

    private Matrix3 matrix3 = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private Matrix3[] matrix3s = new Matrix3[]{ new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9), new Matrix3(2, 4, 6, 8, 10, 12, 14, 16, 18) };

    private Matrix4 matrix4 = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
    private Matrix4[] matrix4s = new Matrix4[]{ new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), new Matrix4(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32) };

    private List<Vector2> list = new ArrayList<>();

    public TestComponent(){
        list.add(new Vector2(5, 10));
    }
}
