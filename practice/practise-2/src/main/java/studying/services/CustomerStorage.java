package studying.services;

import studying.domains.Customer;
import studying.interfaces.ICustomerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс хранилище покупателей
 * хранит коллекцию покупателей (customers)
 */
public class CustomerStorage implements ICustomerProvider {
    private List<Customer> customers = new ArrayList<>();

    /**
     * Получить список покупателей из коллекции класса
     *
     * @return список покупателей
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Добавить покупателя в коллекцию класса
     *
     * @param customer - покупатель которого добавить
     */
    public void addCustomer(Customer customer)
    {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
