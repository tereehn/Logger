package logger;


import util.ErrorLevel;
import util.TimeStamp;

import java.util.Objects;

public class LogRecord {
    private final TimeStamp timeStamp;
    private final ErrorLevel errorLevel;
    private final String message;

    public LogRecord(TimeStamp timeStamp, ErrorLevel errorLevel, String message) {
        this.timeStamp = timeStamp;
        this.errorLevel = errorLevel;
        this.message = message;
    }

    public LogRecord(TimeStamp timeStamp,String message) {
        this.timeStamp = timeStamp;
        this.errorLevel = null;
        this.message = message;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public ErrorLevel getSeverity() {
        return errorLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogRecord logRecord = (LogRecord) o;
        return Objects.equals(timeStamp, logRecord.timeStamp) && errorLevel == logRecord.errorLevel && Objects.equals(message, logRecord.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, errorLevel, message);
    }
}
