package utils.logging;

import java.util.Date;

import static utils.logging.LogLevel.*;

public class Logger {
    public static void log(LogLevel level, String origin, String output){
        String[] messages = output.split("\n");
        for(String message : messages){
            Date date = new Date();
            message = message.replace("%","%%");
            String s;

            if(origin != null) s = String.format("[%tD %tT][" + level.name + "] - [" + origin + "] " + message, date, date);
            else s = String.format("[%tD %tT][" + level.name + "] - " + message, date, date);

            if(level == ERROR || level == WARNING) System.err.println(s);
            else System.out.println(s);
        }
    }

    public static void log(LogLevel level, String origin, Object object  ){ log(level, origin, object.toString()); }
    public static void log(LogLevel level, String origin, Exception error){ log(level, origin, error.toString()); for(StackTraceElement e : error.getStackTrace()) log(level, origin, e.toString()); }
    public static void log(LogLevel level, String origin, Throwable error){ log(level, origin, error.toString()); for(StackTraceElement e : error.getStackTrace()) log(level, origin, e.toString()); }
}
