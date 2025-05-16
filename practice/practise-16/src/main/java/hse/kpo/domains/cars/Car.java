package hse.kpo.domains.cars;

import hse.kpo.domains.AbstractEngine;
import hse.kpo.domains.Customer;
import hse.kpo.domains.HandEngine;
import hse.kpo.domains.LevitationEngine;
import hse.kpo.domains.PedalEngine;
import hse.kpo.enums.EngineTypes;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import hse.kpo.interfaces.Transport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private AbstractEngine engine;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vin;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Ссылка на владельца

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
}
