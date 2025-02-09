package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CustomerProviderI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * Класс, предоставляющий покупателей.
 */
@Component
@RequiredArgsConstructor
public class CustomerStorageI implements CustomerProviderI {
    private List<Customer> customers = new ArrayList<>();

    /**
     * Метод, возвращающий список покупателей.
     *
     * @return список покупателей
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Метод, добавляющий покупателя.
     *
     * @param customer - новый покупатель
     */
    public void addCustomer(Customer customer) {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
