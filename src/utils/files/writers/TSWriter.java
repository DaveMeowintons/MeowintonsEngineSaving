package utils.files.writers;

import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.maths.matrices.Matrix2;
import utils.maths.matrices.Matrix3;
import utils.maths.matrices.Matrix4;
import utils.maths.matrices.Quaternion;
import utils.maths.vectors.Vector2;
import utils.maths.vectors.Vector3;
import utils.maths.vectors.Vector4;
import utils.serialisation.dataObjects.TSArray;
import utils.serialisation.dataObjects.TSDatabase;
import utils.serialisation.dataObjects.TSField;
import utils.serialisation.dataObjects.TSObject;
import utils.serialisation.types.TSDataType;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class TSWriter extends FileWriter {
    public static final byte[] HEADER  = "TSDB".getBytes();
    public static final short  VERSION = 0x0103; //[MAJOR][MINOR]

    private BufferedOutputStream stream;

    public void createFile(String file){
        this.fileName = file;

        try{
            stream = new BufferedOutputStream(new FileOutputStream(file));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), "Error creating writer for file: " + this.fileName);
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), error);
            close();
        }
    }

    public void writeDatabaseToFile(TSDatabase database){
        try{
            stream.write(writeDatabase(database));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), "Error writing data to file: " + this.fileName);
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), error);
            close();
        }
    }

    /**
     * Convert database into serialised byte array
     * @param database Database class
     * @return byte array
     */
    private byte[] writeDatabase(TSDatabase database){
        byte[] data = new byte[database.getSize()];
        int pointer = 0;

        pointer = writeBytes(data, pointer, database.getHeader());
        pointer = writeBytes(data, pointer, database.getVersion());
        pointer = writeBytes(data, pointer, TSDatabase.CONTAINER_TYPE);
        pointer = writeBytes(data, pointer, database.getNameLength());
        pointer = writeBytes(data, pointer, database.getNameData());
        pointer = writeBytes(data, pointer, database.getSize());

        pointer = writeBytes(data, pointer, database.getObjectCount());
        for(TSObject object : database.getObjects())
            pointer = writeObject(data, pointer, object);

        return data;
    }

    /**
     * Write database object into byte array
     * @param data byte array
     * @param pointer byte array pointer
     * @param object object to write into byte array
     * @return pointer value
     */
    private int writeObject(byte[] data, int pointer, TSObject object){
        pointer = writeBytes(data, pointer, TSObject.CONTAINER_TYPE);
        pointer = writeBytes(data, pointer, object.getNameLength());
        pointer = writeBytes(data, pointer, object.getNameData());
        pointer = writeBytes(data, pointer, object.getSize());

        pointer = writeBytes(data, pointer, object.getObjectCount());
        for(TSObject obj : object.getObjects().values())
            pointer = writeObject(data, pointer, obj);

        pointer = writeBytes(data, pointer, object.getFieldCount());
        for(TSField field : object.getFields().values())
            pointer = writeField(data, pointer, field);

        pointer = writeBytes(data, pointer, object.getArrayCount());
        for(TSArray array : object.getArrays().values())
            pointer = writeArray(data, pointer, array);

        return pointer;
    }

    /**
     * Write database field into byte array
     * @param data byte array
     * @param pointer byte array pointer
     * @param field field to write into byte array
     * @return pointer value
     */
    private int writeField(byte[] data, int pointer, TSField field){
        pointer = writeBytes(data, pointer, TSField.CONTAINER_TYPE);
        pointer = writeBytes(data, pointer, field.getNameLength());
        pointer = writeBytes(data, pointer, field.getNameData());
        pointer = writeBytes(data, pointer, field.getSize());
        pointer = writeBytes(data, pointer, field.getDataType());
        pointer = writeBytes(data, pointer, field.getData());

        return pointer;
    }

    /**
     * Write database array into byte array
     * @param data byte array
     * @param pointer byte array pointer
     * @param array array to write into byte array
     * @return array pointer
     */
    private int writeArray(byte[] data, int pointer, TSArray array){
        pointer = writeBytes(data, pointer, TSArray.CONTAINER_TYPE);
        pointer = writeBytes(data, pointer, array.getNameLength());
        pointer = writeBytes(data, pointer, array.getNameData());
        pointer = writeBytes(data, pointer, array.getSize());
        pointer = writeBytes(data, pointer, array.getDataType());
        pointer = writeBytes(data, pointer, array.getDataCount());

        switch(array.getDataType()){
            case TSDataType.BYTE:       pointer = writeBytes(data, pointer, array.getByteData());       break;
            case TSDataType.SHORT:      pointer = writeBytes(data, pointer, array.getShortData());      break;
            case TSDataType.INTEGER:    pointer = writeBytes(data, pointer, array.getIntData());        break;
            case TSDataType.LONG:       pointer = writeBytes(data, pointer, array.getLongData());       break;
            case TSDataType.FLOAT:      pointer = writeBytes(data, pointer, array.getFloatData());      break;
            case TSDataType.DOUBLE:     pointer = writeBytes(data, pointer, array.getDoubleData());     break;
            case TSDataType.BOOLEAN:    pointer = writeBytes(data, pointer, array.getBooleanData());    break;
            case TSDataType.CHAR:       pointer = writeBytes(data, pointer, array.getCharData());       break;
            case TSDataType.STRING:     pointer = writeBytes(data, pointer, array.getStringData());     break;
            case TSDataType.VECTOR2:    pointer = writeBytes(data, pointer, array.getVector2Data());    break;
            case TSDataType.VECTOR3:    pointer = writeBytes(data, pointer, array.getVector3Data());    break;
            case TSDataType.VECTOR4:    pointer = writeBytes(data, pointer, array.getVector4Data());    break;
            case TSDataType.QUATERNION: pointer = writeBytes(data, pointer, array.getQuaternionData()); break;
            case TSDataType.MATRIX2:    pointer = writeBytes(data, pointer, array.getMatrix2Data());    break;
            case TSDataType.MATRIX3:    pointer = writeBytes(data, pointer, array.getMatrix3Data());    break;
            case TSDataType.MATRIX4:    pointer = writeBytes(data, pointer, array.getMatrix4Data());    break;
        }

        return pointer;
    }

    public void close(){
        try{
            stream.close();
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), "Error closing writer for file: " + this.fileName);
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), error);
        }
    }

    /*Byte*/

    /**
     * Write byte value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value byte value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, byte value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.BYTE)) return pointer;

        dest[pointer++] = value;
        return pointer;
    }

    /**
     * Write byte array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values byte array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, byte[] values){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.BYTE) * values.length)) return pointer;

        for(byte value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Short*/

    /**
     * Write short value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value short value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, short value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.SHORT)) return pointer;

        dest[pointer++] = (byte)((value >> 8) & 0xff);
        dest[pointer++] = (byte)((value >> 0) & 0xff);
        return pointer;
    }

    /**
     * Write short array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values short array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, short[] values){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.SHORT) * values.length)) return pointer;

        for(short value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Integer*/

    /**
     * Write int value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value int value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, int value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.INTEGER)) return pointer;

        dest[pointer++] = (byte)((value >> 24) & 0xff);
        dest[pointer++] = (byte)((value >> 16) & 0xff);
        dest[pointer++] = (byte)((value >> 8) & 0xff);
        dest[pointer++] = (byte)((value >> 0) & 0xff);
        return pointer;
    }

    /**
     * Write int array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values int array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, int[] values){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.INTEGER) * values.length)) return pointer;

        for(int value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Long*/

    /**
     * Write long value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value long value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, long value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.LONG)) return pointer;

        dest[pointer++] = (byte)((value >> 56) & 0xff);
        dest[pointer++] = (byte)((value >> 48) & 0xff);
        dest[pointer++] = (byte)((value >> 40) & 0xff);
        dest[pointer++] = (byte)((value >> 32) & 0xff);
        dest[pointer++] = (byte)((value >> 24) & 0xff);
        dest[pointer++] = (byte)((value >> 16) & 0xff);
        dest[pointer++] = (byte)((value >> 8) & 0xff);
        dest[pointer++] = (byte)((value >> 0) & 0xff);
        return pointer;
    }

    /**
     * Write long array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values long array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, long[] values){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.LONG) * values.length)) return pointer;

        for(long value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Float*/

    /**
     * Write float value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value float value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, float value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.FLOAT)) return pointer;

        return writeBytes(dest, pointer, Float.floatToIntBits(value));
    }

    /**
     * Write float array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values float array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, float[] values){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.FLOAT) * values.length)) return pointer;

        for(float value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Double*/

    /**
     * Write double value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value double value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, double value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.DOUBLE)) return pointer;

        return writeBytes(dest, pointer, Double.doubleToLongBits(value));
    }

    /**
     * Write double array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values double array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, double[] values){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.DOUBLE) * values.length)) return pointer;

        for(double value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Boolean*/

    /**
     * Write boolean value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value boolean value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, boolean value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.BOOLEAN)) return pointer;

        return writeBytes(dest, pointer, value ? (byte)1 : (byte)0);
    }

    /**
     * Write boolean array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values boolean array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, boolean[] values){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.BOOLEAN) * values.length)) return pointer;

        for(boolean value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Character*/

    /**
     * Write char value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value char value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, char value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.CHAR)) return pointer;

        dest[pointer++] = (byte)((value >> 8) & 0xff);
        dest[pointer++] = (byte)((value >> 0) & 0xff);
        return pointer;
    }

    /**
     * Write char array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values char array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, char[] values){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.CHAR) * values.length)) return pointer;

        for(char value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*String*/

    /**
     * Write String value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value String value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, String value){
        if(dest.length < pointer + TSDataType.getSize(TSDataType.SHORT) + (TSDataType.getSize(TSDataType.CHAR) * value.length())) return pointer;

        char[] chars = new char[value.length()];
        for (int i = 0; i < value.length(); i++) chars[i] = value.charAt(i);

        pointer = writeBytes(dest, pointer, (short)(TSDataType.getSize(TSDataType.BYTE) * value.length()));
        return writeBytes(dest, pointer, chars);
    }

    /**
     * Write String array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values String array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, String[] values){
        short totalLength = 0; for(String s : values) totalLength += s.length();
        if(dest.length < pointer + TSDataType.getSize(TSDataType.SHORT) + (TSDataType.getSize(TSDataType.BYTE) * totalLength)) return pointer;

        for(String value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Vector2*/

    /**
     * Write Vector2 value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value Vector2 value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Vector2 value){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.FLOAT) * 2)) return pointer;

        pointer = writeBytes(dest, pointer, value.getX());
        pointer = writeBytes(dest, pointer, value.getY());

        return pointer;
    }

    /**
     * Write Vector2 array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values Vector2 array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Vector2[] values){
        if(dest.length < pointer + ((TSDataType.getSize(TSDataType.FLOAT) * 2) * values.length)) return pointer;

        for(Vector2 value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Vector3*/

    /**
     * Write Vector3 value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value Vector3 value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Vector3 value){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.FLOAT) * 3)) return pointer;

        pointer = writeBytes(dest, pointer, value.getX());
        pointer = writeBytes(dest, pointer, value.getY());
        pointer = writeBytes(dest, pointer, value.getZ());

        return pointer;
    }

    /**
     * Write Vector3 array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values Vector3 array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Vector3[] values){
        if(dest.length < pointer + ((TSDataType.getSize(TSDataType.FLOAT) * 3) * values.length)) return pointer;

        for(Vector3 value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Vector4*/

    /**
     * Write Vector4 value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value Vector4 value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Vector4 value){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.FLOAT) * 4)) return pointer;

        pointer = writeBytes(dest, pointer, value.getX());
        pointer = writeBytes(dest, pointer, value.getY());
        pointer = writeBytes(dest, pointer, value.getZ());
        pointer = writeBytes(dest, pointer, value.getW());

        return pointer;
    }

    /**
     * Write Vector4 array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values Vector4 array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Vector4[] values){
        if(dest.length < pointer + ((TSDataType.getSize(TSDataType.FLOAT) * 4) * values.length)) return pointer;

        for(Vector4 value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Quaternion*/

    /**
     * Write Quaternion value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value Quaternion value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Quaternion value){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.FLOAT) * 4)) return pointer;

        pointer = writeBytes(dest, pointer, value.getX());
        pointer = writeBytes(dest, pointer, value.getY());
        pointer = writeBytes(dest, pointer, value.getZ());
        pointer = writeBytes(dest, pointer, value.getW());

        return pointer;
    }

    /**
     * Write Quaternion array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values Quaternion array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Quaternion[] values){
        if(dest.length < pointer + ((TSDataType.getSize(TSDataType.FLOAT) * 4) * values.length)) return pointer;

        for(Quaternion value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Matrix2*/

    /**
     * Write Matrix2 value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value Matrix2 value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Matrix2 value){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.FLOAT) * 4)) return pointer;

        pointer = writeBytes(dest, pointer, value.m00()); pointer = writeBytes(dest, pointer, value.m01());
        pointer = writeBytes(dest, pointer, value.m10()); pointer = writeBytes(dest, pointer, value.m11());

        return pointer;
    }

    /**
     * Write Matrix2 array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values Matrix2 array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Matrix2[] values){
        if(dest.length < pointer + ((TSDataType.getSize(TSDataType.FLOAT) * 4) * values.length)) return pointer;

        for(Matrix2 value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Matrix3*/

    /**
     * Write Matrix3 value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value Matrix3 value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Matrix3 value){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.FLOAT) * 9)) return pointer;

        pointer = writeBytes(dest, pointer, value.m00()); pointer = writeBytes(dest, pointer, value.m01()); pointer = writeBytes(dest, pointer, value.m02());
        pointer = writeBytes(dest, pointer, value.m10()); pointer = writeBytes(dest, pointer, value.m11()); pointer = writeBytes(dest, pointer, value.m12());
        pointer = writeBytes(dest, pointer, value.m20()); pointer = writeBytes(dest, pointer, value.m21()); pointer = writeBytes(dest, pointer, value.m22());

        return pointer;
    }

    /**
     * Write Matrix3 array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values Matrix3 array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Matrix3[] values){
        if(dest.length < pointer + ((TSDataType.getSize(TSDataType.FLOAT) * 9) * values.length)) return pointer;

        for(Matrix3 value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }

    /*Matrix4*/

    /**
     * Write Matrix4 value to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param value Matrix4 value
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Matrix4 value){
        if(dest.length < pointer + (TSDataType.getSize(TSDataType.FLOAT) * 16)) return pointer;

        pointer = writeBytes(dest, pointer, value.m00()); pointer = writeBytes(dest, pointer, value.m01()); pointer = writeBytes(dest, pointer, value.m02()); pointer = writeBytes(dest, pointer, value.m03());
        pointer = writeBytes(dest, pointer, value.m10()); pointer = writeBytes(dest, pointer, value.m11()); pointer = writeBytes(dest, pointer, value.m12()); pointer = writeBytes(dest, pointer, value.m13());
        pointer = writeBytes(dest, pointer, value.m20()); pointer = writeBytes(dest, pointer, value.m21()); pointer = writeBytes(dest, pointer, value.m22()); pointer = writeBytes(dest, pointer, value.m23());
        pointer = writeBytes(dest, pointer, value.m30()); pointer = writeBytes(dest, pointer, value.m31()); pointer = writeBytes(dest, pointer, value.m32()); pointer = writeBytes(dest, pointer, value.m33());

        return pointer;
    }

    /**
     * Write Matrix4 array values to byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @param values Matrix4 array values
     * @return pointer
     */
    public static int writeBytes(byte[] dest, int pointer, Matrix4[] values){
        if(dest.length < pointer + ((TSDataType.getSize(TSDataType.FLOAT) * 16) * values.length)) return pointer;

        for(Matrix4 value : values) pointer = writeBytes(dest, pointer, value);
        return pointer;
    }
}
