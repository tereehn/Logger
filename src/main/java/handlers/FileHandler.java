package handlers;

import java.io.*;

import logger.LogRecord;

public class FileHandler extends Handler {
    protected final String fileName;
    protected FileWriter fw = null;
    protected BufferedWriter bw = null;
    protected PrintWriter out = null;
    protected String fileRoot =  "testdir/";
    private int maxFileSize;
    private int maxFiles;
    private int numOfFiles = 0;
    private int currentFileSize = 0;

    public FileHandler(String fileName) { // create file

        this.setFileRoot(this.fileRoot);
        this.fileName = fileRoot+fileName;
        this.openFile();
    }

    public FileHandler(String fileName, int maxFileSize) {
        this.setFileRoot(this.fileRoot);
        this.fileName = fileName;
        this.maxFileSize = maxFileSize;
        this.setCurrentFileSize();
    }

    public FileHandler(String fileName, int maxFileSize, int maxFiles) {
        this.setFileRoot(this.fileRoot);
        this.fileName = fileName;
        this.maxFileSize = maxFileSize;
        this.maxFiles = maxFiles;
        this.setCurrentFileSize();
    }

    public void openFile(){
        try {
            fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            // out.close();
        } catch (IOException e) {
        }
    }

    public void rotateFile(){

    }

    public String getFileRoot() {
        return fileRoot;
    }

    public void setFileRoot(String fileRoot) {

        this.fileRoot = fileRoot;
        File file = new File(fileRoot);

        // true if the directory was created, false otherwise
        if (file.mkdirs()) {
            System.out.println("Directory is created!");
        } else {
            System.out.println("Failed to create directory!");
        }
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public int getMaxFiles() {
        return maxFiles;
    }

    public void setMaxFiles(int maxFiles) {
        this.maxFiles = maxFiles;
    }

    public int getNumOfFiles() {
        return numOfFiles;
    }

    public void setNumOfFiles(int numOfFiles) {
        this.numOfFiles = numOfFiles;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize() {
        File file = new File(fileName);
        this.currentFileSize = (int) file.length();
    }

    @Override
    public void close() {
        out.close();
    }

    @Override
    public void write(LogRecord record) throws IOException {

        System.out.println(record.toString());
        out.println(record);
        this.close();
    }

    @Override
    public void flush() {

    }
}
