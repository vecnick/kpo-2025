package studying.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс покупателя
 * имеет ФИО (name), силу ног (legPower), силу рук (handPower), интеллект (IQ)
 * имеет привязку к своей машине (car)
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
     * Конструктор для создания покупателя, задающегося параметрами
     *
     * @param name - ФИО
     * @param legPower - сила ног
     * @param handPower - сила рук
     * @param IQ - интеллект
     */
    public Customer(String name, int legPower, int handPower, int IQ) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.IQ = IQ;
    }
}
