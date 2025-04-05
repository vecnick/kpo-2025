package hse.kpo.interfaces;

import hse.kpo.domains.Customer;
import hse.kpo.storages.CustomerStorage;

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

    boolean updateCustomer(Customer updatedCustomer);

    boolean deleteCustomer(String name);
}
