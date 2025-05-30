package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Класс, реализующий {@link AbstractEngine} ручного типа.
 */
@NoArgsConstructor
@ToString
@Entity
@DiscriminatorValue("LEVITATION")
public class LevitationEngine extends AbstractEngine {
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return switch (type) {
            case ProductionTypes.CAR -> customer.getIq() > 300;
            case ProductionTypes.CATAMARAN -> customer.getIq() > 150;
            case null, default -> throw new RuntimeException("This type of production doesn't exist");
        };
    }
}
