package ecs.systems;

import ecs.Entity;

import java.util.List;

public abstract class ECSystem {
    protected ComponentSorter[] componentSorters;

    public ECSystem(ComponentSorter... componentSorters){ this.componentSorters = componentSorters; }

    public abstract void update(float interval);

    public List<Entity> entities(          ){ return this.entities(0); }
    public List<Entity> entities(int sorter){ return componentSorters[sorter].getEntities(); }

    public void consider(Entity e){
        for(ComponentSorter cs : componentSorters)
            cs.consider(e);
    }

    public void validate(Entity e){
        for(ComponentSorter cs : componentSorters)
            cs.validate(e);
    }

    public void remove(Entity e){
        for(ComponentSorter cs: componentSorters)
            cs.remove(e);
    }
}
