package utils.serialisation.types;

import utils.files.readers.TSReader;

public class TSDataType {
    public static final byte UNKNOWN = 0;
    public static final byte BYTE    = 1;
    public static final byte SHORT   = 2;
    public static final byte INTEGER = 3;
    public static final byte LONG    = 4;
    public static final byte FLOAT   = 5;
    public static final byte DOUBLE  = 6;
    public static final byte BOOLEAN = 7;
    public static final byte CHAR    = 8;
    public static final byte STRING  = 9;
    public static final byte VECTOR2    = 10;
    public static final byte VECTOR3    = 11;
    public static final byte VECTOR4    = 12;
    public static final byte QUATERNION = 13;
    public static final byte MATRIX2    = 14;
    public static final byte MATRIX3    = 15;
    public static final byte MATRIX4    = 16;

    /**
     * @param type TSDataType
     * @return The number in bytes used to represent the specified TSDataType
     */
    public static int getSize(byte type){
        switch(type){
            case BYTE:       return Byte.SIZE      / 8;
            case SHORT:      return Short.SIZE     / 8;
            case INTEGER:    return Integer.SIZE   / 8;
            case LONG:       return Long.SIZE      / 8;
            case FLOAT:      return Float.SIZE     / 8;
            case DOUBLE:     return Double.SIZE    / 8;
            case BOOLEAN:    return Byte.SIZE      / 8;
            case CHAR:       return Character.SIZE / 8;
            case STRING:     return Character.SIZE / 8;
            case VECTOR2:    return (Float.SIZE     / 8) * 2;
            case VECTOR3:    return (Float.SIZE     / 8) * 3;
            case VECTOR4:    return (Float.SIZE     / 8) * 4;
            case QUATERNION: return (Float.SIZE     / 8) * 4;
            case MATRIX2:    return (Float.SIZE     / 8) * 4;
            case MATRIX3:    return (Float.SIZE     / 8) * 9;
            case MATRIX4:    return (Float.SIZE     / 8) * 16;
        }

        return 0;
    }

    /**
     * @param type TSDataType
     * @param data Raw byte data
     * @return The correct value of the inputted raw data based on the TSDataType
     */
    @SuppressWarnings("unchecked")
    public static <T> T value(byte type, byte[] data){
        switch(type){
            case BYTE:       return (T)(Byte)      TSReader.readByte(data, 0);
            case SHORT:      return (T)(Short)     TSReader.readShort(data, 0);
            case INTEGER:    return (T)(Integer)   TSReader.readInt(data, 0);
            case LONG:       return (T)(Long)      TSReader.readLong(data, 0);
            case FLOAT:      return (T)(Float)     TSReader.readFloat(data, 0);
            case DOUBLE:     return (T)(Double)    TSReader.readDouble(data, 0);
            case BOOLEAN:    return (T)(Boolean)   TSReader.readBoolean(data, 0);
            case CHAR:       return (T)(Character) TSReader.readChar(data, 0);
            case STRING:     return (T)(String)    TSReader.readString(data, 0);
            case VECTOR2:    return (T)            TSReader.readVector2(data, 0);
            case VECTOR3:    return (T)            TSReader.readVector3(data, 0);
            case VECTOR4:    return (T)            TSReader.readVector4(data, 0);
            case QUATERNION: return (T)            TSReader.readQuaternion(data, 0);
            case MATRIX2:    return (T)            TSReader.readMatrix2(data, 0);
            case MATRIX3:    return (T)            TSReader.readMatrix3(data, 0);
            case MATRIX4:    return (T)            TSReader.readMatrix4(data, 0);
        }

        return null;
    }
}
