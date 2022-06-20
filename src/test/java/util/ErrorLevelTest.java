package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorLevelTest {

    @Test
    void isWorseThan() {
        ErrorLevel test = ErrorLevel.DEBUG;
        ErrorLevel test1 = ErrorLevel.INFO;
        ErrorLevel test2 = ErrorLevel.DEBUG;

        assertAll("address name",
                () -> assertEquals(false,test.isWorseThan(test1)),
                () -> assertEquals(true,test1.isWorseThan(test)),
                () -> assertEquals(false,test2.isWorseThan(test1))
        );
    }
}