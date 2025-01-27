package studying;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, предоставляющий покупателей
 */
public class CustomerStorage implements ICustomerProvider{
    private List<Customer> customers = new ArrayList<>();

    /**
     * Метод, возвращающий список покупателей
     * @return список покупателей
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Метод, добавляющий покупателя
     * @param customer - новый покупатель
     */
    public void addCustomer(Customer customer)
    {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
