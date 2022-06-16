import handlers.RotatingFileHandler;
import logger.Logger;

import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException  //static method
    {

        RotatingFileHandler handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(200).build();
        Logger logger = new Logger();
        logger.addHandler(handler);
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");
        logger.addLog("2012/05/22 15:40:07 5 INFO string string");

        handler.close();

    }
}
