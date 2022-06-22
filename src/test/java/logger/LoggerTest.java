package logger;

import formatters.SimpleFormatter;
import handlers.RotatingFileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import util.ErrorLevel;
import util.TimeStamp;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    private Logger logger;

    @BeforeEach
    void initEach() throws InterruptedException {
        logger = new Logger();
        RotatingFileHandler handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(200).build();
        SimpleFormatter formatter = new SimpleFormatter("%{time} - %{levelname} - %{message}");
        logger.addHandler(handler);
        handler.addFormatter(formatter);
    }

    @Test
    @DisplayName("Set error level of messages")
    void testSettingLevel() {
        // if not set then level should be the lowest one possible
        assertEquals(ErrorLevel.TRACE, logger.getLevel());
        logger.setLevel(ErrorLevel.INFO);
        assertEquals(ErrorLevel.INFO, logger.getLevel());
    }

    @Test
    @DisplayName("Input parsing")
    void testInputParsing() {
        String testInput1 = "2012/05/22 15:40:07 3 INFO string string, string2, string3";
        String testInput2 = "2012/05/22 15:40:07 3 INFO string string";
        LogRecord resultTest21 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.INFO,"string string");
        LogRecord resultTest11 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.INFO,"string string");
        LogRecord resultTest12 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.INFO,"string2");
        LogRecord resultTest13 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.INFO,"string3");
        ArrayList<LogRecord> arrayTest1 = new ArrayList<>();
        arrayTest1.add(resultTest11);
        arrayTest1.add(resultTest12);
        arrayTest1.add(resultTest13);

        ArrayList<LogRecord> arrayTest2 = new ArrayList<>();
        arrayTest2.add(resultTest21);

        ArrayList<LogRecord> finalArrayTest1=logger.parseInput(testInput1);
        assertArrayEquals(arrayTest1.toArray(), finalArrayTest1.toArray());

        ArrayList<LogRecord> finalArrayTest2=logger.parseInput(testInput2);
        assertArrayEquals(arrayTest2.toArray(), finalArrayTest2.toArray());

    }

    @Test
    @DisplayName("Input parsing, no severity")
    void testInputParsingWithoutSeverity() {
        String testInput1 = "2012/05/22 15:40:07 3 string string, string2, string3";
        String testInput2 = "2012/05/22 15:40:07 3  string string";
        LogRecord resultTest21 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),null,"string string");
        LogRecord resultTest11 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),null,"string string");
        LogRecord resultTest12 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),null,"string2");
        LogRecord resultTest13 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),null,"string3");
        ArrayList<LogRecord> arrayTest1 = new ArrayList<>();
        arrayTest1.add(resultTest11);
        arrayTest1.add(resultTest12);
        arrayTest1.add(resultTest13);

        ArrayList<LogRecord> finalArrayTest1=logger.parseInput(testInput1);
        assertArrayEquals(arrayTest1.toArray(), finalArrayTest1.toArray());
        ArrayList<LogRecord> arrayTest2 = new ArrayList<>();
        arrayTest2.add(resultTest21);
        ArrayList<LogRecord> finalArrayTest2=logger.parseInput(testInput2);
        assertArrayEquals(arrayTest2.toArray(), finalArrayTest2.toArray());
    }


    @Test
    @DisplayName("Logging to file")
    void testWriteLog() {
        LogRecord Test1 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.TRACE,"string string");
        LogRecord Test2 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.INFO,"string string");
        LogRecord Test3 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.DEBUG,"string2");
        LogRecord Test4 = new LogRecord(new TimeStamp("2012/05/22 15:40:07"),ErrorLevel.FATAL,"string3");

        assertAll(
                () -> assertEquals(true,logger.writeLog(Test1)),
                () -> assertEquals(true,logger.writeLog(Test2)),
                () -> assertEquals(true,logger.writeLog(Test3)),
                () -> assertEquals(true,logger.writeLog(Test4))
        );
    }

    @Test
    @DisplayName("Logging to file with given severity")
    void testAddLogLowSeverity() {
        logger.setLevel(ErrorLevel.INFO);
        String testInput1 = "2012/05/22 15:40:07 3 INFO string string, string2, string3";
        String testInput2 = "2012/05/22 15:40:07 3 INFO string string";
        String testInput3= "2012/05/22 15:40:07 3 TRACE string string";
        String testInput4= "2012/05/22 15:40:07 3 DEBUG string string";
        String testInput5= "2012/05/22 15:40:07 3 FATAL string string";
        String testInput6= "2012/05/22 15:40:07 3  string string"; // no severity is given, assume its ok


        assertAll(
                () -> assertEquals(true,logger.addLog(testInput1)),
                () -> assertEquals(true,logger.addLog(testInput2)),
                () -> assertEquals(false,logger.addLog(testInput3)),
                () -> assertEquals(false,logger.addLog(testInput4)),
                () -> assertEquals(true,logger.addLog(testInput5)),
                () -> assertEquals(true,logger.addLog(testInput6))
        );
    }

}