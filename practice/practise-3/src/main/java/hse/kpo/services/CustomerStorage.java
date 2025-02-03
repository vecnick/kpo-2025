package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.interfaces.ICustomerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerStorage implements ICustomerProvider {
    private List<Customer> customers = new ArrayList<>();

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer)
    {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
