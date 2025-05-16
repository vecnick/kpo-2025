package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Класс, реализующий {@link AbstractEngine} педального типа.
 */
@ToString
@Getter
@Entity
@DiscriminatorValue("PEDAL")
@NoArgsConstructor
public class PedalEngine extends AbstractEngine {
    private int size;

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
