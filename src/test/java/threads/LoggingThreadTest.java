package threads;

import formatters.SimpleFormatter;
import handlers.RotatingFileHandler;
import logger.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ErrorLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class LoggingThreadTest {

    private Logger logger;
    private RotatingFileHandler handler;

    @BeforeEach
    void initEach() {
        logger = new Logger();
        handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(300).maxFiles(2).build();
        logger.addHandler(handler);
        SimpleFormatter formatter = new SimpleFormatter("%{time} - %{levelname} - %{message}");
        handler.addFormatter(formatter);
    }

    @Test
    void testWriteThreads() throws InterruptedException {
        logger.setLevel(ErrorLevel.TRACE);
        LoggingThread x1 = new LoggingThread("1", logger, 4);
        LoggingThread x2 = new LoggingThread("2", logger, 3);
        WritingThread y = new WritingThread("3", logger);
        x1.start();
        x2.start();
        y.start();
        x1.join();
        x2.join();
        y.join();
        assertEquals(4, x1.getSuccessCounter());
        assertEquals(3, x2.getSuccessCounter());
    }

    @Test
    void testFileContentThreads() throws InterruptedException {
        logger.setLevel(ErrorLevel.TRACE);
        LoggingThread x1 = new LoggingThread("1", logger, 4);
        LoggingThread x2 = new LoggingThread("2", logger, 3);
        WritingThread y = new WritingThread("3", logger);
        x1.start();
        x2.start();
        y.start();
        x1.join();
        x2.join();
        y.join();
        handler.close();

        Path path = Paths.get("testdir/test.log");

        long lines = 0;
        try {

            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(7, lines);
    }

    @Test
    void testNumberOfWorkingThreads() throws InterruptedException {
        LoggingThread x1 = new LoggingThread("1", logger, 4);
        LoggingThread x2 = new LoggingThread("2", logger, 3);
        WritingThread y = new WritingThread("3", logger);
        x1.start();
        x2.start();
        y.start();
        x1.join();
        x2.join();
        y.join();

        assertEquals(0, logger.getWorkingThreads());
    }

    @AfterEach
    void closeHandler(){
        handler.close();
    }
}