package hse.kpo.kafka.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.kafka.CustomerAddedEvent;
import hse.kpo.kafka.KafkaProducerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxProcessor {
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void processEvents() throws JsonProcessingException {
        var events = outboxEventRepository.findAllBySentFalseOrderByCreatedAtAsc(); // получить события, которые не были отпралвены

        for (OutboxEvent event : events) {
            handleEvent(event); // отправляем одно событие
        }
    }

    @Transactional
    protected void handleEvent(OutboxEvent event) throws JsonProcessingException {
        CustomerAddedEvent customerEvent = objectMapper.readValue(event.getPayload(), CustomerAddedEvent.class);

        kafkaProducerService.sendCustomerToTraining(customerEvent); // отправляем в Kafka
        event.setSent(true); // помечаем как "обработанное"
        outboxEventRepository.save(event); // сохраняем изменение в базу
    }

}
