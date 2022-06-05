package handlers;

import logger.LogRecord;

import java.io.File;

public class RotateFileHandler extends FileHandler {

    private int maxFileSize;
    private int maxFiles;
    private int numOfFiles = 0;
    private int currentFileSize = 0;


    public RotateFileHandler(String fileName) {
        super(fileName);

    }

    public RotateFileHandler(String fileName,int maxFileSize) {
        super(fileName);
        this.maxFileSize = maxFileSize;
    }

    public RotateFileHandler(String fileName,int maxFileSize, int maxFiles) {
        super(fileName);
        this.maxFileSize = maxFileSize;
        this.maxFiles = maxFiles;
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
        System.out.println(this.currentFileSize);
    }

    @Override
    public void write(LogRecord record) {
        System.out.println("ROTATE");
        if (this.getCurrentFileSize() >= this.getMaxFileSize()){
            // vytvorime novy subor a stare v zlozke premenujeme
        }
        this.setCurrentFileSize();
        super.write(record);
        this.setCurrentFileSize();
    }
}
