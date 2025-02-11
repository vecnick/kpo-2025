package hse.kpo.Report;

import hse.kpo.domains.Customer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportBuilder {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private StringBuilder content = new StringBuilder();

    public Report build()
    {
        return new Report(String.format("Отчет за %s", ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
                content.toString());
    }

    public ReportBuilder addCustomers(List<Customer> customers)
    {
        content.append("Покупатели:");
        customers.forEach(customer -> content.append(String.format(" - %s", customer)));
        content.append("\n");

        return this;
    }

    public ReportBuilder addOperation(String operation)
    {
        content.append(String.format("Операция: %s", operation));
        content.append(System.lineSeparator());
        return this;
    }
}
