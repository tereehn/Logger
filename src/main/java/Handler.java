abstract class Handler {
    protected int maxFileSize;
    public abstract void close(); // close the Handler and free all  resources
    public abstract void write(LogRecord record); // Write a LogRecord.

    public int getMaxFileSize() {
        return maxFileSize;
    }
}
