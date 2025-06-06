package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * Класс автомобиля
 * имеет двигатель (engine) и порядковый номер производства (VIN)
 */
@ToString
public class Car {
    private IEngine engine;

    @Getter
    private int VIN;

    /**
     * Конструктор для создания объекта автомобиля
     *
     * @param VIN - порядковый номер производства
     * @param engine - двигатель автомобиля
     */
    public Car(int VIN, IEngine engine) {
        this.VIN = VIN;
        this.engine = engine;
    }

    /**
     * Определение совместимости покупателя с двигателем в данном автомобиле с покупателем
     *
     * @param customer - покупатель, с которым мы сравнимаем двигатель
     * @return true, если двигатель автомобиля совместим с покупателем, иначе false.
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer);
    }
}
