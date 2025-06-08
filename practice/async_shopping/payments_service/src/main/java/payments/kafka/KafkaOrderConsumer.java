package payments.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import payments.enums.DelayedTaskType;
import payments.interfaces.IInboxTaskService;

@Component
public class KafkaOrderConsumer {

    private final IInboxTaskService inboxTaskService;

    public KafkaOrderConsumer(IInboxTaskService inboxTaskService) {
        this.inboxTaskService = inboxTaskService;
    }

    @KafkaListener(topics = "PAYMENT", groupId = "order-consumer")
    public void receive(String jsonRequest) {
        if (inboxTaskService.createInboxTask(jsonRequest, DelayedTaskType.PAYMENT).isEmpty()) {
            throw new RuntimeException();
        }
    }
}
