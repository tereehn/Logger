package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TimeStampTest {
    @Test
    public void testTimeStamp() {
        TimeStamp test = new TimeStamp("2012/05/22 10:40:07");
        TimeStamp test1 = new TimeStamp("2000/11/23 14:10:52");
        TimeStamp test2 = new TimeStamp("1999/01/24 15:40:00");
        assertAll("address name",
                () -> assertEquals("2012-05-22",test.toString()),
                () -> assertEquals("2000-11-23",test1.toString()),
                () -> assertEquals("1999-01-24",test2.toString())
        );
    }

    @Test
    public void testTimeFormat(){
        TimeStamp test = new TimeStamp("2012/05/22 10:40:07");
        TimeStamp test1 = new TimeStamp("2000/11/23 14:10:52");
        TimeStamp test2 = new TimeStamp("1999/01/24 15:40:00");

        test.changeFormat("yyyy-MM");
        test1.changeFormat("yyyy-MM");
        test2.changeFormat("yyyy-MM");

        assertAll("address name",
                () -> assertEquals("2012-05",test.toString()),
                () -> assertEquals("2000-11",test1.toString()),
                () -> assertEquals("1999-01",test2.toString())
        );

    }

}