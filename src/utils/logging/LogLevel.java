package utils.logging;

public enum LogLevel {
    DEBUG   (1, "DEBUG"),
    WARNING (2, "WARNING"),
    ERROR   (3, "ERROR");

    public final int id;
    public final String name;

    LogLevel(int id, String name){
        this.id = id;
        this.name = name;
    }
}
