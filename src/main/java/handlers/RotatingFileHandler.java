package handlers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import logger.LogRecord;

public class RotatingFileHandler extends Handler {
    protected final String fileName;
    private String baseName;
    protected FileWriter fw = null;
    protected BufferedWriter bw = null;
    protected PrintWriter out = null;
    protected String fileRoot;
    private int maxFileSize;
    private int maxFiles;
    private int currentFiles[];


    public RotatingFileHandler(FileHandlerBuilder builder) { // create file
        this.fileName = builder.fileName;
        this.fileRoot = builder.fileRoot;
        this.baseName = builder.fileRoot + builder.fileName;
        this.maxFileSize = builder.maxFileSize;
        this.maxFiles = builder.maxFiles;
        currentFiles = new int[]{0,0}; // num, current file size
        this.getFormat();
        this.openFile();
    }

    public void openFile(){
        try {
            fw = new FileWriter(fileRoot+fileName, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            // out.close();
        } catch (IOException e) {
        }
    }

    public void removeFile(int num){

    }

    public void getFormat(){
        String[] parts = baseName.split("\\.");
        this.baseName = parts[0];
    }

    public String getNewName(){
        return (currentFiles[0] + 1) +".log";
    }

    public void rotateFile() throws IOException {
        String newName = getNewName();
        this.close();
        Path yourFile = Paths.get("testdir",fileName);
        System.out.println(yourFile);
        Files.move(yourFile, yourFile.resolveSibling(newName));
        openFile();
        currentFiles[0]++;
        currentFiles[1] = 0;
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
        return currentFiles[1];
    }

    public void setCurrentFileSize(int length) {

        this.currentFiles[1]+= length;
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

    public static class FileHandlerBuilder {

        private final String fileName;
        private String fileRoot =  "testdir/";
        private int maxFileSize;
        private int maxFiles;

        public FileHandlerBuilder(String fileName) {
            this.fileName = fileName;
        }

        public FileHandlerBuilder fileRoot(String fileRoot) {
            this.fileRoot = fileRoot;
            return this;
        }

        public FileHandlerBuilder maxFileSize(int maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        public FileHandlerBuilder maxFiles(int maxFiles) {
            this.maxFiles = maxFiles;
            return this;
        }

        public RotatingFileHandler build() {
            RotatingFileHandler rotatingFileHandler =  new RotatingFileHandler(this);
            return rotatingFileHandler;
        }


    }
}
