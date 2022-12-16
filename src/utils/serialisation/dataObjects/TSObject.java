package utils.serialisation.dataObjects;

import utils.files.readers.TSReader;
import utils.files.writers.TSWriter;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.serialisation.types.TSContainerType;
import utils.serialisation.types.TSDataType;

import java.util.HashMap;
import java.util.Map;

public class TSObject extends TSBase {
    public static final byte CONTAINER_TYPE = TSContainerType.OBJECT;

    private short  objectCount; //Amount of items in "objects" map stored as a short
    private short  fieldCount;  //Amount of items in "fields" map stored as a short
    private short  arrayCount;  //Amount of items in "arrays" map stored as a short

    private final Map<String, TSObject> objects = new HashMap<>();
    private final Map<String, TSField> fields = new HashMap<>();
    private final Map<String, TSArray> arrays = new HashMap<>();

    private TSObject(){
        size += TSDataType.getSize(TSDataType.SHORT) + //objectCount
                TSDataType.getSize(TSDataType.SHORT) + //fieldCount
                TSDataType.getSize(TSDataType.SHORT);  //arrayCount
    }

    /**
     * Create a new TSObject and set its name
     * @param name Name of TSObject
     * @return new TSObject
     */
    public static TSObject Create(String name){
        TSObject object = new TSObject();
        object.setName(name);
        return object;
    }

    /**
     * Load a new TSObject from raw byte data
     * @param data raw byte array data
     * @param pointer current pointer position in byte array
     * @return a new TSObject
     */
    public static TSObject deserialise(byte[] data, int pointer){
        //Get the container type
        //This should confirm we are trying to deserialise the correct object
        //Future iterations could probably do better error checking with this
        byte containerType = TSReader.readByte(data, pointer);
        if(containerType != CONTAINER_TYPE){
            Logger.log(LogLevel.ERROR, TSObject.class.getSimpleName(),
                            "Data may be corrupt, passed data isn't an object\n" +
                            "Pointer                    - " + pointer + "\n" +
                            "Container type             - " + containerType + "\n" +
                            "Expected container type    - " + CONTAINER_TYPE
                      );
            return null;
        }

        pointer += TSDataType.getSize(TSDataType.BYTE);

        //Create return object
        TSObject object = new TSObject();

        //Get the name length and then the name of the object
        object.nameLength = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);
        object.name = TSReader.readString(data, pointer, object.nameLength).getBytes();
        pointer += object.nameLength;

        //Get the size (in bytes) of the object
        object.size = TSReader.readInt(data, pointer);
        pointer += TSDataType.getSize(TSDataType.INTEGER);

        //Get how many objects are in the object
        object.objectCount = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);
        //Deserialise the child objects stored within the raw data
        for(int i = 0; i < object.objectCount; i++) {
            TSObject child = TSObject.deserialise(data, pointer);
            object.objects.put(child.getName(), child);
            pointer += child.getSize();
        }

        //Get how many fields are in the object
        object.fieldCount = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);
        //Deserialise the fields stored within the raw data
        for(int i = 0; i < object.fieldCount; i++) {
            TSField field = TSField.deserialise(data, pointer);
            object.fields.put(field.getName(), field);
            pointer += field.getSize();
        }

        //Get how many arrays are in the object
        object.arrayCount = TSReader.readShort(data, pointer);
        pointer += TSDataType.getSize(TSDataType.SHORT);
        //Deserialise the arrays stored within the raw data
        for(int i = 0; i < object.arrayCount; i++) {
            TSArray array = TSArray.deserialise(data, pointer);
            object.arrays.put(array.getName(), array);
            pointer += array.getSize();
        }

        //Return the completed object
        return object;
    }

    /**
     * Add a TSObject into the local TSObject array
     * @param object TSObject to add
     */
    public void add(TSObject object){ this.objects.put(object.getName(), object); this.objectCount = (short)this.objects.size(); this.size += object.size; }
    /**
     * Add a TSField into the local TSField array
     * @param field TSField to add
     */
    public void add (TSField  field){ this.fields .put(field.getName(), field ); this.fieldCount  = (short)this.fields .size(); this.size += field .size; }
    /**
     * Add a TSArray into the local TSArray array
     * @param array TSArray to add
     */
    public void add (TSArray  array){ this.arrays .put(array.getName(), array ); this.arrayCount  = (short)this.arrays .size(); this.size += array .size; }

    /**
     * Find a specified TSObject from the Mapped TSObject list
     * @param name name of TSObject
     */
    public TSObject findObject(String name){
        if(objects.containsKey(name)) return objects.get(name);
        return null;
    }

    /**
     * Find a specified TSField from the Mapped TSField list
     * @param name name of TSField
     */
    public TSField findField(String name){
        if(fields.containsKey(name)) return fields.get(name);
        return null;
    }

    /**
     * Find a specified TSArray from the Mapped TSArray list
     * @param name name of TSArray
     */
    public TSArray findArray(String name){
        if(arrays.containsKey(name)) return arrays.get(name);
        return null;
    }

    /**Getters**/

    public short getObjectCount(){ return objectCount; }
    public short getFieldCount() { return fieldCount;  }
    public short getArrayCount() { return arrayCount;  }

    public Map<String, TSObject> getObjects(){ return objects; }
    public Map<String, TSField>  getFields() { return fields;  }
    public Map<String, TSArray>  getArrays() { return arrays;  }

    /**To String**/

    public String toString(){
        StringBuilder returnString = new StringBuilder();

        returnString.append("==OBJECT==").append("\n")
                .append("Name:                ").append(getName()).append("\n")
                .append("Size (in bytes):     ").append(size).append("\n")
                .append("Child object count:  ").append(objectCount).append("\n")
                .append("Field count:         ").append(fieldCount).append("\n")
                .append("Array count:         ").append(arrayCount).append("\n").append("\n");

        for(TSObject object : objects.values())
            returnString.append(object);

        for(TSField field : fields.values())
            returnString.append(field);

        for(TSArray array : arrays.values())
            returnString.append(array);

        return returnString.toString();
    }
}
