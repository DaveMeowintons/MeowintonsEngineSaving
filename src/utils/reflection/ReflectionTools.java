package utils.reflection;

import utils.logging.LogLevel;
import utils.logging.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class ReflectionTools {
    /**
     * Reads an Object's field and returns the value
     * @param origin Object with field value we want
     * @param f Field we're reading
     * @return Value of field in context of the origin
     */
    public static Object readField(Object origin, Field f){
        try{
            f.setAccessible(true);
            Object value = f.get(origin);
            f.setAccessible(false);

            return value;
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, ReflectionTools.class.getSimpleName(), "Couldn't read field " + f.getName() + "!");
            Logger.log(LogLevel.ERROR, ReflectionTools.class.getSimpleName(), error);
        }

        return null;
    }

    /**
     * Sets an Object's field value
     * @param origin Object with field we want to set value of
     * @param f Field we're setting
     * @param value Value we're giving the field
     */
    public static void setField(Object origin, Field f, Object value){
        try{
            f.setAccessible(true);
            f.set(origin, value);
            f.setAccessible(false);
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, ReflectionTools.class.getSimpleName(), "Couldn't set field " + f.getName() + "!");
            Logger.log(LogLevel.ERROR, ReflectionTools.class.getSimpleName(), error);
        }
    }

    /**
     * Creates a new object of the given Class instance using the Constructor matching the given args
     * @param clazz Class we want to make an instance of
     * @param args Arguments used to make instance
     * @return New instance of given Class
     */
    public static Object createObject(Class<?> clazz, List<Object> args){
        try{
            Class<?>[] clazzArgs = new Class[args.size()];
            for(int i = 0; i < clazzArgs.length; i++) clazzArgs[i] = args.get(i).getClass();

            Constructor<?> explicitConstructor = clazz.getConstructor(clazzArgs);
            return explicitConstructor.newInstance(args.toArray());
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, ReflectionTools.class.getSimpleName(), error);
        }

        return null;
    }

    /**
     * Create a new object of a given Class instance using the constructor matching the given class types
     * @param clazz Class we want to make an instance of
     * @param clazzArgs Class types used to make instance
     * @return New instance of given class
     */
    public static Object createObject(Class<?> clazz, Class<?>[] clazzArgs){
        try{
            Object[] args = new Class[clazzArgs.length];

            Constructor<?> explicitConstructor = clazz.getConstructor(clazzArgs);
            return explicitConstructor.newInstance(args);
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, ReflectionTools.class.getSimpleName(), error);
        }

        return null;
    }
}
