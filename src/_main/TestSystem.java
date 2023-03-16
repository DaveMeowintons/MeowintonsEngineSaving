package _main;

import ecs.systems.ComponentSorter;
import ecs.systems.ECSystem;

public class TestSystem extends ECSystem {

    public TestSystem(){
        super(
                new ComponentSorter(true, TestComponent.class, OtherComponent.class)
        );
    }

    @Override
    public void update(float interval) {

    }
}
