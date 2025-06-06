package storing.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileDateUtil {
    private static final String DATE_PATTERN = "yyyyMMdd_HHmmss_SSS";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String getLocalDateTimeStr() {
        return LocalDateTime.now().format(FORMATTER);
    }
}