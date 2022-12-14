package utils.serialisation.dataObjects;

import utils.serialisation.types.TSDataType;

public abstract class TSBase {
    protected short  nameLength;        //Length of name array in bytes
    protected byte[] name;              //Name of TS item stored as byte array

    protected int    size = (    //Size of TS item in bytes
                                TSDataType.getSize(TSDataType.BYTE)  +     //CONTAINER_TYPE
                                TSDataType.getSize(TSDataType.SHORT) +     //nameLength
                                TSDataType.getSize(TSDataType.INTEGER)     //size
                            );

    /**
     * Store field data into byte array
     * @param dest destination byte array
     * @param pointer int pointer in byte array
     * @return pointer
     */
    protected abstract int getBytes(byte[] dest, int pointer);

    /**Getters**/

    public short getNameLength(){ return nameLength; }
    public String getName(){ return new String(name, 0, nameLength); }

    public int getSize(){ return size; }

    /**Setters**/

    protected void setName(String name){
        this.nameLength = (short)name.length();
        this.name       = name.getBytes();
        this.size      += this.nameLength;
    }
}
