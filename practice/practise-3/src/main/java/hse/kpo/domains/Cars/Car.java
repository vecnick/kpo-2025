package hse.kpo.domains.Cars;

import hse.kpo.Enums.Types;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.EngineI;
import hse.kpo.interfaces.Transport;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс машины.
 */
@ToString
public class Car implements Transport {

    private EngineI engine;

    @Getter
    private int vin;

    /**
     * Конструктор из номера и двигателя.
     *
     * @param vin    - номер новой машины
     * @param engine - двигатель новой машины
     */
    public Car(int vin, EngineI engine) {
        this.vin = vin;
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

