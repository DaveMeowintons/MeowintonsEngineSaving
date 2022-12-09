package ecs;

import _main.TestComponent;
import ecs.components.Component;
import ecs.systems.ECSystem;

import java.util.*;
import java.util.List;

public class ECSManager {
    private long entityID;

    private final List<ECSystem> systems = new LinkedList<>();

    /**Entities**/
    private final HashMap<Entity, List<Component>> componentsToAdd = new HashMap<>();
    private final HashMap<Entity, Component[]> componentsToRemove = new HashMap<>();

    private final List<Entity> entitiesToAdd = new ArrayList<>();
    private final List<Entity> entitiesToRemove = new LinkedList<>();
    private final Set<Entity> entitiesToValidate = new HashSet<>();

    private final List<Entity> entities = new LinkedList<>();

    /**Components**/
    private final List<Class<? extends Component>> components = new ArrayList<>();

    public ECSManager() { entityID = 0; }

    public void addSystem(ECSystem... systems){ Collections.addAll(this.systems, systems); }

    /**Entities**/
    public Entity createEntity(Component... components){
        Entity e = new Entity(entityID++, validateComponents(components));
        entitiesToAdd.add(e);
        return e;
    }

    public Entity createEntity(List<Component> components){
        Entity e = new Entity(entityID++, validateComponents(components));
        entitiesToAdd.add(e);
        return e;
    }

    public void addEntity(Entity e){ e.setID(entityID++); entitiesToAdd.add(e); }
    public void removeEntity(Entity e){ entitiesToRemove.add(e); }

    public void addComponent(Entity e, Component... components){ componentsToAdd.put(e, validateComponents(components)); }
    public void removeComponent(Entity e, Component... components){ componentsToRemove.put(e, components); }

    /**Components**/
    public void loadComponent(Class<? extends Component> component){ components.add(component); }
    public List<Component> validateComponents(List<Component> componentsToValidate){
        ArrayList<Component> returnComponents = new ArrayList<>();
        for(Component c : componentsToValidate)
            if(components.contains(c.getClass())) returnComponents.add(c);

        return returnComponents;
    }
    public List<Component> validateComponents(Component... componentsToValidate){
        ArrayList<Component> returnComponents = new ArrayList<>();
        for(Component c : componentsToValidate)
            if(components.contains(c.getClass())) returnComponents.add(c);

        return returnComponents;
    }

    public void update(float interval){
        for(ECSystem s : systems)
            s.update(interval);

        for(Entity e : componentsToRemove.keySet()){
            e.remove(componentsToRemove.get(e));
            entitiesToValidate.add(e);
        }
        componentsToRemove.clear();

        for(Entity e : componentsToAdd.keySet()){
            e.add(componentsToAdd.get(e));
            entitiesToValidate.add(e);
        }
        componentsToAdd.clear();

        entities.addAll(entitiesToAdd);

        for(ECSystem s : systems){
            for(Entity e : entitiesToAdd)
                s.consider(e);
            for(Entity e : entitiesToValidate)
                s.validate(e);
            for(Entity e : entitiesToRemove)
                s.remove(e);
        }

        entities.removeAll(entitiesToRemove);

        entitiesToAdd.clear();
        entitiesToRemove.clear();
        entitiesToValidate.clear();
    }

    public void destroy(){
        entitiesToRemove.addAll(entities);
        update(0);
    }

    /**Getters**/

    public List<ECSystem> getSystems(){ return systems; }
    public List<Entity> getEntities(){ return entities; }

}
