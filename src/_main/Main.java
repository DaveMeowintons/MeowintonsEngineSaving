package _main;

import ecs.ECSManager;
import ecs.Entity;

public class Main {

    public static void main(String[] args){
        System.out.println("The boars are in the pantry");

        //Init
        ECSManager testManager = new ECSManager();
        testManager.loadComponent(TestComponent.class);
        testManager.createEntity(new TestComponent());

        //Update
        testManager.update(0);

        for(Entity e : testManager.getEntities())
            System.out.println(e);

    }

}