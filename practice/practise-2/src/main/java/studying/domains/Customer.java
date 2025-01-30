package studying.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Покупатель
 */
@Getter
@ToString
public class Customer {
    private final String name;

    private final int legPower;

    private final int handPower;

    @Setter
    private Car car;

    /**
     * Конструктор
     *
     * @param name имя
     * @param legPower сила ног
     * @param handPower сила рук
     */
    public Customer(String name, int legPower, int handPower) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
    }
}
