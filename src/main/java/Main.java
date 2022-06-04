import handlers.FileHandler;
import logger.Logger;

public class Main {

    public static void main(String args[])  //static method
    {

        FileHandler handler = new FileHandler("test");
        Logger logger = new Logger();
        logger.addHandler(handler);
        logger.addLog("2012/05/22 15:40:07 5 FINE string string");

    }
}
