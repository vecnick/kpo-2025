package hse.kpo.domains;

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

    private final int IQ;

    @Setter
    private Car car;

    /**
     * Конструктор
     *
     * @param name имя
     * @param legPower сила ног
     * @param handPower сила рук
     * @param IQ интеллект
     */
    public Customer(String name, int legPower, int handPower, int IQ) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.IQ = IQ;
    }
}
