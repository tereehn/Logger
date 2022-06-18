package util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class TimeStamp  {

    private Date date;
    private final SimpleDateFormat parser;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Parses string into Date format.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeStamp timeStamp = (TimeStamp) o;
        return Objects.equals(date, timeStamp.date) && Objects.equals(parser, timeStamp.parser) && Objects.equals(formatter, timeStamp.formatter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, parser, formatter);
    }
}
