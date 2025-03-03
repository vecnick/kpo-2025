package hse.kpo.domains;

/**
 * Отчет о работе системы.
 *
 * @param title название отчета
 * @param content наполнение отчета
 */
public record Report(String title, String content) {
    @Override
    public String toString() {
        return String.format("%s%n%n%s", title, content);
    }
}
