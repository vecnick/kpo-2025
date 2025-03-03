package hse.kpo.domains;

import hse.kpo.Enums.Types;
import hse.kpo.interfaces.EngineI;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс катамарана.
 */
@ToString
public class Ship {
    @Getter
    private EngineI engine;

    @Getter
    private int vin;

    /**
     * Конструктор из номера и двигателя.
     *
     * @param vin    - номер новой машины
     * @param engine - двигатель новой машины
     */
    public Ship(int vin, EngineI engine) {
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
        return this.engine.isCompatible(customer, Types.SHIP); // внутри метода просто вызываем соответствующий метод двигателя
    }
}
