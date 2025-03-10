package hse.kpo.interfaces;

import hse.kpo.domains.Customers.Customer;
import java.util.List;

/**
 * Интерфейс класса, передающего покупателей.
 */
public interface CustomerProviderI {
    /**
     * Метод, позволяющий получить список клиентов.
     *
     * @return - список клиентов;
     */
    List<Customer> getCustomers();
}
