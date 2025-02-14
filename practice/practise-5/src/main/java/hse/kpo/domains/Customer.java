package hse.kpo.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс, описывающий покупателя.
 */
@Getter
@ToString
@Builder
public class Customer {
    private final String name;

    private final int legPower;

    private final int handPower;

    private final int iq;

    @Setter
    private Car car;

    public Customer(String name, int legPower, int handPower, int iq) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }
}
