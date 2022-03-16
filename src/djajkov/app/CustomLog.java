package djajkov.app;

import java.io.*;
import java.time.LocalDateTime;

public class CustomLog {
    static String filename = "custom-log.txt";
    public enum LogLevel{
        CRITICAL("CRITICAL"),
        ERROR("ERROR"),
        NOTIFICATION("NOTIFICATION"),
        DEFAULT("DEFAULT");

        private final String text;

        LogLevel(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    public static void write(LogLevel logLevel, String message){
        try {
            File myObj = new File(filename);
            if(!myObj.exists())
                myObj.createNewFile();
            FileWriter myWriter = new FileWriter(filename,true);
            String time = LocalDateTime.now().toString().replace("T"," ");
            myWriter.write(time + "|" + logLevel.toString() + ": " + message + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void start(){
        try {
            File myObj = new File(filename);
            if(!myObj.exists())
                myObj.createNewFile();
            FileWriter myWriter = new FileWriter(filename,true);
            String time = LocalDateTime.now().toString().replace("T"," ");
            myWriter.write(time + "|" + "Application started" + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void finish(){
        try {
            File myObj = new File(filename);
            if(!myObj.exists())
                myObj.createNewFile();
            FileWriter myWriter = new FileWriter(filename,true);
            String time = LocalDateTime.now().toString().replace("T"," ");
            myWriter.write(time + "|" + "Application finished" + "\n\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
