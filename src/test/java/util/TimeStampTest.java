package util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class TimeStampTest {
    @Test
    public void testTimeStamp() {
        TimeStamp test = new TimeStamp("2012/05/22 10:40:07");
        TimeStamp test1 = new TimeStamp("2000/11/23 14:10:52");
        TimeStamp test2 = new TimeStamp("1999/01/24 15:40:00");
        assertAll("address name",
                () -> assertEquals("TimeStamp: 2012-05-22",test.toString()),
                () -> assertEquals("TimeStamp: 2000-11-23",test1.toString()),
                () -> assertEquals("TimeStamp: 1999-01-24",test2.toString())
        );
    }

}