package utils.files.readers;

import utils.Loader;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.maths.matrices.Matrix2;
import utils.maths.matrices.Matrix3;
import utils.maths.matrices.Matrix4;
import utils.maths.matrices.Quaternion;
import utils.maths.vectors.Vector2;
import utils.maths.vectors.Vector3;
import utils.maths.vectors.Vector4;
import utils.serialisation.types.TSDataType;

import java.io.BufferedInputStream;
import java.io.File;

public class TSReader extends FileReader {
    protected BufferedInputStream reader;
    protected byte[]              data;

    @Override
    public void open(Loader loader, String file){
        this.fileName = file;
        this.reader = loader.getSerialisedData(file);

        readData();
    }

    @Override
    public void open(Loader loader, File file){
        this.fileName = file.getName();
        this.reader = loader.getSerialisedData(file);

        readData();
    }

    @Override
    public void close(){
        try{
            this.reader.close();
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), "Error closing reader for file: " + this.fileName);
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), error);
        }
    }

    /**
     * Read all data in file and store it into an array
     */
    private void readData(){
        try{
            this.data = new byte[reader.available()];
            this.reader.read(this.data);
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), error);
        }
    }

    /**Byte**/
    public static byte         readByte        (byte[] src, int pointer){ return src[pointer]; }
    public static byte[]       readBytes       (byte[] src, int pointer, int length){
        byte[] dest = new byte[length];
        for (int i = 0; i < length; i++) dest[i] = readByte(src, pointer + i);
        return dest;
    }

    /**Short**/
    public static short        readShort       (byte[] src, int pointer){ return (short)((src[pointer] & 0xff) << 8 | (src[pointer + 1] & 0xff)); }
    public static short[]      readShorts      (byte[] src, int pointer, int length){
        short[] dest = new short[length];
        for (int i = 0; i < length; i++){ dest[i] = readShort(src, pointer); pointer += TSDataType.getSize(TSDataType.SHORT); }
        return dest;
    }

    /**Integer**/
    public static int          readInt         (byte[] src, int pointer){ return (int)((src[pointer] & 0xff) << 24 | (src[pointer + 1] & 0xff) << 16 | (src[pointer + 2] & 0xff) << 8 | (src[pointer + 3] & 0xff)); }
    public static int[]        readInts        (byte[] src, int pointer, int length){
        int[] dest = new int[length];
        for (int i = 0; i < length; i++){ dest[i] = readInt(src, pointer); pointer += TSDataType.getSize(TSDataType.INTEGER); }
        return dest;
    }

    /**Long**/
    public static long         readLong        (byte[] src, int pointer){ return (long)((src[pointer] & 0xff) << 56 | (src[pointer + 1] & 0xff) << 48 | (src[pointer + 2] & 0xff) << 40 | (src[pointer + 3] & 0xff) << 32 | (src[pointer + 4] & 0xff) << 24 | (src[pointer + 5] & 0xff) << 16 | (src[pointer + 6] & 0xff) << 8 | (src[pointer + 7] & 0xff)); }
    public static long[]       readLongs       (byte[] src, int pointer, int length){
        long[] dest = new long[length];
        for (int i = 0; i < length; i++){ dest[i] = readLong(src, pointer); pointer += TSDataType.getSize(TSDataType.LONG); }
        return dest;
    }

    /**Float**/
    public static float        readFloat       (byte[] src, int pointer){ return Float.intBitsToFloat(readInt(src, pointer)); }
    public static float[]      readFloats      (byte[] src, int pointer, int length){
        float[] dest = new float[length];
        for (int i = 0; i < length; i++){ dest[i] = readFloat(src, pointer); pointer += TSDataType.getSize(TSDataType.FLOAT); }
        return dest;
    }

    /**Double**/
    public static double       readDouble      (byte[] src, int pointer){ return Double.longBitsToDouble(readLong(src, pointer)); }
    public static double[]     readDoubles     (byte[] src, int pointer, int length){
        double[] dest = new double[length];
        for (int i = 0; i < length; i++){ dest[i] = readDouble(src, pointer); pointer += TSDataType.getSize(TSDataType.DOUBLE); }
        return dest;
    }

    /**Boolean**/
    public static boolean      readBoolean     (byte[] src, int pointer){ return src[pointer] != 0; }
    public static boolean[]    readBooleans    (byte[] src, int pointer, int length){
        boolean[] dest = new boolean[length];
        for (int i = 0; i < length; i++){ dest[i] = readBoolean(src, pointer); pointer += TSDataType.getSize(TSDataType.BOOLEAN); }
        return dest;
    }

    /**Character**/
    public static char         readChar        (byte[] src, int pointer){ return (char)((src[pointer] & 0xff) << 8 | (src[pointer + 1] & 0xff)); }
    public static char[]       readChars       (byte[] src, int pointer, int length){
        char[] dest = new char[length];
        for (int i = 0; i < length; i++){ dest[i] = readChar(src, pointer); pointer += TSDataType.getSize(TSDataType.CHAR); }
        return dest;
    }

    /**String**/
    public static String       readString      (byte[] src, int pointer){ return String.valueOf(readChars(src, pointer + TSDataType.getSize(TSDataType.SHORT), readShort(src, pointer))); }
    public static String[]     readStrings     (byte[] src, int pointer, int length){
        String[] dest = new String[length];
        for (int i = 0; i < length; i++){ dest[i] = readString(src, pointer); pointer += TSDataType.getSize(TSDataType.SHORT) + ((TSDataType.getSize(TSDataType.STRING) * dest[i].length())); }
        return dest;
    }

    public static String       readString      (byte[] src, int pointer, int length){ return new String(src, pointer, length); }

    /**Vector2**/
    public static Vector2      readVector2     (byte[] src, int pointer){ return new Vector2(readFloat(src, pointer), readFloat(src, pointer + TSDataType.getSize(TSDataType.FLOAT))); }
    public static Vector2[]    readVector2s    (byte[] src, int pointer, int length){
        Vector2[] dest = new Vector2[length];
        for (int i = 0; i < length; i++){ dest[i] = readVector2(src, pointer); pointer += (TSDataType.getSize(TSDataType.FLOAT)*2); }
        return dest;
    }

    /**Vector3**/
    public static Vector3      readVector3     (byte[] src, int pointer){ return new Vector3(readFloat(src, pointer), readFloat(src, pointer + TSDataType.getSize(TSDataType.FLOAT)), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 2)); }
    public static Vector3[]    readVector3s    (byte[] src, int pointer, int length){
        Vector3[] dest = new Vector3[length];
        for (int i = 0; i < length; i++){ dest[i] = readVector3(src, pointer); pointer += (TSDataType.getSize(TSDataType.FLOAT)*3); }
        return dest;
    }

    /**Vector4**/
    public static Vector4      readVector4     (byte[] src, int pointer){ return new Vector4(readFloat(src, pointer), readFloat(src, pointer + TSDataType.getSize(TSDataType.FLOAT)), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 2), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 3)); }
    public static Vector4[]    readVector4s    (byte[] src, int pointer, int length){
        Vector4[] dest = new Vector4[length];
        for (int i = 0; i < length; i++){ dest[i] = readVector4(src, pointer); pointer += (TSDataType.getSize(TSDataType.FLOAT)*4); }
        return dest;
    }

    /**Quaternion**/
    public static Quaternion   readQuaternion (byte[] src, int pointer){ return new Quaternion(readFloat(src, pointer), readFloat(src, pointer + TSDataType.getSize(TSDataType.FLOAT)), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 2), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 3)); }
    public static Quaternion[] readQuaternions(byte[] src, int pointer, int length){
        Quaternion[] dest = new Quaternion[length];
        for (int i = 0; i < length; i++){ dest[i] = readQuaternion(src, pointer); pointer += (TSDataType.getSize(TSDataType.FLOAT)*4); }
        return dest;
    }

    /**Matrix2**/
    public static Matrix2      readMatrix2     (byte[] src, int pointer){
        return new Matrix2(
                readFloat(src, pointer)                                                    , readFloat(src, pointer + TSDataType.getSize(TSDataType.FLOAT))      ,
                readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 2), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 3)
        );
    }
    public static Matrix2[]    readMatrix2s    (byte[] src, int pointer, int length){
        Matrix2[] dest = new Matrix2[length];
        for (int i = 0; i < length; i++){ dest[i] = readMatrix2(src, pointer); pointer += (TSDataType.getSize(TSDataType.FLOAT)*4); }
        return dest;
    }

    /**Matrix3**/
    public static Matrix3      readMatrix3     (byte[] src, int pointer){
        return new Matrix3(
                readFloat(src, pointer)                                                    , readFloat(src, pointer + TSDataType.getSize(TSDataType.FLOAT))      , readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 2),
                readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 3), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 4), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 5),
                readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 6), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 7), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 8)
        );
    }
    public static Matrix3[]    readMatrix3s    (byte[] src, int pointer, int length){
        Matrix3[] dest = new Matrix3[length];
        for (int i = 0; i < length; i++){ dest[i] = readMatrix3(src, pointer); pointer += (TSDataType.getSize(TSDataType.FLOAT)*9); }
        return dest;
    }

    /**Matrix4**/
    public static Matrix4      readMatrix4     (byte[] src, int pointer){
        return new Matrix4(
                readFloat(src, pointer)                                                     , readFloat(src, pointer + TSDataType.getSize(TSDataType.FLOAT))       , readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) *  2), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) *  3),
                readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) *  4), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) *  5), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) *  6), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) *  7),
                readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) *  8), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) *  9), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 10), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 11),
                readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 12), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 13), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 14), readFloat(src, pointer + (TSDataType.getSize(TSDataType.FLOAT)) * 15)
        );
    }
    public static Matrix4[]    readMatrix4s    (byte[] src, int pointer, int length){
        Matrix4[] dest = new Matrix4[length];
        for (int i = 0; i < length; i++){ dest[i] = readMatrix4(src, pointer); pointer += (TSDataType.getSize(TSDataType.FLOAT)*16); }
        return dest;
    }

    /**Getters**/

    public byte[] getData(){ return data; }
}
