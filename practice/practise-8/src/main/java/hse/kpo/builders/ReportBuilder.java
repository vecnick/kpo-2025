package hse.kpo.builders;

import hse.kpo.domains.Customer;
import hse.kpo.domains.Report;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс для составления отчета о работе системы.
 */
public class ReportBuilder {

    /**
     * Пример форматированной даты - 2025-02-10.
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private StringBuilder content = new StringBuilder();

    /**
     * Метод для добавления информации о покупателях в отчет.
     *
     * @param customers покупатели
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addCustomers(List<Customer> customers) {
        content.append("Покупатели:");
        customers.forEach(customer -> content.append(String.format(" - %s", customer)));
        content.append("\n");

        return this;
    }

    /**
     * Метод для добавления информации о действиях в системе в отчет.
     *
     * @param operation операция в системе
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addOperation(String operation) {
        content.append(String.format("Операция: %s", operation));
        content.append(System.lineSeparator());
        return this;
    }

    /**
     * Метод получения итогового отчета о системе.
     *
     * @return {@link Report} отчет о системе
     */
    public Report build() {
        return new Report(String.format("Отчет за %s", ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
                content.toString());
    }
}
