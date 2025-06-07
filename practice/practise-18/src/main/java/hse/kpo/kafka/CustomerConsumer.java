package hse.kpo.kafka;

import hse.kpo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerConsumer {
    @Autowired
    private CustomerService customerService;

    @KafkaListener(topics = "training-updates", groupId = "kpo")
    public void handleTrainingUpdate(TrainingCompletedEvent event) {
        var customerOptional = customerService.findById(event.customerId());

        if (customerOptional.isEmpty()) {
            return;
        }

        var customer = customerOptional.get();
        switch (event.updatedParameter()) {
            case "handPower" -> customer.setHandPower(customer.getHandPower() + 1);
            case "legPower" -> customer.setLegPower(customer.getLegPower() + 1);
            case "iq" -> customer.setIq(customer.getIq() + 1);
            default -> {
                return;
            }
        }
        customerService.save(customer); // Сохраняем в БД
    }
}
