package util;


import util.Months;

public class TimeStamp  {

    private final int year;
    private final Months month;
    private final int day;
    private final int hour;
    private final int minute;
    private final int seconds;

    public TimeStamp(int year, Months month, int day, int hour, int minute, int seconds) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;
    }

    public Months getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "TimeStamp{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", seconds=" + seconds +
                '}';
    }
}
