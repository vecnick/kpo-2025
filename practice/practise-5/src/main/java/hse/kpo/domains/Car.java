package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.Getter;
import lombok.ToString;

/**
 * Машина.
 */
@ToString
public class Car {
    private IEngine engine;

    @Getter
    private int vin;

    /**
     * Конструктор.
     *
     * @param vin номер машины
     * @param engine объект двигателя
     */
    public Car(int vin, IEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    /**
     * Проверяет на совместимость покупателя с машиной.
     *
     * @param customer покупатель
     * @return совместимость с машиной в виде булевого значения
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer); // внутри метода просто вызываем соответствующий метод двигателя
    }
}
