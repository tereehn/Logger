package handlers;

import java.io.IOException;
import java.util.Formatter;

import formatters.SimpleFormatter;
import logger.LogRecord;

public abstract class Handler {

    protected SimpleFormatter formatter;
    public abstract void close(); // close the handlers.Handler and free all  resources
    public abstract void write(LogRecord record) throws IOException; // Write a logger.LogRecord.
    public abstract void flush(); // Write a logger.LogRecord.

    public void addFormatter(SimpleFormatter formatter){
        this.formatter = formatter;
    }

}
