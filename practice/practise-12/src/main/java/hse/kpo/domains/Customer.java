package hse.kpo.domains;

import hse.kpo.domains.cars.Car;
import hse.kpo.domains.catamarans.Catamaran;
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

    @Setter
    private Catamaran catamaran;
}
