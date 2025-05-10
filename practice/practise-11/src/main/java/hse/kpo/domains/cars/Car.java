package hse.kpo.domains.cars;

import hse.kpo.domains.*;
import hse.kpo.enums.EngineTypes;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import hse.kpo.interfaces.Transport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс хранящий информацию о машине.
 */
@Getter
@Setter
@Entity
@Table(name = "cars")
@ToString
@NoArgsConstructor
public class Car implements Transport {

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private AbstractEngine engine;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vin;

    public Car(int vin, AbstractEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public Car(AbstractEngine engine) {
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

    public void setEngine(AbstractEngine engineFromRequest) {
    }
}
