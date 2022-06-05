package handlers;

import java.io.*;

import logger.LogRecord;

public class FileHandler extends Handler {
    protected final String fileName;
    protected FileWriter fw = null;
    protected BufferedWriter bw = null;
    protected PrintWriter out = null;
    protected String fileRoot =  "testdir/";

    public FileHandler(String fileName) { // create file

        File file = new File(fileRoot);

        // true if the directory was created, false otherwise
        if (file.mkdirs()) {
            System.out.println("Directory is created!");
        } else {
            System.out.println("Failed to create directory!");
        }
        this.fileName = fileRoot+fileName;
        try {
            fw = new FileWriter(fileRoot+fileName, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println("test");
           // out.close();
        } catch (IOException e) {
        }
    }

    public String getFileRoot() {
        return fileRoot;
    }

    public void setFileRoot(String fileRoot) {
        this.fileRoot = fileRoot;
    }

    @Override
    public void close() {

    }

    @Override
    public void write(LogRecord record) {
        out.println(record.toString());
        out.close();
    }

    @Override
    public void flush() {

    }
}
