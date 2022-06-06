package handlers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import logger.LogRecord;

public class RotatingFileHandler extends Handler {
    protected final String fileName;
    private String baseName;
    private String ending;
    protected FileWriter fw = null;
    protected BufferedWriter bw = null;
    protected PrintWriter out = null;
    protected String fileRoot =  "testdir/";
    private int maxFileSize;
    private int maxFiles;
    private int numOfFiles = 0;
    private int currentFileSize = 0;

    public RotatingFileHandler(String fileName) { // create file
        this.fileName = fileRoot+fileName;
        this.baseName = fileName;
        config();
        this.getFormat();
    }

    public RotatingFileHandler(String fileName, int maxFileSize ) {
        this.fileName = fileRoot+fileName;
        this.baseName = fileName;
        this.maxFileSize = maxFileSize;
        config();
        this.getFormat();
    }

    public RotatingFileHandler(String fileName, int maxFileSize, int maxFiles) {
        this.fileName = fileRoot+fileName;
        this.baseName = fileName;
        this.maxFileSize = maxFileSize;
        this.maxFiles = maxFiles;
        config();
        this.getFormat();
    }

    public void config(){
        this.setFileRoot(this.fileRoot);
        this.openFile();
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

    public void getFormat(){
        String[] parts = baseName.split("\\.");
        this.baseName = parts[0];
        this.ending = parts[1];
    }

    public String getNewName(){
        return baseName + (numOfFiles + 1) +".log";
    }

    public void rotateFile() throws IOException {
        String newName = getNewName();
        this.close();
        Path yourFile = Paths.get(fileName);
        Files.move(yourFile, yourFile.resolveSibling(newName));
        openFile();
        numOfFiles++;
        currentFileSize = 0;
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

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int length) {

        this.currentFileSize+= length;
    }

    @Override
    public void close() {
        out.close();
    }

    @Override
    public void write(LogRecord record) throws IOException {
       // openFile();
        System.out.println(getCurrentFileSize());
        if (getCurrentFileSize() >= getMaxFileSize()){
            this.rotateFile();
        }
        out.println(record);
        setCurrentFileSize((record.toString()+"\n").getBytes().length);
        //this.close();
    }

    @Override
    public void flush() {

    }
}
