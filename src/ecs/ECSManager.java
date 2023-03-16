package ecs;

import ecs.components.Component;
import ecs.systems.ComponentSorter;
import ecs.systems.ECSystem;
import utils.reflection.ReflectionTools;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.reflection.TSParser;
import utils.serialisation.dataObjects.TSBase;
import utils.serialisation.dataObjects.TSDatabase;
import utils.serialisation.dataObjects.TSField;
import utils.serialisation.dataObjects.TSObject;

import java.lang.reflect.Field;
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

    public void addSystem(ECSystem... systems){
        for(ECSystem system : systems){
            for(ComponentSorter cs : system.getComponentSorters())
                loadComponents(cs.getIncludedComponents());

            this.systems.add(system);
        }
    }

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
    public void loadEntity(Entity e){ entitiesToAdd.add(e); }
    public void removeEntity(Entity e){ entitiesToRemove.add(e); }

    public void addComponent(Entity e, Component... components){ componentsToAdd.put(e, validateComponents(components)); }
    public void removeComponent(Entity e, Component... components){ componentsToRemove.put(e, components); }

    /**Components**/

    /**
     * Load component classes into local array of used components
     * @param components Component classes
     */
    @SafeVarargs
    private void loadComponents(Class<? extends Component>... components){
        for(Class<? extends Component> c : components)
            loadComponent(c);
    }

    /**
     * Load component class into local array of used components
     * @param component Component class
     */
    private void loadComponent(Class<? extends Component> component){
        if(components.contains(component)){
            return;  //Component already loaded into list so ignore
        }

        components.add(component);
    }

    /**
     * Validate if component is added to ECS and then if found assign it the correct ID
     * Ignores any component that ECS isn't set to support
     * @param componentsToValidate List of components
     * @return Array of validated components
     */
    public List<Component> validateComponents(List<Component> componentsToValidate){
        return validateComponents(listToArray(componentsToValidate));
    }

    /**
     * Validate if component is added to ECS and then if found assign it the correct ID
     * Ignores any component that ECS isn't set to support
     * @param componentsToValidate List of components
     * @return Array of validated components
     */
    public List<Component> validateComponents(Component... componentsToValidate){
        ArrayList<Component> returnComponents = new ArrayList<>();
        for(Component c : componentsToValidate)
            if(components.contains(c.getClass())){
                c.setID(components.indexOf(c.getClass()));
                returnComponents.add(c);
            }else{
                Logger.log(LogLevel.WARNING, getClass().getSimpleName(), "Component [" + c.getClass().getSimpleName() + "] not in use");
            }

        return returnComponents;
    }

    /**
     * Converts a List of components to an array of components
     * @param components List object of components
     * @return array of components
     */
    private Component[] listToArray(List<Component> components){
        Component[] array = new Component[components.size()];
        for(int i = 0; i < array.length; i++)
            array[i] = components.get(i);

        return array;
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

    /**
     * Memory clean of ECS
     */
    public void destroy(){
        entitiesToRemove.addAll(entities);
        //Ensures all working lists are cleared
        update(0);
    }

    /**Getters**/

    public List<ECSystem>                   getSystems()   { return systems;    }
    public List<Entity>                     getEntities()  { return entities;   }
    public List<Class<? extends Component>> getComponents(){ return components; }

}
