package hse.kpo.services;

import java.util.List;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService implements CustomerProvider {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean updateCustomer(Customer updatedCustomer) {
        if (customerRepository.existsById(updatedCustomer.getId())) {
            customerRepository.save(updatedCustomer);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String name) {
        customerRepository.deleteByName(name); // Добавьте метод в CustomerRepository
        return true;
    }
}