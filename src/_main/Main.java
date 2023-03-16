package _main;

import ecs.ECSManager;
import utils.Loader;
import utils.files.readers.TSReader;
import utils.files.writers.TSWriter;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.serialisation.dataObjects.TSDatabase;

import java.io.File;

public class Main {

    public static void main(String[] args){
        Logger.log(LogLevel.DEBUG, Main.class.getSimpleName(), "The boars are in the pantry");

        //Init
        Loader loader = new Loader();

        String dir = System.getProperty("user.home") + "/Documents/Bovril Beggars";
        File base = new File(dir);
        if(!base.exists())
            if(base.mkdir()) Logger.log(LogLevel.DEBUG, Main.class.getSimpleName(), "Created Bovril Beggars folder within Documents");
            else Logger.log(LogLevel.WARNING, Main.class.getSimpleName(), "Couldn't create Bovril Beggars folder in Documents");

        ECSManager testManager = new ECSManager();
        testManager.addSystem(new TestSystem());
        testManager.createEntity(new TestComponent(), new MeshComponent("test"));

        TSDatabase database = loader.saveECSManagerToDatabase(testManager);
        TSWriter writer = new TSWriter();
        writer.createFile(dir + "/Example.tsd");
        writer.writeDatabaseToFile(database);
        writer.close();

        TSReader reader = new TSReader();
        reader.open(loader, dir + "/Example.tsd");
        TSDatabase loaded = TSDatabase.deserialise(reader.getData());
        reader.close();

        loader.loadECSManagerFromDatabase(testManager, loaded);

        //Clean up
        testManager.destroy();
    }

}