package utils;

import utils.logging.LogLevel;
import utils.logging.Logger;

import java.io.*;

public class Loader {
    public BufferedReader getReader(String file){
        try{
            return new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + file)));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),"Error loading file");
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),error);
            return null;
        }
    }

    public BufferedReader getReader(File file){
        try{
            return new BufferedReader(new FileReader(file));
        }catch(FileNotFoundException error){
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),"Error loading file");
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),error);
            return null;
        }
    }

    public BufferedInputStream getSerialisedData(String file){
        try{
            return new BufferedInputStream(new FileInputStream(file));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),"Error loading file: " + file);
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),error);
            return null;
        }
    }

    public BufferedInputStream getSerialisedData(File file){
        try{
            return new BufferedInputStream(new FileInputStream(file));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),"Error loading file: " + file);
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),error);
            return null;
        }
    }
}
