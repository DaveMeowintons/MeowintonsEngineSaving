package utils.serialisation.dataObjects;

import utils.files.readers.TSReader;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.maths.vectors.Vector2;
import utils.maths.vectors.Vector3;
import utils.maths.vectors.Vector4;
import utils.maths.matrices.Quaternion;
import utils.maths.matrices.Matrix2;
import utils.maths.matrices.Matrix3;
import utils.maths.matrices.Matrix4;
import utils.serialisation.types.TSContainerType;
import utils.serialisation.types.TSDataType;

public class TSArray extends TSBase {
    public static final byte CONTAINER_TYPE = TSContainerType.ARRAY;

    private byte     dataType;      //Byte representation of TSDataType
    private int      dataCount;     //Amount of items in array stored as a short

    /**Only one of these arrays will be populated based on "dataType" variable**/
    public byte[]       byteData;       //Raw byte array data
    public short[]      shortData;      //Raw short array data
    public int[]        intData;        //Raw int array data
    public long[]       longData;       //Raw long array data
    public float[]      floatData;      //Raw float array data
    public double[]     doubleData;     //Raw double array data
    public boolean[]    booleanData;    //Raw boolean array data
    public char[]       charData;       //Raw char array data
    public String[]     stringData;     //Raw String array data
    public Vector2[]    vector2Data;    //Raw Vector2 array data
    public Vector3[]    vector3Data;    //Raw Vector3 array data
    public Vector4[]    vector4Data;    //Raw Vector4 array data
    public Quaternion[] quaternionData; //Raw Quaternion array data
    public Matrix2[]    matrix2Data;    //Raw Quaternion array data
    public Matrix3[]    matrix3Data;    //Raw Quaternion array data
    public Matrix4[]    matrix4Data;    //Raw Quaternion array data

    private TSArray(){
        size += TSDataType.getSize(TSDataType.BYTE) + //dataType
                TSDataType.getSize(TSDataType.INTEGER); //dataCount
    }

    /**
     * Create a new TSArray holding a list of bytes
     * @param name Name of array
     * @param data Byte array data
     * @return new TSArray
     */
    public static TSArray Byte(String name, byte[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType  = TSDataType.BYTE;
        array.dataCount = data.length;
        array.byteData  = data;
        array.size     += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of shorts
     * @param name Name of array
     * @param data Short array data
     * @return new TSArray
     */
    public static TSArray Short(String name, short[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType  = TSDataType.SHORT;
        array.dataCount = data.length;
        array.shortData = data;
        array.size     += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of integers
     * @param name Name of array
     * @param data Integer array data
     * @return new TSArray
     */
    public static TSArray Integer(String name, int[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType  = TSDataType.INTEGER;
        array.dataCount = data.length;
        array.intData   = data;
        array.size     += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of longs
     * @param name Name of array
     * @param data Long array data
     * @return new TSArray
     */
    public static TSArray Long(String name, long[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType  = TSDataType.LONG;
        array.dataCount = data.length;
        array.longData  = data;
        array.size     += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of floats
     * @param name Name of array
     * @param data Float array data
     * @return new TSArray
     */
    public static TSArray Float(String name, float[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType  = TSDataType.FLOAT;
        array.dataCount = data.length;
        array.floatData = data;
        array.size     += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of doubles
     * @param name Name of array
     * @param data Double array data
     * @return new TSArray
     */
    public static TSArray Double(String name, double[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType   = TSDataType.DOUBLE;
        array.dataCount  = data.length;
        array.doubleData = data;
        array.size      += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of booleans
     * @param name Name of array
     * @param data Boolean array data
     * @return new TSArray
     */
    public static TSArray Boolean(String name, boolean[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType    = TSDataType.BOOLEAN;
        array.dataCount   = data.length;
        array.booleanData = data;
        array.size       += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of chars
     * @param name Name of array
     * @param data Char array data
     * @return new TSArray
     */
    public static TSArray Char(String name, char[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType  = TSDataType.CHAR;
        array.dataCount = data.length;
        array.charData  = data;
        array.size     += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of Strings
     * @param name Name of array
     * @param data String array data
     * @return new TSArray
     */
    public static TSArray String(String name, String[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType   = TSDataType.STRING;
        array.dataCount  = data.length;
        array.stringData = data;
        array.size      += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of Vector2s
     * @param name Name of array
     * @param data Vector2 array data
     * @return new TSArray
     */
    public static TSArray Vector2(String name, Vector2[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType    = TSDataType.VECTOR2;
        array.dataCount   = data.length;
        array.vector2Data = data;
        array.size       += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of Vector3s
     * @param name Name of array
     * @param data Vector3 array data
     * @return new TSArray
     */
    public static TSArray Vector3(String name, Vector3[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType    = TSDataType.VECTOR3;
        array.dataCount   = data.length;
        array.vector3Data = data;
        array.size       += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of Vector4s
     * @param name Name of array
     * @param data Vector4 array data
     * @return new TSArray
     */
    public static TSArray Vector4(String name, Vector4[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType    = TSDataType.VECTOR4;
        array.dataCount   = data.length;
        array.vector4Data = data;
        array.size       += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of Quaternions
     * @param name Name of array
     * @param data Quaternion array data
     * @return new TSArray
     */
    public static TSArray Quaternion(String name, Quaternion[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType       = TSDataType.QUATERNION;
        array.dataCount      = data.length;
        array.quaternionData = data;
        array.size          += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of Matrix2s
     * @param name Name of array
     * @param data Matrix2 array data
     * @return new TSArray
     */
    public static TSArray Matrix2(String name, Matrix2[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType    = TSDataType.MATRIX2;
        array.dataCount   = data.length;
        array.matrix2Data = data;
        array.size       += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of Matrix3s
     * @param name Name of array
     * @param data Matrix3 array data
     * @return new TSArray
     */
    public static TSArray Matrix3(String name, Matrix3[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType    = TSDataType.MATRIX3;
        array.dataCount   = data.length;
        array.matrix3Data = data;
        array.size       += array.getDataSize();
        return array;
    }

    /**
     * Create a new TSArray holding a list of Matrix4s
     * @param name Name of array
     * @param data Matrix4 array data
     * @return new TSArray
     */
    public static TSArray Matrix4(String name, Matrix4[] data){
        TSArray array = new TSArray();
        array.setName(name);
        array.dataType    = TSDataType.MATRIX4;
        array.dataCount   = data.length;
        array.matrix4Data = data;
        array.size       += array.getDataSize();
        return array;
    }

    /**
     * @return How large in bytes the data size is
     */
    private int getDataSize(){
        switch(dataType){
            case TSDataType.BYTE:       return byteData   .length    * TSDataType.getSize(TSDataType.BYTE);
            case TSDataType.SHORT:      return shortData  .length    * TSDataType.getSize(TSDataType.SHORT);
            case TSDataType.INTEGER:    return intData    .length    * TSDataType.getSize(TSDataType.INTEGER);
            case TSDataType.LONG:       return longData   .length    * TSDataType.getSize(TSDataType.LONG);
            case TSDataType.FLOAT:      return floatData  .length    * TSDataType.getSize(TSDataType.FLOAT);
            case TSDataType.DOUBLE:     return doubleData .length    * TSDataType.getSize(TSDataType.DOUBLE);
            case TSDataType.BOOLEAN:    return booleanData.length    * TSDataType.getSize(TSDataType.BOOLEAN);
            case TSDataType.CHAR:       return charData   .length    * TSDataType.getSize(TSDataType.CHAR);
            case TSDataType.STRING:     short  totalLength = 0; for(String s : stringData) totalLength += s.length(); return (stringData.length * TSDataType.getSize(TSDataType.SHORT)) + (totalLength * TSDataType.getSize(TSDataType.STRING));
            case TSDataType.VECTOR2:    return vector2Data.length    * (TSDataType.getSize(TSDataType.FLOAT) *  2);
            case TSDataType.VECTOR3:    return vector3Data.length    * (TSDataType.getSize(TSDataType.FLOAT) *  3);
            case TSDataType.VECTOR4:    return vector4Data.length    * (TSDataType.getSize(TSDataType.FLOAT) *  4);
            case TSDataType.QUATERNION: return quaternionData.length * (TSDataType.getSize(TSDataType.FLOAT) *  4);
            case TSDataType.MATRIX2:    return matrix2Data.length    * (TSDataType.getSize(TSDataType.FLOAT) *  4);
            case TSDataType.MATRIX3:    return matrix3Data.length    * (TSDataType.getSize(TSDataType.FLOAT) *  9);
            case TSDataType.MATRIX4:    return matrix4Data.length    * (TSDataType.getSize(TSDataType.FLOAT) * 16);
        }
        return 0;
    }

    /**
     * Load a new TSArray from raw byte data
     * @param data raw byte array data
     * @param pointer current pointer position in byte array
     * @return a new TSArray
     */
    public static TSArray deserialise(byte[] data, int pointer){
        //Get the container type
        //This should confirm we are trying to deserialise the correct field
        //Future iterations could probably do better error checking with this
        byte containerType = TSReader.readByte(data, pointer);
        if(containerType != CONTAINER_TYPE){
            Logger.log(LogLevel.ERROR, TSArray.class.getSimpleName(),
                            "Data may be corrupt, passed data isn't an array\n" +
                            "Pointer                    - " + pointer + "\n" +
                            "Container type             - " + containerType + "\n" +
                            "Expected container type    - " + CONTAINER_TYPE
                      );
            return null;
        }

        pointer += TSDataType.getSize(TSDataType.BYTE);

        //Create return array
        TSArray array = new TSArray();

        //Get the name length and then the name of the array
        array.nameLength = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);
        array.name = TSReader.readString(data, pointer, array.nameLength).getBytes();
        pointer += array.nameLength;

        //Get the size (in bytes) of the array
        array.size = TSReader.readInt(data, pointer);
        pointer += TSDataType.getSize(TSDataType.INTEGER);

        //Get what data type the field is (Byte, Short, Int, etc...)
        array.dataType = TSReader.readByte(data, pointer);
        pointer += TSDataType.getSize(TSDataType.BYTE);
        //Get how much data is stored within the array
        array.dataCount = TSReader.readInt(data, pointer);
        pointer += TSDataType.getSize(TSDataType.INTEGER);

        //Get array data and populate the corresponding array
        switch(array.dataType){
            case TSDataType.BYTE:       array.byteData       = TSReader.readBytes      (data, pointer, array.dataCount); break;
            case TSDataType.SHORT:      array.shortData      = TSReader.readShorts     (data, pointer, array.dataCount); break;
            case TSDataType.INTEGER:    array.intData        = TSReader.readInts       (data, pointer, array.dataCount); break;
            case TSDataType.LONG:       array.longData       = TSReader.readLongs      (data, pointer, array.dataCount); break;
            case TSDataType.FLOAT:      array.floatData      = TSReader.readFloats     (data, pointer, array.dataCount); break;
            case TSDataType.DOUBLE:     array.doubleData     = TSReader.readDoubles    (data, pointer, array.dataCount); break;
            case TSDataType.BOOLEAN:    array.booleanData    = TSReader.readBooleans   (data, pointer, array.dataCount); break;
            case TSDataType.CHAR:       array.charData       = TSReader.readChars      (data, pointer, array.dataCount); break;
            case TSDataType.STRING:     array.stringData     = TSReader.readStrings    (data, pointer, array.dataCount); break;
            case TSDataType.VECTOR2:    array.vector2Data    = TSReader.readVector2s   (data, pointer, array.dataCount); break;
            case TSDataType.VECTOR3:    array.vector3Data    = TSReader.readVector3s   (data, pointer, array.dataCount); break;
            case TSDataType.VECTOR4:    array.vector4Data    = TSReader.readVector4s   (data, pointer, array.dataCount); break;
            case TSDataType.QUATERNION: array.quaternionData = TSReader.readQuaternions(data, pointer, array.dataCount); break;
            case TSDataType.MATRIX2:    array.matrix2Data    = TSReader.readMatrix2s   (data, pointer, array.dataCount); break;
            case TSDataType.MATRIX3:    array.matrix3Data    = TSReader.readMatrix3s   (data, pointer, array.dataCount); break;
            case TSDataType.MATRIX4:    array.matrix4Data    = TSReader.readMatrix4s   (data, pointer, array.dataCount); break;
        }

        pointer += array.dataCount * TSDataType.getSize(array.dataType);

        //Return the completed array
        return array;
    }

    /**Getters**/

    public byte getDataType(){ return dataType; }
    public int getDataCount(){ return dataCount; }

    public Object getDataObject(){
        switch(dataType){
            case TSDataType.BYTE: return byteData;
            case TSDataType.SHORT: return shortData;
            case TSDataType.INTEGER: return intData;
            case TSDataType.LONG: return longData;
            case TSDataType.FLOAT: return floatData;
            case TSDataType.DOUBLE: return doubleData;
            case TSDataType.BOOLEAN: return booleanData;
            case TSDataType.CHAR: return charData;
            case TSDataType.STRING: return stringData;
            case TSDataType.VECTOR2: return vector2Data;
            case TSDataType.VECTOR3: return vector3Data;
            case TSDataType.VECTOR4: return vector4Data;
            case TSDataType.QUATERNION: return quaternionData;
            case TSDataType.MATRIX2: return matrix2Data;
            case TSDataType.MATRIX3: return matrix3Data;
            case TSDataType.MATRIX4: return matrix4Data;
            default: return null;
        }
    }

    public byte[]       getByteData()      { return byteData;       }
    public short[]      getShortData()     { return shortData;      }
    public int[]        getIntData()       { return intData;        }
    public long[]       getLongData()      { return longData;       }
    public float[]      getFloatData()     { return floatData;      }
    public double[]     getDoubleData()    { return doubleData;     }
    public boolean[]    getBooleanData()   { return booleanData;    }
    public char[]       getCharData()      { return charData;       }
    public String[]     getStringData()    { return stringData;     }
    public Vector2[]    getVector2Data()   { return vector2Data;    }
    public Vector3[]    getVector3Data()   { return vector3Data;    }
    public Vector4[]    getVector4Data()   { return vector4Data;    }
    public Quaternion[] getQuaternionData(){ return quaternionData; }
    public Matrix2[]    getMatrix2Data()   { return matrix2Data;    }
    public Matrix3[]    getMatrix3Data()   { return matrix3Data;    }
    public Matrix4[]    getMatrix4Data()   { return matrix4Data;    }

    /**To String**/

    public String toString(){
        StringBuilder returnString = new StringBuilder();

        returnString.append("==Array==").append("\n")
                .append("Name:       ").append(getName()).append("\n")
                .append("Data type:  ").append(dataType).append("\n")
                .append("Data count: ").append(dataCount).append("\n")
                .append("Data:       ").append(arrayDataAsString()).append("\n").append("\n");

        return returnString.toString();
    }

    private String arrayDataAsString(){
        StringBuilder result = new StringBuilder();

        switch(dataType){
            case TSDataType.BYTE:       for (int i = 0; i < dataCount; i++) result.append(byteData[i])      .append(", "); break;
            case TSDataType.SHORT:      for (int i = 0; i < dataCount; i++) result.append(shortData[i])     .append(", "); break;
            case TSDataType.INTEGER:    for (int i = 0; i < dataCount; i++) result.append(intData[i])       .append(", "); break;
            case TSDataType.LONG:       for (int i = 0; i < dataCount; i++) result.append(longData[i])      .append(", "); break;
            case TSDataType.FLOAT:      for (int i = 0; i < dataCount; i++) result.append(floatData[i])     .append(", "); break;
            case TSDataType.DOUBLE:     for (int i = 0; i < dataCount; i++) result.append(doubleData[i])    .append(", "); break;
            case TSDataType.BOOLEAN:    for (int i = 0; i < dataCount; i++) result.append(booleanData[i])   .append(", "); break;
            case TSDataType.CHAR:       for (int i = 0; i < dataCount; i++) result.append(charData[i])      .append(", "); break;
            case TSDataType.STRING:     for (int i = 0; i < dataCount; i++) result.append(stringData[i])    .append(", "); break;
            case TSDataType.VECTOR2:    for (int i = 0; i < dataCount; i++) result.append(vector2Data[i])   .append(", "); break;
            case TSDataType.VECTOR3:    for (int i = 0; i < dataCount; i++) result.append(vector3Data[i])   .append(", "); break;
            case TSDataType.VECTOR4:    for (int i = 0; i < dataCount; i++) result.append(vector4Data[i])   .append(", "); break;
            case TSDataType.QUATERNION: for (int i = 0; i < dataCount; i++) result.append(quaternionData[i]).append(", "); break;
            case TSDataType.MATRIX2:    for (int i = 0; i < dataCount; i++) result.append(matrix2Data[i])   .append(", "); break;
            case TSDataType.MATRIX3:    for (int i = 0; i < dataCount; i++) result.append(matrix3Data[i])   .append(", "); break;
            case TSDataType.MATRIX4:    for (int i = 0; i < dataCount; i++) result.append(matrix4Data[i])   .append(", "); break;
        }

        return result.toString();
    }
}
