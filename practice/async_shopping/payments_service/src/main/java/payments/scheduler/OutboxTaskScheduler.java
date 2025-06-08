package payments.scheduler;

import lombok.extern.slf4j.Slf4j;
import payments.entity.OutboxTask;
import payments.enums.DelayedTaskStatus;
import payments.interfaces.IKafkaProducer;
import payments.interfaces.IOutboxTaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class OutboxTaskScheduler {

    private final IOutboxTaskService outboxTaskService;
    private final IKafkaProducer kafkaProducer;

    public OutboxTaskScheduler(IOutboxTaskService outboxTaskService, IKafkaProducer kafkaProducer) {
        this.outboxTaskService = outboxTaskService;
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(fixedRate = 5000) // каждые 5 секунд
    public void sendNewTasks() {
        Optional<List<OutboxTask>> outboxTasks = outboxTaskService.getOutboxTasksByStatus(DelayedTaskStatus.NEW);
        if (outboxTasks.isEmpty()) {
            System.out.println("OutboxTaskScheduler: sendNewTasks: не удалось получить список задач");
            return;
        }

        for (OutboxTask task : outboxTasks.get()) {
            try {
                String topic = task.getTaskType().toString();
                kafkaProducer.send(topic, task.getRequestPayload()); // отправляем запрос kafka и ожидаем принятия сообщения;
                outboxTaskService.setStatusById(task.getId(), DelayedTaskStatus.SENT);
            } catch (Exception e) {
                System.out.println("OutboxTaskScheduler: sendNewTasks: не удалось отправить запрос с id=" + task.getId() + " в kafka");
            }
        }
    }
}
