package formatters;

import logger.LogRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleFormatter {

    protected String timeFormat;
    protected String format;
    protected final String defaultFormat = "%{time} - %{levelname} - %{message}";
    protected final String fieldStart = "\\%\\{";
    protected final String fieldEnd = "\\}";

    protected final String regex = fieldStart + "([^}]+)" + fieldEnd;
    protected final Pattern pattern = Pattern.compile(regex);

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public SimpleFormatter(String format) {
        this.format = format;
    }
    /**
     * @param record log string to format.
     * Formats data into desired form given on input.
     * */
    public String formatData(LogRecord record) {
        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("time", record.getTimeStamp().toString());
        valuesMap.put("levelname", record.getSeverity().toString());
        valuesMap.put("message", record.getMessage());

        Matcher m = pattern.matcher(format);
        String result = format;
        while (m.find()) {
            String[] found = m.group(1).split("\\.");
            String newVal = valuesMap.get(found[0]);
            result = result.replaceFirst(regex, newVal);
        }

        return result;
    }
}
