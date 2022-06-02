abstract class Handler {

    public abstract void close(); // close the Handler and free all  resources
    public abstract void write(LogRecord record); // Write a LogRecord.
    public abstract void flush(); // Write a LogRecord.
}
