package threads;

import logger.LogRecord;
import logger.Logger;


public class WritingThread extends Thread {
    protected final String tag;
    protected final Logger logger;

    public WritingThread(String tag, Logger logger) {
        this.tag = tag;
        this.logger = logger;
    }

    /**
     * Overrides run method of Thread class and handles writing to file.
     */
    @Override
    public void run() {

        while(true){

            LogRecord element = null;
            try {
                element = logger.getElement();
                if (element!=null) logger.writeLog(element);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (element == null) break;

        }
    }

}
