package formatters;

import logger.LogRecord;

import java.util.regex.Pattern;

abstract class Formatter {

    protected String timeFormat;
    protected final String format;
    protected final String defaultFormat = "${time} - ${levelname} - ${message}";
    protected final String fieldStart = "\\$\\{";
    protected final String fieldEnd = "\\}";

    protected final String regex = fieldStart + "([^}]+)" + fieldEnd;
    protected final Pattern pattern = Pattern.compile(regex);

    protected Formatter(String format) {
        this.format = format;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public abstract String formatData(LogRecord record);

}
