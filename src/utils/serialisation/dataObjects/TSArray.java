package utils.serialisation.dataObjects;

import utils.files.readers.TSReader;
import utils.files.writers.TSWriter;
import utils.logging.LogLevel;
import utils.logging.Logger;
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
     * Create a new TSArray holding a list of strings
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


    public int getBytes(byte[] dest, int pointer){
        pointer = TSWriter.writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = TSWriter.writeBytes(dest, pointer, nameLength);
        pointer = TSWriter.writeBytes(dest, pointer, name);
        pointer = TSWriter.writeBytes(dest, pointer, size);
        pointer = TSWriter.writeBytes(dest, pointer, dataType);
        pointer = TSWriter.writeBytes(dest, pointer, dataCount);

        switch(dataType){
            case TSDataType.BYTE:       pointer = TSWriter.writeBytes(dest, pointer, byteData);       break;
            case TSDataType.SHORT:      pointer = TSWriter.writeBytes(dest, pointer, shortData);      break;
            case TSDataType.INTEGER:    pointer = TSWriter.writeBytes(dest, pointer, intData);        break;
            case TSDataType.LONG:       pointer = TSWriter.writeBytes(dest, pointer, longData);       break;
            case TSDataType.FLOAT:      pointer = TSWriter.writeBytes(dest, pointer, floatData);      break;
            case TSDataType.DOUBLE:     pointer = TSWriter.writeBytes(dest, pointer, doubleData);     break;
            case TSDataType.BOOLEAN:    pointer = TSWriter.writeBytes(dest, pointer, booleanData);    break;
            case TSDataType.CHAR:       pointer = TSWriter.writeBytes(dest, pointer, charData);       break;
            case TSDataType.STRING:     pointer = TSWriter.writeBytes(dest, pointer, stringData);     break;
        }

        return pointer;
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
        }

        pointer += array.dataCount * TSDataType.getSize(array.dataType);

        //Return the completed array
        return array;
    }

    /**Getters**/

    public byte getDataType(){ return dataType; }
    public int getDataCount(){ return dataCount; }

    public byte[]       getByteData()      { return byteData;       }
    public short[]      getShortData()     { return shortData;      }
    public int[]        getIntData()       { return intData;        }
    public long[]       getLongData()      { return longData;       }
    public float[]      getFloatData()     { return floatData;      }
    public double[]     getDoubleData()    { return doubleData;     }
    public boolean[]    getBooleanData()   { return booleanData;    }
    public char[]       getCharData()      { return charData;       }
    public String[]     getStringData()    { return stringData;     }

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
        }

        return result.toString();
    }
}
