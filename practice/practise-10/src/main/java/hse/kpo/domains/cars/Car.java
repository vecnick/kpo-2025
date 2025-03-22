package hse.kpo.domains.cars;

import hse.kpo.domains.Customer;
import hse.kpo.domains.HandEngine;
import hse.kpo.domains.LevitationEngine;
import hse.kpo.domains.PedalEngine;
import hse.kpo.enums.EngineTypes;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import hse.kpo.interfaces.Transport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Класс хранящий информацию о машине.
 */
@ToString
@NoArgsConstructor
public class Car implements Transport {

    @Getter
    private Engine engine;

    @Getter
    private int vin;

    public Car(int vin, Engine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public String getEngineType() {
        if (engine instanceof HandEngine) {
            return EngineTypes.HAND.name();
        }
        if (engine instanceof PedalEngine) {
            return EngineTypes.PEDAL.name();
        }
        if (engine instanceof LevitationEngine) {
            return EngineTypes.LEVITATION.name();
        };
        throw new RuntimeException("Where is engine???");
    }

    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, ProductionTypes.CAR);
    }

    @Override
    public String getTransportType() {
        return ProductionTypes.CAR.name();
    }
}
