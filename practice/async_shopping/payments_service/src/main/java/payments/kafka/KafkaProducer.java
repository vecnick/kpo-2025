package payments.kafka;

import payments.interfaces.IKafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class KafkaProducer implements IKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public boolean send(String topic, String jsonMessage) {
        try {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, jsonMessage);
            future.get(5, TimeUnit.SECONDS); // ждём максимум 5 секунд подтверждения принятия kafk-ой
            return true;
        } catch (Exception e) {
            System.out.println("KafkaProducer: send - Kafka не отвечает");
            return false;
        }
    }
}
