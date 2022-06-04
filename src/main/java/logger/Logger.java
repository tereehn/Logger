package logger;

import handlers.Handler;

import javax.print.attribute.standard.Severity;

public class Logger {
    private Handler handler; // handles writing to file
    private Severity level;

    public Logger() { // received input string

    }

    public void addHandler(Handler newHandler){
        this.handler = newHandler;
    }

    public Handler getHandler() {
        return handler;
    }

    public boolean addLog(String input){

        LogRecord logRecord = parseInput(input);
        handler.write(logRecord);
        return true;
    }

    public Severity getLevel() {
        return level;
    }

    public void setLevel(Severity level) {
        this.level = level;
    }

    public LogRecord parseInput(String stringToSplit){
        System.out.println(stringToSplit);
        String[] parts = stringToSplit.split(" ", 4);
        String[] time = stringToSplit.split("/");
        System.out.println(time[0]);
        System.out.println(Integer. parseInt(time[1]));
        System.out.println(".......................");
        for(int i =0; i < parts.length;i++){
           System.out.println(parts[i]);
        }
        return null;
    }
}
