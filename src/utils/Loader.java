package utils;

import ecs.ECSManager;
import ecs.Entity;
import ecs.components.Component;
import ecs.systems.ECSystem;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.reflection.TSParser;
import utils.serialisation.dataObjects.TSBase;
import utils.serialisation.dataObjects.TSDatabase;
import utils.serialisation.dataObjects.TSField;
import utils.serialisation.dataObjects.TSObject;
import utils.serialisation.types.TSDataType;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Loader {
    public TSDatabase saveECSManagerToDatabase(ECSManager manager){
        //Update ECS to clear and removal queues without updating game logic
        manager.update(0);

        TSDatabase database = TSDatabase.Create("MeowintonsDatabase");

        for(ECSystem s : manager.getSystems()){
            TSObject system = TSObject.Create("System0");
            system.add(TSField.Long("ID", 4));

            database.add(system);
        }

        for(Entity e : manager.getEntities()){
            TSObject entity = TSObject.Create("Entity"+e.getID());
            entity.add(TSField.Long("ID", e.getID()));

            for(Component c : e.getComponents().values()){
                TSObject component = TSObject.Create(c.getClass().getSimpleName());
                component.add(TSField.Long("ID", c.getID()));

                for(Field f : c.getClass().getDeclaredFields()){
                    TSBase b = TSParser.parseObject(c, f);
                    if(b != null)
                        component.add(b);
                }

                entity.add(component);
            }

            database.add(entity);
        }

        return database;
    }

    public void loadECSManagerFromDatabase(ECSManager manager, TSDatabase database){
        //Go through each object in database
        for(TSObject object : database.getObjects().values()){
            switch(object.getName()){
                case "System":
                    System.out.println("System stuff");
                    break;
                //Load entities
                case "Entity":
                    //Get the ID
                    TSField eID = object.getFields().get("ID");
                    long entityID = TSDataType.value(eID.getDataType(), eID.getData());

                    List<Component> components = new ArrayList<>();

                    //Load components
                    for(TSObject component : object.getObjects().values()){
                        TSField cID = component.getFields().get("ID");
                        long componentID = TSDataType.value(cID.getDataType(), cID.getData());

                        if(componentID > manager.getComponents().size()) continue;  //This is a component no loaded system uses so ignore.

                        Class<? extends Component> clazz = manager.getComponents().get((int)componentID);

                        for(TSField field : component.getFields().values()){
                            if(field.getName().equals("ID")) continue;

                            switch(field.getDataType()){
                                case TSDataType.BYTE:
                                    break;
                                case TSDataType.SHORT:
                                    break;
                            }
                        }
                    }

                    manager.loadEntity(new Entity(entityID, components));
                    break;
            }
        }
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
