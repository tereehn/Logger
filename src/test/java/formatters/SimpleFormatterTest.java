package formatters;

import handlers.RotatingFileHandler;
import logger.LogRecord;
import logger.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ErrorLevel;
import util.TimeStamp;

import static org.junit.jupiter.api.Assertions.*;

class SimpleFormatterTest {

    private SimpleFormatter formatter;

    @BeforeEach
    void initEach() {
        String s = "%{time} - %{levelname} - %{message}";
        formatter = new SimpleFormatter(s);

    }

    @Test
    void TestFormatData() {
        LogRecord resultTest11 = new LogRecord(new TimeStamp("2012/05/21 13:40:07"), ErrorLevel.DEBUG,"string string");
        LogRecord resultTest12 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.TRACE,"strfing string");
        LogRecord resultTest13 = new LogRecord(new TimeStamp("2013/05/22 15:40:07"),ErrorLevel.INFO,"strfding2");
        assertAll(
                () -> assertEquals("2012-05-21 - DEBUG - string string",formatter.formatData(resultTest11)),
                () -> assertEquals("2012-05-22 - TRACE - strfing string",formatter.formatData(resultTest12)),
                () -> assertEquals("2013-05-22 - INFO - strfding2",formatter.formatData(resultTest13))
        );
    }
}