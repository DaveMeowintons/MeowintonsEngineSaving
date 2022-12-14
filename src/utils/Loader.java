package utils;

import utils.logging.LogLevel;
import utils.logging.Logger;

import java.io.*;

public class Loader {
    /**
     * Get BufferedReader from local file
     * @param file String path to local file
     * @return BufferedReader of data from file
     */
    public BufferedReader getReader(String file){
        try{
            return new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + file)));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),"Error loading file");
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),error);
            return null;
        }
    }

    /**
     * Get BufferedReader from external file
     * @param file External file
     * @return BufferedReader of data from file
     */
    public BufferedReader getReader(File file){
        try{
            return new BufferedReader(new FileReader(file));
        }catch(FileNotFoundException error){
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),"Error loading file");
            Logger.log(LogLevel.ERROR,getClass().getSimpleName(),error);
            return null;
        }
    }

    /**
     * Get BufferedInputStream from external file path
     * @param file String path to external file
     * @return BufferedInputStream of data from file
     */
    public BufferedInputStream getSerialisedData(String file){
        try{
            return new BufferedInputStream(new FileInputStream(file));
        }catch(Exception error){
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),"Error loading file: " + file);
            Logger.log(LogLevel.ERROR,Loader.class.getSimpleName(),error);
            return null;
        }
    }

    /**
     * Get BufferedInputStream from external file
     * @param file External file
     * @return BufferedInputStream of data from file
     */
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
