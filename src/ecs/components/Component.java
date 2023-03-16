package ecs.components;

public abstract class Component {
    public static final long NO_ID = Long.MIN_VALUE;

    private long id;

    protected Component(){
        id = NO_ID;
    }

    /**Getters**/

    public long getID(){ return id; }

    /**Setters**/

    public void setID(long id){ this.id = id; }
}
