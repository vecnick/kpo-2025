package hse.kpo.Report;

import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.domains.Reports.Report;
import hse.kpo.domains.Ships.Ship;

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

    public ReportBuilder addCars(List<Car> cars) {
        content.append("Машины: ");
        cars.forEach(car -> content.append(String.format(" - %s", car)));
        content.append("\n");
        return this;
    }

    public ReportBuilder addShips(List<Ship> ships) {
        content.append("Корабли: ");
        ships.forEach(ship -> content.append(String.format(" - %s", ship)));
        content.append("\n");
        return this;
    }
}
