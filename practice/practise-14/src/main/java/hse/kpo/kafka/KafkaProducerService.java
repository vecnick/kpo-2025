package hse.kpo.kafka;

import hse.kpo.domains.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, CustomerAddedEvent> kafkaTemplate;

    public void sendCustomerToTraining(Customer customer) {
        CustomerAddedEvent event = new CustomerAddedEvent(
            customer.getId(),
            customer.getName(),
            customer.getHandPower(),
            customer.getLegPower(),
            customer.getIq()
        );
        kafkaTemplate.send("customers", String.valueOf(customer.getId()), event);
    }
}
