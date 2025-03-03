package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс хранящий информацию о машине.
 */
@ToString
public class Car {

    private Engine engine;

    @Getter
    private int vin;

    public Car(int vin, Engine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, ProductionTypes.CAR);
    }
}
