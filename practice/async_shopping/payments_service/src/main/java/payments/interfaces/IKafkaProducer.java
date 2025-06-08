package payments.interfaces;

public interface IKafkaProducer {

    boolean send(String topic, String jsonMessage);
}
