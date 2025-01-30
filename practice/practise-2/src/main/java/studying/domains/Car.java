package studying.domains;

import lombok.Getter;
import lombok.ToString;
import studying.interfaces.IEngine;

/**
 * Машина
 */
@ToString
public class Car {
    private IEngine engine;

    @Getter
    private int VIN;

    /**
     * Конструктор
     *
     * @param VIN номер машины
     * @param engine объект двигателя
     */
    public Car(int VIN, IEngine engine) {
        this.VIN = VIN;
        this.engine = engine;
    }

    /**
     * Проверяет на совместимость покупателя с машиной
     *
     * @param customer покупатель
     * @return совместимость с машиной в виде булевого значения
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer); // внутри метода просто вызываем соответствующий метод двигателя
    }
}
