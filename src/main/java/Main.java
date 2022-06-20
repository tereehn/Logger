
/**
 * Logging framework
 * provides the objects, methods, and configuration necessary
 * to create and send log messages.
 *
 * @author Tereza Ehnova
 * @version 1.0
 * @since 2022-06-17
 */

import formatters.SimpleFormatter;
import handlers.RotatingFileHandler;
import logger.Logger;
import threads.*;
import util.ErrorLevel;


public class Main {

    public static void main(String[] args) throws InterruptedException   //static method
    {

        RotatingFileHandler handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(200).build();
        Logger logger = new Logger();
        logger.setLevel(ErrorLevel.TRACE);
        logger.addHandler(handler);
        SimpleFormatter formatter = new SimpleFormatter("%{time} - %{levelname} - %{message}");
        handler.addFormatter(formatter);

        LoggingThread x1 = new LoggingThread("1", logger, 4);
        LoggingThread x2 = new LoggingThread("2", logger, 3);
        WritingThread y = new WritingThread("3", logger);

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
