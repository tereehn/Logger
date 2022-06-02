import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    private Logger logger;


    @BeforeEach
    void initEach() {
        logger = new Logger("test_name");
    }

    @Nested
    class AddTest {

    }



}