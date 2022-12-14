package utils.files.readers;

import utils.Loader;

import java.io.File;

public abstract class FileReader {
    protected String         fileName;

    /**
     * Opens a local file stored within the project file
     * @param loader Loader class
     * @param file local file path
     */
    public abstract void open(Loader loader, String file);

    /**
     * Opens an external file stored outside the project file
     * @param loader Loader class
     * @param file external file class
     */
    public abstract void open(Loader loader, File file);

    /**
     * Closes file and clears memory
     */
    public abstract void close();


    /**Getters**/

    public String getFileName(){ return fileName; }
}
