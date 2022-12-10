package ecs.systems;

import ecs.Entity;
import ecs.components.Component;

import java.util.LinkedList;
import java.util.List;

public class ComponentSorter {
    private final boolean strict;
    private final Class<? extends Component>[] include;
    private final List<Entity> entities;

    @SafeVarargs
    public ComponentSorter(boolean strict, Class<? extends Component>... components){
        this.strict = strict;
        this.include = components;
        this.entities = new LinkedList<>();
    }

    private boolean matches(Entity e){ if(strict) return e.contains(include); else return e.containsAny(include); }

    public synchronized boolean consider(Entity e){
        if(this.matches(e)){
            this.entities.add(e);
            return true;
        }

        return false;
    }

    public synchronized boolean validate(Entity e){
        if(this.entities.contains(e)){
            if(!this.matches(e)){
                this.remove(e);
                return false;
            }
        }else{
            return this.consider(e);
        }

        return false;
    }

    public void remove(Entity e){ this.entities.remove(e); }

    /**Getters**/

    public List<Entity> getEntities(){ return entities; }
    public Class<? extends Component>[] getIncludedComponents(){ return include; }
}
