package utils.reflection;

import ecs.components.Component;
import utils.maths.matrices.Matrix2;
import utils.maths.matrices.Matrix3;
import utils.maths.matrices.Matrix4;
import utils.maths.matrices.Quaternion;
import utils.maths.vectors.Vector2;
import utils.maths.vectors.Vector3;
import utils.maths.vectors.Vector4;
import utils.serialisation.dataObjects.TSArray;
import utils.serialisation.dataObjects.TSBase;
import utils.serialisation.dataObjects.TSField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TSParser {
    public static TSBase parseField(Component component, Field field){
        Object obj = ReflectionTools.readField(component, field);

        switch(obj.getClass().getSimpleName()){
            case "Byte":            return TSField.Byte         (field.getName(), (byte)obj);
            case "byte[]":          return TSArray.Byte         (field.getName(), (byte[])obj);
            case "Short":           return TSField.Short        (field.getName(), (short)obj);
            case "short[]":         return TSArray.Short        (field.getName(), (short[])obj);
            case "Integer":         return TSField.Integer      (field.getName(), (int)obj);
            case "int[]":           return TSArray.Integer      (field.getName(), (int[])obj);
            case "Long":            return TSField.Long         (field.getName(), (long)obj);
            case "long[]":          return TSArray.Long         (field.getName(), (long[])obj);
            case "Float":           return TSField.Float        (field.getName(), (float)obj);
            case "float[]":         return TSArray.Float        (field.getName(), (float[])obj);
            case "Double":          return TSField.Double       (field.getName(), (double)obj);
            case "double[]":        return TSArray.Double       (field.getName(), (double[])obj);
            case "Boolean":         return TSField.Boolean      (field.getName(), (boolean)obj);
            case "boolean[]":       return TSArray.Boolean      (field.getName(), (boolean[])obj);
            case "Character":       return TSField.Char         (field.getName(), (char)obj);
            case "char[]":          return TSArray.Char         (field.getName(), (char[])obj);
            case "String":          return TSField.String       (field.getName(), (String)obj);
            case "String[]":        return TSArray.String       (field.getName(), (String[])obj);
            case "Vector2":         return TSField.Vector2      (field.getName(), (Vector2)obj);
            case "Vector2[]":       return TSArray.Vector2      (field.getName(), (Vector2[])obj);
            case "Vector3":         return TSField.Vector3      (field.getName(), (Vector3)obj);
            case "Vector3[]":       return TSArray.Vector3      (field.getName(), (Vector3[])obj);
            case "Vector4":         return TSField.Vector4      (field.getName(), (Vector4)obj);
            case "Vector4[]":       return TSArray.Vector4      (field.getName(), (Vector4[])obj);
            case "Quaternion":      return TSField.Quaternion   (field.getName(), (Quaternion)obj);
            case "Quaternion[]":    return TSArray.Quaternion   (field.getName(), (Quaternion[])obj);
            case "Matrix2":         return TSField.Matrix2      (field.getName(), (Matrix2)obj);
            case "Matrix2[]":       return TSArray.Matrix2      (field.getName(), (Matrix2[])obj);
            case "Matrix3":         return TSField.Matrix3      (field.getName(), (Matrix3)obj);
            case "Matrix3[]":       return TSArray.Matrix3      (field.getName(), (Matrix3[])obj);
            case "Matrix4":         return TSField.Matrix4      (field.getName(), (Matrix4)obj);
            case "Matrix4[]":       return TSArray.Matrix4      (field.getName(), (Matrix4[])obj);
            case "ArrayList":
                List list = (ArrayList)obj;
                if(list.size() == 0) break;

                switch(list.get(0).getClass().getSimpleName()){
                    case "Byte":
                        byte[] bytes = new byte[list.size()];
                        for(int i = 0; i < list.size(); i++) bytes[i] = (byte)list.get(i);
                        return TSArray.Byte(field.getName(), bytes);
                    case "Short":
                        short[] shorts = new short[list.size()];
                        for(int i = 0; i < list.size(); i++) shorts[i] = (short)list.get(i);
                        return TSArray.Short(field.getName(), shorts);
                    case "Integer":
                        int[] ints = new int[list.size()];
                        for(int i = 0; i < list.size(); i++) ints[i] = (int)list.get(i);
                        return TSArray.Integer(field.getName(), ints);
                    case "Long":
                        long[] longs = new long[list.size()];
                        for(int i = 0; i < list.size(); i++) longs[i] = (long)list.get(i);
                        return TSArray.Long(field.getName(), longs);
                    case "Float":
                        float[] floats = new float[list.size()];
                        for(int i = 0; i < list.size(); i++) floats[i] = (float)list.get(i);
                        return TSArray.Float(field.getName(), floats);
                    case "Double":
                        double[] doubles = new double[list.size()];
                        for(int i = 0; i < list.size(); i++) doubles[i] = (double)list.get(i);
                        return TSArray.Double(field.getName(), doubles);
                    case "Boolean":
                        boolean[] booleans = new boolean[list.size()];
                        for(int i = 0; i < list.size(); i++) booleans[i] = (boolean)list.get(i);
                        return TSArray.Boolean(field.getName(), booleans);
                    case "Character":
                        char[] chars = new char[list.size()];
                        for(int i = 0; i < list.size(); i++) chars[i] = (char)list.get(i);
                        return TSArray.Char(field.getName(), chars);
                    case "String":
                        String[] strings = new String[list.size()];
                        for(int i = 0; i < list.size(); i++) strings[i] = (String)list.get(i);
                        return TSArray.String(field.getName(), strings);
                    case "Vector2":
                        Vector2[] vector2s = new Vector2[list.size()];
                        for(int i = 0; i < list.size(); i++) vector2s[i] = (Vector2)list.get(i);
                        return TSArray.Vector2(field.getName(), vector2s);
                    case "Vector3":
                        Vector3[] vector3s = new Vector3[list.size()];
                        for(int i = 0; i < list.size(); i++) vector3s[i] = (Vector3)list.get(i);
                        return TSArray.Vector3(field.getName(), vector3s);
                    case "Vector4":
                        Vector4[] vector4s = new Vector4[list.size()];
                        for(int i = 0; i < list.size(); i++) vector4s[i] = (Vector4)list.get(i);
                        return TSArray.Vector4(field.getName(), vector4s);
                    case "Quaternion":
                        Quaternion[] quaternions = new Quaternion[list.size()];
                        for(int i = 0; i < list.size(); i++) quaternions[i] = (Quaternion)list.get(i);
                        return TSArray.Quaternion(field.getName(), quaternions);
                    case "Matrix2":
                        Matrix2[] matrix2s = new Matrix2[list.size()];
                        for(int i = 0; i < list.size(); i++) matrix2s[i] = (Matrix2)list.get(i);
                        return TSArray.Matrix2(field.getName(), matrix2s);
                    case "Matrix3":
                        Matrix3[] matrix3s = new Matrix3[list.size()];
                        for(int i = 0; i < list.size(); i++) matrix3s[i] = (Matrix3)list.get(i);
                        return TSArray.Matrix3(field.getName(), matrix3s);
                    case "Matrix4":
                        Matrix4[] matrix4s = new Matrix4[list.size()];
                        for(int i = 0; i < list.size(); i++) matrix4s[i] = (Matrix4)list.get(i);
                        return TSArray.Matrix4(field.getName(), matrix4s);
                }

                break;
        }

        return null;
    }

    public static List convertToList(Object object){
        switch(object.getClass().getSimpleName()){
            case "byte[]":
                List<Byte> bytes = new ArrayList<>();
                for(byte b : (byte[])object) bytes.add(b);
                return bytes;
            case "short[]":
                List<Short> shorts = new ArrayList<>();
                for(short s : (short[])object) shorts.add(s);
                return shorts;
            case "int[]":
                List<Integer> ints = new ArrayList<>();
                for(int i : (int[])object) ints.add(i);
                return ints;
            case "long[]":
                List<Long> longs = new ArrayList<>();
                for(long l : (long[])object) longs.add(l);
                return longs;
            case "float[]":
                List<Float> floats = new ArrayList<>();
                for(float f : (float[])object) floats.add(f);
                return floats;
            case "double[]":
                List<Double> doubles = new ArrayList<>();
                for(double d : (double[])object) doubles.add(d);
                return doubles;
            case "Boolean[]":
                List<Boolean> booleans = new ArrayList<>();
                for(Boolean bool : (boolean[])object) booleans.add(bool);
                return booleans;
            case "char[]":
                List<Character> chars = new ArrayList<>();
                for(char c : (char[])object) chars.add(c);
                return chars;
            case "String[]":
                List<String> strings = new ArrayList<>();
                for(String s : (String[])object) strings.add(s);
                return strings;
            case "Vector2[]":
                List<Vector2> vector2s = new ArrayList<>();
                for(Vector2 vector2 : (Vector2[])object) vector2s.add(vector2);
                return vector2s;
            case "Vector3[]":
                List<Vector3> vector3s = new ArrayList<>();
                for(Vector3 vector3 : (Vector3[])object) vector3s.add(vector3);
                return vector3s;
            case "Vector4[]":
                List<Vector4> vector4s = new ArrayList<>();
                for(Vector4 vector4 : (Vector4[])object) vector4s.add(vector4);
                return vector4s;
            case "Quaternion[]":
                List<Quaternion> quaternions = new ArrayList<>();
                for(Quaternion quaternion : (Quaternion[])object) quaternions.add(quaternion);
                return quaternions;
            case "Matrix2[]":
                List<Matrix2> matrix2s = new ArrayList<>();
                for(Matrix2 matrix2 : (Matrix2[])object) matrix2s.add(matrix2);
                return matrix2s;
            case "Matrix3[]":
                List<Matrix3> matrix3s = new ArrayList<>();
                for(Matrix3 matrix3 : (Matrix3[])object) matrix3s.add(matrix3);
                return matrix3s;
            case "Matrix4[]":
                List<Matrix4> matrix4s = new ArrayList<>();
                for(Matrix4 matrix4 : (Matrix4[])object) matrix4s.add(matrix4);
                return matrix4s;
        }

        return null;
    }
}
