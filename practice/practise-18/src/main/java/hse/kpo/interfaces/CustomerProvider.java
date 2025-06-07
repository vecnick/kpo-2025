package hse.kpo.interfaces;

import hse.kpo.domains.Customer;
import hse.kpo.dto.request.CustomerRequest;

import java.util.List;

/**
 * Интерфейс для определения методов хранилища покупателей.
 */
public interface CustomerProvider {
    /**
     * Метод возвращает коллекцию покупателей.
     *
     * @return список {@link Customer}
     */
    List<Customer> getCustomers();

    void addCustomer(Customer customer);

    Customer updateCustomer(CustomerRequest request);

    boolean deleteCustomer(String name);
}
