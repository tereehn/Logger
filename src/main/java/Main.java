
/**
 * Logging framework
 * provides the objects, methods, and configuration necessary
 * to create and send log messages.
 * @author  Tereza Ehnova
 * @version 1.0
 * @since   2022-06-17
 */

import handlers.RotatingFileHandler;
import logger.Logger;
import threads.LoggingThread;
import util.ErrorLevel;

import java.io.IOException;

public class Main {

    public static void main(String []args)   //static method
    {

        RotatingFileHandler handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(200).build();
        Logger logger = new Logger();
        logger.setLevel(ErrorLevel.INFO);
        logger.addHandler(handler);
        logger.addLog("2012/05/22 15:40:07 3 INFO string string, string2, string3");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");

        //LoggingThread x1 = new LoggingThread("cus",logger);
        //LoggingThread x2 = new LoggingThread("cus",logger);
        //LoggingThread x3 = new LoggingThread("cus",logger);
       // LoggingThread x4 = new LoggingThread("cus",logger);

      /*  x1.start();
        x2.start();
        sleep(10000);
        x1.stop();
        x2.stop();
        */
        handler.close();
    }

    private static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException ex) {
        }
    }
}
