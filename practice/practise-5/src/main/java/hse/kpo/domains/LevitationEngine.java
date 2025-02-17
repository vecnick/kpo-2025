package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.IEngine;
import lombok.ToString;

/**
 * Класс, реализующий {@link IEngine} ручного типа.
 */
@ToString
public class LevitationEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return switch (type) {
            case ProductionTypes.CAR -> customer.getIq() > 300;
            case ProductionTypes.CATAMARAN -> customer.getIq() > 150;
            case null, default -> throw new RuntimeException("This type of production doesn't exist");
        };
    }
}
