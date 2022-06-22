package threads;

import logger.Logger;
import util.ErrorLevel;
import util.Sleeper;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LoggingThread extends Thread{

    protected final String tag;
    protected final Random random;
    protected final Logger logger;

    protected int counter = 0;
    protected int successCounter = 0;
    public int processingCount;

    public LoggingThread(String tag, Logger logger, int processingCount) {
        this.tag = tag;
        this.random = new Random();
        this.logger = logger;
        this.processingCount = processingCount;
    }

    /**
     * Prints how many log strings were successfully written..
     */
    public void printStatistics() {
        System.out.printf("[%s]: Tried logging %d times, %d times successfully\n", tag, counter, successCounter);
    }

    /**
     * Overrides run method of Thread class and handles logging from one thread. .
     */
    @Override
    public void run() {
        Sleeper sleeper = new Sleeper(tag);
        while (processingCount > 0) {
              sleeper.randomSleep();
            if (goLogging())
                successCounter++;
            counter++;
            processingCount--;

        }
        logger.decreaseWorkingThreads();

    }

    /**
     * Overrides run method of Thread class and handles logging from one thread. .
     */
    protected boolean goLogging() {
        String randomLog = RandomMessage.getRandomMessage();
        if (logger.addLog(randomLog)) {
            if (processingCount -1 == 0 && logger.getWorkingThreads() == 1) {
                logger.addLog(null);
            }
            return true;
        }else {
            if (processingCount -1  == 0 && logger.getWorkingThreads() == 1) {
                logger.addLog(null);
            }
        }

        return false;
    }

    public int getSuccessCounter() {
        return successCounter;
    }

    /**
     * Class that creates random log message and passes it as input.
     * For demonstration purposes.
     */

    public static class RandomMessage {

        private static final List<ErrorLevel> VALUES = Collections.unmodifiableList(Arrays.asList(ErrorLevel.values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static String getRandomMessage(){
            return getRandomTime() + " " + getRandomCount() + " " +  getRandomSeverity() + " " + getRandomString();
        }

        public static String getRandomSeverity(){
            return VALUES.get(RANDOM.nextInt(SIZE)).toString();
        }

        public static String getRandomString(){
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder sb = new StringBuilder();
            int length = 7;
            for(int i = 0; i < length; i++) {

                int index = RANDOM.nextInt(alphabet.length());
                char randomChar = alphabet.charAt(index);
                sb.append(randomChar);
            }
            return sb.toString();
        }

        public static String getRandomTime(){
            Date dat = new Date(ThreadLocalRandom.current().nextInt() * 1000L);
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return format.format(dat);
        }

        public static String getRandomCount(){
            int tmp = RANDOM.nextInt();
            return  String.valueOf(tmp);
        }

    }

}

