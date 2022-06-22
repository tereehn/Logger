package handlers;

import formatters.SimpleFormatter;
import logger.LogRecord;
import logger.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ErrorLevel;
import util.TimeStamp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class RotatingFileHandlerTest {

    private Logger logger;
    private RotatingFileHandler handler;

    @BeforeEach
    void initEach() throws InterruptedException {
        logger = new Logger();
        handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(100).maxFiles(2).build();
        logger.addHandler(handler);
        SimpleFormatter formatter = new SimpleFormatter("%{time} - %{levelname} - %{message}");
        handler.addFormatter(formatter);
    }

    @Test
    void TestOpenFile() {
        handler.openFile();
        File file = new File("testdir/test.log");
        assertTrue(file.exists());
    }

    @Test
    void TestRemoveFile() {
        LogRecord resultTest11 = new LogRecord(new TimeStamp("2012/05/21 13:40:07"), ErrorLevel.DEBUG,"string string");
        LogRecord resultTest12 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.TRACE,"strfing string");
        LogRecord resultTest13 = new LogRecord(new TimeStamp("2013/05/22 15:40:07"),ErrorLevel.INFO,"strfding2");
        LogRecord resultTest14 = new LogRecord(new TimeStamp("2012/05/21 13:40:07"), ErrorLevel.DEBUG,"string string");
        LogRecord resultTest15 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.TRACE,"strfing string");
        LogRecord resultTest16 = new LogRecord(new TimeStamp("2012/05/21 13:40:07"), ErrorLevel.DEBUG,"string string");
        LogRecord resultTest17 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.TRACE,"strfing string");

        handler.write(resultTest11);
        handler.write(resultTest12);
        handler.write(resultTest13);
        handler.write(resultTest14);
        handler.write(resultTest15);
        handler.write(resultTest16);
        handler.write(resultTest17);
        handler.write(resultTest16);
        handler.write(resultTest17);
        assertEquals(2,handler.getCurrentNumberOfFiles());

    }

    @Test
    void TestCreateDir() {
        handler.setFileRoot();
        File file = new File("testdir");
        assertTrue(file.exists());
    }

    @Test
    void TestCreateName() {
        int testNum = 3;
        String testName = handler.createName(testNum);
        assertEquals("test3.log",testName);
    }

    @Test
    void TestRotateFile() {
        LogRecord resultTest11 = new LogRecord(new TimeStamp("2012/05/21 13:40:07"), ErrorLevel.DEBUG,"string string");
        LogRecord resultTest12 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.TRACE,"strfing string");
        LogRecord resultTest13 = new LogRecord(new TimeStamp("2013/05/22 15:40:07"),ErrorLevel.INFO,"strfding2");
        LogRecord resultTest14 = new LogRecord(new TimeStamp("2012/05/21 13:40:07"), ErrorLevel.DEBUG,"string string");
        LogRecord resultTest15 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.TRACE,"strfing string");

        handler.write(resultTest11);
        handler.write(resultTest12);
        handler.write(resultTest13);
        handler.write(resultTest14);
        handler.write(resultTest15);

        assertEquals(handler.getCurrentNumberOfFiles(),2);
        File file1 = new File("testdir/test.log");
        File file2 = new File("testdir/test1.log");
        assertTrue(file1.exists());
        assertTrue(file2.exists());
    }

    @Test
    void write() throws IOException {
        LogRecord resultTest1 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"), ErrorLevel.INFO,"string string");
        handler.write(resultTest1);
        handler.close();

       BufferedReader br = new BufferedReader(new FileReader("testdir/test.log"));
       StringBuilder sb = new StringBuilder();
       String line = br.readLine();

       while (line != null) {
           sb.append(line);
           sb.append(System.lineSeparator());
           line = br.readLine();
       }
       assertEquals("2012-05-22 - INFO - string string",sb.toString().trim());
    }

    @Test
    void flush() {
    }

    @AfterEach
    void closeHandler(){
        handler.close();
    }

    @AfterEach
    void deleteFileIfExists(){
        File file = new File("/testdir");
        File[] listOfFiles = file.listFiles();
        for(File x: listOfFiles)
            if (!x.isDirectory())
                x.delete();
    }
}