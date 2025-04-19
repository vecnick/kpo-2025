package hse.kpo.services;

import hse.kpo.Repository.CustomerRepository;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.CustomerProviderI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Класс, предоставляющий покупателей.
 */
@Component
@RequiredArgsConstructor
public class CustomerStorageI implements CustomerProviderI {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public boolean updateCustomer(Customer updatedCustomer) {
        if (customerRepository.existsById(updatedCustomer.getId())) {
            customerRepository.save(updatedCustomer);
            return true;
        }
        return false;
    }

    public boolean deleteCustomer(String name) {
        customerRepository.deleteByName(name); // Добавьте метод в CustomerRepository
        return true;
    }
}
