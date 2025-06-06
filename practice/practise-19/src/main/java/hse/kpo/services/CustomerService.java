package hse.kpo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.domains.Customer;
import hse.kpo.dto.request.CustomerRequest;
import hse.kpo.exception.KpoException;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.kafka.CustomerAddedEvent;
import hse.kpo.kafka.KafkaProducerService;
import hse.kpo.kafka.outbox.OutboxEvent;
import hse.kpo.kafka.outbox.OutboxEventRepository;
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

    @Autowired
    private final OutboxEventRepository outboxEventRepository;

    @Autowired
    private final ObjectMapper objectMapper;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    @Override
    public void addCustomer(Customer customer) {
        var savedCustomer = customerRepository.save(customer);

        saveToOutbox(savedCustomer);
    }

    private void saveToOutbox(Customer customer) {
        CustomerAddedEvent event = createAddedEvent(customer);

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setAggregateType("Customer");
        outboxEvent.setEventType("CustomerAdded");
        outboxEvent.setCreatedAt(LocalDateTime.now());

        try {
            outboxEvent.setPayload(objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new KpoException(500, "Failed to serialize customer event");
        }

        outboxEventRepository.save(outboxEvent);
    }

    private CustomerAddedEvent createAddedEvent(Customer customer) {
        return new CustomerAddedEvent(
            customer.getId(),
            customer.getName(),
            customer.getHandPower(),
            customer.getLegPower(),
            customer.getIq()
        );
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
