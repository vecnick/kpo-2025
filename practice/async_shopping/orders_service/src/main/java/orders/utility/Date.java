package orders.utility;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {
    private static final String DATE_PATTERN = "dd.MM.yyyy HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String getLocalDateTimeStr() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
