package formatters;

import logger.LogRecord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class SimpleFormatter extends Formatter {

    public SimpleFormatter(String format) {
        super(format);
    }

    @Override
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
