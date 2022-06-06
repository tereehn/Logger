package logger;

public class LogRecord {
    private final TimeStamp timeStamp;
    private final Severity severity;
    private final int counter;
    private final String message;

    public LogRecord(TimeStamp timeStamp, Severity severity, int counter, String message) {
        this.timeStamp = timeStamp;
        this.severity = severity;
        this.counter = counter;
        this.message = message;
    }

    public LogRecord(TimeStamp timeStamp, int counter, String message) {
        this.timeStamp = timeStamp;
        this.severity = null;
        this.counter = counter;
        this.message = message;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    public Severity getSeverity() {
        return severity;
    }

    public int getCounter() {
        return counter;
    }

    public String getMessage() {
        return message;
    }
}