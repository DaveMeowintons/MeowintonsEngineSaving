package _main;

import ecs.ECSManager;
import ecs.Entity;
import utils.Loader;
import utils.files.readers.TSReader;
import utils.files.writers.TSWriter;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.serialisation.dataObjects.TSDatabase;
import utils.serialisation.dataObjects.TSField;
import utils.serialisation.dataObjects.TSObject;

import java.io.File;

public class Main {

    public static void main(String[] args){
        Logger.log(LogLevel.DEBUG, Main.class.getSimpleName(), "The boars are in the pantry");

        //Init
        Loader loader = new Loader();

        TSDatabase database = TSDatabase.Create("Database");
        TSObject object = TSObject.Create("Example");
        TSField  field  = TSField.Integer("Example Variable", 12);
        object.add(field);
        database.add(object);

        String dir = System.getProperty("user.home") + "/Documents/Bovril Beggars";
        File base = new File(dir);
        if(!base.exists())
            if(base.mkdir()) Logger.log(LogLevel.DEBUG, Main.class.getSimpleName(), "Created Bovril Beggars folder within Documents");
            else Logger.log(LogLevel.WARNING, Main.class.getSimpleName(), "Couldn't create Bovril Beggars folder in Documents");

        TSWriter writer = new TSWriter();
        writer.createFile(dir + "/Example.tsd");
        writer.writeDatabaseToFile(database);
        writer.close();

        TSReader reader = new TSReader();
        reader.open(loader, dir + "/Example.tsd");
        TSDatabase loaded = TSDatabase.deserialise(reader.getData());
        reader.close();
        System.out.println(loaded);

        ECSManager testManager = new ECSManager();
        testManager.addSystem(new TestSystem());
        testManager.createEntity(new TestComponent(), new AnotherComponent());
        testManager.createEntity(new AnotherComponent());

        //Update
        testManager.update(0);

        for(Entity e : testManager.getEntities())
            Logger.log(LogLevel.DEBUG, Main.class.getSimpleName(), e);

        //Clean up
        testManager.destroy();
    }

}