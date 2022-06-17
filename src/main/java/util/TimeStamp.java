package util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp  {

    private Date date;
    private final SimpleDateFormat parser;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public TimeStamp(String input)  {
        parser = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = parser.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "TimeStamp: " + formatter.format(date);
    }
}
