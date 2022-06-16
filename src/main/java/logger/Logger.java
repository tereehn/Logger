package logger;

import handlers.Handler;
import util.Months;
import util.TimeStamp;

import javax.print.attribute.standard.Severity;
import java.io.IOException;
import java.util.Arrays;

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

    public boolean addLog(String input) throws IOException {

        LogRecord logRecord = parseInput(input);
        // check level
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

        String trimmedString = stringToSplit.trim().replaceAll(" +", " ");
        String[] parts = trimmedString.split(" ", 4);
        int[] date = Arrays.stream( parts[0].split("/")).mapToInt(Integer::parseInt).toArray();
        int[] time = Arrays.stream( parts[1].split(":")).mapToInt(Integer::parseInt).toArray();
        int tmpMonth = date[1] -1;
        TimeStamp timeStamp = new TimeStamp(date[0], Months.values()[tmpMonth],date[2],time[0],time[1],time[2]);

        // check if there is more input, get log message
        if ( parts[3].indexOf(' ') != -1){
            String[] end = parts[3].split(" ", 2);
            return new LogRecord(timeStamp, util.Severity.valueOf(end[0].toUpperCase()),Integer. parseInt(parts[2]),end[1]);
        }
        return new LogRecord(timeStamp, Integer. parseInt(parts[2]),parts[3]);
    }
}
