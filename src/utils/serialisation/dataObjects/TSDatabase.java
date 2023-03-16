package utils.serialisation.dataObjects;

import utils.Maths;
import utils.files.readers.TSReader;
import utils.files.writers.TSWriter;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.serialisation.types.TSContainerType;
import utils.serialisation.types.TSDataType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TSDatabase extends TSBase {
    public static final byte CONTAINER_TYPE = TSContainerType.DATABASE;

    private byte[] header  = TSWriter.HEADER;    //Current header prefix (shouldn't change)
    private short  version = TSWriter.VERSION;   //Current version of ThunderStorm for legacy support (can change)

    private short objectCount;          //Amount of items in "objects" map stored as a short
    private final Map<String, TSObject> objects = new HashMap<>();

    private TSDatabase(){
        size += header.length                          +    //HEADER
                TSDataType.getSize(TSDataType.SHORT)   +    //VERSION
                TSDataType.getSize(TSDataType.SHORT);       //objectCount
    }

    /**
     * Create a new TSDatabase and set its name
     * @param name Name of TSDatabase
     * @return new TSDatabase
     */
    public static TSDatabase Create(String name){
        TSDatabase database = new TSDatabase();
        database.setName(name);
        return database;
    }

    /**
     * Load a new TSDatabase from raw byte data
     * @param data raw byte array data
     * @return a new TSDatabase
     */
    public static TSDatabase deserialise(byte[] data) {
        int pointer = 0;

        //Get Header and Version of database
        //Header should reveal file is supposed to resemble a ThunderStorm Database
        //Future iterations should decide what to do based on the version of the Database
        byte[] header = TSReader.readBytes(data, pointer, TSWriter.HEADER.length);
        if(!Maths.checkEqual(header, header)){
            Logger.log(LogLevel.ERROR, TSDatabase.class.getSimpleName(),
                            "Data may be corrupt, expected database header doesn't match\n" +
                            "Read header        - " + Arrays.toString(header) + "\n" +
                            "Expected header    - " + Arrays.toString(header)
                        );
            return null;
        }
        pointer += TSWriter.HEADER.length;

        short version = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);

        //Get the container type
        //This should confirm we are trying to deserialise the correct object
        //Future iterations could probably do better error checking with this
        byte containerType = TSReader.readByte(data, pointer);
        if(containerType != CONTAINER_TYPE){
            Logger.log(LogLevel.ERROR, TSDatabase.class.getSimpleName(),
                            "Data may be corrupt, this isn't a database\n" +
                            "Pointer                    - " + pointer + "\n" +
                            "Container type             - " + containerType + "\n" +
                            "Expected container type    - " + CONTAINER_TYPE
                        );
            return null;
        }
        pointer += TSDataType.getSize(TSDataType.BYTE);

        //Create return database
        TSDatabase database = new TSDatabase();

        //Store header and version
        database.header  = header;
        database.version = version;

        //Get the name length and then the name of the database
        database.nameLength = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);
        database.name = TSReader.readString(data, pointer, database.nameLength).getBytes();
        pointer += database.nameLength;

        //Get the size (in bytes) of the database
        database.size = TSReader.readInt(data, pointer);
        pointer += TSDataType.getSize(TSDataType.INTEGER);

        //Get how many objects are in the database
        database.objectCount = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);

        //Deserialise the objects stored within the raw data
        for(int i = 0; i < database.objectCount; i++){
            TSObject obj = TSObject.deserialise(data, pointer);
            database.objects.put(obj.getName(), obj);
            pointer += obj.getSize();
        }

        //Return the completed database
        return database;
    }

    /**
     * Add a TSObject into the local TSObject array
     * @param object TSObject to add
     */
    public void add(TSObject object){ this.objects.put(object.getName(), object); this.objectCount = (short)this.objects.size(); this.size += object.getSize(); }

    /**
     * Find a specified TSObject from the Mapped TSObject list
     * @param name name of TSObject
     */
    public TSObject findObject(String name){
        if(objects.containsKey(name)) return objects.get(name);

        return null;
    }

    /**Getters**/

    public byte[] getHeader(){ return header; }
    public short getVersion(){ return version; }

    public short getObjectCount(){ return objectCount; }
    public Map<String, TSObject> getObjects (){ return objects; }

    /**To String**/

    public String toString(){
        StringBuilder returnString = new StringBuilder();

        returnString.append("==DATABASE==").append("\n")
                    .append("Name: ").append(getName()).append("\n")
                    .append("Header: ").append(new String(getHeader())).append("\n")
                    .append("Version: ").append((getVersion() >> 8 & 0xff)).append(".").append((getVersion() & 0xff)).append("\n")
                    .append("Size (in bytes): ").append(size).append("\n")
                    .append("Object count: ").append(objectCount).append("\n").append("\n");

        for(TSObject object : objects.values())
            returnString.append(object);

        return returnString.toString();
    }
}
