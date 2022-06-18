

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
    /**
     * Default constructor.
     */
    public Logger() {

    }

    public void addHandler(Handler newHandler){
        this.handler = newHandler;
    }

    public boolean addLog(String input){
        ArrayList<LogRecord> listOfLogs = parseInput(input);
        if (listOfLogs.get(0).getSeverity() != null && !listOfLogs.get(0).getSeverity().isWorseThan( getLevel())) return false;
        synchronized(this){
            queue.addAll(listOfLogs);
            this.notifyAll();
        }
        return  true;
    }



    /**
     * Tries to log messages to a file.
     * @param input strings to process.
     * @return boolean return true if writing was successfully, false otherwise.
     * @exception IOException On input error.
     * @see IOException
     */
    public boolean writeLog(String input)  {

        LogRecord logRecord = queue.poll();

      /* listOfLogs.forEach(x -> {
            try {
                handler.write(x);
                System.out.println(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/

        return true;
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
        int counter = Integer. parseInt(parts[2]);
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
