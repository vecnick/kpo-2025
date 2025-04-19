package hse.kpo.domains.Ships;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hse.kpo.Enums.Types;
import hse.kpo.domains.AbstractEngine;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.EngineI;
import hse.kpo.interfaces.Transport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс катамарана.
 */
@ToString
@Entity
@Table(name = "Ship")
@JsonIgnoreProperties({"engine"})
public class Ship implements Transport {
    @Getter
    @JoinColumn(name = "engine_id")
    @OneToOne(cascade = CascadeType.ALL)
    private AbstractEngine engine;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vin;

    /**
     * Конструктор из номера и двигателя.
     *
     * @param vin    - номер новой машины
     * @param engine - двигатель новой машины
     */
    public Ship(int vin, AbstractEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public Ship(AbstractEngine engine) {
        this.engine = engine;
    }

    public Ship() {

    }

    /**
     * Метод, проверяющий, подходит ли машина покупателю.
     *
     * @param customer - пользователь, для которого проверяем машину
     * @return true, если машина совместима с пользователем
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, Types.SHIP); // внутри метода просто вызываем соответствующий метод двигателя
    }

    @Override
    public String getEngineType() {
        return engine.getClass().getSimpleName();
    }

    @Override
    public String getTransportType() {
        return Types.SHIP.toString();
    }
}
