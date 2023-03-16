package utils.reflection;

import utils.logging.LogLevel;
import utils.logging.Logger;

import java.lang.reflect.Field;

public class ReflectionTools {
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
}
