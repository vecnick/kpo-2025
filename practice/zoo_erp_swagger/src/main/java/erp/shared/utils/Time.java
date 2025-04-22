package erp.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Time {
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static boolean isTimeFormat(String time) {
        try {
            LocalDateTime.parse(time, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
