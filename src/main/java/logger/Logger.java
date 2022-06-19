

package logger;

import handlers.Handler;
import util.ErrorLevel;
import util.TimeStamp;

import java.io.IOException;
import java.util.*;

public class Logger {
    private Handler handler;
    private ErrorLevel level = ErrorLevel.TRACE;
    private final Queue<LogRecord> queue = new LinkedList<>();
    private int workingThreads = 2;

    /**
     * Default constructor.
     */
    public Logger() {

    }

    public void addHandler(Handler newHandler){
        this.handler = newHandler;
    }

    public boolean addLog(String input){
        ArrayList<LogRecord> listOfLogs = new ArrayList<>();
        if (input != null) {
            listOfLogs = parseInput(input);
            if (listOfLogs.get(0).getSeverity() != null && !listOfLogs.get(0).getSeverity().isWorseThan( getLevel())) return false;
        }else {
            listOfLogs.add(null);
        }

        synchronized(queue){
            queue.addAll(listOfLogs);
            queue.notify();
        }
        return  true;
    }

    public LogRecord getElement() throws InterruptedException {

        synchronized (queue){

            while(queue.isEmpty()){
                queue.wait();
            }

            return queue.poll();
        }

    }

    public boolean writeLog(LogRecord record)  {

            try {
                handler.write(record);
            } catch (IOException e) {
                e.printStackTrace();
            }


        return true;
    }
    public  void decreaseWorkingThreads(){
        workingThreads--;
    }

    public int getWorkingThreads(){
        return  workingThreads;
    }

    /**
     * Gets level for this logger.
     * @return ErrorLevel .
     */
    public ErrorLevel getLevel() {
        return level;
    }

    /**
     * Set level for this logger, messages with lower severity won't be saved in output file.
     * @param level .
     */
    public void setLevel(ErrorLevel level) {
        this.level = level;
    }

    /**
     * Parses input string and create LogRecord intances.
     * @param stringToSplit string to be parsed.
     * @return ArrayList<LogRecord> arrays of log records.
     */
    public ArrayList<LogRecord> parseInput(String stringToSplit){
        ArrayList<LogRecord> listOfLogs = new ArrayList<>();
        String trimmedString = stringToSplit.trim().replaceAll(" +", " ");
        String[] parts = trimmedString.split(" ", 4);
        TimeStamp timeStamp = new TimeStamp(parts[0] + " " + parts[1]);
        String lotStrings = "";
        ErrorLevel errorLevel = null;

        if ( parts[3].indexOf(' ') != -1){
            String[] end = parts[3].split(" ", 2);
            //lotStrings = end [1];
            if (!ErrorLevel.contains(end[0])){
               lotStrings = parts[3];
            }else {
                lotStrings = end [1];
                errorLevel = ErrorLevel.valueOf(end[0].toUpperCase());
            }

        }else {
            lotStrings = parts[3];
        }

        String[] result = Arrays.stream(lotStrings.split(",")).map(String::trim).toArray(String[]::new);
        for (String i : result) {
           LogRecord tmp = new LogRecord(timeStamp, errorLevel,i);
           listOfLogs.add(tmp);
        }

        return listOfLogs;
    }
}
