package hse.kpo.service;

import hse.kpo.domains.Customer;
import hse.kpo.kafka.CustomerAddedEvent;
import hse.kpo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TrainingConsumer {
    @Autowired
    private CustomerRepository repository;

    @KafkaListener(topics = "customers", groupId = "kpo")
    public void handleCustomerEvent(CustomerAddedEvent event) {
        // Сохраняем клиента в локальную БД тренировочного сервиса
        Customer customer= new Customer(
            event.customerId(),
            event.customerName(),
            event.handPower(),
            event.legPower(),
            event.iq()
        );
        repository.save(customer);
    }
}