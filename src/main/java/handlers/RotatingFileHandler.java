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
        currentFiles = new int[]{1,0}; // num, current file size
        this.setFileRoot();
        this.getFormat();
        this.openFile();
    }

    /**
     * Tries to open given file.
     */
    public void openFile(){
        try {
            fw = new FileWriter(fileRoot+fileName, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets file root given in constructor.
     */
    public void setFileRoot(){
        File file = new File(fileRoot);
        File[] listOfFiles = file.listFiles();
        // true if the directory was created, false otherwise
        if (file.mkdirs()) {
            System.out.println("Directory is created!");
        } else {
            System.out.println("Failed to create directory!");
        }
        // clean directory in case other files are there
        if (listOfFiles!=null){
            for(File x: listOfFiles)
                if (!x.isDirectory())
                    x.delete();
        }
    }

    /**
     * @param fileName file to be removed.
     * Removes file in case max limit is reached.
     */
    public void removeFile(String fileName){
        File myObj = new File(fileRoot+fileName);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
            currentFiles[0]--;
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    public void getFormat(){
        String[] parts = baseName.split("\\.");
        this.baseName = parts[0];
    }

    /**
     * Adds corresponding name to newly created file when rotation applied.
     */
    public String createName(int num){
        return (baseName+(num )) +".log";
    }

    /**
     * Renames files in directory in case rotating is performed.
     */
    public void renameFiles() {

        File folder = new File(fileRoot);
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles, (a, b) -> -a.getName().compareTo(b.getName()));

        if (currentFiles[0] >= getMaxFiles()) { // remove the last file
            removeFile(createName(getCurrentNumberOfFiles()-1));
        }

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String tmpName = listOfFile.getName();
                String numberOnly = tmpName.replaceAll("[^0-9]", "");
                int num = 0;
                if (!numberOnly.equals(""))
                    num = Integer.parseInt(numberOnly);

                Path yourFile = Paths.get(fileRoot, tmpName);
                try {
                    Files.move(yourFile, yourFile.resolveSibling(createName(num + 1)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Rotates files in case maximum file size was reached.
     */

    public void rotateFile()  {
        this.close();
        renameFiles();
        openFile();
        currentFiles[0]++;
        currentFiles[1] = 0;
    }

    public int getMaxFiles() {
        return maxFiles;
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

    public void setCurrentFileSize(int length) {
        this.currentFiles[1]+= length;
    }

    @Override
    public void close() { out.close();
    }

    @Override
    public void write(LogRecord record) {
        if (getCurrentFileSize() >= getMaxFileSize()){
            this.rotateFile();
        }
        String logToWrite = formatter.formatData(record);
        out.println(logToWrite);
        this.flush();
        setCurrentFileSize((logToWrite+"\n").getBytes().length);
    }

    @Override
    public void flush() {
        out.flush();

    }

    /**
     * Builds RotateFileHandler and sets files attributes.
     */
    public static class FileHandlerBuilder {

        private final String fileName;
        private String fileRoot =  "testdir/";
        private int maxFileSize = 200;
        private int maxFiles = 10;

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

        /**
         * Builds rotatingFileHandler.
         */
        public RotatingFileHandler build() {
            return new RotatingFileHandler(this);
        }

    }
}
