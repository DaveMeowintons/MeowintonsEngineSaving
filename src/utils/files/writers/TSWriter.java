package utils.files.writers;

import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.serialisation.dataObjects.TSArray;
import utils.serialisation.dataObjects.TSDatabase;
import utils.serialisation.dataObjects.TSField;
import utils.serialisation.dataObjects.TSObject;
import utils.serialisation.types.TSDataType;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

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
        for(TSObject object : database.getObjects().values())
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
}
