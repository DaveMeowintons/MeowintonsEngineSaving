package _main;

import ecs.ECSManager;
import ecs.Entity;
import utils.logging.LogLevel;
import utils.logging.Logger;

public class Main {

    public static void main(String[] args){
        Logger.log(LogLevel.DEBUG, Main.class.getSimpleName(), "The boars are in the pantry");

        //Init
        ECSManager testManager = new ECSManager();
        testManager.addSystem(new TestSystem());
        testManager.createEntity(new TestComponent(), new AnotherComponent());

        //Update
        testManager.update(0);

        for(Entity e : testManager.getEntities())
            Logger.log(LogLevel.DEBUG, Main.class.getSimpleName(), e);

        //Clean up
        testManager.destroy();
    }

}