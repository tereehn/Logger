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
    private final int maxFileSize;
    private final int maxFiles;
    private final int[] currentFiles;


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
            e.printStackTrace();
        }

    }

    public void removeFile(String fileName){
        File myObj = new File(fileRoot+fileName);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    public void getFormat(){
        String[] parts = baseName.split("\\.");
        this.baseName = parts[0];
    }

    public String createName(int num){
        return (baseName+(num )) +".log";
    }

    public synchronized void renameFiles() {

        File folder = new File(fileRoot);
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles, (a, b) -> -a.getName().compareTo(b.getName()));

        if (currentFiles[0] >= getMaxFileSize()) { // remove the last file
            removeFile(createName(getCurrentNumberOfFiles()));
        }

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String tmpName = listOfFile.getName();
                String numberOnly = tmpName.replaceAll("[^0-9]", "");
                int num = 0;
                if (numberOnly != "")
                    num = Integer.parseInt(numberOnly);

                System.out.println(tmpName);
                System.out.println("CISLO: " + num);
                Path yourFile = Paths.get(fileRoot, tmpName);
                try {
                    Files.move(yourFile, yourFile.resolveSibling(createName(num + 1)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public synchronized void rotateFile()  {
        this.close();
        renameFiles();
        openFile();
        currentFiles[0]++;
        currentFiles[1] = 0;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public int getCurrentFileSize() {
        return currentFiles[1];
    }

    public int getCurrentNumberOfFiles() {
        return currentFiles[0];
    }

    public synchronized void setCurrentFileSize(int length) {
        this.currentFiles[1]+= length;
    }

    @Override
    public void close() { out.close(); }

    @Override
    public synchronized void write(LogRecord record) {
        if (getCurrentFileSize() >= getMaxFileSize()){
            this.rotateFile();
        }

        out.println(record);
        System.out.println(currentFiles[0]);
        setCurrentFileSize((record.toString()+"\n").getBytes().length);
    }

    @Override
    public void flush() {
        out.flush();
    }

    /**
     * Buils RotateFileHandler and sets files attributes.
     */
    public static class FileHandlerBuilder {

        private final String fileName;
        private String fileRoot =  "testdir/";
        private int maxFileSize;
        private int maxFiles = 10;

        public FileHandlerBuilder(String fileName) {
            this.fileName = fileName;
        }

        public FileHandlerBuilder fileRoot(String fileRoot) {
            this.fileRoot = fileRoot;
            File file = new File(fileRoot);

            // true if the directory was created, false otherwise
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
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

        /**
         * Builds rotatingFileHandler.
         */
        public RotatingFileHandler build() {
            return new RotatingFileHandler(this);
        }

    }
}
