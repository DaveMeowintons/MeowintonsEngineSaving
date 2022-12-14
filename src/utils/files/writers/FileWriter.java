package utils.files.writers;

public abstract class FileWriter {
    protected String         fileName;

    /**
     * Create an external file
     * @param path external file path
     */
    public abstract void createFile(String path);

    /**
     * Closes file and clears memory
     */
    public abstract void close();

    /**Getters**/

    public String getFileName(){ return fileName; }
}
