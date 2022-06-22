package util;

import java.io.IOException;
import java.util.Random;

public class Sleeper {
    private final String name;
    private final Random random;

    /**
     * Construct class object.
     * @param name tag of a thread.
     */
    public Sleeper(String name) {
        this.name = name;
        this.random = new Random();
    }

    /**
     * Puts thread to sleep for random time frame.
     */
    public void randomSleep() {
        int time = (int) Math.abs(Math.round(random.nextGaussian() * 500 + 1100));

        System.out.printf("[%s] Sleeping for %d ms...\n", name, time);
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
        }
    }
}
