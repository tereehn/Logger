
/**
 * Logging framework
 * provides the objects, methods, and configuration necessary
 * to create and send log messages.
 * @author  Tereza Ehnova
 * @version 1.0
 * @since   2022-06-17
 */

import formatters.SimpleFormatter;
import handlers.RotatingFileHandler;
import logger.LogRecord;
import logger.Logger;
import threads.LoggingThread;
import threads.WritingThread;
import util.ErrorLevel;
import util.TimeStamp;

import java.io.IOException;

public class Main {

    public static void main(String []args) throws InterruptedException   //static method
    {

        RotatingFileHandler handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(200).build();
        Logger logger = new Logger();
        logger.setLevel(ErrorLevel.INFO);
        logger.addHandler(handler);
        SimpleFormatter formatter = new SimpleFormatter("${time} - ${levelname} - ${message}");
        handler.addFormatter(formatter);

        LoggingThread x1 = new LoggingThread("cus",logger,4);
        LoggingThread x2 = new LoggingThread("cus",logger,3);
        WritingThread y = new WritingThread("cau",logger);
        //LoggingThread x3 = new LoggingThread("cus",logger);
       // LoggingThread x4 = new LoggingThread("cus",logger);

        x1.start();
        x2.start();
        y.start();
        x1.join();
        x2.join();
        y.join();
     //   sleep(10000);


        handler.close();
    }

    private static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException ex) {
        }
    }
}
