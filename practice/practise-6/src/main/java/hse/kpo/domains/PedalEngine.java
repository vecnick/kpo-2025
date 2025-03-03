package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс, реализующий {@link Engine} педального типа.
 */
@ToString
@Getter
public class PedalEngine implements Engine {
    private final int size;

    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return switch (type) {
            case ProductionTypes.CAR -> customer.getLegPower() > 5;
            case ProductionTypes.CATAMARAN -> customer.getLegPower() > 2;
            case null, default -> throw new RuntimeException("This type of production doesn't exist");
        };
    }

    public PedalEngine(int size) {
        this.size = size;
    }
}
