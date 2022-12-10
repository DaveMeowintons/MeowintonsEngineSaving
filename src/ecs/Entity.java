package ecs;

import ecs.components.Component;

import java.util.*;
import java.util.List;

public class Entity {
    private long id;
    private final Map<Class<? extends Component>, Component> components;

    public Entity(List<Component> components){
        this.components = new HashMap<>();
        add(components);
    }

    public Entity(Component... components){
        this.components = new HashMap<>();
        add(components);
    }

    public Entity(long id, Component... components){
        this(components);
        this.id = id;
    }

    public Entity(long id, List<Component> components){
        this(components);
        this.id = id;
    }

    /**
     * Get all components of type in an entity
     * @return specified component
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<? extends Component> componentClass){ return (T)components.get(componentClass); }
    public void add(List<Component> components){ for(Component c : components) this.components.put(c.getClass(), c); }
    public void add(Component... components){ for(Component c : components) this.components.put(c.getClass(), c); }
    public void remove(Component... components){ for(Component c : components) this.components.remove(c.getClass()); }

    @SafeVarargs
    public final boolean contains(Class<? extends Component>... components){ return this.components.keySet().containsAll(Arrays.asList(components)); }

    @SafeVarargs
    public final boolean containsAny(Class<? extends Component>... components){
        Set<Class<? extends Component>> s = this.components.keySet();
        for(Class<? extends Component> c : components){
            if(s.contains(c)) return true;
        }
        return false;
    }

    /**Getters**/

    public Map<Class<? extends Component>, Component> getComponents(){ return components; }

    public long getID(){ return id; }

    /**Setters**/

    public void setID(long id){ this.id = id; }

    /**String**/

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("Entity[").append(id).append("]{\n");
        builder.append("  Components(").append(components.size()).append("){").append("\n");
        for(Component c : components.values())
            builder.append("    ").append(c.getClass().getSimpleName()).append(", ID: ").append(c.getID()).append("\n");
        builder.append("  }\n").append("}");

        return builder.toString();
    }
}
