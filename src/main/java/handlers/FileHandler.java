package handlers;

import java.io.FileWriter; // Import the FileWriter class
import java.io.File; // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import logger.LogRecord;

public class FileHandler extends Handler {
    private final String fileName;

    public FileHandler(String fileName) { // create file
        this.fileName = fileName;
        try {
            File obj = new File(fileName);
            if (obj.createNewFile()) {
                System.out.println("File created: " + obj.getName());
            } else {
                System.out.println("File already exists.");
            }

            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void write(LogRecord record) {

    }

    @Override
    public void flush() {

    }
}
