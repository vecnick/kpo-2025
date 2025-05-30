package hse.kpo.kafka.outbox;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.kafka.CustomerAddedEvent;
import hse.kpo.kafka.KafkaProducerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OutboxProcessor {
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutboxEvents() throws JsonProcessingException {
        List<OutboxEvent> events = outboxEventRepository.findAllBySentFalseOrderByCreatedAtAsc();

        for (OutboxEvent event : events) {
            try {
                CustomerAddedEvent customerEvent = objectMapper.readValue(
                    event.getPayload(),
                    CustomerAddedEvent.class
                );

                kafkaProducerService.sendCustomerToTraining(customerEvent);
                event.setSent(true);
                outboxEventRepository.save(event);

            } catch (Exception e) {
                log.error("Failed to process outbox event ID: {}", event.getId(), e);
                throw e;
            }
        }
    }
}
