package handlers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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
        this.baseName = builder.fileName;
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
        } catch (IOException e) {
        }
    }

    public void removeFile(String fileName){

    }

    public void getFormat(){
        String[] parts = baseName.split("\\.");
        this.baseName = parts[0];
    }

    public String getNewName(int num){
        return (baseName+(num + 1)) +".log";
    }

    public synchronized void renameFiles() throws IOException{

        File folder = new File(fileRoot);
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles, (a, b) -> -a.getName().compareTo(b.getName()));

        if (currentFiles[0] >= getMaxFileSize()) { // remove the last file
            removeFile(listOfFiles[0].getName());
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String tmpName = listOfFiles[i].getName();
                String numberOnly= tmpName.replaceAll("[^0-9]", "");
                int num = 0;
                if (numberOnly!="")
                    num = Integer.parseInt(numberOnly);

                Path yourFile = Paths.get(fileRoot,tmpName);
                Files.move(yourFile, yourFile.resolveSibling(getNewName(num)));

            }
        }
    }

    public synchronized void rotateFile() throws IOException {
        this.close();
        renameFiles();
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
    public synchronized void write(LogRecord record) throws IOException {
        if (getCurrentFileSize() >= getMaxFileSize()){
            this.rotateFile();
        }
        // tuto vyzujime formattor a tam posleme tu sprostu spravu a ona sa nam vrati a mozme to printerovat zapisat do tej kokotint
        out.println(record);
        setCurrentFileSize((record.toString()+"\n").getBytes().length);
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
