package hse.kpo.services;

import java.util.List;
import java.util.Optional;
import hse.kpo.domains.Customer;
import hse.kpo.dto.request.CustomerRequest;
import hse.kpo.exception.KpoException;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.kafka.KafkaProducerService;
import hse.kpo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomerService implements CustomerProvider {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void addCustomer(Customer customer) {
        var savedCustomer = customerRepository.save(customer);
        kafkaProducerService.sendCustomerToTraining(customer);
    }

    @Transactional
    @Override
    public Customer updateCustomer(CustomerRequest request) {
        var customerOptional = customerRepository.findByName(request.getName());

        if (customerOptional.isPresent()) {
            var customer = customerOptional.get();
            customer.setIq(request.getIq());
            customer.setHandPower(request.getHandPower());
            customer.setLegPower(request.getLegPower());
            return customerRepository.save(customer);
        }
        throw new KpoException(HttpStatus.NOT_FOUND.value(), String.format("no customer with name: %s", request.getName()));
    }

    @Transactional
    @Override
    public boolean deleteCustomer(String name) {
        customerRepository.deleteByName(name); // Добавьте метод в CustomerRepository
        return true;
    }

    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
