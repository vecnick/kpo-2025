package studying.services;

import studying.domains.Customer;
import studying.interfaces.ICustomerProvider;

import java.util.ArrayList;
import java.util.List;

public class CustomerStorage implements ICustomerProvider {
    private List<Customer> customers = new ArrayList<>();

    /**
     * Метод для получения всех покупателей из списка customers.
     * 
     * @return - список покупателей
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Метод для добавления покупателя.
     * 
     * @param customer - покупатель.
     */
    public void addCustomer(Customer customer)
    {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
