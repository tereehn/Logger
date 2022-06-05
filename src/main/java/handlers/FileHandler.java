package handlers;

import java.io.*;

import logger.LogRecord;

public class FileHandler extends Handler {
    private final String fileName;
    private FileWriter fw = null;
    private BufferedWriter bw = null;
    private PrintWriter out = null;

    public FileHandler(String fileName) { // create file
        this.fileName = fileName;
        try {
            fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            //out.println("test");
           // out.close();
        } catch (IOException e) {
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void write(LogRecord record) {
        System.out.println();
        out.println(record.toString());
        out.close();
    }

    @Override
    public void flush() {

    }
}
