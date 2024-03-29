package utils.serialisation.dataObjects;

import utils.files.readers.TSReader;
import utils.files.writers.TSWriter;
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

public class TSField extends TSBase {
    public static final byte CONTAINER_TYPE = TSContainerType.FIELD;

    private byte   dataType;    //Byte representation of TSDataType
    private byte[] data;        //Raw byte array data

    private TSField(){
        size += TSDataType.getSize(TSDataType.BYTE); //dataType
    }

    /**
     * Create a new TSField holding a byte variable
     * @param name Name of variable
     * @param value Byte value of variable
     * @return new TSField
     */
    public static TSField Byte(String name, byte value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.BYTE;
        field.data     = new byte[TSDataType.getSize(TSDataType.BYTE)];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a short variable
     * @param name Name of variable
     * @param value Short value of variable
     * @return new TSField
     */
    public static TSField Short(String name, short value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.SHORT;
        field.data     = new byte[TSDataType.getSize(TSDataType.SHORT)];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a integer variable
     * @param name Name of variable
     * @param value Integer value of variable
     * @return new TSField
     */
    public static TSField Integer(String name, int value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.INTEGER;
        field.data     = new byte[TSDataType.getSize(TSDataType.INTEGER)];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a long variable
     * @param name Name of variable
     * @param value Long value of variable
     * @return new TSField
     */
    public static TSField Long(String name, long value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.LONG;
        field.data     = new byte[TSDataType.getSize(TSDataType.LONG)];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a float variable
     * @param name Name of variable
     * @param value Float value of variable
     * @return new TSField
     */
    public static TSField Float(String name, float value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.FLOAT;
        field.data     = new byte[TSDataType.getSize(TSDataType.FLOAT)];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a double variable
     * @param name Name of variable
     * @param value Double value of variable
     * @return new TSField
     */
    public static TSField Double(String name, double value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.DOUBLE;
        field.data     = new byte[TSDataType.getSize(TSDataType.DOUBLE)];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a boolean variable
     * @param name Name of variable
     * @param value Boolean value of variable
     * @return new TSField
     */
    public static TSField Boolean(String name, boolean value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.BOOLEAN;
        field.data     = new byte[TSDataType.getSize(TSDataType.BOOLEAN)];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a char variable
     * @param name Name of variable
     * @param value Char value of variable
     * @return new TSField
     */
    public static TSField Char(String name, char value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.CHAR;
        field.data     = new byte[TSDataType.getSize(TSDataType.CHAR)];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a string variable
     * @param name Name of variable
     * @param value String value of variable
     * @return new TSField
     */
    public static TSField String(String name, String value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.STRING;
        field.data     = new byte[TSDataType.getSize(TSDataType.SHORT) + (TSDataType.getSize(TSDataType.STRING) * value.length())];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a Vector2 variable
     * @param name Name of variable
     * @param value Vector2 value of variable
     * @return new TSField
     */
    public static TSField Vector2(String name, Vector2 value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.VECTOR2;
        field.data     = new byte[TSDataType.getSize(TSDataType.FLOAT) * 2];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a Vector3 variable
     * @param name Name of variable
     * @param value Vector3 value of variable
     * @return new TSField
     */
    public static TSField Vector3(String name, Vector3 value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.VECTOR3;
        field.data     = new byte[TSDataType.getSize(TSDataType.FLOAT) * 3];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a Vector4 variable
     * @param name Name of variable
     * @param value Vector4 value of variable
     * @return new TSField
     */
    public static TSField Vector4(String name, Vector4 value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.VECTOR4;
        field.data     = new byte[TSDataType.getSize(TSDataType.FLOAT) * 4];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a Quaternion variable
     * @param name Name of variable
     * @param value Quaternion value of variable
     * @return new TSField
     */
    public static TSField Quaternion(String name, Quaternion value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.QUATERNION;
        field.data     = new byte[TSDataType.getSize(TSDataType.FLOAT) * 4];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a Matrix2 variable
     * @param name Name of variable
     * @param value Matrix2 value of variable
     * @return new TSField
     */
    public static TSField Matrix2(String name, Matrix2 value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.MATRIX2;
        field.data     = new byte[TSDataType.getSize(TSDataType.FLOAT) * 4];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a Matrix3 variable
     * @param name Name of variable
     * @param value Matrix3 value of variable
     * @return new TSField
     */
    public static TSField Matrix3(String name, Matrix3 value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.MATRIX3;
        field.data     = new byte[TSDataType.getSize(TSDataType.FLOAT) * 9];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Create a new TSField holding a Matrix4 variable
     * @param name Name of variable
     * @param value Matrix4 value of variable
     * @return new TSField
     */
    public static TSField Matrix4(String name, Matrix4 value){
        TSField field = new TSField();
        field.setName(name);
        field.dataType = TSDataType.MATRIX4;
        field.data     = new byte[TSDataType.getSize(TSDataType.FLOAT) * 16];
        field.size    += field.data.length;
        TSWriter.writeBytes(field.data, 0, value);
        return field;
    }

    /**
     * Load a new TSField from raw byte data
     * @param data raw byte array data
     * @param pointer current pointer position in byte array
     * @return a new TSField
     */
    public static TSField deserialise(byte[] data, int pointer){
        //Store the pointer to a local variable
        //Used for reading data - Strings have an undetermined length of bytes
        //                        Logical to me to store them as fields because they're used as fields within Java
        int inPointer = pointer;

        //Get the container type
        //This should confirm we are trying to deserialise the correct field
        //Future iterations could probably do better error checking with this
        byte containerType = TSReader.readByte(data, pointer);
        if(containerType != CONTAINER_TYPE){
            Logger.log(LogLevel.ERROR, TSField.class.getSimpleName(),
                            "Data may be corrupt, passed data isn't a field\n" +
                            "Pointer                    - " + pointer + "\n" +
                            "Container type             - " + containerType + "\n" +
                            "Expected container type    - " + CONTAINER_TYPE
                      );
            return null;
        }
        pointer += TSDataType.getSize(TSDataType.BYTE);

        //Create return field
        TSField field = new TSField();

        //Get the name length and then the name of the field
        field.nameLength = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);
        field.name = TSReader.readString(data, pointer, field.nameLength).getBytes();
        pointer += field.nameLength;

        //Get the size (in bytes) of the field
        field.size = TSReader.readInt(data, pointer);
        pointer += TSDataType.getSize(TSDataType.INTEGER);

        //Get what data type the field is (Byte, Short, Int, etc...)
        field.dataType = TSReader.readByte(data, pointer);
        pointer += TSDataType.getSize(TSDataType.BYTE);

        //Get the field data stored within the raw data
        //This is where inPointer comes in,
        //field.getSize() - (pointer - inPointer) should get remaining length of this field's data
        field.data = TSReader.readBytes(data, pointer, field.getSize() - (pointer - inPointer));
        pointer += field.data.length;

        //Return the completed field
        return field;
    }

    /**Getters**/

    public byte   getDataType(){ return dataType; }
    public byte[] getData(){ return data; }
    public Object getDataObject(){ return TSDataType.value(dataType, data); }

    /**To String**/

    public String toString(){
        StringBuilder returnString = new StringBuilder();

        returnString.append("==Field==").append("\n")
                .append("Name:      ").append(getName()).append("\n")
                .append("Data type: ").append(dataType).append("\n")
                .append("Data:      ").append(TSDataType.value(dataType, data).toString()).append("\n").append("\n");

        return returnString.toString();
    }
}
