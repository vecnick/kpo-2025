package hse.kpo.domains.Cars;

import hse.kpo.Enums.Types;
import hse.kpo.domains.AbstractEngine;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.EngineI;
import hse.kpo.interfaces.Transport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс машины.
 */

@Getter
@Setter
@Entity
@Table(name = "cars")
@NoArgsConstructor
@ToString
public class Car implements Transport {

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private AbstractEngine engine;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vin;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * Конструктор из номера и двигателя.
     *
     * @param vin    - номер новой машины
     * @param engine - двигатель новой машины
     */
    public Car(int vin, AbstractEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public Car(AbstractEngine engine) {
        this.engine = engine;
    }

    /**
     * Метод, проверяющий, подходит ли машина покупателю.
     *
     * @param customer - пользователь, для которого проверяем машину
     * @return true, если машина совместима с пользователем
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, Types.CAR); // внутри метода просто вызываем соответствующий метод двигателя
    }

    @Override
    public String getEngineType() {
        return engine.getClass().getSimpleName();
    }

    @Override
    public String getTransportType() {
        return Types.CAR.toString();
    }
}

