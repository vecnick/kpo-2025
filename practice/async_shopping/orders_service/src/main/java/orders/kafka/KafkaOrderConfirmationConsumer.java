package orders.kafka;

import orders.interfaces.IInboxTaskOrderConfirmationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderConfirmationConsumer {

    private final IInboxTaskOrderConfirmationService inboxTaskService;

    public KafkaOrderConfirmationConsumer(IInboxTaskOrderConfirmationService inboxTaskService) {
        this.inboxTaskService = inboxTaskService;
    }

    @KafkaListener(topics = "PAYMENT_CONFIRMATION", groupId = "order-confirmation-consumer")
    public void receive(String jsonRequest) {
        inboxTaskService.confirmOrder(jsonRequest);
    }
}
