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
        }

        return null;
    }
}
