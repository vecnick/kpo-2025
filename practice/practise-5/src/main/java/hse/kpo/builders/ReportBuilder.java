package hse.kpo.builders;

import hse.kpo.domains.Customer;

public class ReportBuilder {
    private String content;

    public ReportBuilder addCustomers(List<Customer> customers)
    {
        content.append("Покупатели:");
        customers.forEach(customer -> content.append(String.format(" - %s", customer)));
        content.append("\n");

        return this;
    }
}
