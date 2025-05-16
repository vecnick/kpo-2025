package hse.kpo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, CustomerAddedEvent> kafkaTemplate;

    public void sendCustomerToTraining(CustomerAddedEvent event) {
        kafkaTemplate.send(
            "customers",
            String.valueOf(event.customerId()),
            event
        );
    }
}
