package hse.kpo.storages;

import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CustomerProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Хранилище информации о пользователях.
 */
@Component
public class CustomerStorage implements CustomerProvider {
    private final List<Customer> customers = new ArrayList<>();

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Метод добавления покупателя в систему.
     *
     * @param customer покупатель
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
