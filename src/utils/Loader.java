package utils;

import _main.MeshComponent;
import ecs.ECSManager;
import ecs.Entity;
import ecs.components.Component;
import ecs.components.ComponentSerializer;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.reflection.ReflectionTools;
import utils.reflection.TSParser;
import utils.serialisation.dataObjects.*;
import utils.serialisation.types.TSDataType;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    /**
     * Store all Entities in an ECSManager into a TSDatabase for saving to file
     * @param manager ECSManager we want to save
     * @return TSDatabase containing all necessary Entity information for loading later
     */
    public TSDatabase saveECSManagerToDatabase(ECSManager manager){
        //Update ECS to clear and removal queues without updating game logic
        manager.update(0);

        TSDatabase database = TSDatabase.Create("MeowintonsDatabase");

        for(Entity e : manager.getEntities()){
            TSObject entity = TSObject.Create("Entity");
            //Add entity ID to TSObject
            entity.add(TSField.Long("ID", e.getID()));

            //Go through each component in entity to store in database
            for(Component c : e.getComponents().values()){
                //Create a new TSObject for the component
                TSObject component = TSObject.Create(c.getClass().getSimpleName());
                //Add component ID to TSObject
                component.add(TSField.Long("ID", c.getID()));

                if(c instanceof ComponentSerializer){
                    //Component has specific serialise instructions
                    component.add(((ComponentSerializer)c).serialise());
                }else{
                    //Store all fields in component
                    for(Field f : c.getClass().getDeclaredFields()){
                        TSBase b = TSParser.parseField(c, f);
                        if(b != null) component.add(b);
                    }
                }

                //Add component to parent entity TSObject
                entity.add(component);
            }

            //Add entity to database
            database.add(entity);
        }

        return database;
    }

    /**
     * Reset an ECSManager and load a TSDatabase into it
     * @param manager ECSManager we want to load a TSDatabase into
     * @param database TSDatabase we want to load into the ECSManager
     */
    public void loadECSManagerFromDatabase(ECSManager manager, TSDatabase database){
        //Remove all entities from the ECSManager before loading in new entities
        for(Entity e : manager.getEntities())
            manager.removeEntity(e);
        manager.update(0);

        //Go through each object in database
        for(TSObject object : database.getObjects()){
            //Only want to deal with entities
            if(object.getName().equals("Entity")){
                //Get the ID
                TSField eID = object.getFields().get("ID");
                long entityID = TSDataType.value(eID.getDataType(), eID.getData());

                List<Component> components = new ArrayList<>();

                //Load components
                for(TSObject component : object.getObjects().values()){
                    //Get the ID
                    TSField cID = component.getFields().get("ID");
                    long componentID = TSDataType.value(cID.getDataType(), cID.getData());

                    if(componentID > manager.getComponents().size()) continue;  //This is a component no loaded system uses so ignore.

                    //Get Class from stored list of used component classes with the component ID
                    Class<? extends Component> clazz = manager.getComponents().get((int)componentID);

                    //Create the component object
                    Object obj = ReflectionTools.createObject(clazz, getClassArguments(clazz));

                    //Load all component variables and store them in created component object
                    for(Field f : clazz.getDeclaredFields()){
                        if(component.getFields().containsKey(f.getName())){
                            TSField field = component.getFields().get(f.getName());
                            ReflectionTools.setField(obj, f, field.getDataObject());
                        }else if(component.getArrays().containsKey(f.getName())){
                            TSArray array = component.getArrays().get(f.getName());

                            //Check if field is a List
                            //If yes convert array data into List data
                            //If no assign array data
                            ReflectionTools.setField(obj, f, f.getType().isAssignableFrom(List.class) ? TSParser.convertToList(array.getDataObject()) : array.getDataObject());
                        }
                    }

                    //Add component object to list of components
                    components.add((Component)obj);
                }

                //Create entity with correct ID and components
                manager.loadEntity(new Entity(entityID, components));
            }
        }

        //Update ECS to add all entities into working list
        manager.update(0);
    }

    private Class<?>[] getClassArguments(Class<?> clazz){
        //Get first available constructor for component
        Constructor<?> constructor = clazz.getConstructors()[0];
        //Create array of Classes to store argument Class types in
        Class<?>[] clazzArguments = new Class<?>[constructor.getParameters().length];

        //Store all Class types into array
        for(int i = 0; i < clazzArguments.length; i++)
            clazzArguments[i] = constructor.getParameters()[i].getType();

        //Return Class argument array
        return clazzArguments;
    }

    /**
     * Get BufferedReader from local file
     * @param file String path to local file
     * @return BufferedReader of data from file
     */
    public BufferedReader getReader(String file){
        try{
            return new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + file)));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),"Error loading file");
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),error);
            return null;
        }
    }

    /**
     * Get BufferedReader from external file
     * @param file External file
     * @return BufferedReader of data from file
     */
    public BufferedReader getReader(File file){
        try{
            return new BufferedReader(new FileReader(file));
        }catch(FileNotFoundException error){
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),"Error loading file");
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),error);
            return null;
        }
    }

    /**
     * Get BufferedReader from local file
     * @param file String path to local file
     * @return BufferedReader of data from file
     */
    public byte[] getByteData(String file){
        try{
            return Files.readAllBytes(Paths.get(file));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),"Error loading file");
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),error);
            return null;
        }
    }

    /**
     * Get BufferedInputStream from external file
     * @param file External file
     * @return BufferedInputStream of data from file
     */
    public byte[] getByteData(File file){
        try{
            return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),"Error loading file: " + file);
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),error);
            return null;
        }
    }

    /**
     * Get BufferedInputStream from external file path
     * @param file String path to external file
     * @return BufferedInputStream of data from file
     */
    public BufferedInputStream getSerialisedData(String file){
        try{
            return new BufferedInputStream(new FileInputStream(file));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),"Error loading file: " + file);
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),error);
            return null;
        }
    }

    /**
     * Get BufferedInputStream from external file
     * @param file External file
     * @return BufferedInputStream of data from file
     */
    public BufferedInputStream getSerialisedData(File file){
        try{
            return new BufferedInputStream(new FileInputStream(file));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),"Error loading file: " + file);
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),error);
            return null;
        }
    }
}
