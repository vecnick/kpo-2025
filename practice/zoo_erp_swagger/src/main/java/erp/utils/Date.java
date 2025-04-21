package erp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Date {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String getLocalDateTimeStr() {
        return LocalDateTime.now().format(FORMATTER);
    }

    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    public static boolean isDateFormat(String date) {
        try {
            LocalDateTime.parse(date, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDateTime stringToDateTime(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты: " + dateStr);
        }
    }

    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}
