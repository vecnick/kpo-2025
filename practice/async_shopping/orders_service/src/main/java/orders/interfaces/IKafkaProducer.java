package orders.interfaces;

public interface IKafkaProducer {

    boolean send(String topic, String jsonMessage);
}
