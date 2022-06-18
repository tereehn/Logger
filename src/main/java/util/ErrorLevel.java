package util;

/**
 * Specifies possible levels of messages.
 */
public enum ErrorLevel {
    TRACE(0),DEBUG(1),INFO(2),WARN(3),ERROR(4),FATAL(5);

    private Integer severity;

    ErrorLevel(int severity) {
        this.severity = severity;
    }

    /**
     * Compares severity of two levels.
     * @param other to compare with.
     * @return boolean .
     */
    public boolean isWorseThan(ErrorLevel other) {
        return this.severity >= other.severity;
    }

    /**
     * Check whether input string defines some level.
     * @param test to search.
     * @return boolean if such a value exists.
     */
    public static boolean contains(String test) {

        for (ErrorLevel c : ErrorLevel.values()) {
            if (c.name().equals(test.toUpperCase())) {
                return true;
            }
        }

        return false;
    }
}
