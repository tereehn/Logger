package logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ErrorLevel;
import util.TimeStamp;

import static org.junit.jupiter.api.Assertions.*;

class LogRecordTest {
    private TimeStamp timeStamp;
    @BeforeEach
    void initEach() {
        timeStamp = new TimeStamp("2012/05/22 10:40:07");
    }

    @Test
    void testSeverityIfGiven() {
        LogRecord logRecord = new LogRecord(timeStamp,ErrorLevel.ERROR,"text test");
        assertEquals(ErrorLevel.ERROR,logRecord.getSeverity());
    }

    @Test
    void testSeverityIfNotGiven() {
        LogRecord logRecord = new LogRecord(timeStamp,"text test");
        assertNull(logRecord.getSeverity());
    }
}